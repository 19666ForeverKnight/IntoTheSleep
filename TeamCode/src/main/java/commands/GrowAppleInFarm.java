package commands;

import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;

import com.arcrobotics.ftclib.command.CommandBase;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import constants.RobotConstants;
import subsystems.IntakeSubsystem.SuperIntake;
import subsystems.IntakeSubsystem.Vision;
import subsystems.SleepyStuffff.Math.VisionUtil;
import subsystems.SleepyStuffff.Util.DetectorLLResultPair;

public class GrowAppleInFarm extends CommandBase {
    private Vision Slave;
    private SuperIntake Farmer;
    private DetectorLLResultPair AppleTypePair;
    ScheduledExecutorService SlaveWorkPlan;
    double turretDegree = 90, xDis = 0, yDis = 0, intakePercentage = 0, rotateDegree = 0;
    public GrowAppleInFarm (Vision slave1, SuperIntake farmer, RobotConstants.AllianceColour a){
        Slave = slave1;
        Farmer = farmer;
        Slave.goWork();
        SlaveWorkPlan = Executors.newScheduledThreadPool(5);
    }
    public void GrabApple(){
        AppleTypePair = Slave.getLatestResult(RobotConstants.AllianceColour.Blue, true);
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

        Farmer.intakeClawOpen();
        SlaveWorkPlan.schedule(() -> Farmer.setTurretDegree(turretDegree), 50, TimeUnit.MILLISECONDS);
        SlaveWorkPlan.schedule(() -> Farmer.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP), 100, TimeUnit.MILLISECONDS);
        SlaveWorkPlan.schedule(() -> Farmer.setExtendPercent(intakePercentage), 150, TimeUnit.MILLISECONDS);
        SlaveWorkPlan.schedule(() -> Farmer.setRotateDegree(rotateDegree), 200, TimeUnit.MILLISECONDS);
    }
    @Override
    public void initialize(){
        GrabApple();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}
