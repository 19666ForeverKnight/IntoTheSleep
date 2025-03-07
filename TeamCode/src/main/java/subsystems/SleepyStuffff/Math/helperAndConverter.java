package subsystems.SleepyStuffff.Math;

public class helperAndConverter {
    public static boolean isNear(double exp, double act, double tolerance){
        return Math.abs(exp - act) < tolerance;
    }
}
