package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class PrototypeHoodFire {
private DcMotor outreachHoodMotor;
Constants constants = new Constants();

    public void init(HardwareMap hwMap){
        outreachHoodMotor = hwMap.get(DcMotor.class , "frontLeft");

        outreachHoodMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        outreachHoodMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void moveMotor(){
        outreachHoodMotor.setPower(constants.testMotorPower);

    }
    public void stopMotor(){
        outreachHoodMotor.setPower(constants.testMotorStop);
    }
}
