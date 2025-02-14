package teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import constants.Configs;

@TeleOp(name = "Servotest", group = "Test")
public class Servotest extends OpMode {
    private Servo x20_1;
    private Servo x20_2;
    private Servo x20_3;
    private Servo x20_4;
    private Servo x20_5;
    private Servo x20_6;
    private Servo x20_7;
    private Servo x20_8;
    private Servo x20_9;
    private Servo x20_10;
    private Servo x20_11;
    private int index = 0;

    @Override
    public void init() {
        x20_1 = hardwareMap.get(Servo.class, "0");
        x20_2 = hardwareMap.get(Servo.class, "1");
        x20_3 = hardwareMap.get(Servo.class, "2");
        x20_4 = hardwareMap.get(Servo.class, "3");
        x20_5 = hardwareMap.get(Servo.class, "4");
        x20_6 = hardwareMap.get(Servo.class, "5");
        x20_7 = hardwareMap.get(Servo.class, "6");
        x20_8 = hardwareMap.get(Servo.class, "7");
        x20_9 = hardwareMap.get(Servo.class, "8");
        x20_10 = hardwareMap.get(Servo.class, "9");
        x20_11 = hardwareMap.get(Servo.class, "10");
        index = 0;

        x20_1.setPosition(0.5);
        x20_2.setPosition(0.5);
        x20_3.setPosition(0.5);
        x20_4.setPosition(0.5);
        x20_5.setPosition(0.5);
        x20_6.setPosition(0.5);
        x20_7.setPosition(0.5);
        x20_8.setPosition(0.5);
        x20_9.setPosition(0.5);
        x20_10.setPosition(0.5);
        x20_11.setPosition(0.5);
    }

    public void loop() {
        index = 0;
//        if(gamepad1.a){
//            index ++;
//        }
//        if (gamepad1.b){
//            index --;
//        }
        if(gamepad1.dpad_up){
            switch (index){
                case 1:
                    double current_pos = x20_1.getPosition();
                    x20_1.setPosition(current_pos + 0.1);
                    break;
                case 2:
                    double current_pos2 = x20_2.getPosition();
                    x20_2.setPosition(current_pos2 + 0.1);
                    break;
                case 3:
                    double current_pos3 = x20_3.getPosition();
                    x20_3.setPosition(current_pos3 + 0.1);
                    break;
                case 4:
                    double current_pos4 = x20_4.getPosition();
                    x20_4.setPosition(current_pos4 + 0.1);
                    break;
                case 5:
                    double current_pos5 = x20_5.getPosition();
                    x20_5.setPosition(current_pos5 + 0.1);
                    break;
                case 6:
                    double current_pos6 = x20_6.getPosition();
                    x20_6.setPosition(current_pos6 + 0.1);
                    break;
                case 7:
                    double current_pos7 = x20_7.getPosition();
                    x20_7.setPosition(current_pos7 + 0.1);
                    break;
                case 8:
                    double current_pos8 = x20_8.getPosition();
                    x20_8.setPosition(current_pos8 + 0.1);
                    break;
                case 9:
                    double current_pos9 = x20_9.getPosition();
                    x20_9.setPosition(current_pos9 + 0.1);
                    break;
                case 10:
                    double current_pos10 = x20_10.getPosition();
                    x20_10.setPosition(current_pos10 + 0.1);
                    break;
                case 11:
                    double current_pos11 = x20_11.getPosition();
                    x20_11.setPosition(current_pos11 + 0.1);
                    break;
            }
            if(gamepad1.dpad_down){
                switch (index){
                    case 1:
                        double current_pos = x20_1.getPosition();
                        x20_1.setPosition(current_pos - 0.1);
                        break;
                    case 2:
                        double current_pos2 = x20_2.getPosition();
                        x20_2.setPosition(current_pos2 - 0.1);
                        break;
                    case 3:
                        double current_pos3 = x20_3.getPosition();
                        x20_3.setPosition(current_pos3 - 0.1);
                        break;
                    case 4:
                        double current_pos4 = x20_4.getPosition();
                        x20_4.setPosition(current_pos4 - 0.1);
                        break;
                    case 5:
                        double current_pos5 = x20_5.getPosition();
                        x20_5.setPosition(current_pos5 - 0.1);
                        break;
                    case 6:
                        double current_pos6 = x20_6.getPosition();
                        x20_6.setPosition(current_pos6 - 0.1);
                        break;
                    case 7:
                        double current_pos7 = x20_7.getPosition();
                        x20_7.setPosition(current_pos7 - 0.1);
                        break;
                    case 8:
                        double current_pos8 = x20_8.getPosition();
                        x20_8.setPosition(current_pos8 - 0.1);
                        break;
                    case 9:
                        double current_pos9 = x20_9.getPosition();
                        x20_9.setPosition(current_pos9 - 0.1);
                        break;
                    case 10:
                        double current_pos10 = x20_10.getPosition();
                        x20_10.setPosition(current_pos10 - 0.1);
                        break;
                    case 11:
                        double current_pos11 = x20_11.getPosition();
                        x20_11.setPosition(current_pos11 - 0.1);
                        break;
                }
            }
        }
        telemetry.addData("index", index);
        telemetry.addData("servo1_pos", x20_1.getPosition());
        telemetry.addData("servo2_pos", x20_1.getPosition());
        telemetry.addData("servo3_pos", x20_1.getPosition());
        telemetry.addData("servo4_pos", x20_1.getPosition());
        telemetry.addData("servo5_pos", x20_1.getPosition());
        telemetry.addData("servo6_pos", x20_1.getPosition());
        telemetry.addData("servo7_pos", x20_1.getPosition());
        telemetry.addData("servo8_pos", x20_1.getPosition());
        telemetry.addData("servo9_pos", x20_1.getPosition());
        telemetry.addData("servo10_pos", x20_1.getPosition());
        telemetry.addData("servo11_pos", x20_1.getPosition());
        telemetry.update();
    }
}
