package teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import constants.Configs;

@TeleOp(name = "AscentTest", group = "Test")
public class AscentTest extends LinearOpMode {
    private DcMotorEx liftLeft;
    private DcMotorEx liftRight;
    private DcMotorEx liftMiddle;
    ElapsedTime timer = new ElapsedTime();
    boolean prevY = false;
    double ms = 0;


    @Override
    public void runOpMode() {
        liftLeft = hardwareMap.get(DcMotorEx.class, Configs.LIFT_LEFT);
        liftMiddle = hardwareMap.get(DcMotorEx.class, Configs.LIFT_MIDDLE);
        liftRight = hardwareMap.get(DcMotorEx.class, Configs.LIFT_RIGHT);

        liftLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        liftMiddle.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Initialize lift motors
        liftLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        liftMiddle.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        timer.reset();
        while (opModeIsActive()) {
            if (gamepad2.y && liftLeft.getCurrentPosition() < 1365 && liftRight.getCurrentPosition() < 1365) {
                ms = timer.milliseconds();
                liftLeft.setPower(1);
                liftMiddle.setPower(1);
                liftRight.setPower(1);
            } else if (gamepad2.a) {
                liftLeft.setPower(-1);
                liftMiddle.setPower(-1);
                liftRight.setPower(-1);
            } else {
                liftLeft.setPower(0);
                liftMiddle.setPower(0);
                liftRight.setPower(0);
            }

            telemetry.addData("liftLeftPos", liftLeft.getCurrentPosition());
            telemetry.addData("liftRightPos", liftRight.getCurrentPosition());
            telemetry.addData("liftMiddlePos", liftMiddle.getCurrentPosition());
            telemetry.update();
        }
    }
}
