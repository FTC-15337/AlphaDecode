package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.INCH;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

@Autonomous
public class Auto extends LinearOpMode {
    MecDrivebase mecDrivebase = new MecDrivebase();
    GoBildaPinpointDriver pinpoint;
    ElapsedTime timer = new ElapsedTime();

    //WHEN USING PINPOINT PLEASE MAKE A METHOD WHERE YOU WRITE THE DRIVE AND ALIGN
    //CODE SO IT CAN BE REUSED AND IS EASILY ACCESSIBLE
    //See driveAndAlignToTest(); as an example
    //If it is just a pinpoint value like driving forward and then it aligns
    //via limelight keep the limelight align method separate and call it within
    //the pinpoint method
    //Limelight methods will need to be called using an instance of the limelight
    //class as we are making it a separate class for organization (not made yet)
    //Ex:
    //public void driveToGoal(){
    //call driveToPos method with correct values
    //call limelight align method (could be replaced with autoAlign method and
    //timer based if (also in driveAndAlignToTest)
    //}

    @Override
    public void runOpMode() {

        mecDrivebase.init(hardwareMap);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.initialize();
        pinpoint.setOffsets(101.6, 177.8, DistanceUnit.MM);

        //Pinpoint starting pos for tracking
        telemetry.addLine("Init Active");
        telemetry.addData("Starting y: ", pinpoint.getPosY(MM));
        telemetry.addData("Starting x: ", pinpoint.getPosX(MM));
        timer.reset();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            driveToPos(0.01, 0.0);
            telemetry.update();
        }

    }


//    public void DriveToPos(double targetX, double targetY) {
//        pinpoint.update();
//        boolean telemAdded = false;
//
//        telemetry.addLine("Inside the driveToPos");
//        if (opModeIsActive() &&
//                (Math.abs(targetX - pinpoint.getPosX(MM)) > 5 || Math.abs(targetY - pinpoint.getPosY(MM)) > 5)
//        ){
//            telemetry.addData("Inside while X", Math.abs(targetX - pinpoint.getPosX(MM)));
//            telemetry.addData("Inside while Y", Math.abs(targetY - pinpoint.getPosY(MM)));
//            pinpoint.update();
//
//            double x = 0.001*(targetX - pinpoint.getPosX(MM));
//            double y = -0.001*(targetY - pinpoint.getPosY(MM));
//
//            double botHeading = mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//            double rotateY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);
//            double rotateX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);
//
//            if (!telemAdded) {
//                telemetry.addData("x: ", x);
//                telemetry.addData("y: ", y);
//                telemetry.addData("rotateX: ", rotateX);
//                telemetry.addData("rotateY: ", rotateY);
//                telemetry.update();
//                telemAdded = true;
//            }
//
//            if (Math.abs(rotateX) < 0.05) {
//                rotateX = Math.signum(rotateX) * 0.05;
//            }
//
//            if (Math.abs(rotateY) < 0.05) {
//                rotateY = Math.signum(rotateY) * 0.05;
//            }
//
//            double denominator = Math.max(Math.abs(y) + Math.abs(x), 1);
//            double frontLeftPower = (rotateX + rotateY) / denominator;
//            double backLeftPower = (rotateX - rotateY) / denominator;
//            double frontRightPower = (rotateX - rotateY) / denominator;
//            double backRightPower = (rotateX + rotateY) / denominator;
//
//            mecDrivebase.frontLeft.setPower(frontLeftPower);
//            mecDrivebase.backLeft.setPower(backLeftPower);
//            mecDrivebase.frontRight.setPower(frontRightPower);
//            mecDrivebase.backRight.setPower(backRightPower);
//
//            //mecDrivebase.frontLeft.setPower(0.25);
//            //mecDrivebase.backLeft.setPower(0.25);
//            //mecDrivebase.frontRight.setPower(0.25);
//            //mecDrivebase.backRight.setPower(0.25);
//
//            telemetry.addData("X: ", pinpoint.getPosX(MM));
//            telemetry.addData("Y: ", pinpoint.getPosY(MM));
//            telemetry.addData("Heading pinpoint: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.DEGREES)));
//            telemetry.addData("Heading IMU: ", mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
//            telemetry.update();
//        }
//        else {
//            mecDrivebase.frontLeft.setPower(0);
//            mecDrivebase.backLeft.setPower(0);
//            mecDrivebase.frontRight.setPower(0);
//            mecDrivebase.backRight.setPower(0);
//        }
//    }

    private void driveToPos(double targetX, double targetY) {
        pinpoint.update();
        boolean telemAdded = false;

        while (opModeIsActive() &&
                (Math.abs(targetX - pinpoint.getPosX(MM)) > 0 || Math.abs(targetY - pinpoint.getPosY(MM)) > 0)
        ) {
            pinpoint.update();

            double x = 0.001 * (targetX - pinpoint.getPosX(MM));
            double y = -0.001 * (targetY - pinpoint.getPosY(MM));

            double botHeading = mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS); // getRobotOrientationAsQuaternion().  .().firstAngle(); //.getHeading();

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
            double frontLeftPower = (rotX + rotY)
                    / denominator;
            double backLeftPower = (rotX - rotY) / denominator;
            double frontRightPower = (rotX - rotY) / denominator;
            double backRightPower = (rotX + rotY) / denominator;

            mecDrivebase.frontLeft.setPower(frontLeftPower);
            mecDrivebase.backLeft.setPower(backLeftPower);
            mecDrivebase.frontRight.setPower(frontRightPower);
            mecDrivebase.backRight.setPower(backRightPower);

            telemetry.addData("X: ", pinpoint.getPosX(MM));
            telemetry.addData("Y: ", pinpoint.getPosY(MM));
            telemetry.addData("Heading pinpoint: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.RADIANS)));
            telemetry.addData("Heading IMU: ", mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }

        mecDrivebase.frontLeft.setPower(0);
        mecDrivebase.frontRight.setPower(0);
        mecDrivebase.backLeft.setPower(0);
        mecDrivebase.backRight.setPower(0);
    }
}
