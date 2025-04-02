package subsystems.IntakeSubsystem;

import static constants.RobotConstants.CVSmoothing;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

import constants.RobotConstants.AllianceColour;
import subsystems.SleepyStuffff.Math.QuadrilateralTracker;
import subsystems.SleepyStuffff.Util.Vector2d;

public class Vision {
    public Limelight3A limelight;
    public QuadrilateralTracker apple = new QuadrilateralTracker();
    List<Vector2d> appleFarm = new ArrayList<>();

    public void Init(HardwareMap hardwaremap, AllianceColour colour){
        limelight = hardwaremap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        if (colour == AllianceColour.Red) limelight.pipelineSwitch(1);
        if (colour == AllianceColour.Blue) limelight.pipelineSwitch(2);
    }

    public void Init(HardwareMap hardwaremap){
        limelight = hardwaremap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
    }

    public void start(){ limelight.start(); }
    public void stop(){ limelight.stop(); }

    public boolean thereIsAnApple(){
        LLResult hxg = limelight.getLatestResult();
        if(hxg != null) {
            return true;
        }
        return false;
    }

    public LLResult appleGrowing() {
        LLResult hxg = limelight.getLatestResult();
        if (hxg != null) {
            List<Vector2d> corners = (List<Vector2d>) getAppleCornerPositions(hxg);
            if (corners == null){
                apple.update(corners);
            }
        }
        return hxg;
    }

    public Vector2d getAppleCornerPositions(LLResult appleGrowing) {
        if (appleGrowing != null) {
            Vector2d position = new Vector2d(appleGrowing.getTx(), appleGrowing.getTy());
            appleFarm.add(position);
            if (appleFarm.size() > CVSmoothing) appleFarm.remove(0);
            Vector2d average = new Vector2d(0, 0);
            for (Vector2d apple : appleFarm) {
                average.add(apple);
            }
            average.divide(CVSmoothing);
            if (appleFarm.size() >= CVSmoothing) {
                return new Vector2d(average.x, average.y);
            } else {
                return null;
            }
        } else {
            appleFarm.clear();
        }
        return null;
    }

    public double getAppleArea(LLResult appleGrowing) {
        if (appleGrowing != null){
            Double a = appleGrowing.getTa();
            if (a != null){
                return a;
            } else {
                return 0.0;
            }
        } else {
            return 0.0;
        }
    }
}
