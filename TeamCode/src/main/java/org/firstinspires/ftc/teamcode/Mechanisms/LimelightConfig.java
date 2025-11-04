/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

@Autonomous
public class LimelightConfig {

    private Limelight3A limelight;
    private LinearOpMode linearOpMode;

    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        linearOpMode.telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();
        linearOpMode.telemetry.update();
    }

    public void LimeLightScan() {
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
    }

    public int getId() {
        LLResult result = limelight.getLatestResult();

        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        return fiducialResults.get(0).getFiducialId();
    }
}
