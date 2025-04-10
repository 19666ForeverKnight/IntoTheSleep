package constants;

public class RobotConstants {
    // Intake Constants
    public static final double INTAKE_CLAW_OPEN = 0.710;
    public static final double INTAKE_CLAW_CLOSE = 0.928;
    public static final double INTAKE_CLAW_CLOSE_AUTO = 0.94;
    public static final double INTAKE_CLAW_OPEN_AUTO = 0.3;

    public static final double INTAKE_CLAW_ROTATE_LEFT_LIMIT = 0.219;
    public static final double INTAKE_CLAW_ROTATE_RIGHT_LIMIT = 0.839;
    public static final double INTAKE_CLAW_ROTATE_MID = 0.528;

    public static final double INTAKE_CLAW_TURRET_INTAKE_AND_TRANS = 0.6575;
    public static final double INTAKE_CLAW_TURRET_LEFT_LIMIT = 0.979;
    public static final double INTAKE_CLAW_TURRET_RIGHT_LIMIT = 0.336;
    public static final double INTAKE_CLAW_TURRET_DELTA = 0.02;
    public static final double INTAKE_CLAW_TURRET_CHAMBER_AUTO_INIT = 0.880;
    public static final double INTAKE_CLAW_TURRET_THROW_SAMPLE_IN_OBS_ZONE = 0.210;

    public static final double INTAKE_CLAW_ARM_INTAKE_UP = 0.17; //0.155
    public static final double INTAKE_CLAW_ARM_INTAKE_DOWN = 0.072;
    public static final double INTAKE_CLAW_ARM_TRANS = 0.48;
    public static final double INTAKE_CLAW_ARM_AUTO_INIT = 0.656;
    public static final double INTAKE_CLAW_ARM_CHAMBER_AUTO_INIT = 0.672;
    public static final double INTAKE_CLAW_ARM_AVOID_LOW_CHAMBER = 0.304;
    public static final double INTAKE_CLAW_THROW_SAMPLE_IN_OBS_ZONE = 0.246;

    public static final double EXTEND_LEFT_IN = 0.719;
    public static final double EXTEND_LEFT_OUT = 0.404;
//    public static final double EXTEND_LEFT_TRANS = 0.906;
//    public static final double EXTEND_LEFT_TRANS_PREP = 0.886;
    public static final double EXTEND_RIGHT_IN = 0.201;
    public static final double EXTEND_RIGHT_OUT = 0.576;
//    public static final double EXTEND_RIGHT_TRANS = 0.765;
//    public static final double EXTEND_RIGHT_TRANS_PREP = 0.803;

    //Intake Thrown - Right
    public static final double INTAKE_CLAW_TURRET_RIGHT = 0.219;
    public static final double INTAKE_CLAW_ARM_RIGHT = 0.257;
    public static final double INTAKE_CLAW_ROTATE = 0.714;

    // Extend Auto
    public static final double EXTEND_LEFT_AUTO_COLLECT1 = 0.675;
    public static final double EXTEND_RIGHT_AUTO_COLLECT1 = 1;
    public static final double EXTEND_LEFT_AUTO_COLLECT2 = 0;
    public static final double EXTEND_RIGHT_AUTO_COLLECT2 = 0;
    public static final double EXTEND_LEFT_AUTO_COLLECT3 = 0.728;
    public static final double EXTEND_RIGHT_AUTO_COLLECT3 = 0.972;



    // TeleOp Scoring Constants
    public static final double SCORE_CLAW_ARM_DROP_TELEOP = 0.649;
    public static final double SCORE_CLAW_ARM_TRANS = 0.175;
    public static final double SCORE_CLAW_ARM_PREP_TRANS = 0.234;
    public static final double SCORE_CLAW_ARM_SPECIMEN = 0.946;
    public static final double SCORE_CLAW_ARM_HANG = 0.411;
    public static final double SCORE_CLAW_ARM_AUTO_INIT = 0.316;
    public static final double SCORE_CLAW_ARM_AUTO_CHAMBER_INIT = 0.31;
    public static final double SCORE_CLAW_ARM_PARK = 0.;
    public static final double SCORE_CLAW_ARM_L1A = 0.29;


    // Common Scoring Constants
    public static final double SCORE_CLAW_FLIP_DROP = 0.611;
    public static final double SCORE_CLAW_FLIP_DROP_DIVE = 0.732;
    public static final double SCORE_CLAW_FLIP_TRANS = 0.218;
    public static final double SCORE_CLAW_FLIP_TRANS_PREP = 0.183;
    public static final double SCORE_CLAW_FLIP_READY_FOR_SPECIMEN = 0.271;
    public static final double SCORE_CLAW_FLIP_HANG = 0.221;
    public static final double SCORE_CLAW_FLIP_AUTO_INIT = 0.236;
    public static final double SCORE_CLAW_FLIP_AUTO_CHAMBER_INIT = 0.264;


    public static final double SCORE_CLAW_OPEN = 0.682;
    public static final double SCORE_CLAW_CLOSE = 0.25;

    public static final int LIFT_HIGH_CHAMBER = 110;
    public static final int LIFT_LOW_CHAMBER = 0;
    public static final int LIFT_HIGH_BASKET = 1300;
    public static final int LIFT_LOW_BASKET = 400;

    public static final int LIFT_OPEN_SPECIMEN_CLAW = 330;
    public static final int LIFT_OPEN_SPECIMEN_CLAW_AUTO = 550;


    // Other Constants
    public static final double RATCHET_LOCK = 0.165;
    public static final double RATCHET_RELEASE = 0.796;

    public static final double SPECIMEN_CLAW_OPEN = 0.608;
    public static final double SPECIMEN_CLAW_CLOSE = 0.261;

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