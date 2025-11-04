package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends LinearOpMode {
    MecDrivebase drive = new MecDrivebase();
    StorageConfig sorter = new StorageConfig();
    SortingWithColor colorSensor = new SortingWithColor();
    ShooterConfig shooter = new ShooterConfig();
    IntakeConfig intake = new IntakeConfig();
    ServoKick kick = new ServoKick();

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

//        if(gamepad1.right_trigger >= 0.7 && gamepad1.dpad_up) {
//            shooter.setPower1();
//        } else
        if (gamepad1.right_trigger >= 0.7 && gamepad1.dpad_left) {
            shooter.setPower2();
        } else if (gamepad1.right_trigger >= 0.7 && gamepad1.dpad_right) {
            shooter.setPower3();
        } else if (gamepad1.right_trigger >= 0.7 && gamepad1.dpad_down) {
            shooter.setPower4();
        }else{
            shooter.stopMotor();
        }
    }
    public void setOperator(){

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
        if(gamepad2.a) {
            telemetry.addLine("Sorter OA");
            sorter.setOutA();
        }
        if(gamepad2.b) {
            telemetry.addLine("Sorter OB");
            sorter.setOutB();
        }
        if(gamepad2.x) {
            telemetry.addLine("Sorter OC");
            sorter.setOutC();
        }
        telemetry.update();
    }
    public void sortPurple(){

        sorter.setOutA();
        sleep(800);

        if(colorSensor.detectColor().equals("Purple")){
            return;
        }else{
            sorter.setOutC();
            sleep(800);
        }
        if(colorSensor.detectColor().equals("Purple")){
            return;
        }else{
            sorter.setOutB();
            sleep(800);
        }
        if(colorSensor.detectColor().equals("Purple")){
            return;
        }else{
            telemetry.addLine("ERROR NONE FOUND");
        }
    }
    public void sortGreen(){

        sorter.setOutA();
        sleep(800);

        if(colorSensor.detectColor().equals("Green")){
            return;
        }else{
            sorter.setOutC();
            sleep(800);
        }
        if(colorSensor.detectColor().equals("Green")){
            return;
        }else{
            sorter.setOutB();
            sleep(800);
        }
        if(colorSensor.detectColor().equals("Green")){
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

        sorter.resetToIntake();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            setDriver();
            setOperator();
        }
    }
}
