package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Shooter Intake Test", group = "Test")
public class ShooterIntakeTest extends OpMode {
    private DcMotor intake;
    private DcMotor shooter;
    private Servo kick_arm;
    private CRServo kick_wheel;

    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intakeMotor");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        kick_arm = hardwareMap.get(Servo.class, "kickArm");
//        kick_arm.setPosition(0.4);
//        kick_wheel = hardwareMap.get(CRServo.class, "kickWheel");
    }

    public void loop() {
        if (gamepad1.right_bumper) {
            intake.setPower(-0.5);
        }
        if (gamepad1.a) {
            intake.setPower(0.0);
            shooter.setPower(0.0);
        }
        if (gamepad1.left_bumper) {
            shooter.setPower(1);
        }
//        if (gamepad1.x) {
//            telemetry.addLine("Getting into launch position.");
//            kick_wheel.setPower(-1.0);
//            kick_arm.setPosition(0.0);
//        }
//        if (gamepad1.y) {
//            telemetry.addLine("Getting out of launch position.");
//            kick_wheel.setPower(0.0);
//            kick_arm.setPosition(0.4);
//        }
//        if (gamepad1.dpad_down) {
//            telemetry.addLine("Resetting all positions and stopping.");
//            intake.setPower(0.0);
//            shooter.setPower(0.0);
//            kick_arm.setPosition(0.0);
//            kick_wheel.setPower(0.0);
//        }
    }
}
