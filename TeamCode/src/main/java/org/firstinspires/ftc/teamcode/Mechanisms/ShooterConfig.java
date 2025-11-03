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

    public void stopMotor(){
        hoodMotor.setPower(0.0);
    }
    public void setPower1(){hoodMotor.setPower(Constants.outPos1);}
    public void setPower2(){hoodMotor.setPower(Constants.outPos2);}
    public void setPower3(){hoodMotor.setPower(Constants.outPos3);}
    public void setPower4(){hoodMotor.setPower(Constants.outPos4);}
}
