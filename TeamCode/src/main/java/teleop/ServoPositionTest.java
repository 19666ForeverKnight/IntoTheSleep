package teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoPositionTest extends LinearOpMode {
    // 0 = claw pitch          Control hub 1    - cp
    // 1 = left intake         Control hub 2    - li
    // 2 = claw arm pitch      Control hub 3    - cap
    // 3 = intake claw         Control hub 4    - ic
    // 4 = right intake        Control hub 5    - ri
    // 5 = claw rorate         Control Hub 6    - cl
    // 6 = extend right        Exp Hub 1   - er
    // 7 = scoring arm         Exp hub 2   - sa
    // 8 = scoring arm pitch (flip)   Exp hub 3   - sap
    // 9 = sweeping            Exp hub 4   - sw
    // 10 = scoring claw       Exp hub 5   - sc
    // 11 = extend left        Exp hub 6   - el

    private DcMotorEx leftFront = null;
    private DcMotorEx leftBack = null;
    private DcMotorEx rightFront = null;
    private DcMotorEx rightBack = null;
    private double x, y, rx, p, posDelta = 0.0003;
    private String[] names = {"cp", "li", "cap", "ic", "ri", "cl", "er", "sa", "sap", "sw", "sc", "el"};
    private int index = 0, servoNum = names.length;
    private Servo[] servos = new Servo[servoNum];
    private double[] poses = new double[servoNum];
    private boolean upHold = false, downHold = false;

    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.get(DcMotorEx.class, "lf");
        leftBack = hardwareMap.get(DcMotorEx.class, "lb");
        rightFront = hardwareMap.get(DcMotorEx.class, "rf");
        rightBack = hardwareMap.get(DcMotorEx.class, "rb");

        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftBack.setDirection(DcMotorEx.Direction.REVERSE);
//        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
//        rightBack.setDirection(DcMotorEx.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        for (int i = 0; i < servoNum; i++) {
            servos[i] = hardwareMap.get(Servo.class, names[i]);
            poses[i] = 0.5;
        }

        waitForStart();
        while (opModeIsActive()) {
            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            rx = gamepad1.right_stick_x;
            if (gamepad1.left_bumper) p = 0.3;
            else p = 1;
            leftFront.setPower((y + x + rx) * p);
            leftBack.setPower((y - x + rx) * p);
            rightFront.setPower((y - x - rx) * p);
            rightBack.setPower((y + x - rx) * p);

//            leftFront.setPower(gamepad1.x ? 1 : 0);
//            leftBack.setPower(gamepad1.a ? 1 : 0);
//            rightFront.setPower(gamepad1.y ? 1 : 0);
//            rightBack.setPower(gamepad1.b ? 1 : 0);

            if (gamepad1.dpad_up && !upHold && index < servoNum - 1) {
                upHold = true;
                index++;
            } else if (!gamepad1.dpad_up) {
                upHold = false;
            }
            if (gamepad1.dpad_down && !downHold && index > 0) {
                downHold = true;
                index--;
            } else if (!gamepad1.dpad_down) {
                downHold = false;
            }

            poses[index] = Math.min(1.0, Math.max(0.0, (gamepad1.right_trigger - gamepad1.left_trigger) / 600 + poses[index]));

            if (gamepad1.right_bumper)
                servos[index].setPosition(poses[index]);

            telemetry.addData("index", String.valueOf(index));
            for (int i = 0; i < servoNum; i++) {
                telemetry.addData(i + " " + names[i], String.valueOf(poses[i]));
            }
            telemetry.update();
        }
    }
}
