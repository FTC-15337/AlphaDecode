package org.firstinspires.ftc.teamcode.Mechanisms;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class IntakeConfig {
    private DcMotor intakeMotor;

    public void init(HardwareMap hwMap){

        intakeMotor = hwMap.get(DcMotor.class , "intakeMotor");

        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public void IntakeMotorStop(){
        intakeMotor.setPower(Constants.IntakeStop);
    }

    public void IntakeMotorMax(){
        intakeMotor.setPower(Constants.IntakeMax);
    }
}
