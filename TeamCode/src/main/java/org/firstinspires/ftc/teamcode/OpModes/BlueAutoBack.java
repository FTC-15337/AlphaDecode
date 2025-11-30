package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Led;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;




@Autonomous(name = "BLUE AUTO BACK")
public class BlueAutoBack extends LinearOpMode {

    private AutoConfig robot = new AutoConfig(this);
    private LimelightConfig ll = new LimelightConfig();
    private ShooterConfig shooter = new ShooterConfig();
    private SortingWithColor colorSensor = new SortingWithColor();
    private StorageConfig sorter = new StorageConfig();
    private ServoKick kick = new ServoKick();
    private IntakeConfig intake = new IntakeConfig();
    private Led led = new Led();

    static double[][] sortingValues;

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
        kick.retract();
        led.init(hardwareMap);

        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        sortingValues = new double[3][2];

        shooter.hoodAutoFar();

        waitForStart();
        robot.resetHeading();
        telemetry.addData("The ID is",ll.getId());
        telemetry.update();


        if(opModeIsActive() && !isStopRequested()){
            robot.drive(6, 1.0, 0.0);
            if(ll.getId() == 23) {
                PPG();
            }
            if (ll.getId() == 22) {
                PGP();
            }
            if(ll.getId() == 21) {
                GPP();
            }
            if(ll.getId() != 21 && ll.getId()!= 22 && ll.getId() != 23){
                telemetry.addLine("No Tag Detected");
            }
            shooter.Stop();
            telemetry.update();
        }
    }


    public void PPG() {
        shooter.FarOut();
        robot.turnTo(22, 1.0, 2.0);
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        shooter.hoodFar();
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(800);
        kick.kick();
        sleep(1000);
        kick.retract();
        sorter.setIntakeC();
        intake.IntakeMotorMax();
        robot.drive(36, 0.5, 0.10);
        robot.turnTo(90, 1.0, 0.0);
        robot.drive(15.5, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(200); //325
        robot.drive(8, 0.2, 0.25);
        sleep(200); //425
        sorter.setIntakeA();
        robot.drive(10, 0.2, 0.10);
        robot.drive(-30, 1.0, 0.0);
        robot.turnTo(22, 1.0, 0.0);
        robot.drive(-32, 1.0, 0.0);
        intake.IntakeMotorStop();
        sorter.setOutA();
        sleep(800);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        shooter.Stop();
        robot.drive(18, 1.0, 0.0);
        robot.turnTo(90, 1.0, 0.0);
        sleep(500);
    }

    public void GPP() {
        shooter.FarOut();
        robot.turnTo(22, 1.0, 2.0);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        shooter.hoodFar();
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutA();
        sleep(800);
        kick.kick();
        sleep(1000);
        kick.retract();
        sorter.setIntakeC();
        intake.IntakeMotorMax();
        robot.drive(36, 0.5, 0.10);
        robot.turnTo(90, 1.0, 0.0);
        robot.drive(15.5, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(200); //325
        robot.drive(8, 0.2, 0.25);
        sleep(200); //425
        sorter.setIntakeA();
        robot.drive(10, 0.2, 0.10);
        robot.drive(-30, 1.0, 0.0);
        robot.turnTo(22, 1.0, 0.0);
        robot.drive(-32, 1.0, 0.0);
        intake.IntakeMotorStop();
        sorter.setOutC();
        sleep(800);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutA();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        shooter.Stop();
        robot.drive(18, 1.0, 0.0);
        robot.turnTo(90, 1.0, 0.0);
        sleep(500);
    }

    public void PGP() {
        shooter.FarOut();
        robot.turnTo(22, 1.0, 2.0);
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        shooter.hoodFar();
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(800);
        kick.kick();
        sleep(1000);
        kick.retract();
        sorter.setIntakeC();
        intake.IntakeMotorMax();
        robot.drive(36, 0.5, 0.10);
        robot.turnTo(90, 1.0, 0.0);
        robot.drive(15.5, 0.2, 0.10);
        sorter.setIntakeB();
        sleep(200); //325
        robot.drive(8, 0.2, 0.25);
        sleep(200); //425
        sorter.setIntakeA();
        robot.drive(10, 0.2, 0.10);
        robot.drive(-30, 1.0, 0.0);
        robot.turnTo(22, 1.0, 0.0);
        robot.drive(-32, 1.0, 0.0);
        intake.IntakeMotorStop();
        sorter.setOutA();
        sleep(800);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(750);
        kick.kick();
        sleep(1000);
        kick.retract();
        shooter.Stop();
        robot.drive(18, 1.0, 0.0);
        robot.turnTo(90, 1.0, 0.0);
        sleep(500);
    }
}


