package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Led;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name= "TeleOp")
public class TeleOp extends LinearOpMode {
    MecDrivebase drive = new MecDrivebase();
    StorageConfig sorter = new StorageConfig();
    SortingWithColor colorSensor = new SortingWithColor();
    ShooterConfig shooter = new ShooterConfig();
    IntakeConfig intake = new IntakeConfig();
    ServoKick kick = new ServoKick();
    Led led = new Led();

    double forward, strafe, rotate;
    double servoValue;
    static double[][] sortingValues;

    public void setDriver(){
        led.startLed();
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);

        if(gamepad1.right_trigger == 1.0) {
            Constants.driveMaxSpeed = 0.3;
        } else {
            Constants.driveMaxSpeed = 1.0;
        }
        if(gamepad1.a) {
            shooter.MedOut();
        } else if(gamepad1.b) {
            shooter.FarOut();
        } else if (gamepad1.left_trigger >= 0.7) {
            shooter.HPIn();
        } else {
            shooter.Stop();
        }
    }

    public void setOperator(){
        if(gamepad2.y){
            telemetry.addLine("Inside Y");
            telemetry.addData("Array value for First INTAKE A", sortingValues[0][0]);
            telemetry.addData("Array value for First INTAKE A.1", sortingValues[0][1]);
            telemetry.addData("Array value for First INTAKE B", sortingValues[1][0]);
            telemetry.addData("Array value for First INTAKE B.1", sortingValues[1][1]);
            telemetry.addData("Array value for First INTAKE C", sortingValues[2][0]);
            telemetry.addData("Array value for First INTAKE C,1", sortingValues[2][1]);

            telemetry.update();


            /*kick.kick();
        }else{
            kick.retract();*/
        }

        if(gamepad2.left_trigger >= 0.7) {
            intake();
            telemetry.update();
        } else {
            intake.IntakeMotorStop();
        }

        if(gamepad2.dpad_down) {
            //telemetry.addLine("Sorter IA");
            sorter.setIntakeA();
        }
        if(gamepad2.dpad_right) {
            //telemetry.addLine("Sorter IC");
            sorter.setIntakeB();
        }
        if(gamepad2.dpad_left) {
            //telemetry.addLine("Sorter IB");
            sorter.setIntakeC();
        }
        if (gamepad2.dpad_up) {
            intake.OutIntake();
        }
        if(gamepad2.a) {
            //telemetry.addLine("Sorter OA");
            sorter.setOutA();
            //telemetry.update();
        }
        if(gamepad2.b) {
            //telemetry.addLine("Sorter OB");
            sorter.setOutB();
            //telemetry.update();
        }
        if(gamepad2.x) {
            //telemetry.addLine("Sorter OC");
            sorter.setOutC();
            //telemetry.update();
        }
        //telemetry.update();
    }

    public void intake(){
        intake.IntakeMotorMax();
        SortingWithColor.DetectedColor detectedColor = colorSensor.getDetectedColor(telemetry);
        servoValue = sorter.GetServoPos();
        telemetry.addData("Detected Color ", detectedColor);
        telemetry.addData("Servo Value ", servoValue);
        if (Math.abs(servoValue - 0.03) < 0.005) {
            sortingValues[0][0] = detectedColor.getCode();
            sortingValues[0][1] = Constants.sorterOutTakeA;
            sleep(10);
        } else if (Math.abs(servoValue - 0.105) < 0.005) {
            sortingValues[1][0] = detectedColor.getCode();
            sortingValues[1][1] = Constants.sorterOutTakeB;
            sleep(10);
            //sorter.setIntakeC();
        } else if (Math.abs(servoValue - 0.17) < 0.005) {
            sortingValues[2][0] = detectedColor.getCode();
            sortingValues[2][1] = Constants.sorterOutTakeC;
            sleep(10);
            //sorter.resetToIntake();
        }
    }


    @Override
    public void runOpMode() throws InterruptedException {

        drive.init(hardwareMap);
        colorSensor.init(hardwareMap);
        intake.init(hardwareMap);
        sorter.init(hardwareMap);
        shooter.init(hardwareMap);
        kick.init(hardwareMap);
        led.init(hardwareMap);

        kick.retract();
        drive.imu.resetYaw();

        sorter.resetToIntake();

        sortingValues = new double[3][2];

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            setDriver();
            setOperator();

            telemetry.update();
        }

    }
}
