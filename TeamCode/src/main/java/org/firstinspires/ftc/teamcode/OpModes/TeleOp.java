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
    double i = 0;

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
            telemetry.addData("Array value for COLOR A", sortingValues[0][0]);
            telemetry.addData("Array value for OUT VAL A", sortingValues[0][1]);
            telemetry.addData("Array value for COLOR B", sortingValues[1][0]);
            telemetry.addData("Array value for OUT VAL B", sortingValues[1][1]);
            telemetry.addData("Array value for COLOR C", sortingValues[2][0]);
            telemetry.addData("Array value for OUT VAL C", sortingValues[2][1]);

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
        if (gamepad2.right_stick_button) {
            shooter.setHood(0.7);
        }

// keep it between 0 and

        telemetry.addData("HOOD POS IS", shooter.returnVal());

        shooter.setHood(i);
        telemetry.addData("HOOD POS IS" , shooter.returnVal());
        telemetry.update();

        if(gamepad2.left_bumper){
            outtakeColor(2);
        }else if(gamepad2.right_bumper){
            outtakeColor(1);
        }
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
        } else if (Math.abs(servoValue - 0.105) < 0.005) {
            sortingValues[1][0] = detectedColor.getCode();
            sortingValues[1][1] = Constants.sorterOutTakeB;

            //sorter.setIntakeC();
        } else if (Math.abs(servoValue - 0.17) < 0.005) {
            sortingValues[2][0] = detectedColor.getCode();
            sortingValues[2][1] = Constants.sorterOutTakeC;
        }
    }

    public void outtakeColor(int targetColor) {
        // Loop through all 3 stored pixels
        for (int index = 0; index < 3; index++) {

            double storedColor = sortingValues[index][0];
            double outPos = sortingValues[index][1];

            // If this slot contains the color we're looking for
            if (storedColor == targetColor) {

                // Move sorter to correct outtake position
                sorter.setServo(outPos);

                sleep(750);   // let servo move
                kick.kick();  // kick pixel out
                sleep(1500);
                kick.retract();

                // EMPTY ONLY THIS ENTRY (not whole array)
                sortingValues[index][0] = 0;
                sortingValues[index][1] = 0;

                return; // stop after removing one match
            }
        }

        // If no more of that color are found
        telemetry.addLine("No more of that color stored.");
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

        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Color", colorSensor.getDetectedColor(telemetry));
            setDriver();
            setOperator();

            telemetry.update();
        }

    }
}
