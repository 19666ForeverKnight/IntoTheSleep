package subsystem;

//import static constants.AutoConst.YELLOW_FIRST_EXTEND;
//import static constants.AutoConst.YELLOW_SECOND_EXTEND;
//import static constants.AutoConst.YELLOW_THIRD_EXTEND;

import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_LEFT_TRANS;
import static constants.RobotConstants.EXTEND_LEFT_TRANS_PREP;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_RIGHT_TRANS;
import static constants.RobotConstants.EXTEND_RIGHT_TRANS_PREP;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_OPEN;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Robot {
    public Intake intake = new Intake();
    public Scoring scoring = new Scoring();
    public Drivetrain drivetrain = new Drivetrain();
    ScheduledExecutorService executor;

    public Robot() {}

    public void init(HardwareMap hardwareMap) {
        executor = Executors.newScheduledThreadPool(5);
        intake.init(hardwareMap);
        scoring.init(hardwareMap);
        drivetrain.init(hardwareMap);
    }

    public void autoInit(HardwareMap hardwareMap) {
        executor = Executors.newScheduledThreadPool(5);
        intake.init(hardwareMap);
        scoring.autoinit(hardwareMap);
    }

    public void trans() {
        scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
        intake.setClawPosition(INTAKE_CLAW_CLOSE);
        intake.intakeClawAvoid();
        intake.setExtendPosition(EXTEND_RIGHT_TRANS_PREP, EXTEND_LEFT_TRANS_PREP);
        executor.schedule(() -> intake.toTransPos(), 300, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setExtendPosition(EXTEND_RIGHT_TRANS, EXTEND_LEFT_TRANS), 450, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreClawPosition(SCORE_CLAW_CLOSE), 750, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawOpen(), 850, TimeUnit.MILLISECONDS);
    }

    public void sweep() {
        intake.sweepOut();
        executor.schedule(() -> intake.sweepIn(), 270, TimeUnit.MILLISECONDS);
    }

    public void autoextendtocollect(int sampleindex) {
        if(sampleindex == 1) {
            intake.setExtendPosition(EXTEND_RIGHT_AUTO_COLLECT1, EXTEND_LEFT_AUTO_COLLECT1);
        }
        else if(sampleindex == 2) {
            intake.setExtendPosition(EXTEND_RIGHT_AUTO_COLLECT2, EXTEND_LEFT_AUTO_COLLECT2);
        }
        else if(sampleindex == 3) {
            intake.setExtendPosition(EXTEND_RIGHT_AUTO_COLLECT3, EXTEND_LEFT_AUTO_COLLECT3);
        }
    }
//    public void autoInit(HardwareMap hardwareMap) {
//        executor = Executors.newScheduledThreadPool(5);
////        intake.init(hardwareMap);
////        scoring.autoInit(hardwareMap);
////        drivetrain.init(hardwareMap);
//    }

//    public void trans() {
////        intake.intakeClawTrans();
////        intake.extendTo(5);
////        scoring.scoreOpen();
//        executor.schedule(() -> intake.intakeStop(), 250, TimeUnit.MILLISECONDS);
//        executor.schedule(() -> scoring.scoreClose(), 700, TimeUnit.MILLISECONDS);
//        executor.schedule(() -> intake.intakeClawOpen(), 400, TimeUnit.MILLISECONDS);
//    }

//    public void collect(int length) {
//        intake.prepareToCollect();
//        executor.schedule(() -> intake.intakeClawOpen(), 0, TimeUnit.MILLISECONDS);
//        executor.schedule(() -> intake.extendTo(length), 400, TimeUnit.MILLISECONDS);
//        executor.schedule(() -> intake.intakeClawClose(), 600, TimeUnit.MILLISECONDS);
//    }



//    public void dropSampleToObservation() {
//        intake.extendEncoder(Math.max(Math.min(EXTEND_UPPER_LIMIT, intake.getPosition() + 150), 350));
//        executor.schedule(() -> intake.intakeClawOpen(), 350, TimeUnit.MILLISECONDS);
//    }

    public void dropSampleToObservation(){
        dropSampleToObservation();
    }

//    public void collectFirstYellow(){
//        collect(YELLOW_FIRST_EXTEND);
//    }
//
//    public void collectSecondYellow(){
//        collect(YELLOW_SECOND_EXTEND);
//    }
//
//    public void collectThirdYellow(){
//        collect(YELLOW_THIRD_EXTEND);
//    }

//    public void transToScore(){
//        trans();
//    }

//    public class collectFirstAlliance implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            collect(ALLIANCE_FIRST_EXTEND, ALLIANCE_FIRST_ROTATE);
//            return false;
//        }
//    }
//
//    public class collectSecondAlliance implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            collect(ALLIANCE_SECOND_EXTEND, ALLIANCE_SECOND_ROTATE);
//            return false;
//        }
//    }
//
//    public class collectThirdAlliance implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            collect(ALLIANCE_THIRD_EXTEND, ALLIANCE_THIRD_ROTATE);
//            return false;
//        }
//    }

}
