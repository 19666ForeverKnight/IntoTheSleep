package subsystems.IntakeSubsystem;

import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static subsystems.SleepyStuffff.Math.helperAndConverter.getDetectorClassIDHelper;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import constants.RobotConstants;
import constants.RobotConstants.AllianceColour;
import subsystems.SleepyStuffff.Math.VisionUtil;
import subsystems.SleepyStuffff.Util.DetectorLLResultPair;

public class Vision extends SubsystemBase {
    private static Vision instance;
    public Limelight3A limelight;
    private DetectorLLResultPair AppleTypePair;
    double turretDegree = 90, xDis = 0, intakePercentage = 0, rotateDegree = 0;
    double yDis = 0;

    public void Init(HardwareMap hardwareMap, Telemetry telemetry){
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        this.goWork();
    }
    public List<Double> getLatestResult(AllianceColour a, boolean includeYellow) {
        LLResult hxg = limelight.getLatestResult();
        for (int b = 0; b < 34; b++) {
            if (!hxg.getDetectorResults().isEmpty()) {
                hxg = limelight.getLatestResult();
            }
        }
        if (hxg.isValid()) {
            List<DetectorResult> unfilteredResults = hxg.getDetectorResults();
            String detectorID = a.name().toLowerCase();
            for (DetectorResult result : unfilteredResults) {
                if (result.getClassName().equals(detectorID) || (includeYellow && result.getClassName().equals("yellow")) && !result.getTargetCorners().isEmpty()) {
                    xDis = VisionUtil.xDistance(result.getTargetXDegrees(), result.getTargetYDegrees());
                    yDis = VisionUtil.yDistance(result.getTargetYDegrees());
                    turretDegree = 90 + VisionUtil.getIntakeDegree(xDis);
                    intakePercentage = VisionUtil.getExtendPercent(xDis, yDis);

                    List<List<Double>> corners = result.getTargetCorners();
                    if (((corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1))) > (Math.abs(result.getTargetXDegrees()) > 15
                            ? 1.3 : 1)) {
                        rotateDegree = turretDegree > 90 ? 270 - turretDegree : 90 - turretDegree;
                    } else {
                        rotateDegree = 180 - turretDegree;
                    }
                    return new ArrayList<Double>() {{
                        add(turretDegree);
                        add(intakePercentage);
                        add(rotateDegree);
                    }};
                }
            }
        }
        return new ArrayList<Double>() {{
            add(0.0);
            add(0.0);
            add(0.0);
        }};
    }
    public void RIP(){
        limelight.stop();
    }
    public void goWork(){
        limelight.start();
    }
}
