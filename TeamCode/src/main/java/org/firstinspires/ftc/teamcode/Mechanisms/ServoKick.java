package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class ServoKick {
    Servo servoKick;

    public void init(HardwareMap hwMap) {
        servoKick = hwMap.get(Servo.class, "servoKick");
    }
    public void kick() {
        servoKick.setPosition(Constants.servokickMax);
    }
    public void retract() {
        servoKick.setPosition(Constants.servokickMin);
    }
}
