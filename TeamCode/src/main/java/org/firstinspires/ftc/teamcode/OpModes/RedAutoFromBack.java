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
    private Led led = new Led();

    double servoValue;
    static double[][] sortingValues;

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
        led.init(hardwareMap);


        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        sortingValues = new double[3][2];

        waitForStart();
        robot.resetHeading();
        telemetry.addData("The ID is",ll.getId());
        telemetry.update();


        if(opModeIsActive() && !isStopRequested()){
            telemetry.addData("Hood Pos is:", shooter.returnVal());
            led.startLed();
            robot.drive(6, 1.0, 0.0);
            robot.turnTo(7, 1.0, 0.25);
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
        robot.turnTo(-21, 1.0, 0.0);
        shooter.FarOut();
        sleep(1700);
        sorter.setOutA();
        sleep(1000);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutB();
        sleep(1000);
        kick.kick();
        sleep(1000);
        kick.retract();
        sleep(200);
        sorter.setOutC();
        sleep(1000);
        kick.kick();
        sleep(1000);
        kick.retract();
        shooter.Stop();
        robot.drive(33, 1.0, 0.0);
        robot.turnTo(-87.5, 1.0, 0.0);
        Intake();
        robot.drive(16, 0.2, 0.10);
        robot.drive(5, 0.2, 0.10);
        robot.drive(5, 0.2, 0.10);
        /*robot.drive(-37.5, 1.0, 0.0);
        robot.turnTo(15, 1.0, 0.0);
        robot.drive(-12, 1.0, 0.0);
        shooter.FarOut();
        OuttakeColor(1);
        OuttakeColor(1);
        OuttakeColor(2);*/

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
        robot.drive(35.3, 0.75, 0.25);
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
        robot.drive(67.5, 0.5, 0.25);
        robot.turnTo(-90, 0.5, 0.25);
        robot.drive(434, 0.75, 0.25);
    }

    public void Intake(){
        sorter.setIntakeA();
        intake.IntakeMotorMax();
        SortingWithColor.DetectedColor detectedColor = colorSensor.getDetectedColor(telemetry);
        servoValue = sorter.GetServoPos();
        telemetry.addData("Detected Color ", detectedColor);
        telemetry.addData("Servo Value ", servoValue);
        if (Math.abs(servoValue - 0.03) < 0.005) {
            sortingValues[0][0] = detectedColor.getCode();
            sortingValues[0][1] = Constants.sorterOutTakeA;

        } else if (Math.abs(servoValue - 0.105) < 0.005) {
            sortingValues[1][0] = detectedColor.getCode();
            sortingValues[1][1] = Constants.sorterOutTakeB;

        } else if (Math.abs(servoValue - 0.17) < 0.005) {
            sortingValues[2][0] = detectedColor.getCode();
            sortingValues[2][1] = Constants.sorterOutTakeC;
        }
    }

    public void OuttakeColor(int targetColor) {
        for (int index = 0; index < 3; index++) {

            double storedColor = sortingValues[index][0];
            double outPos = sortingValues[index][1];

            if (storedColor == targetColor) {

                sorter.setServo(outPos);

                sleep(700);
                kick.kick();
                sleep(1000);
                kick.retract();

                sortingValues[index][0] = 0;
                sortingValues[index][1] = 0;

                return;
            }
        }
    }

    /*robot.drive(18, 0.75, 0.0);
        //sleep(500);

        robot.turnTo(-27, 0.75, 0.25);
        //sleep(500);
        shooter.FarOut();
        sleep(500);
        sorter.setOutA();
        sleep(1250);
        kick.kick();
        sleep(1500);
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
        robot.drive(20, 0.75, 0.0);
        robot.turnTo(-90, 0.75, 0.10);
        Intake.IntakeMotorMax();
        sorter.setIntakeC();
        robot.drive(27, 0.5, 0.10);
        sorter.setIntakeB();
        robot.drive(10, 0.2, 0.25);
        sorter.setIntakeA();
        robot.drive(10, 0.2, 0.25);
        robot.drive(-36, 0.5, 0.0);
        robot.turnTo(15, 0.75, 0.0);
        robot.drive(-20, 0.75, 0.25);*/
}


