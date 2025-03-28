package subsystems;

import static constants.RobotConstants.AllianceColour.Blue;
import static constants.RobotConstants.AllianceColour.Red;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.SCORE_CLAW_ARM_PREP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS_PREP;
import static constants.RobotConstants.SCORE_CLAW_OPEN;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import subsystems.ChassisSubsystem.Drivetrain;
import subsystems.IntakeSubsystem.SuperIntake;
import subsystems.IntakeSubsystem.Vision;

public class Robot {
    public SuperIntake intake = new SuperIntake();
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
        intake.autoInit(hardwareMap);
        scoring.autoinit(hardwareMap);

    }

    public void trans() {
        scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
        intake.setClawPosition(INTAKE_CLAW_CLOSE);
        intake.toTransPos();
        executor.schedule(() -> intake.setRotatePosition(INTAKE_CLAW_ROTATE_MID), 450, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN), 500, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreArmPosition(SCORE_CLAW_ARM_PREP_TRANS, SCORE_CLAW_FLIP_TRANS_PREP), 700, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreArmPosition(SCORE_CLAW_ARM_TRANS, SCORE_CLAW_FLIP_TRANS), 800, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreClawPosition(SCORE_CLAW_CLOSE), 850, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawOpen(), 950, TimeUnit.MILLISECONDS);
    }
    public void collectApple(){
        executor.schedule(() -> intake.intakeClawIntakeDown(), 100, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 200, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawIntakeUp(), 300, TimeUnit.MILLISECONDS);
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
    public void dropSampleToObservation(){
        dropSampleToObservation();
    }
}
