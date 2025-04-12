package subsystems;

import static constants.RobotConstants.AllianceColour.Blue;
import static constants.RobotConstants.AllianceColour.Red;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_LEFT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_LEFT_OUT;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT1;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT2;
import static constants.RobotConstants.EXTEND_RIGHT_AUTO_COLLECT3;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_OUT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_RIGHT;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT_LIMIT;
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

    public void chamberAutoInit(HardwareMap hardwareMap) {
        executor = Executors.newScheduledThreadPool(5);
        intake.chamberAutoInit(hardwareMap);
        scoring.chamberAutoInit(hardwareMap);

    }

    public void trans() {
        // intake.setExtendPosition(EXTEND_LEFT_IN + (EXTEND_LEFT_OUT - EXTEND_LEFT_IN) * 0.1, EXTEND_RIGHT_IN + (EXTEND_RIGHT_OUT - EXTEND_RIGHT_IN) * 0.1);
        intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN);
        scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
        intake.setClawPosition(INTAKE_CLAW_CLOSE);
        intake.setRotatePosition(INTAKE_CLAW_ROTATE_MID);
        scoring.setScoreArmPosition(SCORE_CLAW_ARM_PREP_TRANS, SCORE_CLAW_FLIP_TRANS_PREP);
        intake.toTransPos();
        //executor.schedule(() -> intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN), 270, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreArmPosition(SCORE_CLAW_ARM_TRANS, SCORE_CLAW_FLIP_TRANS), 392, TimeUnit.MILLISECONDS);
        executor.schedule(() -> scoring.setScoreClawPosition(SCORE_CLAW_CLOSE), 450, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawOpen(), 600, TimeUnit.MILLISECONDS);
    }
    public void collectApple(){
        intake.intakeClawOpen();
        executor.schedule(() -> intake.intakeClawIntakeDown(), 150, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 250, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawIntakeUp(), 350, TimeUnit.MILLISECONDS);
    }
    public void collectAppleSlow(){
        intake.intakeClawOpen();
        executor.schedule(() -> intake.intakeClawIntakeDown(), 150, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawClose(), 400, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.intakeClawIntakeUp(), 530, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setTurretPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS), 550, TimeUnit.MILLISECONDS);
    }
    public void sweep() {
        intake.sweepOut();
        executor.schedule(() -> intake.sweepIn(), 270, TimeUnit.MILLISECONDS);
    }

    // Semi-Auto Thrown the specimen to the right
    public void thrownRight() {
        intake.setTurretPosition(INTAKE_CLAW_TURRET_RIGHT);
        intake.setArmPosition(INTAKE_CLAW_ARM_RIGHT);
        intake.setRotatePosition(INTAKE_CLAW_ROTATE);
        executor.schedule(() -> intake.intakeClawOpen(), 200, TimeUnit.MILLISECONDS);
        executor.schedule(() -> intake.setTurretPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS), 500, TimeUnit.MILLISECONDS);
    }

    public void thrownRightStay() {
        intake.setTurretPosition(INTAKE_CLAW_TURRET_RIGHT);
        intake.setArmPosition(INTAKE_CLAW_ARM_RIGHT);
        intake.setRotatePosition(INTAKE_CLAW_ROTATE);
        executor.schedule(() -> intake.intakeClawOpen(), 200, TimeUnit.MILLISECONDS);
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