//package subsystems.ScoringSubsystem;
//
//import static constants.RobotConstants.LIFT_HIGH_BASKET;
//import static constants.RobotConstants.LIFT_HIGH_CHAMBER;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.arcrobotics.ftclib.controller.PIDController;
//import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
//import com.arcrobotics.ftclib.hardware.motors.Motor;
//import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import java.util.concurrent.TimeUnit;
//
//import constants.Configs;
//import lombok.Getter;
//import lombok.Setter;
//import subsystems.SleepyStuffff.Math.helperAndConverter;
//
//@Config
//public class SuperLift extends MotorPIDSlideSubsystem {
//    public static double kP = 0.000, kI = 0.000, kD = 0.000, kV = 0.000, kS = 0.000, kA = 0.000;
//    public final PIDController pidController;
//    public final Motor liftMotorL, liftMotorM, liftMotorR;
//    private final TrapezoidProfile profile;
//    private TrapezoidProfile.State goalState = new TrapezoidProfile.State();
//    private TrapezoidProfile.State setpointState = new TrapezoidProfile.State();
//    private final ElapsedTime timer;
//    private double lastTime;
//    private double MAX_VEL = 0;
//    private  double MAX_ACL = 0;
//    public double lastSetPoint = 0;
//    public double resetPower = -0.7;
//    private final ElevatorFeedforward feedforward;
//    @Getter @Setter private Goal goal = Goal.BACK;
//
//    public SuperLift(HardwareMap hardwareMap, Telemetry telemetry){
//        liftMotorL = new Motor(hardwareMap, Configs.LIFT_LEFT);
//        liftMotorM = new Motor(hardwareMap, Configs.LIFT_MIDDLE);
//        liftMotorR = new Motor(hardwareMap, Configs.LIFT_RIGHT);
//
//        liftMotorL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        liftMotorM.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        liftMotorR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//
//        liftMotorL.setRunMode(Motor.RunMode.RawPower);
//        liftMotorM.setRunMode(Motor.RunMode.RawPower);
//        liftMotorR.setRunMode(Motor.RunMode.RawPower);
//
//        liftMotorL.stopAndResetEncoder();
//        liftMotorM.stopAndResetEncoder();
//        liftMotorR.stopAndResetEncoder();
//
//        pidController = new PIDController(kP, kI, kD);
//        pidController.setIntegrationBounds(-1/kI, 1/kI);
//        feedforward = new ElevatorFeedforward(kS, kA, kV);
//        this.telemetry = telemetry;
//
//        profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(), goalState, setpointState);
//        timer = new ElapsedTime();
//        timer.reset();
//        lastTime = timer.time(TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public void runOpenLoop(double percent) {
//        if (percent == 0) {
//            super.isResetting = false;
//        } else {
//            super.isResetting = true;
//        }
//        double output = Range.clip(percent, -1, 1);
//        liftMotorL.set(output);
//        liftMotorM.set(output);
//        liftMotorR.set(output);
//    }
//    @Override
//    public double getResetPower() {
//        return resetPower;
//    }
//    @Override
//    public void resetEncoder() {
//        runOpenLoop(0);
//        pidController.reset();
//        pidController.calculate(0);
//        goal = Goal.BACK;
//        liftMotorL.resetEncoder();
//        liftMotorM.resetEncoder();
//        liftMotorR.resetEncoder();
//        telemetry.addData("Lift Current Position", getCurrentPosition());
//        telemetry.addData("Lift Error", pidController.getPositionError());
//    }
//    @Override
//    public long getCurrentPosition(){
//        return (liftMotorR.getCurrentPosition() + liftMotorL.getCurrentPosition()) / 2;
//    }
//    public boolean atGoal(){
//        return helperAndConverter.isNear(goal.setpointTicks, getCurrentPosition(), 5);
//    }
//    public boolean atBack(double tolerance){
//        return helperAndConverter.isNear(goal.BACK.setpointTicks, getCurrentPosition(), tolerance);
//    }
//    public void periodicAsync() {
//        if (goal == Goal.OPEN_LOOP || isResetting) return;
//
//        if (lastSetPoint != goal.setpointTicks) {
//            goalState = new TrapezoidProfile.State(goal.setpointTicks, 0);
//            lastSetPoint = goal.setpointTicks;
//        }
//
//        double timeInterval =
//                Range.clip((timer.time(TimeUnit.MILLISECONDS) - lastTime) * 0.001, 0.001, 0.05);
//
//        setpointState = profile.calculate(timeInterval, setpointState, goalState);
//
//        double pidPower = pidController.calculate(getCurrentPosition(), setpointState.position);
//        double output = pidPower + feedforward.calculate(setpointState.velocity);
//
//        output = Range.clip(output, -1, 1);
//        liftMotorL.set(output);
//        liftMotorM.set(output);
//        liftMotorR.set(output);
//
//        lastTime = timer.time(TimeUnit.MILLISECONDS);
//    }
//    public enum Goal {
//        HIGH_BASKET(LIFT_HIGH_BASKET),
//        HIGH_CHAMBER(LIFT_HIGH_CHAMBER),
//        BACK(0.0),
//        OPEN_LOOP(0.0);
//        private final double setpointTicks;
//        Goal(double setpointTicks) {
//            this.setpointTicks = setpointTicks;
//        }
//    }
//}
