package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;

@Autonomous(name = "TestStrafe")
public class StrafeAutoTest extends LinearOpMode {
    private AutoConfig robot = new AutoConfig(this);
    private IntakeConfig intake = new IntakeConfig();

    @Override
    public void runOpMode() {
        robot.initialize(false);
        intake.init(hardwareMap);

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
