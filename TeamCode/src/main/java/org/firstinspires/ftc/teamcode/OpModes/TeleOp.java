package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.Pinpoint;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();
    StorageConfig sorter = new StorageConfig();
    SortingWithColor colorSensor = new SortingWithColor();
    Pinpoint pinPoint = new Pinpoint();

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
        colorSensor.init(hardwareMap);
        sorter.init(hardwareMap);
        pinPoint.init(hardwareMap);
        pinPoint.pinpointInit();
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addLine("OpMode Is Active");
            setDrive();

            telemetry.addData("Ball color is", colorSensor.detectColor());

            pinPoint.pinpoint.update();

            Pose2D pose2D = pinPoint.pinpoint.getPosition();

            telemetry.addData("X Coordinate (IN)", pose2D.getX(DistanceUnit.INCH));
            telemetry.addData("Y Coordinate (IN)", pose2D.getY(DistanceUnit.INCH));
            telemetry.addData("Heading Angle (Degrees)", pose2D.getHeading(AngleUnit.DEGREES));

            telemetry.update();
        }
    }
}
