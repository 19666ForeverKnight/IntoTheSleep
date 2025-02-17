package subsystem;

import static constants.RobotConstants.SWEEPING_APPLE;
import static constants.RobotConstants.SWEEPING_INIT;

import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import constants.Configs;

public class Drivetrain {
    private DcMotorEx leftFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightFront;
    private DcMotorEx rightBack;
    private Servo sweep;
    public GoBildaPinpointDriver pinPoint;
    private double theta, power, turn, realTheta;

    public void init (HardwareMap hardwareMap) {
        pinPoint = hardwareMap.get(GoBildaPinpointDriver.class, Configs.Pinpoint);
        leftFront = hardwareMap.get(DcMotorEx.class, Configs.LEFT_FRONT);
        leftBack = hardwareMap.get(DcMotorEx.class, Configs.LEFT_BACK);
        rightFront = hardwareMap.get(DcMotorEx.class, Configs.RIGHT_FRONT);
        rightBack = hardwareMap.get(DcMotorEx.class, Configs.RIGHT_BACK);
        sweep = hardwareMap.get(Servo.class, Configs.SWEEP);

        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftBack.setDirection(DcMotorEx.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        sweepIn();
    }

    public void drive(double y, double x, double rx, double powerScale) {
        leftFront.setPower((y + x + rx) * powerScale);
        leftBack.setPower((y - x + rx) * powerScale);
        rightFront.setPower((y - x - rx) * powerScale);
        rightBack.setPower((y + x - rx) * powerScale);
    }

    public double getHeading() {
        return Math.toDegrees(pinPoint.getHeading());
    }

    public void sweepIn() {
        sweep.setPosition(SWEEPING_INIT);
    }

    public void sweepOut() {
        sweep.setPosition(SWEEPING_APPLE);
    }

    public void driveFieldOriented(double y, double x, double rx, double p) {
        pinPoint.update();
        theta = Math.atan2(y, x) * 180 / Math.PI;
        power = Math.hypot(x, y);
        turn = rx;

        realTheta = (360 - pinPoint.getPosition().getHeading(AngleUnit.DEGREES)) + theta;

        double sin = Math.sin((realTheta * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((realTheta * (Math.PI / 180)) - (Math.PI / 4));
        double maxSinCos = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFrontPower = (power * cos / maxSinCos + turn);
        double rightFrontPower = (power * sin / maxSinCos - turn);
        double leftBackPower = (power * sin / maxSinCos + turn);
        double rightBackPower = (power * cos / maxSinCos - turn);

//        double maxPow = Math.max(Math.abs(leftFrontPower), Math.max(Math.abs(leftBackPower), Math.max(Math.abs(rightFrontPower), Math.abs(rightBackPower))));
//        leftFrontPower /= maxPow;
//        leftBackPower /= maxPow;
//        rightFrontPower /= maxPow;
//        rightBackPower /= maxPow;

        leftFront.setPower(leftFrontPower * p);
        rightFront.setPower(rightFrontPower * p);
        leftBack.setPower(leftBackPower * p);
        rightBack.setPower(rightBackPower * p);
    }
} 