package subsystems.SleepyStuffff.Math;

public class VisionUtil {
    public static double yDistance(double ty) {
        return 0.0187 * ty * ty * ty - 0.0013 * ty * ty + 13.337 * ty + 254.55;
    }
    public static double xDistance(double tx, double ty) {
        return (0.2798 * ty + 8) * tx - 2.2;
    }
    public static double getIntakeDegree(double xDis) {
        return Math.toDegrees(Math.asin(xDis / 180.0));
    }
    public static double getExtendPercent(double xDis, double yDis) {
        double extendLength = yDis - Math.cos(Math.asin(xDis / 180.0)) * 180;
        return 0.244 * extendLength + 1;
    }
}
