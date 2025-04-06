package subsystems.IntakeSubsystem;

import static constants.RobotConstants.CVSmoothing;
import static subsystems.SleepyStuffff.Math.helperAndConverter.getDetectorClassIDHelper;

import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

import constants.RobotConstants.AllianceColour;
import subsystems.SleepyStuffff.Math.QuadrilateralTracker;
import subsystems.SleepyStuffff.Util.DetectorLLResultPair;
import subsystems.SleepyStuffff.Util.Vector2d;

public class Vision extends SubsystemBase {
    private static Vision instance;
    public Limelight3A limelight;
    public QuadrilateralTracker apple = new QuadrilateralTracker();
    List<Vector2d> appleFarm = new ArrayList<>();
    public void Init(HardwareMap hardwareMap, Telemetry telemetry){
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
    }
    public DetectorLLResultPair getLatestResult(AllianceColour a){
        LLResult hxg = limelight.getLatestResult();
        if(hxg.isValid()){
            List<DetectorResult> unfilteredResults = hxg.getDetectorResults();
            for(DetectorResult hxg12527 : unfilteredResults){
                if(hxg12527.getClassName().equals(getDetectorClassIDHelper(a)) || hxg12527.getClassName().equals("Yellow")){
                    return new DetectorLLResultPair(hxg12527, hxg);
                }
            }
        }
        return null; //TODO: have to add a constructor here to avoid null point exception, or simply let the robot move and re-run the process.
    }


    public void stopWorking(){
        limelight.stop();
    }
    public void goWork(){
        limelight.start();
    }
}
