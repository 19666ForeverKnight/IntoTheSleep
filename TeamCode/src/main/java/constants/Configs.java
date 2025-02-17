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
    public static String INTAKE_CLAW_LEFT = "li";
    public static String INTAKE_CLAW_RIGHT = "ri";
    public static String INTAKE_CLAW_YAW = "cl";
    public static String INTAKE_CLAW_PITCH = "cp";
    public static String INTAKE_CLAW_ARM = "cap";
    public static String EXTEND_LEFT = "el";
    public static String EXTEND_RIGHT = "er";

    public static String SCORE_CLAW = "sc";
    public static String SCORE_CLAW_ARM = "sa";
    public static String SCORE_CLAW_FLIP = "sap";

    public static String SWEEP = "sw";

    //Sensor
    public static String Pinpoint = "pp";
    public static String Limelight = "ll";
    public static String LIFT_TOUCH_LEFT = "ltl";
    public static String LIFT_TOUCH_RIGHT = "ltr";
}
