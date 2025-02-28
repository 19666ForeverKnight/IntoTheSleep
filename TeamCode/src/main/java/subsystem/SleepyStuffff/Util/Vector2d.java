package subsystem.SleepyStuffff.Util;

public class Vector2d {
    public double x;
    public double y;
    public double direction;
    public double magnitude;

    public Vector2d(double a, double b) {
        // Assume component
        x = a;
        y = b;
        magnitude = Math.sqrt(x*x + y*y);
        direction = compassAtan(x, y);
    }

    double clamp(double value, double min, double max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    public double toCompassAngle(double polarDirection) {
        return (polarDirection + 360) % 360;
    }

    public double toPolarAngle(double compassDirection) {
        if (compassDirection >= 180) {
            return -(360 - compassDirection);
        } else {
            return compassDirection;
        }
    }

    double compassAtan(double xComponent, double yComponent) {
        // Handle division by zero
        if (y == 0) { return 0; }
        double value = Math.toDegrees(Math.atan(x/y));
        if (x > 0 && y >= 0) { return value;}
        if (x > 0 && y < 0) { return 90 - value;}
        if (x < 0 && y < 0) { return 180 + value;}
        if (x < 0 && y >= 0) {return 360 + value;}
        return 0;
    }

    public void fromComponent(double new_x, double new_y) {
        x = new_x; y = new_y;
        magnitude = Math.sqrt(x*x + y*y);
        direction = compassAtan(x, y);
    }

    void fromPolar(double new_magnitude, double new_degrees) {
        magnitude = new_magnitude; direction = new_degrees;
        x = Math.sin(Math.toRadians(new_degrees)) * new_magnitude;
        y = Math.cos(Math.toRadians(new_degrees)) * new_magnitude;
    }

    public Vector2d add(Vector2d vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vector2d sub(Vector2d vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    Vector2d normalize() {
        return new Vector2d(x / magnitude, y / magnitude);
    }

    public Vector2d divide(double denominator) {
        this.x /= denominator;
        this.y /= denominator;
        return this;
    }
}