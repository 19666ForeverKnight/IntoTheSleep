package subsystems.IntakeSubsystem;

import static subsystems.SleepyStuffff.Math.helperAndConverter.getDetectorClassIDHelper;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.List;

import constants.RobotConstants.AllianceColour;
import subsystems.SleepyStuffff.Util.DetectorLLResultPair;

public class Vision extends SubsystemBase {
    private static Vision instance;
    public Limelight3A limelight;
    public void Init(HardwareMap hardwareMap, Telemetry telemetry){
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        this.goWork();
    }
    public DetectorLLResultPair getLatestResult(AllianceColour a){
        LLResult hxg = limelight.getLatestResult();
        for(int b=0; b<4; b++){
            if(!hxg.getDetectorResults().isEmpty()) break;
            hxg = limelight.getLatestResult();
        }
        if(hxg.isValid()){
            List<DetectorResult> unfilteredResults = hxg.getDetectorResults();
            for(DetectorResult hxg12527 : unfilteredResults){
                if(hxg12527.getClassName().equals(getDetectorClassIDHelper(a)) || hxg12527.getClassName().equals("yellow")){
                    return new DetectorLLResultPair(hxg12527, hxg);
                }
            }
        }
        return null;
    }
    public void stopWorking(){
        limelight.stop();
    }
    public void goWork(){
        limelight.start();
    }
}
