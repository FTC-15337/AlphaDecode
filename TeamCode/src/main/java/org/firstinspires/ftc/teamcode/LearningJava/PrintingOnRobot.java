package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

@TeleOp(name = "Joystick Program")
public class PrintingOnRobot extends LinearOpMode {

        public void initialize(){
            telemetry.addLine("Init is active");
    }

    @Override
    public void runOpMode() throws InterruptedException {


        initialize();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Gamepad 1 stick y is ", gamepad1.left_stick_y);
            telemetry.addData("Gamepad 1 stick x is ", gamepad1.left_stick_x);
            telemetry.addData("Gamepad 1 stick y is ", gamepad1.right_stick_y);
            telemetry.addData("Gamepad 1 stick x is ", gamepad1.right_stick_x);

            if (gamepad1.a){
                telemetry.addLine("Robotics is fun!!!");
            }
            telemetry.update();
        }
    }
}
