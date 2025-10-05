package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp()
public class IntakeOuttakeCode extends OpMode {
    private DcMotor intake;
    private DcMotor outtake;

    public void init() {
        intake=hardwareMap.get(DcMotor.class, "intake");
        outtake=hardwareMap.get(DcMotor.class, "outtake");
    }
    public void loop() {
//        if (gamepad1.a) {
//            intake.setPower(0.5);
//        }
        if (gamepad1.b) {
            intake.setPower(-0.5);
        }
        else {
            intake.setPower(0.0);
        }
        if (gamepad1.x) {
            outtake.setPower(1.0);
        }
//        else if (gamepad1.y) {
//            outtake.setPower(-1.0);
//        }
        else {
            outtake.setPower(0.0);
        }
    }
}