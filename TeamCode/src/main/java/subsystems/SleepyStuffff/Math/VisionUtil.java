package subsystems.SleepyStuffff.Math;

public class VisionUtil {
    public static double yDistance(double ty) {
        return 0.016 * ty * ty * ty - 0.017 * ty * ty + 11.716 * ty + 176.876;
    }
    public static double xDistance(double tx, double ty) {
        return (0.2742 * ty + 7.3) * tx;
    }
    public static double getIntakeDegree(double xDis) {
        return Math.toDegrees(Math.asin(xDis / 180.0));
    }
    public static double getExtendPercent(double xDis, double yDis) {
        double extendLength = yDis - Math.cos(Math.asin(xDis / 180.0)) * 180;
        return 0.2497 * extendLength + 8.352;
    }
}
