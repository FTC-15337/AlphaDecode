package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
    }
    public void setOperator(){

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
            outWithoutSort();
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
            setSorterGreen();
        }
        if(gamepad2.right_bumper) {
            telemetry.addLine("PURPLE SORT");
            setSorterPurple();
        }

        if(gamepad2.dpad_up) {
            telemetry.addLine("Sorter IA");
            sorter.setIntakeA();
        }
        if(gamepad2.dpad_left) {
            telemetry.addLine("Sorter IB");
            sorter.setIntakeB();
        }
        if(gamepad2.dpad_right) {
            telemetry.addLine("Sorter IC");
            sorter.setIntakeC();
        }
        if(gamepad2.a) {
            telemetry.addLine("Sorter OA");
            sorter.setOutA();
        }
        if(gamepad2.x) {
            telemetry.addLine("Sorter OB");
            sorter.setOutB();
        }
        if(gamepad2.b) {
            telemetry.addLine("Sorter OC");
            sorter.setOutC();
        }
        if(gamepad2.start) {
            sorter.setZero();
        }
    }

    public void outWithoutSort(){
        kick.retract();
        shooter.fireMotor();

        sorter.setOutA();
        kick.kick();

        kick.retract();
        sorter.setOutB();
        kick.kick();

        kick.retract();
        sorter.setOutC();
        kick.kick();

        sorter.resetToIntake();

    }

    public void setSorterPurple(){
        switch (posP){
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
        switch (posG){
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
                    pos = -1;
                }
                break;
            default:
                kick.retract();
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
