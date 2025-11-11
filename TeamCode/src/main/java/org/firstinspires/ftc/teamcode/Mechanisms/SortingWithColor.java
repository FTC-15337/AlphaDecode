package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/*public class SortingWithColor {
private NormalizedColorSensor colorSensor;


StorageConfig sorter = new StorageConfig();

    public enum DetectedColor {
        PURPLE,
        GREEN,
        UNKNOWN;
    }


    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "ColorSensor");
    }

    public DetectedColor getDetectedColor(Telemetry telemetry) {
        // Read normalized colors
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float r = colors.red;
        float g = colors.green;
        float b = colors.blue;

        // Compute total intensity to get ratios
        float total = r + g + b;
        if (total == 0) return DetectedColor.UNKNOWN; // Avoid divide-by-zero

        float redRatio = r / total;
        float greenRatio = g / total;
        float blueRatio = b / total;

        // Telemetry for tuning
        telemetry.addData("Red Ratio", redRatio);
        telemetry.addData("Green Ratio", greenRatio);
        telemetry.addData("Blue Ratio", blueRatio);

        //Colors too close together
        if (greenRatio > 0.4 && greenRatio < 0.44 && redRatio > 0.2 && redRatio < 0.21 && blueRatio > 0.35 && blueRatio < 0.38) {
            return DetectedColor.GREEN;
        } else if (redRatio > 0.23 && redRatio < 0.24 && greenRatio < 0.38 && blueRatio < 0.4) {
            return DetectedColor.PURPLE;
        } else {
            return DetectedColor.UNKNOWN;
        }
    }
}*/

    /*if(redRatio < 0.23 && blueRatio > 0.34){
            return DetectedColor.UNKNOWN;
        }*/

