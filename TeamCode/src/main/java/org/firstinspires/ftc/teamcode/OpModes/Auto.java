package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

import java.util.List;


@Autonomous(name = "Autonomous")
public class Auto extends LinearOpMode{

    MecDrivebase drive = new MecDrivebase();
    public GoBildaPinpointDriver pinpoint;
    public Limelight3A limelight;


    @Override
    public void runOpMode() throws InterruptedException {
        drive.init(hardwareMap);

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class , "odo");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();

        pinpoint.setOffsets(130, 215, DistanceUnit.MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pinpoint.resetPosAndIMU();



        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            LimeLightScan();

            driveToPos(100,0);
            turnToAngle(90);

            telemetry.update();
        }
        limelight.stop();
    }
    public void turnToAngle(double turnAngle) {
        double error, currentHeadingAngle, driveMotorsPower;
        drive.imu.resetYaw();

        error = turnAngle;

        while (opModeIsActive() && ((error > 1) || (error < -1))) {
            pinpoint.update();
            telemetry.addData("X: ", pinpoint.getPosX(DistanceUnit.MM));
            telemetry.addData("Y: ", pinpoint.getPosY(DistanceUnit.MM));
            telemetry.addData("Heading pinpoint: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.DEGREES)));
            telemetry.addData("Heading IMU: ", drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();

            driveMotorsPower = error / 50;

            if ((driveMotorsPower < 0.2) && (driveMotorsPower > 0)) {
                driveMotorsPower = 0.2;
            } else if ((driveMotorsPower > -0.2) && (driveMotorsPower < 0)) {
                driveMotorsPower = -0.2;
            }

            if ((driveMotorsPower < 0.35) && (driveMotorsPower > 0)) {
                driveMotorsPower = 0.35;
            } else if ((driveMotorsPower > -0.35) && (driveMotorsPower < 0)) {
                driveMotorsPower = -0.35;
            }
            // Positive power causes left turn
            drive.frontLeft.setPower(-driveMotorsPower);
            drive.backLeft.setPower(-driveMotorsPower);
            drive.frontRight.setPower(driveMotorsPower);
            drive.backRight.setPower(driveMotorsPower);

            currentHeadingAngle = drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            error = turnAngle - currentHeadingAngle;
        }
        drive.frontLeft.setPower(0);
        drive.backLeft.setPower(0);
        drive.frontRight.setPower(0);
        drive.backRight.setPower(0);
    }
    public void driveToPos(double targetX, double targetY) {
        pinpoint.update();

        while (opModeIsActive() && ((Math.abs(targetX - pinpoint.getPosX(DistanceUnit.MM)) > 50)
                || (Math.abs(targetY - pinpoint.getPosY(DistanceUnit.MM))) > 50)) {
            pinpoint.update();

            double x = 0.001*(targetX - pinpoint.getPosX(DistanceUnit.MM));
            double y = -0.001*(targetY - pinpoint.getPosY(DistanceUnit.MM));

            double botHeading = pinpoint.getHeading(AngleUnit.DEGREES);

            double rotY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);
            double rotX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);

            if (Math.abs(rotX) < 0.15) {
                rotX = Math.signum(rotX) * 0.15;
            }

            if (Math.abs(rotY) < 0.15) {
                rotY = Math.signum(rotY) * 0.15;
            }

            double denominator = Math.max(Math.abs(y) + Math.abs(x), 1);
            double frontLeftPower = (rotX + rotY) / denominator;
            double backLeftPower = (rotX - rotY) / denominator;
            double frontRightPower = (rotX - rotY) / denominator;
            double backRightPower = (rotX + rotY) / denominator;

            drive.frontLeft.setPower(frontLeftPower);
            drive.backLeft.setPower(backLeftPower);
            drive.frontRight.setPower(frontRightPower);
            drive.backRight.setPower(backRightPower);

                telemetry.addData("X: ", pinpoint.getPosX(DistanceUnit.MM));
                telemetry.addData("Y: ", pinpoint.getPosY(DistanceUnit.MM));
                telemetry.addData("Heading pinpoint: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.DEGREES)));
                telemetry.addData("Heading IMU: ", drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
                telemetry.update();
        }

        drive.frontLeft.setPower(0);
        drive.backLeft.setPower(0);
        drive.frontRight.setPower(0);
        drive.backRight.setPower(0);
    }

    public void LimeLightScan() {
        while (opModeIsActive()) {

            LLResult result = limelight.getLatestResult();
            if (result.isValid()) {

                telemetry.addData("tx", result.getTx());
                telemetry.addData("ta" , result.getTa());
                telemetry.addData("ty", result.getTy());

                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                }
            } else {
                telemetry.addData("Limelight", "No data available");
            }
            telemetry.update();
        }
        limelight.stop();
    }
}