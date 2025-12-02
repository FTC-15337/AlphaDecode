package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class LimelightConfig {

    private Limelight3A limelight;

    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);  // tag pipeline
        limelight.start();
    }

    private LLResult getSafeResult() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) {
            return null;
        }
        return result;
    }

    public boolean hasTarget() {
        LLResult result = getSafeResult();
        return result != null &&
                result.getFiducialResults() != null &&
                !result.getFiducialResults().isEmpty();
    }

    public int getId() {
        LLResult result = getSafeResult();
        if (result == null) return 0;

        List<LLResultTypes.FiducialResult> fid = result.getFiducialResults();
        if (fid == null || fid.isEmpty()) return 0;

        return fid.get(0).getFiducialId();
    }

    public double getTx() {
        LLResult result = getSafeResult();
        if (result == null) return 0;

        return result.getTx();
    }
}