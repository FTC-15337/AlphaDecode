package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class PrintingOnRobot extends OpMode {
    @Override
    public void init(){
        telemetry.addLine("Hello World");
    }
    @Override
    public void loop(){
       telemetry.addData("Joystick value is : ", gamepad1.left_stick_y);
    }

}
