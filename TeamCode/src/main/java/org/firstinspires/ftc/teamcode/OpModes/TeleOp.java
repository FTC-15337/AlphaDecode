package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode {
    MecDrivebase drive = new MecDrivebase();
    StorageConfig sorter = new StorageConfig();
    SortingWithColor colorSensor = new SortingWithColor();
    ShooterConfig shooter = new ShooterConfig();
    IntakeConfig intake = new IntakeConfig();
    ServoKick kick = new ServoKick();

    int pos = 0;
    int posP = 0;
    int posG = 0;

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
    }
    public void setOperator(){

        telemetry.addData("Color sensor g" , colorSensor.getGreen());
        telemetry.addData("Color sensor b" , colorSensor.getBlue());
        telemetry.addData("Color sensor r" , colorSensor.getRed());

        if(gamepad2.right_trigger >= 0.7){
            telemetry.addLine("Hood motor is firing");
            shooter.fireMotor();
        }else{
            telemetry.addLine("Hood motor is stopped");
            shooter.stopMotor();
        }

        if(gamepad2.y){
            telemetry.addLine("Servo kick is kicking");
            kick.kick();
        }else{
            telemetry.addLine("Servo kick retracted");
            kick.retract();
        }

        if(gamepad2.right_stick_button){
            fireAtRandom();
        }

         if(gamepad2.left_trigger >= 0.7){
             telemetry.addLine("Intaking");
             intake.IntakeMotorMax();
         }else{
             telemetry.addLine("Intake motor is not moving");
             intake.IntakeMotorStop();
         }

        if(gamepad2.left_bumper){
            telemetry.addLine("GREEN SORT");
            sortGreen();
        }
        if(gamepad2.right_bumper) {
            telemetry.addLine("PURPLE SORT");
            sortPurple();
        }
        if(gamepad2.dpad_up) {
            telemetry.addLine("Outtake Without Sort");
            fireAtRandom();
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
        if(gamepad2.a) {
            telemetry.addLine("Sorter OA");
            sorter.setOutA();
        }
        if(gamepad2.b) {
            telemetry.addLine("Sorter OC");
            sorter.setOutB();
        }
        if(gamepad2.x) {
            telemetry.addLine("Sorter OB");
            sorter.setOutC();
        }
    }

    public void fireAtRandom(){
        switch (pos){
            case 0:
                kick.retract();
                sorter.setOutA();
                kick.kick();
                pos = 1;
                break;
            case 1:
                kick.retract();
                sorter.setOutB();
                kick.kick();
                pos = 2;
                break;
            case 2:
                kick.retract();
                sorter.setOutC();
                kick.kick();
                pos = -1;
                break;
            default:
                kick.retract();
                sorter.setIntakeA();
        }
    }
    public void sortPurple(){
        sorter.setOutA();
        if(colorSensor.detectColor().equals("Purple")){
            telemetry.addLine("KICK");
            sorter.setOutA();
            sleep(100);
        }else{
            sorter.setOutB();
        }
        if(colorSensor.detectColor().equals("Purple")){
            telemetry.addLine("KICK");
            sorter.setOutB();
            sleep(100);
        }else{
            sorter.setOutC();
        }
        if(colorSensor.detectColor().equals("Purple")){
            telemetry.addLine("KICK");
            sorter.setOutC();
            sleep(100);
        }else{
            telemetry.addLine("ERROR");
        }
    }
    public void sortGreen(){
        sorter.setOutA();
        if(colorSensor.detectColor().equals("Green")){
            telemetry.addLine("KICK");
            sorter.setOutA();
            sleep(100);
        }else{
            sorter.setOutB();
        }
        if(colorSensor.detectColor().equals("Green")){
            telemetry.addLine("KICK");
            sorter.setOutB();
            sleep(100);
        }else{
            sorter.setOutC();
        }
        if(colorSensor.detectColor().equals("Green")){
            telemetry.addLine("KICK");
            sorter.setOutC();
            sleep(100);
        }else{
            telemetry.addLine("ERROR");
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
