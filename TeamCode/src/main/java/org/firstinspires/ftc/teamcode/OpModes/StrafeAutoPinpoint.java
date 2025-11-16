package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig2;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;

@Autonomous(name = "TestStrafePinpoint")
public class StrafeAutoPinpoint extends LinearOpMode {
    private AutoConfig2 robot = new AutoConfig2(this);
    private IntakeConfig intake = new IntakeConfig();

    @Override
    public void runOpMode() {
        robot.initialize(false);

        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        robot.resetHeading();

        if(opModeIsActive() && !isStopRequested()) {
            telemetry.addLine("In OpMode");
            robot.strafe(10, 0.5, 1.0);
            intake.IntakeMotorMax();
            telemetry.update();
        }
    }
}
