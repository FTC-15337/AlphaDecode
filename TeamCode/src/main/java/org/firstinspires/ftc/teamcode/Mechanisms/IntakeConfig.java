package org.firstinspires.ftc.teamcode.Mechanisms;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class IntakeConfig {
    private DcMotor intakeMotor;

    StorageConfig sorter = new StorageConfig();

    public void init(HardwareMap hwMap){

        intakeMotor = hwMap.get(DcMotor.class , "intakeMotor");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void IntakeMotorStop(){
        intakeMotor.setPower(Constants.intakeStop);
    }

    public void IntakeMotorMax(){
        intakeMotor.setPower(Constants.intakeMax);
    }
    public void intake(){
        ElapsedTime timer = new ElapsedTime();
        IntakeMotorMax();
        if(timer.seconds() < 1 && timer.seconds() > 0){
            sorter.setIntakeC();
        }
        if(timer.seconds() < 2 && timer.seconds() > 1){
            sorter.setIntakeB();
        }
        if(timer.seconds() < 3 && timer.seconds() > 2){
            sorter.setIntakeC();
        }
        if(timer.seconds() > 3){
            IntakeMotorStop();
        }
    }
}
