package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class SortingWithColor {
    private NormalizedColorSensor colorSensor;


    StorageConfig sorter = new StorageConfig();
    private DistanceSensor distance;

    public enum DetectedColor {
        PURPLE(1),
        GREEN(2),
        UNKNOWN(3);

        private final int code; // Field to store the numeric value

        // Constructor to initialize the code
        DetectedColor(int code) {
            this.code = code;
        }

        // Getter method to retrieve the code
        public int getCode() {
            return code;
        }
    }


    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "colorSensor");
        distance = hwMap.get(DistanceSensor.class , "distance");
    }

    public DetectedColor getDetectedColor(Telemetry telemetry) {
        // Read normalized colors
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float r = colors.red;
        float g = colors.green;
        float b = colors.blue;

        // Compute total intensity to get ratios
        float total = r + g + b;

        float redRatio = r / total;
        float greenRatio = g / total;
        float blueRatio = b / total;

        // Telemetry for tuning
//        telemetry.addData("Red Ratio", redRatio);
//        telemetry.addData("Green Ratio", greenRatio);
//        telemetry.addData("Blue Ratio", blueRatio);
        if (GetDistance() < 12.0) {
            //Colors too close together
            if (greenRatio > 0.4 && greenRatio < 0.44 && redRatio > 0.15 && redRatio < 0.20) {
                return DetectedColor.GREEN;
            } else {
                return DetectedColor.PURPLE;
            }
        }
        return DetectedColor.UNKNOWN;
    }

    public double GetDistance(){
        return distance.getDistance(DistanceUnit.CM);
    }

}

    /*if(redRatio < 0.23 && blueRatio > 0.34){
            return DetectedColor.UNKNOWN;
        }*/

