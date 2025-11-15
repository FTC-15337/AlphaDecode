package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

@Autonomous
public class LimelightConfig{

    private Limelight3A limelight;
    MecDrivebase drive = new MecDrivebase();
    private LinearOpMode myOpMode;
    private double distance;

    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }
    public int getId() {

        LLResult result = limelight.getLatestResult();

        if(result.isValid()) {

            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            return fiducialResults.get(0).getFiducialId();
        }else{
            return 0;
        }
    }

    /*public void TargetArea() {

        YawPitchRollAngles orientation = drive.getOrientation();
        limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

        LLResult llResult = limelight.getLatestResult();
        if(llResult.isValid()) {
            Pose3D botpose = llResult.getBotpose_MT2();
            //distance = Distance(llResult.getTa());
            //myOpMode.telemetry.addData("Distance", distance);
            myOpMode.telemetry.addData("Target X", llResult.getTx());
            myOpMode.telemetry.addData("Target Area", llResult.getTa());
            myOpMode.telemetry.addData("Botpose", botpose.toString());
        }
    }*/

    /*public double Distance(double ta){
        double scale = 30665.95;
        double distance = (scale / ta);
        return distance;
    }*/

    /*public void LimeLightScan() {
        while (linearOpMode.opModeIsActive()) {

            LLResult result = limelight.getLatestResult();
            if (result.isValid()) {

                linearOpMode.telemetry.addData("tx", result.getTx());
                linearOpMode.telemetry.addData("ta" , result.getTa());
                linearOpMode.telemetry.addData("ty", result.getTy());

                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    linearOpMode.telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                }
            } else {
                linearOpMode.telemetry.addData("Limelight", "No data available");
            }
            linearOpMode.telemetry.update();
        }
        limelight.stop();
    }*/
}
