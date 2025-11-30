package org.firstinspires.ftc.teamcode.OpModes;
import org.firstinspires.ftc.teamcode.Mechanisms.TurretConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightConfig;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name= "Turret_Red")
public class Turret_Red extends LinearOpMode {



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


        if (!gamepad1.x) {
            turretConfig.turret.setPower(0);
            return;
        }


        if (!limelight.hasTarget()) {
            turretConfig.turret.setPower(0);
            return;
        }


        int id = limelight.getId();
        if (id != 24) {
            turretConfig.turret.setPower(0);
            return;
        }

        double tx = limelight.getTx();

        double tweak = 0.7;


        if (Math.abs(tx) < tweak) {
            turretConfig.turret.setPower(0);
            return;
        }

        double kP = 0.02;
        double turretPower = kP * tx;


        if (Math.abs(tx) > 5.0) {
            double minPower = 0.20;
            turretPower = Math.copySign(Math.max(minPower, Math.abs(turretPower)), turretPower);
        }


        turretPower = Math.max(-0.50, Math.min(0.50, turretPower));

        turretConfig.turret.setPower(turretPower);

        telemetry.addData("TX", tx);
        telemetry.addData("ID", id);
        telemetry.addData("Turret Power", turretPower);
        telemetry.update();
    }



}