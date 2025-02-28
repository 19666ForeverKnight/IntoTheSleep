package subsystem.SleepyStuffff.Math;

public class NearDetector {
    public static boolean isNear(double exp, double act, double tolerance){
        return Math.abs(exp - act) < tolerance;
    }
}
