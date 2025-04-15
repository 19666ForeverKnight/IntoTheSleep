//package teleop;
//
//import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
//import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
//import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
//import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT_LIMIT;
//
//import com.arcrobotics.ftclib.command.CommandScheduler;
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//import java.util.List;
//
//import commands.GrowAppleInFarm;
//import constants.RobotConstants;
//import subsystems.IntakeSubsystem.SuperIntake;
//import subsystems.IntakeSubsystem.Vision;
//import subsystems.Robot;
//import subsystems.SleepyStuffff.Math.VisionUtil;
//import subsystems.SleepyStuffff.Util.DetectorLLResultPair;
//
//@TeleOp
//public class VisionTest extends OpMode {
//    private Telemetry telemetryA;
//    private Robot robot = new Robot();
//    private Vision vision = new Vision();
//    private CommandScheduler cs;
//    private DetectorLLResultPair AppleTypePair;
//    private double xDis = 0, yDis = 0, intakePercentage = 0;
//    @Override
//    public void init(){
//        robot.autoInit(hardwareMap);
//        vision.Init(hardwareMap, telemetryA);
//    }
//    @Override
//    public void loop(){
////        AppleTypePair = vision.getLatestResult(RobotConstants.AllianceColour.Blue);
////        xDis = VisionUtil.xDistance(AppleTypePair.getDr().getTargetXDegrees(), AppleTypePair.getDr().getTargetYDegrees());
////        yDis = VisionUtil.yDistance(AppleTypePair.getDr().getTargetYDegrees());
////        intakePercentage = VisionUtil.getExtendPercent(xDis, yDis);
//
//        if(gamepad1.triangle){
//            robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT);
//            robot.intake.setExtendPercent(0);
//        }
//        if(gamepad1.cross){
//            new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Blue).GrabApple();
//        }
//        if(gamepad1.circle) {
//            robot.thrownRightStay();
//        }
//
////        VisionUtil.getExtendPercent(VisionUtil.xDistance(vision.getLatestResult(RobotConstants.AllianceColour.Blue).getLr().getTx(), vision.getLatestResult(RobotConstants.AllianceColour.Blue).getLr().getTy()), VisionUtil.yDistance(vision.getLatestResult(RobotConstants.AllianceColour.Blue).getLr().getTy()));
////        telemetry.addData("ydis", VisionUtil.yDistance(vision.getLatestResult(RobotConstants.AllianceColour.Blue).getLr().getTy()));
//////        telemetry.addData("xdis", VisionUtil.xDistance(vision.getLatestResult(RobotConstants.AllianceColour.Blue).getLr().getTx(), vision.getLatestResult(RobotConstants.AllianceColour.Blue).getLr().getTy()));
////        telemetry.addData("length", VisionUtil.extendLength);
////        telemetry.update();
//
////        telemetryA.addData("xDis", xDis);
////        telemetryA.addData("yDis", yDis);
////        telemetryA.addData("intakePercentage", intakePercentage);
//    }
//}