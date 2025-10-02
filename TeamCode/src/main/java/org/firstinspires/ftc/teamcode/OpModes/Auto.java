package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.CM;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

public class Auto extends LinearOpMode {
    MecDrivebase mecDrivebase = new MecDrivebase();
    GoBildaPinpointDriver pinpoint;
    TeleOp tele = new TeleOp();
    ElapsedTime timer;

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
    public void runOpMode(){

        mecDrivebase.init(hardwareMap);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class , "pinpoint");
        pinpoint.setOffsets(10.1,17.5,DistanceUnit.CM);

        //Pinpoint starting pos for tracking
        telemetry.addData("Starting y: " , pinpoint.getPosY(CM));
        telemetry.addData("Starting x: " , pinpoint.getPosX(CM));

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){

            timer.reset();

            driveAndAlignToTest();

            telemetry.update();
        }

    }
    public void driveToPos(double targetX, double targetY) {
        pinpoint.update();
        boolean telemAdded = false;

        while (opModeIsActive() &&
                (Math.abs(targetX - pinpoint.getPosX(CM)) > 30 || Math.abs(targetY - pinpoint.getPosY(CM)) > 30)
        ){
            pinpoint.update();

            double x = 0.001*(targetX - pinpoint.getPosX(CM));
            double y = -0.001*(targetY - pinpoint.getPosY(CM));

            double botHeading = mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);            double rotateY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);
            double rotateX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);

            if (!telemAdded) {
                telemetry.addData("x: ", x);
                telemetry.addData("y: ", y);
                telemetry.addData("rotateX: ", rotateX);
                telemetry.addData("rotateY: ", rotateY);
                telemetry.update();
                telemAdded = true;
            }

            if (Math.abs(rotateX) < 0.15) {
                rotateX = Math.signum(rotateX) * 0.15;
            }

            if (Math.abs(rotateY) < 0.15) {
                rotateY = Math.signum(rotateY) * 0.15;
            }

            double denominator = Math.max(Math.abs(y) + Math.abs(x), 1);
            double frontLeftPower = (rotateX + rotateY)
                    / denominator;
            double backLeftPower = (rotateX - rotateY) / denominator;
            double frontRightPower = (rotateX - rotateY) / denominator;
            double backRightPower = (rotateX + rotateY) / denominator;

            mecDrivebase.frontLeft.setPower(frontLeftPower);
            mecDrivebase.backLeft.setPower(backLeftPower);
            mecDrivebase.frontRight.setPower(frontRightPower);
            mecDrivebase.backRight.setPower(backRightPower);

            telemetry.addData("X: ", pinpoint.getPosX(CM));
            telemetry.addData("Y: ", pinpoint.getPosY(CM));
            telemetry.addData("Heading Odo: ", Math.toDegrees(pinpoint.getHeading(AngleUnit.DEGREES)));
            telemetry.addData("Heading IMU: ", mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }

        mecDrivebase.frontLeft.setPower(0);
        mecDrivebase.backLeft.setPower(0);
        mecDrivebase.frontRight.setPower(0);
        mecDrivebase.backRight.setPower(0);
    }

    public void driveAndAlignToTest(){
        driveToPos(1,1);

        if(timer.seconds() <= 3) {
            mecDrivebase.autoRotate(0.25, -0.25);
        }else{
            mecDrivebase.autoRotate(0.0,0.0);
        }
    }
}
