package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "MecBase")
public class TeleOp extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();
    double forward, strafe, rotate;

    @Override
    public void runOpMode() throws InterruptedException {

        drive.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addLine("OpMode is active");
            forward = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            drive.driveFieldRelative(forward, strafe, rotate);

            telemetry.update();
        }
    }
}
