package org.firstinspires.ftc.teamcode.Mechanisms;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;



public class TurretConfig {
    public CRServo turret;

    public void init(HardwareMap hwMap) {
        turret = hwMap.get(CRServo.class, "turret_shooter");
    }

    public void setPower(double power) {
        turret.setPower(power);
    }
}
