package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.LearningJava.PrototypeHoodFire;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TelOp")

public class TeleOp extends LinearOpMode {

    PrototypeHoodFire motorMoveTest = new PrototypeHoodFire();
    MecDrivebase drive = new MecDrivebase();

    double forward, strafe, rotate;

    public void setDrive(){
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveFieldRelative(forward, strafe, rotate);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        drive.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addLine("OpMode is active");
            setDrive();

            telemetry.update();
        }
    }
}
