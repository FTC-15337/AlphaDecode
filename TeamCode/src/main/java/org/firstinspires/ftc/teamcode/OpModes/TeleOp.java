package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
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
    AutoConfig robot = new AutoConfig(this);

    double forward, strafe, rotate;
    public void setDriver(){
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
        telemetry.update();
    }
    public void setOperator(){
        telemetry.addData("Color is" , colorSensor.getDetectedColor(telemetry));

        if(gamepad2.y){
            kick.kick();
        }else{
            kick.retract();
        }

        if(gamepad2.left_trigger >= 0.7){
             intake.IntakeMotorMax();
        }else{
             intake.IntakeMotorStop();
        }

        if(gamepad2.right_bumper) {
            sortPurple();
            kick.kick();
            sleep(1500);
            kick.retract();
        }

        if(gamepad2.left_bumper){
            sortGreen();
            kick.kick();
            sleep(1500);
            kick.retract();
        }

        if(gamepad2.dpad_down) {
            telemetry.addLine("Sorter IA");
            sorter.setIntakeA();
        }
        if(gamepad2.dpad_right) {
            telemetry.addLine("Sorter IC");
            sorter.setIntakeB();
        }
        if(gamepad2.dpad_left) {
            telemetry.addLine("Sorter IB");
            sorter.setIntakeC();
        }
        if (gamepad2.dpad_up) {
            intake.OutIntake();
        }
        if(gamepad2.a) {
            telemetry.addLine("Sorter OA");
            sorter.setOutA();
            telemetry.update();
        }
        if(gamepad2.b) {
            telemetry.addLine("Sorter OB");
            sorter.setOutB();
            telemetry.update();
        }
        if(gamepad2.x) {
            telemetry.addLine("Sorter OC");
            sorter.setOutC();
            telemetry.update();
        }
        telemetry.update();
    }

    public void sortPurple(){

        sorter.setOutA();
        sleep(1000);

        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.PURPLE){
            return;
        }else{
            sorter.setOutC();
            sleep(1000);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.PURPLE){
            return;
        }else{
            sorter.setOutB();
            sleep(1000);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.PURPLE){
            return;
        }else{
            telemetry.addLine("ERROR NONE FOUND");
        }
    }

    public void sortGreen(){

        sorter.setOutA();
        sleep(1000);

        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.GREEN){
            return;
        }else{
            sorter.setOutC();
            sleep(1000);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.GREEN){
            return;
        }else{
            sorter.setOutB();
            sleep(1000);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.GREEN){
            return;
        }else{
            telemetry.addLine("ERROR NONE FOUND");
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

        kick.retract();
        drive.imu.resetYaw();

        sorter.resetToIntake();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            setDriver();
            setOperator();

            telemetry.update();
        }
    }
}
