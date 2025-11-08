package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.Telemetry;

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

        // 2. Colors too close together (ambiguous)
        // --- GREEN CHECK ---
        if (greenRatio > 0.44 && greenRatio < 0.47 && redRatio < 0.15) {
            return DetectedColor.GREEN;
        }

        if(blueRatio < 0.36 && blueRatio > 0.28){
            return DetectedColor.UNKNOWN;
        }

        // --- PURPLE CHECK ---
        if (greenRatio > 0.25 && greenRatio < 0.29 && blueRatio > 0.4) {
            return DetectedColor.PURPLE;
        }

        // --- FALLBACK ---
        return DetectedColor.UNKNOWN;
    }
}

