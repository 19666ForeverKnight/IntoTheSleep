package subsystems.SleepyStuffff.Math;

public class VisionUtil {

    public static double extendLength;

    public static double yDistance(double ty) {
        return 0.369 * Math.pow(ty, 2) + 12.49 * ty + 253.5 ;
    }
    public static double xDistance(double tx, double ty) {
        double result = (0.0008 * Math.pow(ty, 3) - 0.0005 * Math.pow(ty, 2) + 0.1778 * ty + 8.6207) * tx;
        if (result > 0) {
            result -= 20;
        }
        return result;
    }
    public static double getIntakeDegree(double xDis) {
        return Math.toDegrees(Math.asin(xDis / 180.0));
    }
    public static double getExtendPercent(double xDis, double yDis) {
        extendLength = yDis - Math.cos(Math.asin(xDis / 180.0)) * 180;
        return 0.000002 * Math.pow(extendLength,3) - 0.0012 * Math.pow(extendLength, 2) + 0.4 * extendLength - 2.866;
    }
}
