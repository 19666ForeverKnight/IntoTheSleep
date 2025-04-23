package auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import constants.RobotConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;

@Autonomous
public class AutoVisionTest extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    private Vision vision = new Vision();
    Robot robot = new Robot();
    RobotConstants.AllianceColour colour = RobotConstants.AllianceColour.Red;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
//        FollowerConstants.xMovement = 83;
//        FollowerConstants.yMovement = 83;
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(7, 112, Math.toRadians(-90)));
        robot.autoInit(hardwareMap);
        vision.init(hardwareMap, telemetryA);
        telemetryA.update();
        CommandScheduler.getInstance().reset();
    }
    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.follower.update();
        follower.follower.telemetryDebug(telemetryA);
    }
    @Override
    public void start() {
        robot.scoring.scoreClose();
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new WaitCommand(50),
                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, true))),
                        new WaitCommand(250),
                        new InstantCommand(() -> robot.collectApple())
                        )
        );
    }
    @Override
    public void stop() {
        robot.scoring.threadStop();
        CommandScheduler.getInstance().reset();
        vision.RIP();
    }
}
