package constants;

import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class Configs {

    // Motor
    public static String LEFT_FRONT = "lf";         // Control/Expansion [port]
    public static String LEFT_BACK = "lb";
    public static String RIGHT_FRONT = "rf";
    public static String RIGHT_BACK = "rb";

    public static String LIFT_LEFT = "ll";
    public static String LIFT_RIGHT = "lr";
    public static String LIFT_MIDDLE = "lm";

    // Servo
    public static String INTAKE_CLAW = "ic";
    public static String INTAKE_CLAW_LEFT = "icl";
    public static String INTAKE_CLAW_RIGHT = "icr";
    public static String INTAKE_CLAW_YAW = "icy";
    public static String INTAKE_CLAW_PITCH = "icp";
    public static String INTAKE_CLAW_ARM = "ica";
    public static String EXTEND_LEFT = "etl";
    public static String EXTEND_RIGHT = "etr";

    public static String SCORE_CLAW = "sc";
    public static String SCORE_CLAW_ARM = "sca";
    public static String SCORE_CLAW_FLIP = "scf";

    //Sensor
    public static String Pinpoint = "pp";
    public static String Limelight = "ll";
    public static String LIFT_TOUCH = "lt";
}
