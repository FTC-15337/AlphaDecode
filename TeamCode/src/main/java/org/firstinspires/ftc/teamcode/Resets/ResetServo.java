package org.firstinspires.ftc.teamcode.Resets;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "Reset Servo", group = "Resets")
public class ResetServo extends OpMode {
    private Servo servo;
    public void init() {
        servo = hardwareMap.get(Servo.class, "servo");
    }
    public void loop() {
        servo.setPosition(0.0);
    }
}