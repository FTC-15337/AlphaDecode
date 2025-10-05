package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.ConstantValues.Constants;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.Pinpoint;
import org.firstinspires.ftc.teamcode.Mechanisms.PrototypeHoodFire;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

import java.util.Objects;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

public class TeleOp extends LinearOpMode {

    MecDrivebase drive = new MecDrivebase();
    StorageConfig storage = new StorageConfig();
    SortingWithColor sorting = new SortingWithColor();
    Pinpoint pinPoint = new Pinpoint();
    ServoKick kick = new ServoKick();
    PrototypeHoodFire hood = new PrototypeHoodFire();

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
        sorting.init(hardwareMap);
        storage.init(hardwareMap);
        pinPoint.init(hardwareMap);
        pinPoint.pinpointInit();
        kick.init(hardwareMap);
        hood.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(!isStopRequested() && opModeIsActive()) {
            telemetry.addLine("OpMode Is Active");
            setDrive();

            telemetry.addLine("checking color");
            telemetry.addData("R", sorting.getRed());
            telemetry.addData("B", sorting.getBlue());
            telemetry.addData("G", sorting.getGreen());

            pinPoint.pinpoint.update();

            Pose2D pose2D = pinPoint.pinpoint.getPosition();

            if(gamepad1.a){
                sorting.detectColor();
                if(sorting.detectColor().equals("Green")){
                    kick.kick();
                    sleep(600);
                    kick.retract();
                    telemetry.addData("Ball color is", sorting.detectColor());

                }
            }

            if(gamepad1.b){
                sorting.detectColor();
                if(sorting.detectColor().equals("Purple")){
                    kick.kick();
                    sleep(600);
                    kick.retract();
                    telemetry.addData("Ball color is", sorting.detectColor());

                }
            }

            if(gamepad1.right_trigger >= 0.7) {
                hood.moveMotor();
            } else {
                hood.stopMotor();
            }

            telemetry.addData("X Coordinate (IN)", pose2D.getX(DistanceUnit.INCH));
            telemetry.addData("Y Coordinate (IN)", pose2D.getY(DistanceUnit.INCH));
            telemetry.addData("Heading Angle (Degrees)", pose2D.getHeading(AngleUnit.DEGREES));

            telemetry.update();
        }
    }
}
