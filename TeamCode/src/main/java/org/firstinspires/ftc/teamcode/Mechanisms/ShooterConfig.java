package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class ShooterConfig {
private DcMotorEx hoodMotor;

    public void init(HardwareMap hwMap){
        hoodMotor = hwMap.get(DcMotorEx.class , "shooter");

        hoodMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        hoodMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        hoodMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void stopMotor(){
        hoodMotor.setVelocity(0.0);
    }
    //public void setPower1(){hoodMotor.setVelocity(Constants.outPos1);}
    public void setPower2()
    {
        hoodMotor.setVelocity(2100);
    }
    public void setPower3(){hoodMotor.setVelocity(1960);}
    public void setPower4(){hoodMotor.setVelocity(2422);}
    public void setHP(){hoodMotor.setVelocity(-280);}
    public double getVelocity(){return hoodMotor.getVelocity();}
}
