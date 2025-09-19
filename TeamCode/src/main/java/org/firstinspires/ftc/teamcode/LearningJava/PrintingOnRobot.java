package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PrintingOnRobot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        init();
        while (opModeInInit()) {
            telemetry.addLine("Initialized");
            telemetry.update();
        }

        start();
        while (opModeIsActive()) {
            telemetry.addData("Gamepad 1 stick y is ", gamepad1.left_stick_y);
            telemetry.addData("Gamepad 1 stick x is ", gamepad1.left_stick_x);
            telemetry.addData("Gamepad 1 stick y is ", gamepad1.right_stick_y);
            telemetry.addData("Gamepad  1 stick x is ", gamepad1.right_stick_x);

            if (gamepad1.a){
                telemetry.addLine("Robotics is fun!!!");
            }

            telemetry.update();
        }
    }
}
