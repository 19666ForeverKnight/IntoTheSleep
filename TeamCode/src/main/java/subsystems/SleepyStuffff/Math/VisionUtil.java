package subsystems.SleepyStuffff.Math;

public class VisionUtil {
    public static double yDistance(double ty) {
        return 0.0186 * ty * ty * ty - 0.0599 * ty * ty + 12.334 * ty + 256.52;
    }
    public static double xDistance(double tx, double ty) {
        return (0.0006 * ty * ty * ty - 0.0058 * ty * ty + 0.2147 * ty + 8.3889) * tx;
    }
    public static double getIntakeDegree(double xDis) {
        return Math.toDegrees(Math.asin(xDis / 180.0));
    }
    public static double getExtendPercent(double xDis, double yDis) {
        double extendLength = yDis - Math.cos(Math.asin(xDis / 180.0)) * 180;
        return 0.2497 * extendLength + 8.352;
    }
}
