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

import commands.GrowAppleInFarm;
import constants.RobotConstants;
import subsystems.IntakeSubsystem.SuperIntake;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;

@TeleOp
public class VisionTest extends OpMode {
    private Telemetry telemetryA;
    private SuperIntake intake = new SuperIntake();
    private Vision vision = new Vision();
    private CommandScheduler cs;
    @Override
    public void init(){
        intake.autoInit(hardwareMap);
        vision.Init(hardwareMap, telemetryA);
    }
    @Override
    public void loop(){
        if(gamepad1.a){
            intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT);
        }
        if(gamepad1.b){
            new GrowAppleInFarm(vision, intake, RobotConstants.AllianceColour.Red).GrabApple();
        }
    }
}

//package teleop;
//
//import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
//import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
//import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT_LIMIT;
//
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import java.util.List;
//
//import subsystems.IntakeSubsystem.SuperIntake;
//import subsystems.IntakeSubsystem.Vision;
//import subsystems.Robot;
//
//@TeleOp
//public class VisionTest extends LinearOpMode {
//    Vision ll = new Vision();
//    Robot robot = new Robot();
//    double turretDegree = 90, xDis = 0, yDis = 0;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        ll.Init(hardwareMap);
//        ll.start();
//        robot.init(hardwareMap);
//        robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            if (gamepad1.a) {
//                LLResult result = ll.limelight.getLatestResult();
//                List<DetectorResult> detectorResults = result.getDetectorResults();
//
//                if (result != null) {
//                    telemetry.addData("tx", result.getTx());
//                    telemetry.addData("ty", result.getTy());
//                    xDis = ll.xDistance(result.getTx(), result.getTy());
//                    yDis = ll.yDistance(result.getTy());
//                    telemetry.addData("txd", xDis);
//                    telemetry.addData("tyd", yDis);
//                    turretDegree = 90 + ll.getIntakeDegree(ll.xDistance(result.getTx(), result.getTy()));
//                    robot.intake.setTurretDegree(turretDegree);
//                    robot.intake.setExtendPercent(ll.getExtendPercent(xDis, yDis));
//                }
//
//                if (detectorResults != null) {
//                    telemetry.addData("size", detectorResults.size());
////                    for (DetectorResult dr : detectorResults) {
////
////                    }
//                    List<List<Double>> corners = detectorResults.get(0).getTargetCorners();
//                    telemetry.addData("ratio", (corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1)));
//                    if (((corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1))) > (Math.abs(result.getTx()) > 15 ? 1.3 : 1)) {
//                        robot.intake.setRotateDegree(turretDegree > 90 ? 270 - turretDegree : 90 - turretDegree);
//                    } else {
//                        robot.intake.setRotateDegree(180 - turretDegree);
//                    }
//                }
//                telemetry.update();
//            }
//
//            if (gamepad1.right_bumper) {
//                robot.collectAppleSlow();
//            }
//            if (gamepad1.triangle) {
//                robot.intake.setExtendPercent(0);
//            }
//            if (gamepad1.circle) {
//                robot.thrownRightStay();
//            }
//
//            telemetry.update();
//        }
//    }
//}

