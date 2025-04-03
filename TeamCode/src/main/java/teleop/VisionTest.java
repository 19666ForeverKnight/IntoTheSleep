package teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;

import java.util.List;

import subsystems.IntakeSubsystem.SuperIntake;
import subsystems.IntakeSubsystem.Vision;

@TeleOp
public class VisionTest extends LinearOpMode {
    Vision ll = new Vision();
//    SuperIntake intake = new SuperIntake();

    @Override
    public void runOpMode() throws InterruptedException {
        ll.Init(hardwareMap);
        ll.start();
//        intake.init(hardwareMap);
        while (opModeIsActive()) {
            List<DetectorResult> detectorResults = ll.limelight.getLatestResult().getDetectorResults();
            if (detectorResults != null) {
                telemetry.addData("size", detectorResults.size());
                for (DetectorResult dr : detectorResults) {
                    telemetry.addData("xp", dr.getTargetXPixels());
                    telemetry.addData("yp", dr.getTargetYPixels());
                    telemetry.addData("xd", dr.getTargetXDegrees());
                    telemetry.addData("yd", dr.getTargetYDegrees());
                    List<List<Double>> corners = dr.getTargetCorners();
//                    telemetry.addData("corner", dr.getTargetCorners().toString());
                    telemetry.addData("x1", corners.get(0).get(0));
                    telemetry.addData("y1", corners.get(0).get(1));
                    telemetry.addData("x2", corners.get(1).get(0));
                    telemetry.addData("y2", corners.get(1).get(1));
                    telemetry.addData("x3", corners.get(2).get(0));
                    telemetry.addData("y3", corners.get(2).get(1));
                    telemetry.addData("x4", corners.get(3).get(0));
                    telemetry.addData("y4", corners.get(3).get(1));
                }
            } else {
                telemetry.addData("size", "empty");
            }
            telemetry.update();
        }
    }
}
