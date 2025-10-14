package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.PrototypeHoodFire;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();
    StorageConfig sorter = new StorageConfig();
    SortingWithColor colorSensor = new SortingWithColor();
    PrototypeHoodFire hood = new PrototypeHoodFire();
    IntakeConfig intake = new IntakeConfig();

    double forward, strafe, rotate;
    public void setDriver(){
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);
    }
    public void setOperator(){
        if(gamepad1.left_trigger >= 0.7){
            intake.IntakeMotorMax();
        }else{
            intake.IntakeMotorStop();
        }
        //shooter
        if(gamepad1.right_trigger >= 0.7){
            hood.fireMotor();
        }else{
            hood.stopMotor();
        }
        //sorter
        if(gamepad1.dpad_up){
            sorter.setIntakeA();
        }
        if(gamepad1.dpad_down){
            sorter.setIntakeB();
        }
        if(gamepad1.dpad_left){
            sorter.setIntakeC();
        }
        //Checks each slot until purple ball is detected
        if(gamepad1.a){
            setSorterGreen();
        }
        if(gamepad1.b) {
            setSorterPurple();
        }
    }
    public void setSorterPurple(){
        sorter.setOutA();
        colorSensor.detectColor();
        if(colorSensor.detectColor().equals("Purple")) {
            sorter.setOutA();
        } else if(colorSensor.detectColor().equals("Green")){
            sorter.setOutB();
            if(colorSensor.detectColor().equals("Purple")){
                sorter.setOutB();
            } else if(colorSensor.detectColor().equals("Green")) {
                sorter.setOutC();
                if(colorSensor.detectColor().equals("Purple")){
                    sorter.setOutC();
                } else if(colorSensor.detectColor().equals("Green")){
                    sorter.setOutA();
                }
            }
        }
    }

    public void setSorterGreen(){
        sorter.setOutA();
        colorSensor.detectColor();
        if(colorSensor.detectColor().equals("Green")) {
            sorter.setOutA();
        } else if(colorSensor.detectColor().equals("Purple")){
            sorter.setOutB();
            if(colorSensor.detectColor().equals("Green")){
                sorter.setOutB();
            } else if(colorSensor.detectColor().equals("Purple")) {
                sorter.setOutC();
                if(colorSensor.detectColor().equals("Green")){
                    sorter.setOutC();
                } else if(colorSensor.detectColor().equals("Purple")){
                    sorter.setOutA();
                }
            }
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
