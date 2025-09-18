package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PrintingOnRobot extends LinearOpMode {
    public void initialize() {
        telemetry.addLine("Init");
    }
    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        while(!isStopRequested() && opModeIsActive()){
            telemetry.addData("Gamepad 1 stick y is " , gamepad1.left_stick_y);
            telemetry.addData("Gamepad 1 stick x is " , gamepad1.left_stick_x);
        }
    }
}
