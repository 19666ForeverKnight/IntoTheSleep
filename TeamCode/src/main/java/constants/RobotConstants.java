package constants;

public class RobotConstants {
    // Intake Constants
    public static final double INTAKE_CLAW_OPEN = .457;
    public static final double INTAKE_CLAW_CLOSE = .722;
    public static final double INTAKE_CLAW_CLOSE_AUTO = INTAKE_CLAW_OPEN;
    public static final double INTAKE_CLAW_OPEN_AUTO = INTAKE_CLAW_CLOSE;

    public static final double INTAKE_CLAW_ROTATE_LEFT_LIMIT = 0.219;
    public static final double INTAKE_CLAW_ROTATE_RIGHT_LIMIT = 0.839;
    public static final double INTAKE_CLAW_ROTATE_MID = 0.528;
    public static final double INTAKE_CLAW_ROTATE_RIGHT = 0.714;

    public static final double INTAKE_CLAW_TURRET_INTAKE_AND_TRANS = 0.6575;
    public static final double INTAKE_CLAW_TURRET_LEFT_LIMIT = 0.979;
    public static final double INTAKE_CLAW_TURRET_RIGHT_LIMIT = 0.336;
    public static final double INTAKE_CLAW_TURRET_DELTA = 0.02;
    public static final double INTAKE_CLAW_TURRET_CHAMBER_AUTO_INIT = 0.880;
    public static final double INTAKE_CLAW_TURRET_RIGHT = 0.2;

    public static final double INTAKE_CLAW_ARM_INTAKE_UP = 0.547;
    public static final double INTAKE_CLAW_ARM_INTAKE_DOWN = 0.431;
//    public static final double INTAKE_CLAW_ARM_TRANS = 0.793;
    public static final double INTAKE_CLAW_ARM_TRANS = 0.79;
    public static final double INTAKE_CLAW_ARM_AUTO_INIT = 0.962;
    public static final double INTAKE_CLAW_ARM_CHAMBER_AUTO_INIT = 0.672;
    public static final double INTAKE_CLAW_ARM_AVOID_LOW_CHAMBER = 0.6;
    public static final double INTAKE_CLAW_ARM_RIGHT = 0.579;

    public static final double EXTEND_LEFT_IN = 0.341;
    public static final double EXTEND_LEFT_OUT = 0.015;
    public static final double EXTEND_RIGHT_IN = 0.676;
    public static final double EXTEND_RIGHT_OUT = 1;

    // Extend Auto
    public static final double EXTEND_LEFT_AUTO_COLLECT1 = 0.675;
    public static final double EXTEND_RIGHT_AUTO_COLLECT1 = 1;
    public static final double EXTEND_LEFT_AUTO_COLLECT2 = 0;
    public static final double EXTEND_RIGHT_AUTO_COLLECT2 = 0;
    public static final double EXTEND_LEFT_AUTO_COLLECT3 = 0.728;
    public static final double EXTEND_RIGHT_AUTO_COLLECT3 = 0.972;

    // TeleOp Scoring Constants
    public static final double SCORE_CLAW_ARM_DROP_TELEOP = 0.660;
//    public static final double SCORE_CLAW_ARM_TRANS = 0.213;
    public static final double SCORE_CLAW_ARM_TRANS = 0.222;
    public static final double SCORE_CLAW_ARM_PREP_TRANS = 0.276;
    public static final double SCORE_CLAW_ARM_SPECIMEN = 0.99;
    public static final double SCORE_CLAW_ARM_HANG = 0.436;
    public static final double SCORE_CLAW_ARM_AUTO_INIT = .165;
    public static final double SCORE_CLAW_ARM_AUTO_CHAMBER_INIT = 0.063;
    public static final double SCORE_CLAW_ARM_PARK = 0.;
    public static final double SCORE_CLAW_ARM_L1A = 0.29;


    // Common Scoring Constants
    public static final double SCORE_CLAW_FLIP_DROP = 0.611;
    public static final double SCORE_CLAW_FLIP_DROP_DIVE = 0.732;
//    public static final double SCORE_CLAW_FLIP_TRANS = 0.228;
    public static final double SCORE_CLAW_FLIP_TRANS = 0.23;
    public static final double SCORE_CLAW_FLIP_TRANS_PREP = 0.183;
    public static final double SCORE_CLAW_FLIP_READY_FOR_SPECIMEN = 0.299;
    public static final double SCORE_CLAW_FLIP_HANG = 0.221;
    public static final double SCORE_CLAW_FLIP_AUTO_INIT = .202;
    public static final double SCORE_CLAW_FLIP_AUTO_CHAMBER_INIT = 0.171;


    public static final double SCORE_CLAW_OPEN = .609;
    public static final double SCORE_CLAW_CLOSE = 0.24;

    public static final int LIFT_HIGH_CHAMBER = 80;
    public static final int LIFT_LOW_CHAMBER = 0;
    public static final int LIFT_HIGH_BASKET = 1300;
    public static final int LIFT_LOW_BASKET = 400;

    public static final int LIFT_OPEN_SPECIMEN_CLAW = 330;
    public static final int LIFT_OPEN_SPECIMEN_CLAW_AUTO = 550;

    public static final double SWEEPING_INIT = 0.488;
    public static final double SWEEPING_APPLE = 0.128;

    public static final double EXTEND_DELTA = 0.004;
    public static final double ROTATE_DELTA = 0.011;
    public static final double TURRET_DELTA = 0.0035;

    //Vision Constants
    public static final double CVSmoothing = 1.0;
    public enum AllianceColour{
        Red, Blue
    }
} 