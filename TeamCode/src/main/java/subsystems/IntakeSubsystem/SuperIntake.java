package subsystems.IntakeSubsystem;

import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_LEFT_OUT;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_OUT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_AVOID_LOW_CHAMBER;
import static constants.RobotConstants.INTAKE_CLAW_ARM_CHAMBER_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_DOWN;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ARM_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE_AUTO;
import static constants.RobotConstants.INTAKE_CLAW_OPEN;
import static constants.RobotConstants.INTAKE_CLAW_OPEN_AUTO;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_CHAMBER_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT_LIMIT;
import static constants.RobotConstants.SWEEPING_APPLE;
import static constants.RobotConstants.SWEEPING_INIT;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import constants.Configs;

public class SuperIntake extends SubsystemBase {
    private Servo intakeClawTurret;
    private Servo intakeClawRotate;
    private Servo intakeClawArm;
    private Servo intakeClaw;
    private Servo extendLeft;
    private Servo extendRight;
    private Servo sweep;

    public void init(HardwareMap hardwareMap) {
        intakeClaw = hardwareMap.get(Servo.class, Configs.CLAW);
        intakeClawRotate = hardwareMap.get(Servo.class, Configs.CLAW_ROTATE);
        intakeClawTurret = hardwareMap.get(Servo.class, Configs.CLAW_TURRET);
        intakeClawArm = hardwareMap.get(Servo.class, Configs.CLAW_ARM);
        extendLeft = hardwareMap.get(Servo.class, Configs.EXTEND_LEFT);
        extendRight = hardwareMap.get(Servo.class, Configs.EXTEND_RIGHT);
        sweep = hardwareMap.get(Servo.class, Configs.SWEEP);

//        extendLeft.setPosition(EXTEND_LEFT_IN);
//        extendRight.setPosition(EXTEND_RIGHT_IN);
//        toTransPos();
//        sweepIn();
    }
    public void autoInit(HardwareMap hardwareMap) {
        intakeClaw = hardwareMap.get(Servo.class, Configs.CLAW);
        intakeClawRotate = hardwareMap.get(Servo.class, Configs.CLAW_ROTATE);
        intakeClawTurret = hardwareMap.get(Servo.class, Configs.CLAW_TURRET);
        intakeClawArm = hardwareMap.get(Servo.class, Configs.CLAW_ARM);
        extendLeft = hardwareMap.get(Servo.class, Configs.EXTEND_LEFT);
        extendRight = hardwareMap.get(Servo.class, Configs.EXTEND_RIGHT);
        sweep = hardwareMap.get(Servo.class, Configs.SWEEP);

        extendLeft.setPosition(EXTEND_LEFT_IN);
        extendRight.setPosition(EXTEND_RIGHT_IN);
        intakeClawOpen();
        setRotatePosition(INTAKE_CLAW_ROTATE_MID);
        setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT);
        sweepIn();
    }

    public void chamberAutoInit(HardwareMap hardwareMap) {
        intakeClaw = hardwareMap.get(Servo.class, Configs.CLAW);
        intakeClawRotate = hardwareMap.get(Servo.class, Configs.CLAW_ROTATE);
        intakeClawTurret = hardwareMap.get(Servo.class, Configs.CLAW_TURRET);
        intakeClawArm = hardwareMap.get(Servo.class, Configs.CLAW_ARM);
        extendLeft = hardwareMap.get(Servo.class, Configs.EXTEND_LEFT);
        extendRight = hardwareMap.get(Servo.class, Configs.EXTEND_RIGHT);
        sweep = hardwareMap.get(Servo.class, Configs.SWEEP);

        extendLeft.setPosition(EXTEND_LEFT_IN);
        extendRight.setPosition(EXTEND_RIGHT_IN);
        intakeClawOpen();
        setTurretPosition(INTAKE_CLAW_TURRET_CHAMBER_AUTO_INIT);
        setRotatePosition(INTAKE_CLAW_ROTATE_MID);
        setArmPosition(INTAKE_CLAW_ARM_CHAMBER_AUTO_INIT);
        sweepIn();
    }

    public void sweepIn() {
        sweep.setPosition(SWEEPING_INIT);
    }

    public void sweepOut() {
        sweep.setPosition(SWEEPING_APPLE);
    }

    public void toTransPos() {
        intakeClawArm.setPosition(INTAKE_CLAW_ARM_TRANS);
        intakeClawTurret.setPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS);
        intakeClawRotate.setPosition(INTAKE_CLAW_ROTATE_MID);
    }

    public double getClawArmPos() {
        return intakeClawArm.getPosition();
    }
    public double getClawPosition() {
        return intakeClaw.getPosition();
    }
    public double getClawRotatePosition() {
        return intakeClawRotate.getPosition();
    }

    public void setClawPosition(double position) {
        intakeClaw.setPosition(position);
    }

    public void setArmPosition(double position) {
        intakeClawArm.setPosition(position);
    }

    public void setTurretPosition(double position) {
        intakeClawTurret.setPosition(position);
    }

    public void setRotatePosition(double position) {
        intakeClawRotate.setPosition(position);
    }

    public void setExtendPosition(double positionR, double positionL) {
        extendLeft.setPosition(positionL);
        extendRight.setPosition(positionR);
    }

    public void setExtendPercent(double percent) {
        setExtendPosition(EXTEND_RIGHT_IN + ((EXTEND_RIGHT_OUT - EXTEND_RIGHT_IN) * percent / 100), EXTEND_LEFT_IN + ((EXTEND_LEFT_OUT - EXTEND_LEFT_IN) * percent / 100));
    }

    public void setTurretDegree(double d) {
        setTurretPosition(INTAKE_CLAW_TURRET_LEFT_LIMIT + (INTAKE_CLAW_TURRET_RIGHT_LIMIT - INTAKE_CLAW_TURRET_LEFT_LIMIT) * d / 180);
    }

    public void setRotateDegree(double d) {
        setRotatePosition(INTAKE_CLAW_ROTATE_LEFT_LIMIT + (INTAKE_CLAW_ROTATE_RIGHT_LIMIT - INTAKE_CLAW_ROTATE_LEFT_LIMIT) * d / 180);
    }

    public void prepareToCollect() {
        intakeClawIntakeUp();
        intakeClawOpen();
    }

    public void intakeClawIntakeUp() {
        intakeClawArm.setPosition(INTAKE_CLAW_ARM_INTAKE_UP);
    }
    public void intakeClawIntakeDown() {
        intakeClawArm.setPosition(INTAKE_CLAW_ARM_INTAKE_DOWN);
    }
    public void intakeClawAvoid() {
        intakeClawArm.setPosition(INTAKE_CLAW_ARM_AVOID_LOW_CHAMBER);
    }

    public void intakeClawOpen() {
        intakeClaw.setPosition(INTAKE_CLAW_OPEN);
    }

    public void intakeClawOpenAuto() {
        intakeClaw.setPosition(INTAKE_CLAW_OPEN_AUTO);
    }

    public void intakeClawClose() {
        intakeClaw.setPosition(INTAKE_CLAW_CLOSE);
    }

    public void intakeClawCloseAuto() {
        intakeClaw.setPosition(INTAKE_CLAW_CLOSE_AUTO);
    }

    public void intakeClawIntake() {
        intakeClaw.setPosition(INTAKE_CLAW_OPEN);
    }
    public void liftIntake() {
        intakeClawIntakeUp();
    }
}