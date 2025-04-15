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
    /*
     Control Hub 5 : ClawArm : cpi 抓取prep: 0.119; 抓取: 0.072; trans prep: 0.471; 扔sample: 0.246
     Control Hub 2 : ClawTurret : cy | middle 抓取: 0.641; 扔sample: 0.210;
     Control Hub 3 : ClawRotate : cr | middle 抓取: 0.534; 左极限: 0.21; 右极限: 0.848;
     Control Hub 4 : Claw : cla | 抓取: 0.928; 开: 0.710
     Control Hub 0 : ScoreArm : sa | trans prep: 0.239; trans: 0.209;
     Expansion Hub 0 : ExtendRight : er
     Expansion Hub 2 : ScoreArmPitch : sap | trans prep : 0.274; trans: 0.304;
     Expansion Hub 3 : sweep : sw
     Expansion Hub 4 : ExtendLeft : el
     Expansion Hub 5 : ScoreClaw : sc
     */
    public static String CLAW = "cla";
    public static String CLAW_TURRET = "cy";
    public static String CLAW_ROTATE = "cr";
    public static String CLAW_ARM = "cpi";
    public static String EXTEND_RIGHT = "er";
    public static String EXTEND_LEFT = "el";
    public static String SCORE_ARM = "sa";
    public static String SCORE_ARM_PITCH = "sap";
    public static String SCORE_CLAW = "sc";
    public static String SWEEP = "sw";

    //Sensor
    public static String Pinpoint = "pp";
    public static String Limelight = "ll";
    public static String LIFT_TOUCH_LEFT = "ltl";
    public static String LIFT_TOUCH_RIGHT = "ltr";
}
