package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class ShooterConfig {
    private DcMotorEx shooter;

    public void init(HardwareMap hwMap) {
        shooter = hwMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void MaxOut() {
        shooter.setVelocity(2800);
    }

    public void HalfOut() {
        shooter.setVelocity(1400);
    }
    public void Stop() {
        shooter.setVelocity(0);
    }
}
