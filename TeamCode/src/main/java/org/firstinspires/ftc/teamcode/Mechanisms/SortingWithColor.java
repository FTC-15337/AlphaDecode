package org.firstinspires.ftc.teamcode.Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class SortingWithColor {
    private ColorSensor colorSensor;

    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(ColorSensor.class, "ColorSensor");
    }

    public int getRed() {
        return colorSensor.red();
    }

    public int getGreen() {
        return colorSensor.green();
    }

    public int getBlue() {
        return colorSensor.blue();
    }

    public String detectColor() {

        int r = colorSensor.red();
        int b = colorSensor.blue();
        int g = colorSensor.green();

        if (b < 100) {
            return "Unkown";
        }
        if (b < g && r < g) {
            return "Green";
        } else if (r < b && g < b) {
            return "Purple";
        }
        return "Unkown";

    }
}

