package constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {
    // Intake Constants
    public static double INTAKE_CLAW_OPEN = .457;
    public static double INTAKE_CLAW_CLOSE = 0.640;
    public static double INTAKE_CLAW_CLOSE_AUTO = INTAKE_CLAW_OPEN;
    public static double INTAKE_CLAW_OPEN_AUTO = INTAKE_CLAW_CLOSE;

    public static double INTAKE_CLAW_ROTATE_LEFT_LIMIT = 0.219;
    public static double INTAKE_CLAW_ROTATE_RIGHT_LIMIT = 0.839;
    public static double INTAKE_CLAW_ROTATE_MID = 0.528;
    public static double INTAKE_CLAW_ROTATE_RIGHT = 0.714;

    public static double INTAKE_CLAW_TURRET_INTAKE_AND_TRANS = 0.6575;
    public static double INTAKE_CLAW_TURRET_LEFT_LIMIT = 0.979;
    public static double INTAKE_CLAW_TURRET_RIGHT_LIMIT = 0.336;
    public static double INTAKE_CLAW_TURRET_DELTA = 0.02;
    public static double INTAKE_CLAW_TURRET_CHAMBER_AUTO_INIT = 0.880;
    public static double INTAKE_CLAW_TURRET_RIGHT = 0.2;

    public static double INTAKE_CLAW_ARM_INTAKE_UP = 0.547;
    public static double INTAKE_CLAW_ARM_INTAKE_DOWN = 0.431;
//    public static double INTAKE_CLAW_ARM_TRANS = 0.793;
    public static double INTAKE_CLAW_ARM_TRANS = 0.79;
    public static double INTAKE_CLAW_ARM_AUTO_INIT = 0.962;
    public static double INTAKE_CLAW_ARM_CHAMBER_AUTO_INIT = 0.672;
    public static double INTAKE_CLAW_ARM_AVOID_LOW_CHAMBER = 0.6;
    public static double INTAKE_CLAW_ARM_RIGHT = 0.579;

    public static double EXTEND_LEFT_IN = .8789;
    public static double EXTEND_LEFT_OUT = .1907;
    public static double EXTEND_RIGHT_IN = .2413;
    public static double EXTEND_RIGHT_OUT = .9251;

    // Extend Auto
    public static double EXTEND_LEFT_AUTO_COLLECT1 = 0.675;
    public static double EXTEND_RIGHT_AUTO_COLLECT1 = 1;
    public static double EXTEND_LEFT_AUTO_COLLECT2 = 0;
    public static double EXTEND_RIGHT_AUTO_COLLECT2 = 0;
    public static double EXTEND_LEFT_AUTO_COLLECT3 = 0.728;
    public static double EXTEND_RIGHT_AUTO_COLLECT3 = 0.972;

    // TeleOp Scoring Constants
    public static double SCORE_CLAW_ARM_DROP_TELEOP = 0.621;
//    public static double SCORE_CLAW_ARM_TRANS = 0.213;
    public static double SCORE_CLAW_ARM_TRANS = 0.219;
    public static double SCORE_CLAW_ARM_PREP_TRANS = 0.264;
    public static double SCORE_CLAW_ARM_SPECIMEN = 0.782;
    public static double SCORE_CLAW_ARM_HANG = 0.432;
    public static double SCORE_CLAW_ARM_AUTO_INIT = 0.181;
    public static double SCORE_CLAW_ARM_AUTO_CHAMBER_INIT = 0.181;
    public static double SCORE_CLAW_ARM_PARK = 0.0;
    public static double SCORE_CLAW_ARM_L1A = 0.29;


    // Common Scoring Constants
    public static double SCORE_CLAW_FLIP_DROP = 0.521;
    public static double SCORE_CLAW_FLIP_DROP_DIVE = 0.641;
//    public static double SCORE_CLAW_FLIP_TRANS = 0.228;
    public static double SCORE_CLAW_FLIP_TRANS = 0.21;
    public static double SCORE_CLAW_FLIP_TRANS_PREP = 0.183;
    public static double SCORE_CLAW_FLIP_READY_FOR_SPECIMEN = 0.236;
    public static double SCORE_CLAW_FLIP_HANG = 0.103;
    public static double SCORE_CLAW_FLIP_AUTO_INIT = .199;
    public static double SCORE_CLAW_FLIP_AUTO_CHAMBER_INIT = 0.171;


    public static double SCORE_CLAW_OPEN = .609;
    public static double SCORE_CLAW_CLOSE = 0.24;

    public static int LIFT_HIGH_CHAMBER = 80;
    public static int LIFT_LOW_CHAMBER = 0;
    public static int LIFT_HIGH_BASKET = 1300;
    public static int LIFT_LOW_BASKET = 400;

    public static int LIFT_OPEN_SPECIMEN_CLAW = 330;
    public static int LIFT_OPEN_SPECIMEN_CLAW_AUTO = 550;

    public static double SWEEPING_INIT = 0.488;
    public static double SWEEPING_APPLE = 0.128;

    public static double EXTEND_DELTA = 0.004;
    public static double ROTATE_DELTA = 0.011;
    public static double TURRET_DELTA = 0.0035;

    //Vision Constants
    public static double CVSmoothing = 1.0;
    public enum AllianceColour{
        Red, Blue, Both
    }
} 