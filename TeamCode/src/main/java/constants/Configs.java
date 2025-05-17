package constants;

import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class Configs {

    // Motor
    public static String LEFT_FRONT = "leftFront";         // Control/Expansion [port]
    public static String LEFT_BACK = "leftBack";
    public static String RIGHT_FRONT = "rightFront";
    public static String RIGHT_BACK = "rightBack";

    public static String LIFT_LEFT = "liftLeft";
    public static String LIFT_RIGHT = "liftRight";
    public static String LIFT_MIDDLE = "liftMid";

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
    public static String CLAW = "intakeClaw";
    public static String CLAW_TURRET = "intakeTurret";
    public static String CLAW_ROTATE = "intakeRotate";
    public static String CLAW_ARM = "intakeArm";
    public static String EXTEND_RIGHT = "extendRight";
    public static String EXTEND_LEFT = "extendLeft";
    public static String SCORE_ARM = "scoreArm";
    public static String SCORE_ARM_PITCH = "scoreFlip";
    public static String SCORE_CLAW = "scoreClaw";
    public static String SWEEP = "sweep";

    //Sensor
    public static String Pinpoint = "pp";
    public static String Limelight = "ll";
    public static String LIFT_TOUCH_LEFT = "ltl";
    public static String LIFT_TOUCH_RIGHT = "ltr";
}
