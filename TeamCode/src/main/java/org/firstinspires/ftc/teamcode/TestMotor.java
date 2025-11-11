package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "TestMotor", group = "Tests")
public class TestMotor extends OpMode {
    private DcMotor motor;
    private int powerSwap=1;
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
    }
    public void loop() {
        if(gamepad1.a){
            powerSwap = -1;
        }
        else if(gamepad1.b){
            powerSwap = 1;
        }
        motor.setPower(powerSwap*gamepad1.left_trigger);
        telemetry.addData("Power", gamepad1.left_trigger);
    }
}
