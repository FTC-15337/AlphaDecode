package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class MotorMovement {
private DcMotor testMotor;
Constants constants = new Constants();

    public void init(HardwareMap hwMap){
        testMotor = hwMap.get(DcMotor.class , "frontLeft");

        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        testMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void moveMotor(){
        testMotor.setPower(constants.testMotorPower);

    }
    public void stopMotor(){
        testMotor.setPower(constants.testMotorStop);
    }
}
