package teleop;

import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.LIFT_HIGH_BASKET;
import static constants.RobotConstants.LIFT_LOW_BASKET;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

import constants.RobotConstants;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;

@TeleOp
public class A_Competition_Blue extends OpMode {
    private Telemetry telemetryA;
    RobotConstants.AllianceColour color = RobotConstants.AllianceColour.Blue;
    Robot robot = new Robot();
    Vision vision = new Vision();
    Servo led = null;
    private ElapsedTime timer = new ElapsedTime();
    private double x, y, rx, p = 1, extendPercent = 0, rotateDegree = 90, turretDegree = 90;
    private int basketIndex=0, liftPos = 0;
    private boolean manual = false, basket = false, back = true, armDown = false, trans = false, ledOn = true;
    private final int[] basketPos = {LIFT_HIGH_BASKET, LIFT_LOW_BASKET};
    Gamepad prev = new Gamepad();

    @Override
    public void init() {
        led = hardwareMap.get(Servo.class, "led");
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        robot.init(hardwareMap);
        timer.reset();
        telemetryA.update();
        led.setPosition(1);
        vision.initTest(hardwareMap, telemetryA);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }
    @Override
    public void loop() {
        //Drivetrain
        y = gamepad1.left_stick_y * 0.9;
        x = -gamepad1.left_stick_x * 0.9;
        rx = -gamepad1.right_stick_x * 0.85;
        robot.drivetrain.drive(y, x, rx, p);

        if (gamepad1.dpad_up && !prev.dpad_up) {
            if (extendPercent == 0) turretDegree = 90;
            extendPercent += 50;
            if (extendPercent > 100) extendPercent -= 100;
        } else if (gamepad1.dpad_down) {
            extendPercent = 0;
        }

        if (gamepad1.dpad_left && rotateDegree > 0) rotateDegree -= 1;
        else if (gamepad1.dpad_right && rotateDegree < 180) rotateDegree += 1;

        if (gamepad1.touchpad && !prev.touchpad) {
            ledOn = !ledOn;
        }
        if (ledOn) led.setPosition(1);
        else led.setPosition(0);

        if (gamepad1.a && !prev.a) {
            if (armDown) {
                vision.withoutX = 100;
                vision.withoutY = 100;
                List<Double> intakePos = vision.getLatestResult(color, true);
                if (intakePos.get(0).isNaN() || intakePos.get(1).isNaN() || intakePos.get(2).isNaN()) {
                    turretDegree = 0;
                    extendPercent = 0;
                    rotateDegree = 0;
                } else {
                    turretDegree = intakePos.get(0);
                    extendPercent = intakePos.get(1);
                    rotateDegree = intakePos.get(2);
                }

            } else {
                armDown = true;
                turretDegree = 90;
                rotateDegree = 90;
                robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
                robot.scoring.armToPreTrans();
            }
        }

        if (gamepad1.right_trigger > 0) {
            robot.collectApple();
        }

        if (gamepad1.x && !prev.x) {
            trans = true;
            extendPercent = 0;
            turretDegree = 90;
            rotateDegree = 90;
            robot.trans();
            timer.reset();
            armDown = false;
        }
        if (trans && timer.milliseconds() > 1000) {
            trans = false;
        }

        if (gamepad1.y && !prev.y) {
            if (!basket) basketIndex = 1;
            basket = true;
            manual = false;
            back = false;
            basketIndex = (basketIndex + 3) % 2;
        }

        if (gamepad1.b) {
            back = true;
            manual = false;
            basketIndex = 1;
            timer.reset();
            robot.scoring.armToPreTrans();
        }

        if (gamepad1.right_bumper) {
            robot.scoring.scoreOpen();
        }

        if (gamepad1.left_trigger > 0 && robot.scoring.getLiftPosition() >= 0) {
            robot.scoring.setLiftPower(-1);
            manual = true;
        } else if (gamepad1.left_bumper && robot.scoring.getLiftPosition() <= LIFT_HIGH_BASKET) {
            robot.scoring.setLiftPower(1);
            manual = true;
        } else if (manual) {
            robot.scoring.setLiftPower(0);
        }

        if (!manual) {
            if (back) {
                liftPos = 0;
                if (timer.milliseconds() > 1500) {
                    manual = true;
                }
            } else if (basket) {
                liftPos = basketPos[basketIndex];
                if (Math.abs(robot.scoring.getLiftPosition() - liftPos) < 350){
                    robot.scoring.armToBasket();
                }
            }
            robot.scoring.runToPosition(liftPos);
        }

        if (!trans) {
            robot.intake.setTurretDegree(turretDegree);
            robot.intake.setRotateDegree(rotateDegree);
            robot.intake.setExtendPercent(extendPercent);
        }
        prev.copy(gamepad1);
    }

    @Override
    public void stop() {
    }
}