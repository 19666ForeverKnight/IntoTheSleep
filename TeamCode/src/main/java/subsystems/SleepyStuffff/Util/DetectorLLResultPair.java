package subsystems.SleepyStuffff.Util;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
public class DetectorLLResultPair {
    DetectorResult dr;
    LLResult lr;
    public DetectorLLResultPair(DetectorResult detectorResult, LLResult llResult){
        dr = detectorResult;
        lr = llResult;
    }
    public DetectorResult getDr(){return this.dr;}
    public LLResult getLr(){return this.lr;}
}
