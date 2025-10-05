package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp()
public class SpindexerCode extends OpMode {
    private Servo spindexer;
    private boolean outtaking = false;
    public void init() {
        spindexer=hardwareMap.get(Servo.class, "spindexer");
        spindexer.setPosition(0.0);
    }
    public void loop() {
        if (!outtaking) {
            if (gamepad1.a) {
                spindexer.setPosition(0.07);
            }
            else if (gamepad1.b) {
                spindexer.setPosition(0.14);
            }
            else if (gamepad1.x) {
                spindexer.setPosition(0.21);
            }
        }
        else {
            if (gamepad1.a) {
                spindexer.setPosition(0.105);
            }
            else if (gamepad1.b) {
                spindexer.setPosition(0.175);
            }
            else if (gamepad1.x) {
                spindexer.setPosition(0.245);
            }
        }
        if (gamepad1.dpad_down) {
            outtaking = true;
        }
        else if (gamepad1.dpad_up) {
            outtaking = false;
        }
    }
}
