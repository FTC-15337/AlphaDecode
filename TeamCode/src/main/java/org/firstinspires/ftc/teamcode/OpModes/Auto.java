package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Mechanisms.LimeLightConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Auto")
public class Auto extends LinearOpMode {


    private MecDrivebase mecDrivebase = new MecDrivebase();
    private LimeLightConfig ll;
    private ShooterConfig shooter = new ShooterConfig();
    private ServoKick kick = new ServoKick();
    private StorageConfig sorter = new StorageConfig();
    private SortingWithColor colorSensor = new SortingWithColor();

    @Override
    public void runOpMode() throws InterruptedException {

        mecDrivebase.init(hardwareMap);
        ll = new LimeLightConfig(hardwareMap);
        shooter.init(hardwareMap);
        kick.init(hardwareMap);
        sorter.init(hardwareMap);
        colorSensor.init(hardwareMap);

        shooter.stopMotor();
        kick.retract();

        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            autoAlignToTag();
            sleep(20);
        }

    }
    public void autoAlignToTag () {
        if (!ll.hasTarget()) {
            // Slowly rotate in place until it finds a tag
            mecDrivebase.drive(0, 0, 0.2); // rotate right slowly
            telemetry.addLine("Searching for Tag...");
            telemetry.update();
            return;
        }

        double tx = ll.getTx();
        double ta = ll.getTa();
        double strafe = -tx * 0.03;
        double forward = (3.8 - ta) * 0.4;
        double rotate = 0;
        mecDrivebase.drive(forward, strafe, rotate);

        telemetry.addData("tx", tx);
        telemetry.addData("ta", ta);
        telemetry.addData("Forward", forward);
        telemetry.addData("Strafe", strafe);
        telemetry.update();

        if (Math.abs(tx) < 0.5 && Math.abs(3.8 - ta) < 0.2) {
            mecDrivebase.drive(0, 0, 0);
            telemetry.addLine("Tag aligned! Moving to next step...");
            telemetry.update();
            // you can now trigger next autonomous step here
        }
    }
}
