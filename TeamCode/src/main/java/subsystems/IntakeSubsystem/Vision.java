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
    double turretDegree = 90, xDis = 0, yDis = 0, intakePercentage = 0, rotateDegree = 0;

    public void Init(HardwareMap hardwareMap, Telemetry telemetry){
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        this.goWork();
    }
    public DetectorLLResultPair getLatestResult(AllianceColour a, boolean includeYellow){
        LLResult hxg = limelight.getLatestResult();
        for(int b=0; b<4; b++){
            if(!hxg.getDetectorResults().isEmpty()) {
                hxg = limelight.getLatestResult();
            }
        }
        if(hxg.isValid()){
            List<DetectorResult> unfilteredResults = hxg.getDetectorResults();
            for(DetectorResult hxg12527 : unfilteredResults){
                if(hxg12527.getClassName().equals(getDetectorClassIDHelper(a)) || (includeYellow && hxg12527.getClassName().equals("yellow"))){
                    return new DetectorLLResultPair(hxg12527, hxg);
                }
            }
        }
        return null;
    }

    public List<Double> CalculateApple(AllianceColour color, boolean includeYellow){
        AppleTypePair = getLatestResult(color, includeYellow);
        xDis = VisionUtil.xDistance(AppleTypePair.getDr().getTargetXDegrees(), AppleTypePair.getDr().getTargetYDegrees());
        yDis = VisionUtil.yDistance(AppleTypePair.getDr().getTargetYDegrees());
        turretDegree = 90 + VisionUtil.getIntakeDegree(xDis);
        intakePercentage = VisionUtil.getExtendPercent(xDis, yDis);

        List<List<Double>> corners = AppleTypePair.getDr().getTargetCorners();
        if (((corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1))) > (Math.abs(AppleTypePair.getDr().getTargetXDegrees()) > 15
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

//        Farmer.intakeClawOpen();
//        SlaveWorkPlan.schedule(() -> Farmer.setTurretDegree(turretDegree), 50, TimeUnit.MILLISECONDS);
//        SlaveWorkPlan.schedule(() -> Farmer.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP), 100, TimeUnit.MILLISECONDS);
//        SlaveWorkPlan.schedule(() -> Farmer.setExtendPercent(intakePercentage), 150, TimeUnit.MILLISECONDS);
//        SlaveWorkPlan.schedule(() -> Farmer.setRotateDegree(rotateDegree), 200, TimeUnit.MILLISECONDS);
    }

    public void RIP(){
        limelight.stop();
    }
    public void goWork(){
        limelight.start();
    }
}
