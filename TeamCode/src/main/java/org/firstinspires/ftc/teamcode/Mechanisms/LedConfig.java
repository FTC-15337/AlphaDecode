package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

public class LedConfig {
    private LED light;

    public void init(HardwareMap hwMap){
        light = hwMap.get(LED.class , "led");
    }

    public void lightOn(boolean isOn){
        if(isOn){
            light.on();
        }else{
            light.off();
        }
    }
}
