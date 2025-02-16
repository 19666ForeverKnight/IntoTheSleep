package constants;

public class RobotConstants {
    // Intake Constants
    public static final double INTAKE_CLAW_OPEN = 0.50; //0.62
    public static final double INTAKE_CLAW_INTAKE = 0.42;
    public static final double INTAKE_CLAW_CLOSE = 0.36;

    public static final double INTAKE_CLAW_YAW_LEFT_LIMIT = 0.339;
    public static final double INTAKE_CLAW_YAW_RIGHT_LIMIT = 0.63;
    public static final double INTAKE_CLAW_YAW_MID = 0.48;

    public static final double INTAKE_CLAW_PITCH_INTAKE = 0.815;
    public static final double INTAKE_CLAW_PITCH_TRANS = 0.102;
    public static final double INTAKE_CLAW_PITCH_TRANS_PREP = 0.6;

    public static final double INTAKE_CLAW_ARM_INTAKE_UP = 0.426;
    public static final double INTAKE_CLAW_ARM_INTAKE_DOWN = 0.187;
    public static final double INTAKE_CLAW_ARM_TRANS = 0.8;

    public static final double EXTEND_LEFT_IN = 0;
    public static final double EXTEND_LEFT_OUT = 0;
    public static final double EXTEND_RIGHT_IN = 0;
    public static final double EXTEND_RIGHT_OUT = 0;

    // TeleOp Scoring Constants
    public static final double SCORE_CLAW_ARM_DROP_TELEOP = 0.80;

    // Auto Scoring Constants  
    public static final double SCORE_CLAW_ARM_DROP_AUTO = 0.90;

    public static final double SCORE_CLAW_ARM_TRANS = 0.04;

    // Common Scoring Constants
    public static final double SCORE_CLAW_FLIP_DROP = 0.51;
    public static final double SCORE_CLAW_FLIP_TRANS = 0.756;

    public static final double SCORE_CLAW_OPEN = 0.613;
    public static final double SCORE_CLAW_OPEN_Auto = 0.9;
    public static final double SCORE_CLAW_CLOSE = 0.195;

    public static final int LIFT_HIGH_CHAMBER = -1550;
    public static final int LIFT_LOW_CHAMBER = -360;
    public static final int LIFT_HIGH_BASKET = -2050;
    public static final int LIFT_LOW_BASKET = -500;

    public static final int LIFT_OPEN_SPECIMEN_CLAW = -1270;

    // Other Constants
    public static final double RATCHET_LOCK = 0.165;
    public static final double RATCHET_RELEASE = 0.796;

    public static final double SPECIMEN_CLAW_OPEN = 0.608;
    public static final double SPECIMEN_CLAW_CLOSE = 0.261;

    //Vision Constants
    public static final double CVSmoothing = 1.0;
    public enum AllianceColour { Red, Blue }
} 