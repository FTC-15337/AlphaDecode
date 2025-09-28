package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.PrototypeHoodFire;

@TeleOp
public class SimpleTeleOp extends LinearOpMode {
    PrototypeHoodFire motorMovement = new PrototypeHoodFire();
    MecDrivebase drive = new MecDrivebase();
    double forward, strafe, rotate;

    public void setDrive(){
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);
    }
    @Override
    public void runOpMode(){

        motorMovement.init(hardwareMap);
        drive.init(hardwareMap);
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
