package teleop;

import static constants.DriveConstants.Drivetrain_Power;
import static constants.DriveConstants.Drivetrain_Rotate_Speed_Factor;
import static constants.DriveConstants.Drivetrain_X_Speed_Factor;
import static constants.DriveConstants.Drivetrain_Y_Speed_Factor;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_LEFT_OUT;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_OUT;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_DELTA;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT_LIMIT;
import static constants.RobotConstants.LIFT_HIGH_BASKET;
import static constants.RobotConstants.LIFT_OPEN_SPECIMEN_CLAW;
import static constants.RobotConstants.ROTATE_DELTA;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import subsystems.Robot;

public class Competition_New extends OpMode {
    private Telemetry telemetryA;
    Robot robot = new Robot();

    // Drivetrain
    private double x, y, rx, p;

    // Controller
    private double prevRT = 0, prevLT = 0;

    // Mode
    //private boolean ;

    // Upper variables
    private final double[][] extendPos = {{EXTEND_LEFT_OUT, EXTEND_RIGHT_OUT}, {EXTEND_LEFT_IN + (EXTEND_LEFT_OUT - EXTEND_LEFT_IN) / 2, EXTEND_RIGHT_IN + (EXTEND_RIGHT_OUT - EXTEND_RIGHT_IN) / 2}};
    private int basketIndex=0, chamberIndex=0, liftPos = 0, extendIndex = 0;
    public double intakeExtendPosRight = EXTEND_RIGHT_IN, intakeExtendPosLeft = EXTEND_LEFT_IN, intakeRotatePos = INTAKE_CLAW_ROTATE_MID, intakeTurretPos = INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;

    @Override
    public void init() {
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        // Drivetrain Control
        y = gamepad1.left_stick_y * Drivetrain_Y_Speed_Factor;
        x = -gamepad1.left_stick_x * Drivetrain_X_Speed_Factor;
        rx = -gamepad1.right_stick_x * Drivetrain_Rotate_Speed_Factor;
        p = Drivetrain_Power;
        robot.drivetrain.drive(y, x, rx, p);

        // Sweep Control
        if (gamepad1.left_bumper) {
            robot.sweep();
            gamepad1.rumble(1, 0, 100);
        }

        // -------------------------------- Upper Control--------------------------------------
        prevRT = gamepad2.right_trigger;
        prevLT = gamepad2.left_trigger;


        // ----------- Scoring -----------

        // Lift control: Linear Precise Control of Lift (Up and Down)
        if (gamepad2.right_stick_y > 0.2 && robot.scoring.getLiftPosition() >= 0) {  // 软件限位
            robot.scoring.setLiftPower(-1);
        } else if (gamepad2.right_stick_y < -0.2 && robot.scoring.getLiftPosition() <= LIFT_HIGH_BASKET) {
            robot.scoring.setLiftPower(1);
        } else {
            robot.scoring.setLiftPower(0);
        }

        // Basket Lift pre-define control: Click once to alter between high and low basket
        if(gamepad2.y){
            basketIndex = (basketIndex + 3) % 2;
        }

        // Chamber Lift pre-define control: click once to alter between high chamber and specimen pickup position
        if(gamepad2.a){
            chamberIndex = (basketIndex + 1) % 2;
        }

        // Lift to low
        if(gamepad2.b){

        }

        // Lower Chamber to the end
        // Open scoring claw
        if(gamepad1.right_bumper){
            robot.scoring.scoreOpen();
        }

        // ----------- Intake -----------

        // Semi-auto thrown specimen from the right side to the observation zone
        if(gamepad1.right_trigger - prevRT > 0){
            robot.thrownRight();
        }

        // Extend control: Click once to set extend position to 50%, 100%, and 0%.
        if (gamepad2.dpad_up) {
            extendIndex = (extendIndex + 1) % 2;
            intakeExtendPosLeft = extendPos[extendIndex][0];
            intakeExtendPosRight = extendPos[extendIndex][1];
            p = 0.45;
        }
        if (gamepad2.dpad_down) {
            intakeExtendPosLeft = EXTEND_LEFT_IN;
            intakeExtendPosRight = EXTEND_RIGHT_IN;
            extendIndex = 0;
            p = 1;
        }

        // Intake claw rotate control:
        if(gamepad2.dpad_left && robot.intake.getClawRotatePosition() > INTAKE_CLAW_ROTATE_LEFT_LIMIT) {
            intakeRotatePos -= ROTATE_DELTA;
        } else if(gamepad2.dpad_right && robot.intake.getClawRotatePosition() < INTAKE_CLAW_ROTATE_RIGHT_LIMIT) {
            intakeRotatePos += ROTATE_DELTA;
        }

        // Turret control: Linear Precise Control of  Turret turning (Left and Right)
        if(gamepad2.left_stick_x > 0 && intakeTurretPos < INTAKE_CLAW_TURRET_RIGHT_LIMIT) {
            intakeTurretPos += INTAKE_CLAW_TURRET_DELTA;
        } else if(gamepad2.left_stick_x < -0 && intakeTurretPos > INTAKE_CLAW_TURRET_LEFT_LIMIT) {
            intakeTurretPos -= INTAKE_CLAW_TURRET_DELTA;
        }

    }
}
