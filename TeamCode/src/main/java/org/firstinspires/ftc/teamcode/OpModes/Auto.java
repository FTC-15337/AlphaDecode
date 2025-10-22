package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.CM;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;


public class Auto extends LinearOpMode {
    MecDrivebase mecDrivebase = new MecDrivebase();
    GoBildaPinpointDriver pinpoint;

    @Override
    public void runOpMode(){

        mecDrivebase.init(hardwareMap);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class , "pinpoint");
        pinpoint.setOffsets(10.1,17.5,DistanceUnit.CM);

        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        pinpoint.setPosition(new Pose2D(CM, 0, 0, AngleUnit.DEGREES, 0));

        pinpoint.resetPosAndIMU();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){

            driveToPos(100,0);


        }

    }
    public void driveToPos(double targetX, double targetY) {
        pinpoint.update();

        if (opModeIsActive() &&
                (Math.abs(targetX - pinpoint.getPosX(CM)) > 3 || Math.abs(targetY - pinpoint.getPosY(CM)) > 3)
        ){
            pinpoint.update();

            double x = 0.001*(targetX - pinpoint.getPosX(CM));
            double y = -0.001*(targetY - pinpoint.getPosY(CM));

            double botHeading = mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double rotY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);
            double rotX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);

            if (Math.abs(rotX) < 0.15) {
                rotX = Math.signum(rotX) * 0.15;
            }

            if (Math.abs(rotY) < 0.15) {
                rotY = Math.signum(rotY) * 0.15;
            }

            double denominator = Math.max(Math.abs(y) + Math.abs(x), 1);
            double frontLeftPower = (rotX + rotY)
                    / denominator;
            double backLeftPower = (rotX - rotY) / denominator;
            double frontRightPower = (rotX - rotY) / denominator;
            double backRightPower = (rotX + rotY) / denominator;

            mecDrivebase.frontLeft.setPower(frontLeftPower);
            mecDrivebase.backLeft.setPower(backLeftPower);
            mecDrivebase.frontRight.setPower(frontRightPower);
            mecDrivebase.backRight.setPower(backRightPower);

            telemetry.addData("X: ", pinpoint.getPosX(CM));
            telemetry.addData("Y: ", pinpoint.getPosY(CM));
            telemetry.addData("Heading Odo: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.DEGREES)));
            telemetry.addData("Heading IMU: ", mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        }else {

            mecDrivebase.frontLeft.setPower(0);
            mecDrivebase.backLeft.setPower(0);
            mecDrivebase.frontRight.setPower(0);
            mecDrivebase.backRight.setPower(0);
        }
    }
}