package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;

    /**
     * AutoPinpointStraight
     * Moves the robot in a straight line using GoBilda Pinpoint odometry.
     */
    @Autonomous
    public class Auto extends LinearOpMode {

        MecDrivebase mecDrivebase = new MecDrivebase();
        GoBildaPinpointDriver pinpoint;
        ElapsedTime timer = new ElapsedTime();

        @Override
        public void runOpMode() throws InterruptedException {
            // --- Initialize drivebase and odometry ---
            mecDrivebase.init(hardwareMap);

            pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
            pinpoint.initialize();

            // Adjust these offsets for your robot’s pinpoint mounting position
            // Why is this defined over here?
            pinpoint.setOffsets(101.6, 177.8, MM);

            // Reset to 0,0,0 start position
            // Already does that
            pinpoint.resetPosAndIMU();

            telemetry.addLine("Init Active");
            telemetry.update();

            waitForStart();

            // --- Move straight forward 500 mm (about 20 inches) ---
//        goToPoint(500.0, 0.0, 6.0);
            driveToPos(500,0);
            stopDrive();

            telemetry.addLine("Reached Target Position");
            telemetry.update();
            sleep(1000);
        }

        /**
         * Moves robot to (targetX, targetY) in millimeters.
         * Stops when close enough or after timeoutSeconds.
         */
        public void goToPoint(double targetX, double targetY, double timeoutSeconds) {
            timer.reset();
            boolean reached = false;

            while (opModeIsActive() && !reached && timer.seconds() < timeoutSeconds) {
                reached = driveToPos(targetX, targetY);

            }

            stopDrive();
        }

        /**
         * Drives toward (targetX, targetY) using proportional control.
         * Returns true when within distance threshold (mm).
         */
        public boolean driveToPos(double targetX, double targetY) {
            pinpoint.update();

            double currentX = pinpoint.getPosX(MM);
            double currentY = pinpoint.getPosY(MM);

            double dx = targetX - currentX;
            double dy = targetY - currentY;
            double distance = Math.hypot(dx, dy);

            // Stop when close enough
            double distanceThreshold = 12.0; // mm (~0.5 inch)
            if (distance <= distanceThreshold) {
                stopDrive();
                return true;
            }

            // Control constants (tune for your robot)
            double kP = 0.0020;      // proportional gain
            double powerScale = 0.45; // overall speed scaling
            double smallDeadband = 0.05; // prevent twitch from tiny commands

            // Compute movement vector in field coordinates
            double x = kP * dx;
            double y = -kP * dy; // negative Y to align with your setup

            // Adjust for robot heading using IMU
            double botHeading = mecDrivebase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double rotY = y * Math.cos(-botHeading) - x * Math.sin(-botHeading);
            double rotX = y * Math.sin(-botHeading) + x * Math.cos(-botHeading);

            // Apply deadband
            if (Math.abs(rotX) < smallDeadband) rotX = 0.0;
            if (Math.abs(rotY) < smallDeadband) rotY = 0.0;

            // Compute mecanum drive powers (no rotation)
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX), 1.0);
            double frontLeftPower  = (rotY + rotX) / denominator;
            double backLeftPower   = (rotY - rotX) / denominator;
            double frontRightPower = (rotY - rotX) / denominator;
            double backRightPower  = (rotY + rotX) / denominator;

            // Apply power
            mecDrivebase.frontLeft.setPower(frontLeftPower * powerScale);
            mecDrivebase.backLeft.setPower(backLeftPower * powerScale);
            mecDrivebase.frontRight.setPower(frontRightPower * powerScale);
            mecDrivebase.backRight.setPower(backRightPower * powerScale);

            // Telemetry for debugging
            telemetry.addData("Target X (mm)", targetX);
            telemetry.addData("Current X (mm)", currentX);
            telemetry.addData("Distance (mm)", distance);
            telemetry.update();

            return false;
        }

        /** Stops all drive motors */
        public void stopDrive() {
            mecDrivebase.frontLeft.setPower(0);
            mecDrivebase.frontRight.setPower(0);
            mecDrivebase.backLeft.setPower(0);
            mecDrivebase.backRight.setPower(0);
        }
    }
