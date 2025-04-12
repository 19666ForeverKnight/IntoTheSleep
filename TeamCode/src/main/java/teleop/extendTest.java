package teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import subsystems.IntakeSubsystem.SuperIntake;

@TeleOp
public class extendTest extends LinearOpMode {
    SuperIntake intake = new SuperIntake();
    boolean prevUp = false, prevDown = false, prevCross = false, hor = true;
    int p = 0;
    double turret = 90;

    @Override
    public void runOpMode() throws InterruptedException {
        intake.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !prevUp) {
                p += 5;
            }

            if (gamepad1.dpad_down && !prevDown) {
                p -= 5;
            }

            prevUp = gamepad1.dpad_up;
            prevDown = gamepad1.dpad_down;

            intake.setExtendPercent(p);

            if (gamepad1.triangle) {
                turret = 90;
            } else if (gamepad1.square) {
                turret += 0.1;
            } else if (gamepad1.circle) {
                turret -= 0.1;
            }

            if (gamepad1.cross && !prevCross) {
                hor = !hor;
            }

            if (hor) {
                intake.setTurretDegree(turret);
                intake.setRotateDegree(180 - turret);
            } else {
                intake.setTurretDegree(turret);
                intake.setRotateDegree(turret > 90 ? 270 - turret : 90 - turret);
            }

            prevCross = gamepad1.cross;

            telemetry.addData("percent", p);
            telemetry.addData("turret", turret);
            telemetry.addData("hor", hor);
            telemetry.update();
        }
    }
}
