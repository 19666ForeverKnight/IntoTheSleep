package constants;

public class RobotConstants {
    // Intake Constants
    public static final double INTAKE_CLAW_OPEN = 0.374; //0.62
    public static final double INTAKE_CLAW_CLOSE = 0.250;

    public static final double INTAKE_CLAW_YAW_LEFT_LIMIT = 0.365;
    public static final double INTAKE_CLAW_YAW_RIGHT_LIMIT = 0.678;
    public static final double INTAKE_CLAW_YAW_MID = 0.49;

    public static final double INTAKE_CLAW_PITCH_INTAKE = 0.595;
    public static final double INTAKE_CLAW_PITCH_TRANS = 0.0;

    public static final double INTAKE_CLAW_ARM_INTAKE_UP = 0.447;
    public static final double INTAKE_CLAW_ARM_INTAKE_DOWN = 0.260;
    public static final double INTAKE_CLAW_ARM_TRANS = 0.7;

    public static final double EXTEND_LEFT_IN = 0.482;
    public static final double EXTEND_LEFT_OUT = 0.193;
    public static final double EXTEND_LEFT_TRANS = 0.426;
    public static final double EXTEND_LEFT_TRANS_PREP = 0.35;
    public static final double EXTEND_RIGHT_IN = 0.498;
    public static final double EXTEND_RIGHT_OUT = 0.767;
    public static final double EXTEND_RIGHT_TRANS = 0.553;
    public static final double EXTEND_RIGHT_TRANS_PREP = 0.595;


    // TeleOp Scoring Constants
    public static final double SCORE_CLAW_ARM_DROP_TELEOP = 0.582;
    public static final double SCORE_CLAW_ARM_TRANS = 0.02;
    public static final double SCORE_CLAW_ARM_SPECIMEN = 0.95;

    // Common Scoring Constants
    public static final double SCORE_CLAW_FLIP_DROP = 0.706;
    public static final double SCORE_CLAW_FLIP_TRANS = 0.73;
    public static final double SCORE_CLAW_FLIP_READY_FOR_SPECIMEN = 0.293;

    public static final double SCORE_CLAW_OPEN = 0.682;
    public static final double SCORE_CLAW_CLOSE = 0.29;

    public static final int LIFT_HIGH_CHAMBER = 1700;
    public static final int LIFT_LOW_CHAMBER = 1300;
    public static final int LIFT_HIGH_BASKET = 1800;
    public static final int LIFT_LOW_BASKET = 1400;

    public static final int LIFT_OPEN_SPECIMEN_CLAW = -1270;

    // Other Constants
    public static final double RATCHET_LOCK = 0.165;
    public static final double RATCHET_RELEASE = 0.796;

    public static final double SPECIMEN_CLAW_OPEN = 0.608;
    public static final double SPECIMEN_CLAW_CLOSE = 0.261;

    public static final double SWEEPING_INIT = 0.446;
    public static final double SWEEPING_APPLE = 0.100;

    //Vision Constants
    public static final double CVSmoothing = 1.0;
    public enum AllianceColour{
        Red, Blue
    }
} 