//package subsystems.IntakeSubsystem;
//
//import static constants.RobotConstants.EXTEND_LEFT_IN;
//import static constants.RobotConstants.EXTEND_RIGHT_IN;
//import static constants.RobotConstants.INTAKE_CLAW_ARM_AVOID;
//import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_DOWN;
//import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
//import static constants.RobotConstants.INTAKE_CLAW_ARM_TRANS;
//import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
//import static constants.RobotConstants.INTAKE_CLAW_OPEN;
//import static constants.RobotConstants.INTAKE_CLAW_OPEN_AUTO;
//import static constants.RobotConstants.INTAKE_CLAW_PITCH_AVOID;
//import static constants.RobotConstants.INTAKE_CLAW_PITCH_INTAKE;
//import static constants.RobotConstants.INTAKE_CLAW_PITCH_TRANS;
//import static constants.RobotConstants.INTAKE_CLAW_YAW_MID;
//import static constants.RobotConstants.SWEEPING_APPLE;
//import static constants.RobotConstants.SWEEPING_INIT;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import constants.Configs;
//
//public class Intake {
//    private Servo intakeClaw;
//    private Servo intakeClawLeft;
//    private Servo intakeClawRight;
//    private Servo intakeClawYaw;
//    private Servo intakeClawPitch;
//    private Servo intakeClawArm;
//    private Servo intakeClawTurret;
//    private Servo intakeClawRotate;
//
//
//
//
//    private Servo extendLeft;
//    private Servo extendRight;
//    private Servo sweep;
//
//    public void init(HardwareMap hardwareMap) {
//        intakeClaw = hardwareMap.get(Servo.class, Configs.INTAKE_CLAW);
//        intakeClawLeft = hardwareMap.get(Servo.class, Configs.INTAKE_CLAW_LEFT);
//        intakeClawRight = hardwareMap.get(Servo.class, Configs.INTAKE_CLAW_RIGHT);
//        intakeClawYaw = hardwareMap.get(Servo.class, Configs.INTAKE_CLAW_YAW);
//        intakeClawPitch = hardwareMap.get(Servo.class, Configs.INTAKE_CLAW_PITCH);
//        intakeClawArm = hardwareMap.get(Servo.class, Configs.INTAKE_CLAW_ARM);
//        extendLeft = hardwareMap.get(Servo.class, Configs.EXTEND_LEFT);
//        extendRight = hardwareMap.get(Servo.class, Configs.EXTEND_RIGHT);
//        sweep = hardwareMap.get(Servo.class, Configs.SWEEP);
//
//        // Initialize positions
//        extendLeft.setPosition(EXTEND_LEFT_IN);
//        extendRight.setPosition(EXTEND_RIGHT_IN);
//        toTransPos();
//        sweepIn();
//    }
//
//    public void sweepIn() {
//        sweep.setPosition(SWEEPING_INIT);
//    }
//
//    public void sweepOut() {
//        sweep.setPosition(SWEEPING_APPLE);
//    }
//
//    public void toTransPos() {
//        intakeClawLeft.setPosition(0.5);
//        intakeClawRight.setPosition(0.5);
//        intakeClawYaw.setPosition(INTAKE_CLAW_YAW_MID);
//        intakeClawPitch.setPosition(INTAKE_CLAW_PITCH_TRANS);
//        intakeClawArm.setPosition(INTAKE_CLAW_ARM_TRANS);
//    }
//
//    public double getClawArmPos() {
//        return intakeClawArm.getPosition();
//    }
//
//    public double getClawPosition() {
//        return intakeClaw.getPosition();
//    }
//
//    public void setClawPosition(double position) {
//        intakeClaw.setPosition(position);
//    }
//
//    public void setArmPosition(double position) {
//        intakeClawArm.setPosition(position);
//    }
//
//    public void setPitchPosition(double position) {
//        intakeClawPitch.setPosition(position);
//    }
//
//    public void setYawPosition(double position) {
//        intakeClawYaw.setPosition(position);
//    }
//
//    public void setExtendPosition(double positionR, double positionL) {extendLeft.setPosition(positionL); extendRight.setPosition(positionR);}
//
//    public void setLeftRightPosition(double left, double right) {
//        intakeClawLeft.setPosition(left);
//        intakeClawRight.setPosition(right);
//    }
//
////    public void extendTo(int targetPos) {
////        extendLeft.setPosition(targetPos);
////        extendRight.setPosition(targetPos);
////    }
//
//    public void intakeClawTrans() {
//        intakeClawArm.setPosition(INTAKE_CLAW_ARM_TRANS);
//        intakeClawPitch.setPosition(INTAKE_CLAW_PITCH_TRANS);
//        intakeClawYaw.setPosition(INTAKE_CLAW_YAW_MID);
//        intakeClawClose();
//    }
//
//    public void prepareToCollect() {
//        intakeClawIntakeDown();
//        intakeClawOpen();
//        intakeIntake();
//    }
//
//    public void intakeClawIntakeUp() {
//        intakeClawArm.setPosition(INTAKE_CLAW_ARM_INTAKE_UP);
//        intakeClawPitch.setPosition(INTAKE_CLAW_PITCH_INTAKE);
//        intakeClawYaw.setPosition(INTAKE_CLAW_YAW_MID);
//    }
//
//    public void intakeClawAvoid() {
//        intakeClawArm.setPosition(INTAKE_CLAW_ARM_AVOID);
//        intakeClawPitch.setPosition(INTAKE_CLAW_PITCH_AVOID);
//        intakeClawYaw.setPosition(INTAKE_CLAW_YAW_MID);
//    }
//
//    public void intakeClawIntakeDown() {
//        intakeClawArm.setPosition(INTAKE_CLAW_ARM_INTAKE_DOWN);
//        intakeClawPitch.setPosition(INTAKE_CLAW_PITCH_INTAKE);
//        intakeClawYaw.setPosition(INTAKE_CLAW_YAW_MID);
//    }
//
//    public void intakeClawOpen() {
//        intakeClaw.setPosition(INTAKE_CLAW_OPEN);
//    }
//
//    public void intakeClawOpenAuto() {
//        intakeClaw.setPosition(INTAKE_CLAW_OPEN_AUTO);
//    }
//
//    public void intakeClawClose() {
//        intakeClaw.setPosition(INTAKE_CLAW_CLOSE);
//    }
//
//    public void intakeClawIntake() {
//        intakeClaw.setPosition(INTAKE_CLAW_OPEN);
//    }
//
//    public void intakeIntake() {
//        setLeftRightPosition(0, 1);
//    }
//
//    public void intakeOuttake() {
//        setLeftRightPosition(1, 0);
//    }
//
//    public void intakeStop() {
//        setLeftRightPosition(0.5, 0.5);
//    }
//
//    public void liftIntake() {
//        intakeClawIntakeUp();
//    }
//
//    public void IntakeClawOpen() {
//        intakeClawOpen();
//        intakeStop();
//    }
//
////    public void extendOut() {
////        extendTo(-350);
////    }
////
////    public void extendBack() {
////        extendTo(5);
////    }
////
////    public void everythingBack() {
////        extendTo(5);
////        intakeClawTrans();
////    }
//}