package teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import constants.Configs;

@TeleOp(name = "Liftest", group = "Test")
public class LiftTest extends OpMode {
    private DcMotorEx liftLeft;
    private DcMotorEx liftRight;
    private DcMotorEx liftMiddle;

    @Override
    public void init() {
        liftLeft = hardwareMap.get(DcMotorEx.class, Configs.LIFT_LEFT);
        liftMiddle = hardwareMap.get(DcMotorEx.class, Configs.LIFT_MIDDLE);
        liftRight = hardwareMap.get(DcMotorEx.class, Configs.LIFT_RIGHT);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMiddle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize lift motors
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        liftMiddle.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {

        if (gamepad1.y) {
            liftLeft.setPower(1);
            liftMiddle.setPower(1);
            liftRight.setPower(1);
        } else if (gamepad1.a) {
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
