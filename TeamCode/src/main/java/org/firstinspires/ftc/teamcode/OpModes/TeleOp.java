package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Led;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
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
    LimelightConfig limelight = new LimelightConfig();

    double forward, strafe, rotate;
    double servoValue;
    static double[][] sortingValues;
    double velocity;

    public void setDriver(){
        led.startLed();
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);

        if(gamepad1.right_trigger >= 0.8) {
            Constants.driveMaxSpeed = 0.3;
        } else {
            Constants.driveMaxSpeed = 1.0;
        }
        if(gamepad1.a) {
            shooter.MedOut();
            shooter.hoodMed();
            velocity = 1400;
        } else if(gamepad1.b) {
            shooter.FarOut();
            shooter.hoodFar();
            velocity = 1700;
        } else if (gamepad1.x) {
            shooter.CloseOut();
            shooter.hoodClose();
            velocity = 1150;
        } else if(gamepad1.left_trigger >= 0.7){
            shooter.HPIn();
            shooter.hoodClose();
        } else {
            shooter.Stop();
            velocity = 0;
        }

        if(gamepad1.dpad_up){
            shooter.hoodZero();
        } else if(gamepad1.dpad_down){
            drive.imu.resetYaw();
        }
    }

    public void setOperator(){
        if(gamepad2.y && shooter.velocityValue() >= velocity - 75 && shooter.velocityValue() <= velocity + 75){
            kick.kick();
        } else if(gamepad2.right_trigger >= 0.7 && gamepad2.y){
            kick.kick();
        } else {
            kick.retract();
        }

        if(gamepad2.left_trigger >= 0.7) {
            Intake();
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
        if(gamepad2.left_bumper){
            outtakeColor(2);
        }else if(gamepad2.right_bumper){
            outtakeColor(1);
        }
        if(gamepad2.left_trigger >= 0.7){
            sorter.setZero();
        }
    }

    public void Intake(){
        intake.IntakeMotorMax();
        SortingWithColor.DetectedColor detectedColor = colorSensor.getDetectedColor(telemetry);
        servoValue = sorter.GetServoPos();
        telemetry.addData("Detected Color ", detectedColor);
        telemetry.addData("Servo Value ", servoValue);
        if (Math.abs(servoValue - 0.03) < 0.005) {
            sortingValues[0][0] = detectedColor.getCode();
            sortingValues[0][1] = Constants.sorterOutTakeA;

        } else if (Math.abs(servoValue - 0.105) < 0.005) {
            sortingValues[1][0] = detectedColor.getCode();
            sortingValues[1][1] = Constants.sorterOutTakeB;

        } else if (Math.abs(servoValue - 0.17) < 0.005) {
            sortingValues[2][0] = detectedColor.getCode();
            sortingValues[2][1] = Constants.sorterOutTakeC;
        }
    }

    public void outtakeColor(int targetColor) {
        for (int index = 0; index < 3; index++) {

            double storedColor = sortingValues[index][0];
            double outPos = sortingValues[index][1];

            if (storedColor == targetColor) {

                sorter.setServo(outPos);

                sleep(100);
//                kick.kick();
//                sleep(1000);
//                kick.retract();
//                telemetry.addLine("Clearing out value");
                telemetry.update();

                sortingValues[index][0] = 0;
                sortingValues[index][1] = 0;

                return;
            }
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
        limelight.init(hardwareMap);

        drive.imu.resetYaw();

        sortingValues = new double[3][2];

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Color", colorSensor.getDetectedColor(telemetry));
            //limelight.TargetArea();
            setDriver();
            setOperator();

            telemetry.update();
        }
    }
}
