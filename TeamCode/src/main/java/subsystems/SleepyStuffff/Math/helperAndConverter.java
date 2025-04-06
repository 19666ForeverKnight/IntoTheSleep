package subsystems.SleepyStuffff.Math;

import constants.RobotConstants;

public class helperAndConverter {
    public static boolean isNear(double exp, double act, double tolerance){
        return Math.abs(exp - act) < tolerance;
    }
    public static String getDetectorClassIDHelper(RobotConstants.AllianceColour allianceColour){
        switch (allianceColour){
            case Red: return "Red";
            case Blue: return "Blue";
        }
        return "";
    }
}
