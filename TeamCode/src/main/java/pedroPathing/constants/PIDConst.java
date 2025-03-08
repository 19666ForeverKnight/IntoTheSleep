package pedroPathing.constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class PIDConst {
    public static double driveP1 = 0.035;
    public static double driveI1 = 0.0;
    public static double driveD1 = 0.00001;
    public static double driveT1 = 0.6;
    public static double driveF1 = 0.0;

    public static boolean driveSecond = false;

    public static double driveP2 = 0.007;
    public static double driveI2 = 0.0;
    public static double driveD2 = 0.0;
    public static double driveT2 = 0.6;
    public static double driveF2 = 0.0;


    public static double transP1 = 0.25;
    public static double transI1 = 0.0;
    public static double transD1 = 0.01;
    public static double transF1 = 0.0;

    public static boolean transSecond = false;

    public static double transP2 = 0.1;
    public static double transI2 = 0.0;
    public static double transD2 = 0.01;
    public static double transF2 = 0.0;


    public static double headingP1 = 5.0;
    public static double headingI1 = 0.0;
    public static double headingD1 = 0.1;
    public static double headingF1 = 0.0;

    public static boolean headingSecond = false;

    public static double headingP2 = 0.85;
    public static double headingI2 = 0.0;
    public static double headingD2 = 0.075;
    public static double headingF2 = 0.0;


    public static double centripetal = 0.0003;
    public static double zeroPower = 1.0;
}
