package org.firstinspires.ftc.teamcode.OpModes;
import org.firstinspires.ftc.teamcode.Mechanisms.TurretConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name= "Turret_Blue")
public class Turret_Blue extends LinearOpMode {



    LimelightConfig limelight = new LimelightConfig();
    TurretConfig turretConfig = new TurretConfig();

    boolean turretLocked = false;

    @Override
    public void runOpMode() throws InterruptedException {

        limelight.init(hardwareMap);
        turretConfig.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            updateTurret();
        }
    }

    public void updateTurret() {

        double tx = limelight.getTx();
        int id = limelight.getId();

        if (!gamepad1.x) {

            turretLocked = false;
            turretConfig.turret.setPower(0);
            return;
        }


        if (turretLocked) {
            turretConfig.turret.setPower(0);
            telemetry.addData("Turret", "LOCKED");
            return;
        }

        if (gamepad1.left_bumper) {
            turretLocked = false;
            turretConfig.turret.setPower(0.25);
            return;
        }
        if (gamepad1.right_bumper) {
            turretLocked = false;
            turretConfig.turret.setPower(-0.25);
            return;
        }


        if (!limelight.hasTarget() || id == 0) {
            turretConfig.turret.setPower(0);
            return;
        }


        if (id != 20) {
            turretConfig.turret.setPower(0);
            return;
        }

        // IF TAG IS CENTERED → LOCK ON
        if (Math.abs(tx) < 1.5) {
            turretLocked = true;
            turretConfig.turret.setPower(0);
            telemetry.addData("Turret", "Locked On ID " + id);
            return;
        }

        double kP = 0.02;
        double turretPower = kP * tx;


        if (Math.abs(tx) > 5.0) {
            double minPower = 0.20;
            turretPower = Math.copySign(
                    Math.max(minPower, Math.abs(turretPower)),
                    turretPower
            );
        }

        // Limit max speed
        turretPower = Math.max(-0.50, Math.min(0.50, turretPower));

        turretConfig.turret.setPower(turretPower);

        telemetry.addData("TX", tx);
        telemetry.addData("ID", id);
        telemetry.addData("Has Target", limelight.hasTarget());
        telemetry.addData("Turret Power", turretPower);
        telemetry.update();
    }


}