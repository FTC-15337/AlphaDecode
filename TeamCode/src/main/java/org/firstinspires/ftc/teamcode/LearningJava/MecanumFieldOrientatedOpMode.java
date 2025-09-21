package org.firstinspires.ftc.teamcode.LearningJava;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MecBase")
public class MecanumFieldOrientatedOpMode extends OpMode {

    MecDrivebase drive = new MecDrivebase();

    double forward, strafe, rotate;

    @Override
    public void init() {
        drive.init(hardwareMap);
        telemetry.addLine("Initialized");
    }

    @Override
    public void loop() {
        telemetry.addData("Left Joystick Y ", gamepad1.left_stick_y);
        telemetry.addData("Left Joystick X", gamepad1.left_stick_x);
        forward = gamepad1.left_stick_y;
        strafe = -gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        drive.drive(forward, strafe, rotate);
    }
}
