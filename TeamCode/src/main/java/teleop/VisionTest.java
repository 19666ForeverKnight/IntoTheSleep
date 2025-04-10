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
    private Robot robot = new Robot();
    private Vision vision = new Vision();
    private CommandScheduler cs;
    @Override
    public void init(){
        robot.autoInit(hardwareMap);
        vision.Init(hardwareMap, telemetryA);
    }
    @Override
    public void loop(){
        if(gamepad1.triangle){
            robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT);
            robot.intake.setExtendPercent(0);
        }
        if(gamepad1.cross){
            new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Red).GrabApple();
        }
        if(gamepad1.circle) {
            robot.thrownRightStay();
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
//    String color = "blue";
//    List<List<Double>> corners;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        ll.Init(hardwareMap, telemetry);
//        ll.goWork();
//        robot.init(hardwareMap);
//        robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            LLResult result = ll.limelight.getLatestResult();
//            List<DetectorResult> detectorResults = result.getDetectorResults();
//            if (!detectorResults.isEmpty()) {
//                telemetry.addData("size", detectorResults.size());
////                    for (DetectorResult dr : detectorResults) {
////
////                    }
//                for (int i = 0; i < detectorResults.size(); i++) {
//                    if (detectorResults.get(i).getClassName().equals(color) || detectorResults.get(i).getClassName().equals("yellow")) {
//                        telemetry.addData("tx", detectorResults.get(i).getTargetXDegrees());
//                        telemetry.addData("ty", detectorResults.get(i).getTargetYDegrees());
//                        xDis = ll.xDistance(detectorResults.get(i).getTargetXDegrees(), detectorResults.get(i).getTargetYDegrees());
//                        yDis = ll.yDistance(detectorResults.get(i).getTargetYDegrees());
//                        telemetry.addData("txd", xDis);
//                        telemetry.addData("tyd", yDis);
//                        corners = detectorResults.get(i).getTargetCorners();
//                        telemetry.addData("ratio", (corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1)));
//                        telemetry.addData("0x", corners.get(0).get(0));
//                        telemetry.addData("0y", corners.get(0).get(1));
//                        telemetry.addData("1x", corners.get(1).get(0));
//                        telemetry.addData("1y", corners.get(1).get(1));
//                        telemetry.addData("2x", corners.get(2).get(0));
//                        telemetry.addData("2y", corners.get(2).get(1));
//                        telemetry.addData("3x", corners.get(3).get(0));
//                        telemetry.addData("3y", corners.get(3).get(1));
//                    }
//                }
//            }
//            telemetry.update();
//
//            if (gamepad1.left_bumper) color = "red";
//            if (gamepad1.left_trigger > 0) color = "blue";
//
//            if (gamepad1.a) {
//                turretDegree = 90 + ll.getIntakeDegree(ll.xDistance(result.getTx(), result.getTy()));
//                robot.intake.setTurretDegree(turretDegree);
//                robot.intake.setExtendPercent(ll.getExtendPercent(xDis, yDis));
//                if (((corners.get(1).get(0) - corners.get(0).get(0)) / (corners.get(3).get(1) - corners.get(0).get(1))) > (Math.abs(result.getTx()) > 15 ? 1.3 : 1)) {
//                    robot.intake.setRotateDegree(turretDegree > 90 ? 270 - turretDegree : 90 - turretDegree);
//                } else {
//                    robot.intake.setRotateDegree(180 - turretDegree);
//                }
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
//
