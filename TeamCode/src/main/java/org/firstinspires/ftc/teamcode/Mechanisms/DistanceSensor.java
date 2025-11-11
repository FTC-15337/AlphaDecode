package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensor {
    private com.qualcomm.robotcore.hardware.DistanceSensor distance;

    public void init(HardwareMap hwMap){
        distance = hwMap.get(com.qualcomm.robotcore.hardware.DistanceSensor.class, "distance");
    }

    public double GetDistance() {
        return distance.getDistance(DistanceUnit.CM);
    }
}
