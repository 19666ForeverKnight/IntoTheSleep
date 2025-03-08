package subsystems;

import static constants.RobotConstants.LIFT_OPEN_SPECIMEN_CLAW_AUTO;
import static constants.RobotConstants.SCORE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.SCORE_CLAW_ARM_DROP_TELEOP;
import static constants.RobotConstants.SCORE_CLAW_ARM_HANG;
import static constants.RobotConstants.SCORE_CLAW_ARM_L1A;
import static constants.RobotConstants.SCORE_CLAW_ARM_PARK;
import static constants.RobotConstants.SCORE_CLAW_ARM_SPECIMEN;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_FLIP_AUTO_INIT;
import static constants.RobotConstants.SCORE_CLAW_FLIP_DROP_DIVE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_HANG;
import static constants.RobotConstants.SCORE_CLAW_FLIP_READY_FOR_SPECIMEN;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_OPEN;
import static constants.RobotConstants.LIFT_HIGH_BASKET;
import static constants.RobotConstants.LIFT_HIGH_CHAMBER;
import static constants.RobotConstants.LIFT_OPEN_SPECIMEN_CLAW;
//import static constants.RobotConstants.SCORE_CLAW_ARM_DROP_AUTO;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_DROP;
//import static constants.RobotConstants.SCORE_CLAW_OPEN_Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import constants.Configs;

@Config
public class Scoring {
    private DcMotorEx liftLeft;
    private DcMotorEx liftRight;
    private DcMotorEx liftMiddle;
    private Servo scoreClaw;
    private Servo scoreClawArm;
    private Servo scoreClawFlip;
    private DigitalChannel liftTouch;
    private double error = 0, lastError = 0, integral = 0, derivative = 0;
    //    public static double Kp = 0.015, Ki = 0.00005, Kd = 0.00003, power;
    public static double Kp = 0.006, Ki = 0, Kd = 0, power;
    public int liftTargetPos = 0, liftTor = 20;
    private ElapsedTime dt = new ElapsedTime();

    public volatile boolean isRunning = false;
    ScheduledThreadPoolExecutor sch;

    public void init (HardwareMap hardwareMap) {
        sch = new ScheduledThreadPoolExecutor(40);
        liftLeft = hardwareMap.get(DcMotorEx.class, Configs.LIFT_LEFT);
        liftMiddle = hardwareMap.get(DcMotorEx.class, Configs.LIFT_MIDDLE);
        liftRight = hardwareMap.get(DcMotorEx.class, Configs.LIFT_RIGHT);
        scoreClaw = hardwareMap.get(Servo.class, Configs.SCORE_CLAW);
        scoreClawArm = hardwareMap.get(Servo.class, Configs.SCORE_CLAW_ARM);
        scoreClawFlip = hardwareMap.get(Servo.class, Configs.SCORE_CLAW_FLIP);
//        liftTouch = hardwareMap.get(DigitalChannel.class, Configs.LIFT_TOUCH);
//         TODO Add rightTouch to Configs

//        liftTouch.setMode(DigitalChannel.Mode.INPUT);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMiddle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize lift motors
        liftRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        liftMiddle.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Initialize servo positions
        scoreClaw.setPosition(SCORE_CLAW_OPEN);
        scoreClawArm.setPosition(SCORE_CLAW_ARM_SPECIMEN);
        scoreClawFlip.setPosition(SCORE_CLAW_FLIP_READY_FOR_SPECIMEN);


    }

    public void autoinit (HardwareMap hardwareMap) {
        sch = new ScheduledThreadPoolExecutor(40);
        liftLeft = hardwareMap.get(DcMotorEx.class, Configs.LIFT_LEFT);
        liftMiddle = hardwareMap.get(DcMotorEx.class, Configs.LIFT_MIDDLE);
        liftRight = hardwareMap.get(DcMotorEx.class, Configs.LIFT_RIGHT);
        scoreClaw = hardwareMap.get(Servo.class, Configs.SCORE_CLAW);
        scoreClawArm = hardwareMap.get(Servo.class, Configs.SCORE_CLAW_ARM);
        scoreClawFlip = hardwareMap.get(Servo.class, Configs.SCORE_CLAW_FLIP);
//        liftTouch = hardwareMap.get(DigitalChannel.class, Configs.LIFT_TOUCH);
//         TODO Add rightTouch to Configs

//        liftTouch.setMode(DigitalChannel.Mode.INPUT);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMiddle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize lift motors
        liftRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        liftMiddle.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        // Initialize servo positions
        scoreClaw.setPosition(SCORE_CLAW_CLOSE);
        scoreClawArm.setPosition(SCORE_CLAW_ARM_AUTO_INIT);
        scoreClawFlip.setPosition(SCORE_CLAW_FLIP_AUTO_INIT);

        threadStart();
    }

//    public void autoInit (HardwareMap hardwareMap) {
//        sch = new ScheduledThreadPoolExecutor(40);
//        liftLeft = hardwareMap.get(DcMotorEx.class, "ll");
//        liftRight = hardwareMap.get(DcMotorEx.class, "lr");
//        scoreClaw = hardwareMap.get(Servo.class, "sc");
//        scoreClawArm = hardwareMap.get(Servo.class, "sca");
//        scoreClawFlip = hardwareMap.get(Servo.class, "scf");
//        specimenClaw = hardwareMap.get(Servo.class, "spmc");
//        hangRatchet = hardwareMap.get(Servo.class, "hr");
//
//        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        // Initialize lift motors
//        liftLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        liftLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//        liftRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
//
//        liftAuto();
//
//        // Initialize servo positions
//        scoreClaw.setPosition(SCORE_CLAW_OPEN);
//        scoreClawArm.setPosition(SCORE_CLAW_ARM_TRANS);
//        scoreClawFlip.setPosition(SCORE_CLAW_FLIP_TRANS);
//        specimenClaw.setPosition(SPECIMEN_CLAW_OPEN);
//        hangRatchet.setPosition(RATCHET_RELEASE);
//    }

