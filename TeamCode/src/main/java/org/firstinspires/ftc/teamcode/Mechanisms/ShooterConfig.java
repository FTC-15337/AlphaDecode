package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class ShooterConfig {
private DcMotor hoodMotor;

    public void init(HardwareMap hwMap){
        hoodMotor = hwMap.get(DcMotor.class , "shooter");

        hoodMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hoodMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        hoodMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void fireMotor(){
        hoodMotor.setPower(Constants.shooterMax);

    }
    public void stopMotor(){
        hoodMotor.setPower(Constants.shooterStop);
    }
}
