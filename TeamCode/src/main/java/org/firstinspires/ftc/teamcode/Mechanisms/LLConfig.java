package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.List;

public class LLConfig {

    private Limelight3A limelight;
    private LLResult latestResult;
    private boolean initialized = false;

    // Initialize Limelight
    public void init(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Change to your AprilTag pipeline number
        limelight.start();
        initialized = true;
    }

    // Update Limelight data
    public void update() {
        if (!initialized) return;
        latestResult = limelight.getLatestResult();
    }

    // Check if any AprilTag is seen
    public boolean seesAprilTag() {
        return latestResult != null && latestResult.isValid() &&
                latestResult.getFiducialResults() != null &&
                !latestResult.getFiducialResults().isEmpty();
    }

    // Print clean, simple telemetry when called
    public void printTagData(Telemetry telemetry) {
        telemetry.clearAll();

        if (!seesAprilTag()) {
            telemetry.addLine("No AprilTag Detected");
        } else {
            LLResultTypes.FiducialResult tag = latestResult.getFiducialResults().get(0);

            telemetry.addLine("AprilTag Detected");
            telemetry.addData("Tag ID", tag.getFiducialId());
            telemetry.addLine("----------------------");
            telemetry.addData("X Offset (°)", String.format("%.2f", tag.getTargetXDegrees()));
            telemetry.addData("Y Offset (°)", String.format("%.2f", tag.getTargetYDegrees()));
            telemetry.addData("Target Area", String.format("%.2f", tag.getTargetArea()));
        }

        telemetry.update();
    }
}
