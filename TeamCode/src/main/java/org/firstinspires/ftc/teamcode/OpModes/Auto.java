package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.LLConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Pinpoint;


@Autonomous(name = "Autonomous")
public class Auto extends LinearOpMode
{

    private LLConfig ll = new LLConfig();

    @Override public void runOpMode() throws InterruptedException {


        telemetry.addLine("Initialized");
        ll.init(hardwareMap);
        telemetry.update();

        waitForStart();
        // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        while (opModeIsActive() && !isStopRequested()) {
            ll.update();

            ll.printTagData(telemetry);

            telemetry.update();
        }
    }
}