package constants;

import com.pedropathing.localization.Pose;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;

public class AutoConstants {
    public static CustomFilteredPIDFCoefficients FAST_DRIVE_PID = new CustomFilteredPIDFCoefficients(1, 0, 0, 0, 0);
    public static CustomFilteredPIDFCoefficients MED_DRIVE_PID = new CustomFilteredPIDFCoefficients(1,0,0,0,0);
    public static CustomFilteredPIDFCoefficients SLOW_DRIVE_PID = new CustomFilteredPIDFCoefficients(1,0,0,0,0);

}
