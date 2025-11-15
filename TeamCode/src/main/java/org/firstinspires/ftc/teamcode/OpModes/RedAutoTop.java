package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
//import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@Autonomous(name = "RED AUTO TOP")
public class RedAutoTop extends LinearOpMode {
    private AutoConfig robot = new AutoConfig(this);
    private LimelightConfig ll = new LimelightConfig();
    private ShooterConfig shooter = new ShooterConfig();
    private SortingWithColor colorSensor = new SortingWithColor();
    private StorageConfig sorter = new StorageConfig();
    private ServoKick kick = new ServoKick();
    private IntakeConfig intake = new IntakeConfig();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot hardware & Turn on telemetry
        robot.initialize(true);
        ll.init(hardwareMap);
        sorter.init(hardwareMap);
        kick.init(hardwareMap);
        shooter.init(hardwareMap);
        colorSensor.init(hardwareMap);
        intake.init(hardwareMap);

        sorter.setIntakeA();
        kick.retract();


        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();

        if(opModeIsActive() && !isStopRequested()){
            robot.drive(76, 0.75, 0.25);
            robot.turnTo(120, 0.5   , 1.0);
            if(ll.getId() == 23) {
                robot.turnTo(180, 0.5, 0.25);
                robot.turnTo(-180, 0.5, 0.25);
                PPG();
            }
            if (ll.getId() == 22) {
                robot.turnTo(180, 0.5, 0.25);
                PGP();
            }
            if(ll.getId() == 21) {
                robot.turnTo(180, 0.5, 0.25);
                GPP();
            }
            if(ll.getId() != 21 && ll.getId()!= 22 && ll.getId() != 23){
                telemetry.addLine("No Tag Detected");
            } else {
                telemetry.addData("Tag", ll.getId());
            }
            sleep(500);
            shooter.Stop();
            robot.turnTo(45,0.75,0.25);
            robot.strafe(-36, 0.75, 0.25);
            robot.turnTo(-135,0.75,0.25);
            robot.strafe(30, 0.75, 1.0);
            telemetry.update();
        }
        /*if(opModeIsActive() && !isStopRequested()){
            robot.strafe(5, 1.0, 0.25);
            if(ll.getId() == 23) {
                PPG();
            }
            if (ll.getId() == 22) {
                robot.turnTo(-180, 0.5, 0.25);
                PGP();
            }
            if(ll.getId() == 21) {
                robot.turnTo(-180, 0.5, 0.25);
                GPP();
            }
            if(ll.getId() != 21 && ll.getId()!= 22 && ll.getId() != 23){
                telemetry.addLine("No Tag Detected");
            } else {
                telemetry.addData("Tag", ll.getId());
            }
            sleep(500);
            telemetry.update();
        }*/
    }

    public void PPG() {
        robot.turnTo(70, 1.0, 0.0);
        shooter.MedOut();
        sleep(1500);
        sorter.setOutA();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutB();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutC();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        shooter.Stop();
        robot.turnTo(20,1.0,0.0);
        robot.strafe(-30, 1.0, 0.0);
        intake.IntakeMotorMax();
        robot.drive(15, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(1000);
        robot.drive(7, 0.2, 0.25);
        sleep(1000);
        sorter.setIntakeA();
        robot.drive(10, 0.2, 0.10);
        intake.IntakeMotorStop();
        /*robot.drive(-45, 1.0, 0.25);
        robot.turnTo(-135, 1.0, 0.25);
        shooter.MedOut();
        sleep(1500);
        sorter.setOutA();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutB();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutC();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        shooter.Stop();
        robot.turnTo(135,0.75,0.25);
        robot.strafe(-30, 0.75, 1.0);*/
    }

    public void GPP() {
        shooter.MedOut();
        sleep(1500);
        sorter.setOutC();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutB();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutA();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        shooter.Stop();
        robot.turnTo(135,0.75,0.25);
        robot.strafe(-30, 0.75, 1.0);
        intake.IntakeMotorMax();
        sorter.setIntakeA();
        robot.drive(46, 0.3, 0.25);
        sorter.setIntakeB();
        robot.drive(7.5, 0.3, 0.25);
        sorter.setIntakeC();
        robot.drive(7.5, 0.3, 0.25);
        intake.IntakeMotorStop();
        robot.drive(-45, 1.0, 0.25);
        robot.turnTo(-135, 1.0, 0.25);
        shooter.MedOut();
        sleep(1500);
        sorter.setOutC();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutB();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutA();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        shooter.Stop();
        robot.turnTo(135,0.75,0.25);
        robot.strafe(-30, 0.75, 1.0);
    }

    public void PGP() {
        shooter.MedOut();
        sleep(1500);
        sorter.setOutA();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutC();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
        sorter.setOutB();
        sleep(1250);
        kick.kick();
        sleep(1250);
        kick.retract();
    }
}
