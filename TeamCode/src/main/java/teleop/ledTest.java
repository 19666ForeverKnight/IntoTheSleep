package teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class ledTest extends LinearOpMode {
    Servo led = null;
    @Override
    public void runOpMode() throws InterruptedException {
        led = hardwareMap.get(Servo.class, "led");

        waitForStart();
        while (opModeIsActive()) {
            led.setPosition(1);
//            sleep(500);
//            led.setPosition(0.5);
//            sleep(500);
//            led.setPosition(0);
//            sleep(500);
//            led.setPosition(0.5);
//            sleep(500);
        }
    }
}
