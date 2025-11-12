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




@Autonomous(name = "RED AUTO FROM BACK")
public class RedAutoFromBack extends LinearOpMode {
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
        sorter.setIntakeA();
        kick.retract();


        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();
        telemetry.addData("The ID is",ll.getId());
        telemetry.update();


        if(opModeIsActive() && !isStopRequested()){

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
            sleep(500);
            shooter.Stop();
            telemetry.update();
        }
    }


    public void PPG() {
        robot.drive(18, 0.75, 0.0);
        //sleep(500);

        robot.turnTo(-27, 0.75, 0.0);
        //sleep(500);
        shooter.FarOut();
        sleep(500);
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
        robot.drive(18, 0.75, 0.0);
        robot.turnTo(-88, 0.75, 0.10);
        intake.IntakeMotorMax();
        sorter.setIntakeA();
        robot.drive(28, 0.2, 0.0);
        sorter.setIntakeB();

    }

    public void GPP() {
        robot.drive(18, 0.75, 0.25);
        sleep(500);
        robot.turnTo(-30, 0.5, 0.25);
        sleep(500);
        shooter.FarOut();
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
        robot.turnTo(30, 0.5, 0.25);
        robot.drive(35.3,0.75, 0.25);
        robot.turnTo(-90, 0.5, 0.25);
        intake.IntakeMotorMax();
        robot.drive(43, 0.3, 0.25);
        sorter.setIntakeC();
        robot.drive(7.5, 0.3, 0.25);
        sorter.setIntakeB();
        robot.drive(7.5, 0.3, 0.25);
        sorter.setIntakeA();
        intake.IntakeMotorStop();
        robot.drive(-57.5, 0.75, 0.25);
        robot.turnTo(90, 0.5, 0.25);
        robot.drive(-34.5, 0.75, 0.25);
        robot.turnTo(30, 0.5, 0.25);
        shooter.FarOut();
        sleep(1500);
        sorter.setOutC();
        sleep(1500);
        kick.kick();
        sleep(1500);
        sorter.setOutB();
        sleep(1500);
        kick.kick();
        sleep(1500);
        sleep(1500);
        sorter.setOutA();
        sleep(1500);
        kick.kick();
        robot.drive(67.5,0.5, 0.25);
        robot.turnTo(-90, 0.5, 0.25);
        robot.drive(434, 0.75, 0.25);
        }

    public void PGP() {
        robot.drive(18, 0.75, 0.25);
        sleep(500);

        robot.turnTo(-30, 0.5, 0.25);
        sleep(500);
        shooter.FarOut();
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
        robot.turnTo(30, 0.5, 0.25);
        robot.drive(46, 0.5, 0.25);
        robot.turnTo(30, 0.5, 0.25);
        robot.drive(35.3,0.75, 0.25);
        robot.turnTo(-90, 0.5, 0.25);
        intake.IntakeMotorMax();
        robot.drive(43, 0.3, 0.25);
        sorter.setIntakeC();
        robot.drive(7.5, 0.3, 0.25);
        sorter.setIntakeB();
        robot.drive(7.5, 0.3, 0.25);
        sorter.setIntakeA();
        intake.IntakeMotorStop();
        robot.drive(-57.5, 0.75, 0.25);
        robot.turnTo(90, 0.5, 0.25);
        robot.drive(-34.5, 0.75, 0.25);
        robot.turnTo(30, 0.5, 0.25);
        shooter.FarOut();
        sleep(1500);
        sorter.setOutA();
        sleep(1500);
        kick.kick();
        sleep(1500);
        sorter.setOutC();
        sleep(1500);
        kick.kick();
        sleep(1500);
        sleep(1500);
        sorter.setOutB();
        sleep(1500);
        kick.kick();
        robot.drive(67.5,0.5, 0.25);
        robot.turnTo(-90, 0.5, 0.25);
        robot.drive(434, 0.75, 0.25);
    }
}


