/* Created by Phil Malone. 2023.
    This class illustrates my simplified Odometry Strategy.
    It implements basic straight line motions but with heading and drift controls to limit drift.
    See the readme for a link to a video tutorial explaining the operation and limitations of the code.
 */

package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
//import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

/*
 * This OpMode illustrates an autonomous opmode using simple Odometry
 * All robot functions are performed by an external "Robot" class that manages all hardware interactions.
 * Pure Drive or Strafe motions are maintained using two Odometry Wheels.
 * The IMU gyro is used to stabilize the heading during all motions
 */

@Autonomous(name= "BLUE AUTO")
public class BlueAuto extends LinearOpMode
{
    // get an instance of the "Robot" class.
    private AutoConfig robot = new AutoConfig(this);
    private LimelightConfig ll = new LimelightConfig();
    private ShooterConfig shooter = new ShooterConfig();

    private SortingWithColor colorSensor = new SortingWithColor();
    private StorageConfig sorter = new StorageConfig();
    private ServoKick kick = new ServoKick();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the robot hardware & Turn on telemetry
        robot.initialize(true);
        ll.init(hardwareMap);
        sorter.init(hardwareMap);
        kick.init(hardwareMap);
        shooter.init(hardwareMap);
        colorSensor.init(hardwareMap);

        sorter.setIntakeA();
        kick.retract();


        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();  // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        if (opModeIsActive() && !isStopRequested()) {
            robot.drive(118, 1.0, 0.25);
            robot.turnTo(-10, 1.0, 1.0);

            if(ll.getId() == 23) {
                robot.turnTo(47, 0.75, 0.25);
                PPG();
            }
            if (ll.getId() == 22) {
                robot.turnTo(47, 0.75, 0.25);
                PGP();
            }
            if(ll.getId() == 21) {
                robot.turnTo(47, 0.75, 0.25);
                GPP();
            }
            if(ll.getId() != 21 && ll.getId()!= 22 && ll.getId() != 23){
                telemetry.addLine("No Tag Detected");
            }
            sleep(500);
            robot.drive(-18, 0.75, 1.0);
            robot.turnTo(137,0.75,1.0);
            telemetry.update();
        }
    }

    public void PPG() {
        //shooter.setPower2();
        sleep(1000);
        sorter.setOutA();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
        sorter.setOutB();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
        sorter.setOutC();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
    }

    public void GPP() {
        //shooter.setPower2();
        sleep(1000);
        sorter.setOutC();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
        sorter.setOutB();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
        sorter.setOutA();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
    }

    public void PGP() {

        //shooter.setPower2();
        sleep(1000);
        sorter.setOutA();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
        sorter.setOutC();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
        sorter.setOutB();
        sleep(400);
        kick.kick();
        sleep(500);
        kick.retract();
    }

    public void sortPurple(){

        sorter.setOutA();
        sleep(1500);

        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.PURPLE){
            return;
        }else{
            sorter.setOutC();
            sleep(1500);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.PURPLE){
            return;
        }else{
            sorter.setOutB();
            sleep(1500);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.PURPLE){
            return;
        }else{
            telemetry.addLine("ERROR NONE FOUND");
        }
    }

    public void sortGreen(){

        sorter.setOutA();
        sleep(1500);

        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.GREEN){
            return;
        }else{
            sorter.setOutC();
            sleep(1500);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.GREEN){
            return;
        }else{
            sorter.setOutB();
            sleep(1500);
        }
        if(colorSensor.getDetectedColor(telemetry) == SortingWithColor.DetectedColor.GREEN){
            return;
        }else{
            telemetry.addLine("ERROR NONE FOUND");
        }
    }
}