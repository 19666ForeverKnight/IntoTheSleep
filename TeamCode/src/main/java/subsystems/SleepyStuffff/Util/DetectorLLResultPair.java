package subsystems.SleepyStuffff.Util;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;

import java.util.List;

import lombok.Getter;

public class DetectorLLResultPair {
    @Getter DetectorResult dr;
    @Getter LLResult lr;
    public DetectorLLResultPair(DetectorResult detectorResult, LLResult llResult){
        dr = detectorResult;
        lr = llResult;
    }
}
