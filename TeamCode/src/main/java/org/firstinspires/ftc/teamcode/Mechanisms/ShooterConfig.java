package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class ShooterConfig {
private DcMotorEx hoodMotor;

double integralSum = 0;
double Kp = 0.000083;
double Ki = 0.00000083;
double Kd = 0.0000083;
double Kf = 0.00001667;

ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hwMap){
        hoodMotor = hwMap.get(DcMotorEx.class , "shooter");

        hoodMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        hoodMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        hoodMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void stopMotor(){
        hoodMotor.setPower(PIDControl(0, hoodMotor.getVelocity()));
    }
    //public void setPower1(){hoodMotor.setPower(Constants.outPos1);}
    public void setPower2(){hoodMotor.setPower(PIDControl(5750, hoodMotor.getVelocity()));}
    public void setPower3(){hoodMotor.setPower(Constants.outPos3);}
    public void setPower4(){hoodMotor.setPower(Constants.outPos4);}

    public double PIDControl(double reference, double state) {
        double error = reference - state;
        integralSum += error * timer.seconds();
        double lastError = error;
        double derivative = (error = lastError) / timer.seconds();

        timer.reset();

        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki) + (reference * Kf);

        return output;
    }
}
