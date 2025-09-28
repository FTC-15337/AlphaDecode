package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class PrototypeHoodFire {
private DcMotor outreachHoodMotor;


    public void init(HardwareMap hwMap){
        outreachHoodMotor = hwMap.get(DcMotor.class , "testHood");

        outreachHoodMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        outreachHoodMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void moveMotor(){
        outreachHoodMotor.setPower(Constants.testMotorPower);

    }
    public void stopMotor(){
        outreachHoodMotor.setPower(Constants.testMotorStop);
    }
}