    public boolean liftTouched() {
        return !liftTouch.getState();
    }

    public void setLiftPower(double power) {
        liftLeft.setPower(power); //TODO check if this is correct
        liftRight.setPower(power); //TODO check if this is correct
        liftMiddle.setPower(power); //TODO check if this is correct
    }

    public void runToPosition(int targetPosition) {
        error = targetPosition - (liftLeft.getCurrentPosition() + liftRight.getCurrentPosition()) / 2.0;
        integral += error * dt.seconds();
        derivative = (error - lastError) / dt.seconds();
        power = error * Kp + integral * Ki + derivative * Kd;
        setLiftPower(power);
        dt.reset();
        lastError = error;
    }

    public void setScoreClawPosition(double position) {
        scoreClaw.setPosition(position);
    }

    public void setScoreArmPosition(double armPos, double flipPos) {
        scoreClawArm.setPosition(armPos);
        scoreClawFlip.setPosition(flipPos);
    }

    public void armToBasket() {
        setScoreArmPosition(SCORE_CLAW_ARM_DROP_TELEOP, SCORE_CLAW_FLIP_DROP);
    }

    public void armToPark() {
        setScoreArmPosition(SCORE_CLAW_ARM_PARK, SCORE_CLAW_FLIP_AUTO_INIT);
    }
    public void armToBasketDive() {
        setScoreArmPosition(SCORE_CLAW_ARM_DROP_TELEOP, SCORE_CLAW_FLIP_DROP_DIVE);
    }
    public void armToChamber() {
        setScoreArmPosition(SCORE_CLAW_ARM_HANG, SCORE_CLAW_FLIP_HANG);
    }
    public void armToCollect() {
        setScoreArmPosition(SCORE_CLAW_ARM_SPECIMEN, SCORE_CLAW_FLIP_READY_FOR_SPECIMEN);
    }
    public void armToL1A() {
        setScoreArmPosition(SCORE_CLAW_ARM_L1A, SCORE_CLAW_FLIP_TRANS);
    }
    public void armToTrans() {
        setScoreArmPosition(SCORE_CLAW_ARM_TRANS, SCORE_CLAW_FLIP_TRANS);
    }

    public int getLiftPosition() {
        return (liftLeft.getCurrentPosition() + liftRight.getCurrentPosition()) / 2;
    }

    public void scoreOpen() {
        scoreClaw.setPosition(SCORE_CLAW_OPEN);
    }

    public void scoreClose() {
        scoreClaw.setPosition(SCORE_CLAW_CLOSE);
    }

    public void threadStart(){
        if(isRunning) return;
        isRunning = true;
        sch.schedule(this::liftMornitor,0,TimeUnit.MILLISECONDS);
    }
    public void threadStop(){
        isRunning = false;
    }
    public void liftMornitor(){
        while(isRunning){
            int currPos = (liftLeft.getCurrentPosition() + liftRight.getCurrentPosition()) /2 ;
            double powerRate = Math.abs(currPos-liftTargetPos)/300.0;
            powerRate = Math.min(powerRate, 1);
            powerRate = Math.max(powerRate, 0.3);
            if(Math.abs(currPos-liftTargetPos)>liftTor)
                if(currPos<liftTargetPos){
                    setLiftPower(powerRate);
                }else{
                    setLiftPower(-powerRate);
                }
            else{
                liftStop();
            }
        }
        liftStop();
        isRunning = false;
    }
    public void liftToAuto(int pos){
        if(pos>LIFT_HIGH_BASKET) pos = LIFT_HIGH_BASKET;
        if(pos<0) pos = 0;
        liftTargetPos = pos;
    }

    public void liftToHighBasket(){
        liftToAuto(LIFT_HIGH_BASKET);
    }

    public void liftToHighChamber(){
        liftToAuto(LIFT_HIGH_CHAMBER);
    }

    public void liftToChamberOpen(){
        liftToAuto(LIFT_OPEN_SPECIMEN_CLAW);
    }

    public void liftToChamberOpenAuto(){
        liftToAuto(LIFT_OPEN_SPECIMEN_CLAW_AUTO);
    }

    public void extandTo(){
        liftToAuto(LIFT_OPEN_SPECIMEN_CLAW);
    }

    public void liftStop(){
        liftRight.setPower(0);
        liftLeft.setPower(0);
    }

    public void liftBack(){
        liftToAuto(0);
    }

    public double getrightliftheight() {
        return liftRight.getCurrentPosition();
    }

    public double getleftliftheight() {
        return liftLeft.getCurrentPosition();
    }

    public double getmiddleliftheight() {
        return liftMiddle.getCurrentPosition();
    }
}