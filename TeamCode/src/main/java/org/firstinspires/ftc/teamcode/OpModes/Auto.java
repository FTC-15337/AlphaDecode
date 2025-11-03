package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

import java.nio.channels.FileChannel;
import java.util.List;

@Autonomous(name = "Autonomous")
public class Auto extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();
    public GoBildaPinpointDriver pinpoint;
    public Limelight3A limelight;
    StorageConfig sorter = new StorageConfig();
    ServoKick kick = new ServoKick();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.init(hardwareMap);

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class , "odo");

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        sorter.init(hardwareMap);
        limelight.pipelineSwitch(0);
        limelight.start();

        pinpoint.setOffsets(110, 170, MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.REVERSED,
                GoBildaPinpointDriver.EncoderDirection.REVERSED
        );
        pinpoint.resetPosAndIMU();

        telemetry.addLine("Initialized");
        sorter.setOutA();
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Example test sequence:
            driveToPos(100, 0);
        }
        limelight.stop();
    }

    private void gyroTurnToAngle(double turnAngle) {
        double error, currentHeadingAngle, drivesPower;
        drive.imu.resetYaw();

        error = turnAngle;

        while (opModeIsActive() && ((error > 1) || (error < -1))) {
            pinpoint.update();
            telemetry.addData("X: ", pinpoint.getPosX(DistanceUnit.MM));
            telemetry.addData("Y: ", pinpoint.getPosY(MM));
            telemetry.addData("Heading pinpoint: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.RADIANS)));
            telemetry.addData("Heading IMU: ", drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();

            drivesPower = error / 50;

            if ((drivesPower < 0.2) && (drivesPower > 0)) {
                drivesPower = 0.2;
            } else if ((drivesPower > -0.2) && (drivesPower < 0)) {
                drivesPower = -0.2;
            }

            if ((drivesPower < 0.35) && (drivesPower > 0)) {
                drivesPower = 0.35;
            } else if ((drivesPower > -0.35) && (drivesPower < 0)) {
                drivesPower = -0.35;
            }
            // Positive power causes left turn
            drive.frontLeft.setPower(-drivesPower);
            drive.backLeft.setPower(-drivesPower);
            drive.frontRight.setPower(drivesPower);
            drive.backRight.setPower(drivesPower);

            currentHeadingAngle = drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            error = turnAngle - currentHeadingAngle;
        }
        drive.frontLeft.setPower(0);
        drive.backLeft.setPower(0);
        drive.frontRight.setPower(0);
        drive.backRight.setPower(0);
    }
    private void driveToPos(double targetX, double targetY) {
        pinpoint.update();
        boolean telemAdded = false;

        while (opModeIsActive() && ((Math.abs(targetX - pinpoint.getPosX(MM)) > 50)
                || (Math.abs(targetY - pinpoint.getPosY(MM))) > 50)) {
            pinpoint.update();

            double x = 0.001*(targetX - pinpoint.getPosX(MM));
            double y = -0.001*(targetY - pinpoint.getPosY(MM));

            double botHeading = pinpoint.getHeading(AngleUnit.RADIANS);

            double rotY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);
            double rotX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);

            if (!telemAdded) {
                telemetry.addData("x: ", x);
                telemetry.addData("y: ", y);
                telemetry.addData("rotX: ", rotX);
                telemetry.addData("rotY: ", rotY);
                telemetry.update();
                telemAdded = true;
            }

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

            telemetry.addData("X: ", pinpoint.getPosX(MM));
            telemetry.addData("Y: ", pinpoint.getPosY(MM));
            telemetry.addData("Heading pinpoint: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.RADIANS)));
            telemetry.addData("Heading IMU: ", drive.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }

        drive.frontLeft.setPower(0);
        drive.backLeft.setPower(0);
        drive.frontRight.setPower(0);
        drive.backRight.setPower(0);
    }
}
    