package teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp
public class ledTest extends LinearOpMode {
    Servo led = null;
    public static double light = 0.0;
    public static int waitTime = 150;
    @Override
    public void runOpMode() throws InterruptedException {
        led = hardwareMap.get(Servo.class, "led");

        waitForStart();
        while (opModeIsActive()) {
            if (waitTime == 0) {
                led.setPosition(light);
            } else {
                light += 0.05;
                if (light > 1) light -= 1;
                led.setPosition(light);
                sleep(waitTime);
            }
        }
    }
}
