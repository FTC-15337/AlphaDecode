package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.PrototypeHoodFire;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();
    StorageConfig sorter = new StorageConfig();
    SortingWithColor colorSensor = new SortingWithColor();
    PrototypeHoodFire hood = new PrototypeHoodFire();
    IntakeConfig intake = new IntakeConfig();
    ServoKick kick = new ServoKick();

    int pos = 0;

    double forward, strafe, rotate;
    public void setDriver(){
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);
    }
    public void setOperator(){

        if(gamepad2.right_trigger >= 0.7){
            telemetry.addLine("Hood motor is moving");
            hood.fireMotor();
        }else{
            telemetry.addLine("Hood motor is not moving");
            hood.stopMotor();
        }
        if(gamepad2.y){
            telemetry.addLine("Servo kick is kicking");
            kick.kick();
        }else{
            telemetry.addLine("Servo kick is not kicking");
            kick.retract();
        }

         if(gamepad2.left_trigger >= 0.7){
             telemetry.addLine("Intake motor is moving");
             intake.IntakeMotorMax();
         }else{
             telemetry.addLine("Intake motor is not moving");
             intake.IntakeMotorStop();
         }

        if(gamepad2.a){
            telemetry.addLine("Sorting green");
            setSorterGreen();
        }
        if(gamepad2.b) {
            telemetry.addLine("Sorting purple");
            setSorterPurple();
        }
        if(gamepad2.dpad_up) {
            telemetry.addLine("Setting Intake A");
            sorter.setIntakeA();
        }
        if(gamepad2.dpad_left) {
            telemetry.addLine("Setting Intake B");
            sorter.setIntakeB();
        }
        if(gamepad2.dpad_right) {
            telemetry.addLine("Setting Intake C");
            sorter.setIntakeC();
        }
    }

    public void setSorterPurple(){
        switch (pos){
            case 0:
                kick.retract();
                sorter.setOutA();
                if(colorSensor.detectColor().equals("Purple")){
                    kick.kick();
                }else{
                    pos = 1;
                }
                break;
            case 1:
                sorter.setOutB();
                if(colorSensor.detectColor().equals("Purple")){
                kick.kick();
            }else{
                    pos = 2;
                }
                break;
            case 2:
                sorter.setOutC();
                if(colorSensor.detectColor().equals("Purple")){
                    kick.kick();
                }else{
                    telemetry.addLine("ERROR");
                }
                break;
            default:
                sorter.setOutA();
                pos = 0;
                break;
        }
    }
    public void setSorterGreen() {
        switch (pos){
            case 0:
                kick.retract();
                sorter.setOutA();
                if(colorSensor.detectColor().equals("Green")){
                    kick.kick();
                }else{
                    pos = 1;
                }
                break;
            case 1:
                sorter.setOutB();
                if(colorSensor.detectColor().equals("Green")){
                    kick.kick();
                }else{
                    pos = 2;
                }
                break;
            case 2:
                sorter.setOutC();
                if(colorSensor.detectColor().equals("Green")){
                    kick.kick();
                }else{
                    telemetry.addLine("ERROR");
                }
                break;
            default:
                sorter.setOutA();
                pos = 0;
                break;
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {

        drive.init(hardwareMap);
        colorSensor.init(hardwareMap);
        intake.init(hardwareMap);
        sorter.init(hardwareMap);
        hood.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addLine("OpMode Is Active");

            setDriver();
            setOperator();

            telemetry.addData("Ball color is", colorSensor.detectColor());
            telemetry.update();
        }
    }
}
