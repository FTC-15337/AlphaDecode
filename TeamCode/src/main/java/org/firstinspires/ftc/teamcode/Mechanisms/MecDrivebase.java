package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.teamcode.ConstantValues.Constants;

public class MecDrivebase {
    public DcMotor frontLeft, backLeft, frontRight, backRight;
    public IMU imu;

    public void init(HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backRight = hwMap.get(DcMotor.class, "backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double forward, double strafe, double rotate) {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = Constants.driveMaxPower;
        double maxSpeed = Constants.driveMaxSpeed;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeft.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeft.setPower(maxSpeed * (backLeftPower / maxPower));
        frontRight.setPower(maxSpeed * (frontRightPower / maxPower));
        backRight.setPower(maxSpeed * (backRightPower / maxPower));
    }

    public void driveFieldRelative(double forward, double strafe, double rotate) {
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward, newStrafe, rotate);
    }

    //Rotate method for auto rotation
    /*public void autoRotate(double xPower, double yPower) {

        frontLeft.setPower(xPower);
        backLeft.setPower(xPower);
        frontRight.setPower(yPower);
        backRight.setPower(yPower);
    }*/
    /*strafe not working as both go outwards.
        for left and inwards for right
     */

}
