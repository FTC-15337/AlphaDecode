package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class SortingWithColor {
    private ColorSensor colorSensor;

    public void init(HardwareMap hwMap){
        colorSensor = hwMap.get(ColorSensor.class , "ColorSensor");
    }
    int r = colorSensor.red();
    int b = colorSensor.blue();
    int g = colorSensor.green();
    public String detectColor(){

        if (r >= Constants.GREEN_RED_MIN && r <= Constants.GREEN_RED_MAX &&
                g >= Constants.GREEN_GREEN_MIN && g <= Constants.GREEN_GREEN_MAX &&
                b >= Constants.GREEN_BLUE_MIN && b <= Constants.GREEN_BLUE_MAX) {
            return "Green";
        }else if (r >= Constants.PURPLE_RED_MIN && r <= Constants.PURPLE_RED_MAX &&
                g >= Constants.PURPLE_GREEN_MIN && g <= Constants.PURPLE_GREEN_MAX &&
                b >= Constants.PURPLE_BLUE_MIN && b <= Constants.PURPLE_BLUE_MAX) {
            return "Purple";
        }else {
            return "Unknown Color";
        }
    }
}
