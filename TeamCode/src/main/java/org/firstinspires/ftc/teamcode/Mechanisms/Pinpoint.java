package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

public class Pinpoint {
    MecDrivebase driver = new MecDrivebase();

    private final double ODOM_INCHES_PER_COUNT   = 0.002969;
    private final boolean INVERT_DRIVE_ODOMETRY  = true;
    private final boolean INVERT_STRAFE_ODOMETRY = true;

    private static final double DRIVE_GAIN          = 0.03;
    private static final double DRIVE_ACCEL         = 2.0;
    private static final double DRIVE_TOLERANCE     = 0.5;
    private static final double DRIVE_DEADBAND      = 0.2;
    private static final double DRIVE_MAX_AUTO      = 0.6;

    private static final double STRAFE_GAIN         = 0.03;
    private static final double STRAFE_ACCEL        = 1.5;
    private static final double STRAFE_TOLERANCE    = 0.5;
    private static final double STRAFE_DEADBAND     = 0.2;
    private static final double STRAFE_MAX_AUTO     = 0.6;

    private static final double YAW_GAIN            = 0.018;
    private static final double YAW_ACCEL           = 3.0;
    private static final double YAW_TOLERANCE       = 1.0;
    private static final double YAW_DEADBAND        = 0.25;
    private static final double YAW_MAX_AUTO        = 0.6;

    public double driveDistance     = 0;
    public double strafeDistance    = 0;
    public double heading           = 0;

    public ProportionalControl driveController     = new ProportionalControl(DRIVE_GAIN, DRIVE_ACCEL, DRIVE_MAX_AUTO, DRIVE_TOLERANCE, DRIVE_DEADBAND, false);
    public ProportionalControl strafeController    = new ProportionalControl(STRAFE_GAIN, STRAFE_ACCEL, STRAFE_MAX_AUTO, STRAFE_TOLERANCE, STRAFE_DEADBAND, false);
    public ProportionalControl yawController       = new ProportionalControl(YAW_GAIN, YAW_ACCEL, YAW_MAX_AUTO, YAW_TOLERANCE, YAW_DEADBAND, true);

    private DcMotor driveEncoder;
    private DcMotor strafeEncoder;

    private LinearOpMode myOpMode;
    private ElapsedTime holdTimer = new ElapsedTime();

    private int rawDriveOdometer    = 0;
    private int driveOdometerOffset = 7;
    private int rawStrafeOdometer   = 0;
    private int strafeOdometerOffset= 4;
    private double rawHeading       = 0;
    private double headingOffset    = 0;

    private double turnRate           = 0;
    private boolean showTelemetry     = false;

    public Pinpoint(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    public void init(boolean showTelemetry) {
        driver.frontLeft  = myOpMode.hardwareMap.get(DcMotor.class, "frontLeft");
        driver.backLeft   = myOpMode.hardwareMap.get(DcMotor.class, "backLeft");
        driver.frontRight = myOpMode.hardwareMap.get(DcMotor.class, "frontRight");
        driver.backRight  = myOpMode.hardwareMap.get(DcMotor.class, "backRight");

        driver.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driver.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driver.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driver.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driver.frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        driver.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        driver.imu = myOpMode.hardwareMap.get(IMU.class, "imu");

        driveEncoder = myOpMode.hardwareMap.get(DcMotor.class, "axialEncoder");
        strafeEncoder = myOpMode.hardwareMap.get(DcMotor.class, "lateralEncoder");

        List<LynxModule> allHubs = myOpMode.hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        RevHubOrientationOnRobot orientationOnRobot =
                new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP);
        driver.imu.initialize(new IMU.Parameters(orientationOnRobot));

        resetOdometry();
        this.showTelemetry = showTelemetry;
    }

    public boolean readSensors() {
        rawDriveOdometer = driveEncoder.getCurrentPosition() * (INVERT_DRIVE_ODOMETRY ? -1 : 1);
        rawStrafeOdometer = strafeEncoder.getCurrentPosition() * (INVERT_STRAFE_ODOMETRY ? -1 : 1);
        driveDistance = (rawDriveOdometer - driveOdometerOffset) * ODOM_INCHES_PER_COUNT;
        strafeDistance = (rawStrafeOdometer - strafeOdometerOffset) * ODOM_INCHES_PER_COUNT;

        YawPitchRollAngles orientation = driver.imu.getRobotYawPitchRollAngles();
        AngularVelocity angularVelocity = driver.imu.getRobotAngularVelocity(AngleUnit.DEGREES);

        rawHeading  = orientation.getYaw(AngleUnit.DEGREES);
        heading     = rawHeading - headingOffset;
        turnRate    = angularVelocity.zRotationRate;

        if (showTelemetry) {
            myOpMode.telemetry.addData("Odom Ax:Lat", "%6d %6d", rawDriveOdometer - driveOdometerOffset, rawStrafeOdometer - strafeOdometerOffset);
            myOpMode.telemetry.addData("Dist Ax:Lat", "%5.2f %5.2f", driveDistance, strafeDistance);
            myOpMode.telemetry.addData("Head Deg:Rate", "%5.2f %5.2f", heading, turnRate);
        }
        return true;
    }

