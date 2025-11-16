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
    //input: strafe + is left //turn + is left
    //input: strafe - is right //turn - is right
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

        sorter.setOutA();
        shooter.hoodMed();
        kick.retract();


        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();

        if(opModeIsActive() && !isStopRequested()){
            shooter.MedOut();
            robot.strafe(-50, 1.0, 0.0);
            robot.turnTo(-25, 1.0, 0.25);
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
            } else {
                telemetry.addData("Tag", ll.getId());
            }
            shooter.Stop();
            telemetry.update();
        }



    }


    public void PPG() {
        robot.turnTo(60, 1.0, 0.0);
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
        sleep(500);
        sorter.setIntakeA();
        robot.turnTo(90, 1.0, 0.0);
        robot.strafe(27, 1.0, 0.0);
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
