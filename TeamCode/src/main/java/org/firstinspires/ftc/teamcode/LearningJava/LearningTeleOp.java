package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

public class LearningTeleOp extends LinearOpMode {
    OutreachHoodMovement motorMovement = new OutreachHoodMovement();
    OutreachMecDrivebase outreachMecDrivebase = new OutreachMecDrivebase();
    double forward, strafe, rotate;

    public void setDrive(){
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        outreachMecDrivebase.driveFieldRelative(forward, strafe, rotate);
    }
    @Override
    public void runOpMode(){

        motorMovement.init(hardwareMap);
        outreachMecDrivebase.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){
            telemetry.addLine("Tele is active");

            setDrive();

            if(gamepad1.a){
                motorMovement.moveMotor();
            }else{
                motorMovement.stopMotor();
            }


            telemetry.update();
        }
    }
}
