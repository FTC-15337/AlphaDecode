package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.CM;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Mechanisms.LimeLightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.Pinpoint;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;


@Autonomous(name = "Autonomous")
public class Auto extends LinearOpMode
{
    // get an instance of the "Robot" class.
    private Pinpoint robot = new Pinpoint(this);

    @Override public void runOpMode() throws InterruptedException {
        // Initialize the robot hardware & Turn on telemetry
        robot.init(true);

        robot.resetHeading();

        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();
        // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        while (opModeIsActive() && !isStopRequested()) {
            robot.drive(12, 1.0, 1.0);

            telemetry.update();
        }
    }
}