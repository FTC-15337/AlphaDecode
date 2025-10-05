package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp()
public class IntakeOuttakeCode extends OpMode {
    private DcMotor intake;

    public void init() {
        intake=hardwareMap.get(DcMotor.class, "intake");
    }
    public void loop() {
        if (gamepad1.a) {
            intake.setPower(1.0);
        }
        else if (gamepad1.b) {
            intake.setPower(-1.0);
        }
        else {
            intake.setPower(0.0);
        }
    }
}