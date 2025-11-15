package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ShooterConfig {
    private DcMotorEx shooter;
    private Servo hood;

    public void init(HardwareMap hwMap) {
        shooter = hwMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hood = hwMap.get(Servo.class , "hood");
    }
    public void FarOut() {shooter.setVelocity(1850);}

    public void MedOut() {shooter.setVelocity(1320);}
    public void Stop() {
        shooter.setVelocity(0);
    }
    public void HPIn(){
        shooter.setVelocity(-280);
    }
    public double GetVelocity() {
        return shooter.getVelocity();
    }
    public void hoodZero(){
        hood.setPosition(0.0);
    }
    public void hoodMed(){
        hood.setPosition(0.3);
    }
    public void hoodMax(){
        hood.setPosition(1.0);
    }
    public double returnVal(){
        return hood.getPosition();
    }
}
