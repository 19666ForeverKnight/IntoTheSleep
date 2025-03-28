package pedroPathing.constants;

import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;

public class MedPIDConst {
    //drive
    public static double driveP1 = 0.015;
    public static double driveI1 = 0.0;
    public static double driveD1 = 0.0025;
    public static double driveT1 = 0.6;
    public static double driveF1 = 0.0;
    public static final CustomFilteredPIDFCoefficients MedDrivePIDF = new CustomFilteredPIDFCoefficients(driveP1, driveI1, driveD1, driveT1, driveF1);
    public static boolean driveSecond = true;
    public static double driveP2 = 0.007;
    public static double driveI2 = 0.0;
    public static double driveD2 = 0.0;
    public static double driveT2 = 0.6;
    public static double driveF2 = 0.0;
    public static final CustomFilteredPIDFCoefficients MedSecondaryDrivePIDF = new CustomFilteredPIDFCoefficients(driveP2, driveI2, driveD2, driveT2, driveF2);

    //trans
    public static double transP1 = 0.15;
    public static double transI1 = 0.0;
    public static double transD1 = 0.025;
    public static double transF1 = 0.0;
    public static final CustomPIDFCoefficients MedTransPIDF = new CustomPIDFCoefficients(transP1, transI1, transD1, transF1);
    public static boolean transSecond = true;
    public static double transP2 = 0.1;
    public static double transI2 = 0.0;
    public static double transD2 = 0.01;
    public static double transF2 = 0.0;
    public static final CustomPIDFCoefficients MedSecondaryTransPIDF = new CustomPIDFCoefficients(transP2, transI2, transD2, transF2);

    //heading
    public static double headingP1 = 1.3;
    public static double headingI1 = 0.0;
    public static double headingD1 = 0.1;
    public static double headingF1 = 0.0;
    public static final CustomPIDFCoefficients MedHeadingPIDF = new CustomPIDFCoefficients(headingP1, headingI1, headingD1, headingF1);
    public static boolean headingSecond = true;
    public static double headingP2 = 0.85;
    public static double headingI2 = 0.0;
    public static double headingD2 = 0.075;
    public static double headingF2 = 0.0;
    public static final CustomPIDFCoefficients MedSecondaryHeadingPIDF = new CustomPIDFCoefficients(headingP2, headingI2, headingD2, headingF2);

    public static double centripetal = 0.0003;
    public static double zeroPower = 2.2;
}
