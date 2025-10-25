package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

@Autonomous(name = "AutoPinpointStraight")
public class TestAuto extends LinearOpMode {

    private MecDrivebase mecDrivebase = new MecDrivebase();
    private GoBildaPinpointDriver pinpoint;
    private ElapsedTime timer = new ElapsedTime();

    // Control constants
    private final double kP = 0.002;        // Proportional gain
    private final double powerScale = 0.45; // Max speed
    private final double distanceThresholdMM = 30; // Stop distance

    @Override
    public void runOpMode() {

        // --- Initialize drivebase and odometry ---
        mecDrivebase.init(hardwareMap);

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setOffsets(101.6, 177.8, DistanceUnit.MM); // adjust for your robot
        pinpoint.initialize();
        pinpoint.resetPosAndIMU();

        telemetry.addLine("Init Complete");
        telemetry.update();

        waitForStart();

        // Example autonomous sequence
        driveToTarget(500, 0);   // Move 500 mm forward
        sleep(500);  // Move 100 mm forward

        telemetry.addLine("Reached Target");
        telemetry.update();
        sleep(1000);
    }

    /**
     * Drives robot to target (X,Y) in millimeters.
     * Stops when close enough.
     */
    private void driveToTarget(double targetX, double targetY) {
        boolean reached = false;
        timer.reset();

        while (opModeIsActive() && !reached) {
            reached = driveToPos(targetX, targetY);
            sleep(10); // Small delay to prevent busy loop
        }

        stopDrive();
    }

    /**
     * Computes and applies mecanum wheel powers to drive to a target point.
     * Returns true when within distance threshold.
     */
    private boolean driveToPos(double targetX, double targetY) {
        pinpoint.update();

        double currentX = pinpoint.getPosX(DistanceUnit.MM);
        double currentY = pinpoint.getPosY(DistanceUnit.MM);

        double dx = targetX - currentX;
        double dy = targetY - currentY;
        double distance = Math.hypot(dx, dy);

        if (distance <= distanceThresholdMM) {
            stopDrive();
            return true;
        }

        // Compute field-relative velocities
        double x = kP * dx;
        double y = -kP * dy; // adjust for your setup

        // Rotate velocities to robot coordinates
        double botHeading = mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS);
        double rotX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);
        double rotY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);

        // Apply a small minimum power to overcome friction
        double minPower = 0.15;
        if (Math.abs(rotX) < minPower) rotX = Math.signum(rotX) * minPower;
        if (Math.abs(rotY) < minPower) rotY = Math.signum(rotY) * minPower;

        // Mecanum drive computation
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX), 1.0);
        double frontLeftPower  = (rotY + rotX) / denominator;
        double backLeftPower   = (rotY - rotX) / denominator;
        double frontRightPower = (rotY - rotX) / denominator;
        double backRightPower  = (rotY + rotX) / denominator;

        // Apply power scaling
        mecDrivebase.frontLeft.setPower(frontLeftPower * powerScale);
        mecDrivebase.backLeft.setPower(backLeftPower * powerScale);
        mecDrivebase.frontRight.setPower(frontRightPower * powerScale);
        mecDrivebase.backRight.setPower(backRightPower * powerScale);

        // Telemetry for debugging
        telemetry.addData("Target X (mm)", targetX);
        telemetry.addData("Current X (mm)", currentX);
        telemetry.addData("Distance (mm)", distance);
        telemetry.addData("Heading IMU (deg)", mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES));
        telemetry.update();

        return false;
    }

    /**
     * Stops all drive motors.
     */
    private void stopDrive() {
        mecDrivebase.frontLeft.setPower(0);
        mecDrivebase.backLeft.setPower(0);
        mecDrivebase.frontRight.setPower(0);
        mecDrivebase.backRight.setPower(0);
    }
}
