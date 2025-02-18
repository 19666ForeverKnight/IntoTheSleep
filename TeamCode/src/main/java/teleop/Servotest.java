package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import constants.Configs;

@TeleOp(name = "Servotest", group = "Test")
public class Servotest extends OpMode {
    private Servo x20;
//    private int index = 0;

    @Override
    public void init() {
        // 0 = claw pitch          Control hub 1    - cp
        // 2 = left intake         Control hub 2    - li
        // 1 = claw arm pitch      Control hub 3    - cap
        // 3 = intake claw         Control hub 4    - ic
        // 4 = right intake        Control hub 5    - ri
        // 5 = claw rorate         Control Hub 6    - cl
        // 6 = extend right        Exp Hub 1   - er
        // 7 = scoring arm          Exp hub 2   - sa
        // 8 = scoring arm pitch (flip)   Exp hub 3   - sap
        // 9 = sweeping            Exp hub 4   - sw
        // 10 = scoring claw       Exp hub 5   - sc
        // 11 = extend left        Exp hub 6   - el

        x20 = hardwareMap.get(Servo.class, "0");


        x20.setPosition(0.7); // claw yaw
    }

    public void loop() {
    }
}