    // Fixed version of drive()
    public void drive(double distanceInches, double power, double holdTime) {
        resetOdometry();

        driveController.reset(distanceInches, power);
        strafeController.reset(0);
        yawController.reset(0); // disable heading correction
        holdTimer.reset();

        while (myOpMode.opModeIsActive() && readSensors()) {
            moveRobot(
                    driveController.getOutput(driveDistance),
                    strafeController.getOutput(strafeDistance),
                    0
            );

            if (driveController.inPosition()) {
                if (holdTimer.time() > holdTime) {
                    break;
                }
            } else {
                holdTimer.reset();
            }

            myOpMode.sleep(10);
        }
        stopRobot(); // stop once after loop
    }

    public void strafe(double distanceInches, double power, double holdTime) {
        resetOdometry();

        driveController.reset(0.0);
        strafeController.reset(distanceInches, power);
        yawController.reset(0);
        holdTimer.reset();

        while (myOpMode.opModeIsActive() && readSensors()) {
            moveRobot(
                    driveController.getOutput(driveDistance),
                    strafeController.getOutput(strafeDistance),
                    0
            );

            if (strafeController.inPosition()) {
                if (holdTimer.time() > holdTime) {
                    break;
                }
            } else {
                holdTimer.reset();
            }
            myOpMode.sleep(10);
        }
        stopRobot();
    }

    public void turnTo(double headingDeg, double power, double holdTime) {
        yawController.reset(headingDeg, power);
        holdTimer.reset();

        while (myOpMode.opModeIsActive() && readSensors()) {
            moveRobot(0, 0, yawController.getOutput(heading));

            if (yawController.inPosition()) {
                if (holdTimer.time() > holdTime) {
                    break;
                }
            } else {
                holdTimer.reset();
            }

            myOpMode.sleep(10);
        }
        stopRobot();
    }

    public void moveRobot(double drive, double strafe, double yaw) {
        double lF = drive - strafe - yaw;
        double rF = drive + strafe + yaw;
        double lB = drive + strafe - yaw;
        double rB = drive - strafe + yaw;

        double max = Math.max(Math.abs(lF), Math.max(Math.abs(rF), Math.max(Math.abs(lB), Math.abs(rB))));
        if (max > 1.0) {
            lF /= max; rF /= max; lB /= max; rB /= max;
        }

        driver.frontLeft.setPower(lF);
        driver.frontRight.setPower(rF);
        driver.backLeft.setPower(lB);
        driver.backRight.setPower(rB);

        if (showTelemetry) {
            myOpMode.telemetry.addData("Axes D:S:Y", "%5.2f %5.2f %5.2f", drive, strafe, yaw);
            myOpMode.telemetry.addData("Wheels lf:rf:lb:rb", "%5.2f %5.2f %5.2f %5.2f", lF, rF, lB, rB);
            myOpMode.telemetry.update();
        }
    }

    public void stopRobot() {
        moveRobot(0, 0, 0);
    }

    public void resetOdometry() {
        readSensors();
        driveOdometerOffset = rawDriveOdometer;
        driveDistance = 0.0;
        driveController.reset(0);

        strafeOdometerOffset = rawStrafeOdometer;
        strafeDistance = 0.0;
        strafeController.reset(0);
    }

    public void resetHeading() {
        readSensors();
        headingOffset = rawHeading;
        yawController.reset(0);
        heading = 0;
    }

    public double getHeading() { return heading; }
    public double getTurnRate() { return turnRate; }

    public void showTelemetry(boolean show) {
        showTelemetry = show;
    }
}

// =============================== ProportionalControl class ===============================

class ProportionalControl {
    double lastOutput;
    double gain;
    double accelLimit;
    double defaultOutputLimit;
    double liveOutputLimit;
    double setPoint;
    double tolerance;
    double deadband;
    boolean circular;
    boolean inPosition;
    ElapsedTime cycleTime = new ElapsedTime();

    public ProportionalControl(double gain, double accelLimit, double outputLimit, double tolerance, double deadband, boolean circular) {
        this.gain = gain;
        this.accelLimit = accelLimit;
        this.defaultOutputLimit = outputLimit;
        this.liveOutputLimit = outputLimit;
        this.tolerance = tolerance;
        this.deadband = deadband;
        this.circular = circular;
        reset(0.0);
    }

    public double getOutput(double input) {
        double error = setPoint - input;
        double dV = cycleTime.seconds() * accelLimit;
        double output;

        if (circular) {
            while (error > 180)  error -= 360;
            while (error <= -180) error += 360;
        }

        inPosition = (Math.abs(error) < tolerance);

        if (Math.abs(error) <= deadband) {
            output = 0;
        } else {
            output = Range.clip(error * gain, -liveOutputLimit, liveOutputLimit);

            if ((output - lastOutput) > dV) {
                output = lastOutput + dV;
            } else if ((output - lastOutput) < -dV) {
                output = lastOutput - dV;
            }
        }

        lastOutput = output;
        cycleTime.reset();
        return output;
    }

    public boolean inPosition() {
        return inPosition;
    }

    public double getSetpoint() { return setPoint; }

    public void reset(double setPoint, double powerLimit) {
        liveOutputLimit = Math.abs(powerLimit);
        this.setPoint = setPoint;
        reset();
    }

    public void reset(double setPoint) {
        liveOutputLimit = defaultOutputLimit;
        this.setPoint = setPoint;
        reset();
    }

    public void reset() {
        cycleTime.reset();
        inPosition = false;
        lastOutput = 0.0;
    }
}
