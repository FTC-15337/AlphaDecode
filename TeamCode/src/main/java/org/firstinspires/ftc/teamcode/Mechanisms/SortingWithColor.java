package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class SortingWithColor {
    private NormalizedColorSensor colorSensor;
    public enum DetectedColor {
        PURPLE,
        GREEN,
        UNKNOWN
    }
    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "ColorSensor");
    }

    public DetectedColor getDetectedColor(Telemetry telemetry) {

        boolean g;
        boolean p;
        boolean u;

        g = false;
        p = false;
        u = false;

        NormalizedRGBA colors = colorSensor.getNormalizedColors(); //returns red, green, blue and alpha(how bright)

        float normRed, normGreen, normBlue;

        normRed = colors.red / colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("Red", normRed);
        telemetry.addData("Green", normGreen);
        telemetry.addData("Blue", normBlue);

        if(normGreen > normRed && normGreen > normBlue && normGreen < 0.02) {
            g = true;
            return DetectedColor.GREEN;
        }
        if(normBlue > normRed && normBlue > normGreen && normBlue < 0.03) {
            p = true;
            return DetectedColor.PURPLE;
        }
        if(normGreen > 0.037 && normBlue > 0.033 && normRed > 0.0215){
            u = true;
            return DetectedColor.UNKNOWN;
        }
        return DetectedColor.UNKNOWN;
    }
}
