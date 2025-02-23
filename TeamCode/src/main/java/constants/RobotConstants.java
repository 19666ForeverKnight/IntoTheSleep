package constants;

public class RobotConstants {
    // Intake Constants
    public static final double INTAKE_CLAW_OPEN = 0.288; //0.62
    public static final double INTAKE_CLAW_CLOSE = 0.124;
    public static final double INTAKE_CLAW_OPEN_AUTO = 0.5;

    public static final double INTAKE_CLAW_YAW_LEFT_LIMIT = 0.365;
    public static final double INTAKE_CLAW_YAW_RIGHT_LIMIT = 0.678;
    public static final double INTAKE_CLAW_YAW_MID = 0.482;

    public static final double INTAKE_CLAW_PITCH_INTAKE = 0.619;
    public static final double INTAKE_CLAW_PITCH_TRANS = 0.0;
    public static final double INTAKE_CLAW_PITCH_AVOID = 0.14;

    public static final double INTAKE_CLAW_ARM_INTAKE_UP = 0.47;
    public static final double INTAKE_CLAW_ARM_INTAKE_DOWN = 0.250;
    public static final double INTAKE_CLAW_ARM_TRANS = 0.678;
    public static final double INTAKE_CLAW_ARM_AVOID = 0.5;

    public static final double EXTEND_LEFT_IN = 0.948;
    public static final double EXTEND_LEFT_OUT = 0.635;
    public static final double EXTEND_LEFT_TRANS = 0.906;
    public static final double EXTEND_LEFT_TRANS_PREP = 0.886;
    public static final double EXTEND_RIGHT_IN = 0.703;
    public static final double EXTEND_RIGHT_OUT = 1;
    public static final double EXTEND_RIGHT_TRANS = 0.765;
    public static final double EXTEND_RIGHT_TRANS_PREP = 0.803;

    // Extend Auto
    public static final double EXTEND_LEFT_AUTO_COLLECT1 = 0.675;
    public static final double EXTEND_RIGHT_AUTO_COLLECT1 = 1;
    public static final double EXTEND_LEFT_AUTO_COLLECT2 = 0;
    public static final double EXTEND_RIGHT_AUTO_COLLECT2 = 0;
    public static final double EXTEND_LEFT_AUTO_COLLECT3 = 0.728;
    public static final double EXTEND_RIGHT_AUTO_COLLECT3 = 0.972;



    // TeleOp Scoring Constants
    public static final double SCORE_CLAW_ARM_DROP_TELEOP = 0.635;
    public static final double SCORE_CLAW_ARM_TRANS = 0.018;
    public static final double SCORE_CLAW_ARM_SPECIMEN = 0.961;
    public static final double SCORE_CLAW_ARM_HANG = 0.450;
    public static final double SCORE_CLAW_ARM_AUTO_INIT = 0.060;
    public static final double SCORE_CLAW_ARM_PARK = 0.;
    public static final double SCORE_CLAW_ARM_L1A = 0.29;


    // Common Scoring Constants
    public static final double SCORE_CLAW_FLIP_DROP = 0.653;
    public static final double SCORE_CLAW_FLIP_DROP_DIVE = 0.774;
    public static final double SCORE_CLAW_FLIP_TRANS = 0.744;
    public static final double SCORE_CLAW_FLIP_READY_FOR_SPECIMEN = 0.304;
    public static final double SCORE_CLAW_FLIP_HANG = 0.257;
    public static final double SCORE_CLAW_FLIP_AUTO_INIT = 0.628;


    public static final double SCORE_CLAW_OPEN = 0.682;
    public static final double SCORE_CLAW_CLOSE = 0.25;

    public static final int LIFT_HIGH_CHAMBER = 110;
    public static final int LIFT_LOW_CHAMBER = 0;
    public static final int LIFT_HIGH_BASKET = 1800;
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

    //Vision Constants
    public static final double CVSmoothing = 1.0;
    public enum AllianceColour{
        Red, Blue
    }
} 