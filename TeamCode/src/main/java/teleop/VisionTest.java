package teleop;

import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT_LIMIT;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

import constants.RobotConstants;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;
import subsystems.SleepyStuffff.Math.VisionUtil;
import subsystems.SleepyStuffff.Util.DetectorLLResultPair;

@TeleOp
public class VisionTest extends OpMode {
    private Telemetry telemetryA;
    private Robot robot = new Robot();
    private Vision vision = new Vision();

    @Override
    public void init(){
        robot.init(hardwareMap);
        vision.Init(hardwareMap, telemetryA);
    }
    @Override
    public void loop(){
        if(gamepad1.triangle){
            robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT);
            robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_MID);
            robot.intake.setExtendPercent(0);
        }
        if(gamepad1.cross){
            robot.grabApple(vision.getLatestResult(RobotConstants.AllianceColour.Blue, true));
        }
        if(gamepad1.circle) {
            robot.collectApple();
        }

        List<DetectorResult> test = vision.limelight.getLatestResult().getDetectorResults();
        if (!test.isEmpty()) {
            List<List<Double>> corners = test.get(0).getTargetCorners();
            telemetry.addData("tx", test.get(0).getTargetXDegrees());
            telemetry.addData("ty", test.get(0).getTargetYDegrees());
            telemetry.addData("ratio", Math.abs(corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1)));
        }
        telemetry.update();

//        telemetryA.addData("xDis", xDis);
//        telemetryA.addData("yDis", yDis);
//        telemetryA.addData("intakePercentage", intakePercentage);
    }
}