package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@Autonomous(name = "BLUE AUTO TOP")
public class BlueAutoTop extends LinearOpMode {
    private AutoConfig robot = new AutoConfig(this);
    private LimelightConfig ll = new LimelightConfig();
    private ShooterConfig shooter = new ShooterConfig();
    private SortingWithColor colorSensor = new SortingWithColor();
    private StorageConfig sorter = new StorageConfig();
    private ServoKick kick = new ServoKick();
    private IntakeConfig intake = new IntakeConfig();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(false);
        ll.init(hardwareMap);
        sorter.init(hardwareMap);
        kick.init(hardwareMap);
        shooter.init(hardwareMap);
        colorSensor.init(hardwareMap);
        intake.init(hardwareMap);

        sorter.setOutA();
        shooter.hoodAutoMed();
        kick.retract();


        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();

        if (opModeIsActive() && !isStopRequested()) {
            shooter.MedOut();
            robot.drive(-78, 0.5, 0.0);
            robot.turnTo(-57,1.0,0.10);
            if(ll.getId() == 23){
                robot.turnTo(-4,1.0,0.10);
                PPG();
            }
            if(ll.getId() == 22){
                robot.turnTo(-4,1.0,0.10);
                PGP();
            }
            if(ll.getId() == 21){
                robot.turnTo(-4,1.0,0.10);
                GPP();
            }
        }
    }

    public void PPG(){
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(500);
        robot.turnTo(45, 1.0, 0.05);
        sorter.setIntakeA();
        intake.IntakeMotorMax();
        robot.drive(33, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(200);
        robot.drive(9, 0.2, 0.10);
        sleep(200);
        sorter.setIntakeC();
        sleep(200);
        robot.drive(11, 0.2, 0.0);
        robot.drive(-45, 0.5, 0.0);
        sorter.setOutA();
        robot.turnTo(-4, 1.0, 0.0);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        shooter.Stop();
        intake.IntakeMotorStop();
        robot.turnTo(45, 1.0, 0.0);
        robot.drive(25, 1.0, 0.0);
    }
    public void PGP() {;
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(500);
        robot.turnTo(45, 1.0, 0.05);
        sorter.setIntakeA();
        intake.IntakeMotorMax();
        robot.drive(33, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(200);
        robot.drive(9, 0.2, 0.10);
        sleep(200);
        sorter.setIntakeC();
        sleep(200);
        robot.drive(11, 0.2, 0.0);
        robot.drive(-45, 0.5, 0.0);
        sorter.setOutA();
        robot.turnTo(-4, 1.0, 0.0);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        shooter.Stop();
        intake.IntakeMotorStop();
        robot.turnTo(45, 1.0, 0.0);
        robot.drive(25, 1.0, 0.0);
    }
    public void GPP() {
        sorter.setOutC();
        sleep(200);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutA();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(500);
        robot.turnTo(45, 1.0, 0.05);
        sorter.setIntakeA();
        intake.IntakeMotorMax();
        robot.drive(33, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(200);
        robot.drive(9, 0.2, 0.10);
        sleep(200);
        sorter.setIntakeC();
        sleep(200);
        robot.drive(11, 0.2, 0.0);
        robot.drive(-45, 0.5, 0.0);
        sorter.setOutC();
        robot.turnTo(-4, 1.0, 0.0);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        sleep(200);
        sorter.setOutA();
        sleep(750);
        kick.kick();
        sleep(1100);
        kick.retract();
        shooter.Stop();
        intake.IntakeMotorStop();
        robot.turnTo(45, 1.0, 0.0);
        robot.drive(25, 1.0, 0.0);
    }
}
