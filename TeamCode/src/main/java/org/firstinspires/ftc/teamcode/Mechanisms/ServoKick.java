package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class ServoKick {

    private Servo kick;

    public void init(HardwareMap hwMap){
        kick = hwMap.get(Servo.class,"kick");
    }
    public void kickServo(){
        //kick.setPosition(Constants.kickServo);
    }
    public void retractKick(){
        //kick.setPosition(Constants.kickServoRetract);
    }

}
