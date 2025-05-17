package subsystems;

import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ARM_RIGHT;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT;
import static constants.RobotConstants.SCORE_CLAW_ARM_PREP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS_PREP;
import static constants.RobotConstants.SCORE_CLAW_OPEN;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import subsystems.ChassisSubsystem.Drivetrain;
import subsystems.IntakeSubsystem.SuperIntake;

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

    public void chamberAutoInit(HardwareMap hardwareMap) {
        executor = Executors.newScheduledThreadPool(5);
        intake.chamberAutoInit(hardwareMap);
        scoring.chamberAutoInit(hardwareMap);

    }

    public void trans() {
        intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN);
        scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
        intake.setClawPosition(INTAKE_CLAW_CLOSE);
        intake.setRotatePosition(INTAKE_CLAW_ROTATE_MID);
        scoring.armToPreTrans();
        intake.toTransPos();
        executor.schedule(() -> scoring.armToTrans(), 580, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreClawPosition(SCORE_CLAW_CLOSE), 770, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawOpen(), 930, TimeUnit.MILLISECONDS);
    }
    public void collectAppleNoLift(){
        intake.intakeClawOpen();
        executor.schedule(() -> intake.intakeClawIntakeDown(), 100, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 280, TimeUnit.MILLISECONDS);
//        executor.schedule(() -> intake.intakeClawIntakeUp(), 370, TimeUnit.MILLISECONDS);
    }
    public void collectApple(){
        intake.intakeClawOpen();
        executor.schedule(() -> intake.intakeClawIntakeDown(), 100, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 250, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawIntakeUp(), 440, TimeUnit.MILLISECONDS);
    }
    public void grabApple(List<Double> apple) {
        intake.intakeClawOpen();
        executor.schedule(() -> intake.setTurretDegree(apple.get(0)), 50, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP), 100, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setExtendPercent(apple.get(1)), 150, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setRotateDegree(apple.get(2)), 200, TimeUnit.MILLISECONDS);
    }
    public void collectAppleSlow(){
        intake.intakeClawOpen();
        executor.schedule(() -> intake.intakeClawIntakeDown(), 50, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 300, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawIntakeUp(), 430, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setTurretPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS), 500, TimeUnit.MILLISECONDS);
    }
    public void sweep() {
        intake.sweepOut();
        executor.schedule(() -> intake.sweepIn(), 270, TimeUnit.MILLISECONDS);
    }

    // Semi-Auto Thrown the specimen to the right
    public void thrownRight() {
        intake.setTurretPosition(INTAKE_CLAW_TURRET_RIGHT);
        intake.setArmPosition(INTAKE_CLAW_ARM_RIGHT);
        intake.setRotatePosition(INTAKE_CLAW_ROTATE_RIGHT);
        executor.schedule(() -> intake.intakeClawOpen(), 150, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setTurretPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS), 500, TimeUnit.MILLISECONDS);
    }

    public void thrownRightStay() {
        intake.setTurretPosition(INTAKE_CLAW_TURRET_RIGHT);
        intake.setArmPosition(INTAKE_CLAW_ARM_RIGHT);
        intake.setRotatePosition(INTAKE_CLAW_ROTATE_RIGHT);
        executor.schedule(() -> intake.intakeClawOpen(), 150, TimeUnit.MILLISECONDS);
//        executor.schedule(() -> intake.setTurretPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS), 500, TimeUnit.MILLISECONDS);
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