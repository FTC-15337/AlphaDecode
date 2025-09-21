package org.firstinspires.ftc.teamcode.LearningJava;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp(name = "MecBase")
public class MecanumFieldOrientatedOpMode extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();

    double forward, strafe, rotate;

    @Override
    public void runOpMode() throws InterruptedException {

        drive.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();

        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .build();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addData("Left Joystick Y ", gamepad1.left_stick_y);
            telemetry.addData("Left Joystick X", gamepad1.left_stick_x);
            forward = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            drive.driveFieldRelative(forward, strafe, rotate);

            if(tagProcessor.getDetections().size() > 0) {
                AprilTagDetection tag = tagProcessor.getDetections().get(0);

                telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("y", tag.ftcPose.y);
                telemetry.addData("z", tag.ftcPose.z);
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("yaw", tag.ftcPose.yaw);
            }
            telemetry.update();
        }
    }
}
