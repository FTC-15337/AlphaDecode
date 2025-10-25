package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Mechanisms.LimelightForAuto;
import org.firstinspires.ftc.teamcode.Mechanisms.MecDrivebase;
import org.firstinspires.ftc.teamcode.Mechanisms.ShooterConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.ServoKick;
import org.firstinspires.ftc.teamcode.Mechanisms.SortingWithColor;
import org.firstinspires.ftc.teamcode.Mechanisms.StorageConfig;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Auto")
public class Auto extends LinearOpMode {


    private MecDrivebase mecDrivebase = new MecDrivebase();
    private LimelightForAuto ll;
    private ShooterConfig shooter = new ShooterConfig();
    private ServoKick kick = new ServoKick();
    private StorageConfig sorter = new StorageConfig();
    private SortingWithColor colorSensor = new SortingWithColor();
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        mecDrivebase.init(hardwareMap);
        ll = new LimelightForAuto(hardwareMap);
        shooter.init(hardwareMap);
        kick.init(hardwareMap);
        sorter.init(hardwareMap);
        colorSensor.init(hardwareMap);

        shooter.stopMotor();
        kick.retract();

        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {
            autoAlignToTag();
            if (ll.getTa() >= 3.8) {
                mecDrivebase.drive(0,0,0);
                telemetry.addLine("Aligned to tag");
                telemetry.update();
                break;
            }
            sleep(20);
        }

        timer.reset();
        shooter.fireMotor();
        while(opModeIsActive() && timer.seconds() < 3.0) {
            telemetry.addData("Outtake running", timer.seconds());
            telemetry.update();
        }
        shooter.stopMotor();

        kick.kick();
        sleep(500);
        kick.retract();

        String targetColor = detectMotif();

        switch(targetColor){
            case "Green":
                sorter.setOutA();
                break;
            case "Purple":
                sorter.setOutB();
                break;
            case "Blue":
                sorter.setOutC();
                break;
        }

        sleep(300);
        kick.kick();
        sleep(500);
        kick.retract();

        telemetry.addLine("Auto complete with sorting");
        telemetry.update();
    }

    private void autoAlignToTag() {
        if (!ll.hasTarget()) {
            mecDrivebase.drive(0,0,0);
            telemetry.addLine("No Tag Detected");
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
    }

    private String detectMotif() {
        return colorSensor.detectColor();
    }
}
