package subsystem;

//import static constants.AutoConst.YELLOW_FIRST_EXTEND;
//import static constants.AutoConst.YELLOW_SECOND_EXTEND;
//import static constants.AutoConst.YELLOW_THIRD_EXTEND;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Robot {
    public Intake intake = new Intake();
    public Scoring scoring = new Scoring();
    public Drivetrain drivetrain = new Drivetrain();
    static ScheduledExecutorService executor;

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
        scoring.autoInit(hardwareMap);
//        drivetrain.init(hardwareMap);
    }

    public void trans() {
        intake.intakeClawTrans();
        intake.extendTo(5);
        scoring.scoreOpen();
        executor.schedule(() -> intake.intakeStop(), 250, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.scoreClose(), 700, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawOpen(), 400, TimeUnit.MILLISECONDS);
    }

    public void collect(int length) {
        intake.prepareToCollect();
        executor.schedule(() -> intake.intakeClawOpen(), 0, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.extendTo(length), 400, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 600, TimeUnit.MILLISECONDS);
    }



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

    public void transToScore(){
        trans();
    }

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
