package auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystem.Robot;

@Autonomous
public class AutoTestChamber extends OpMode {
    private Telemetry telemetryA;
    private Follower follower;
    private PathChain preload, pushsample, spec1_collect, spec1_score, spec2_collect, spec2_score, spec3_collect, spec3_score, spec4_collect, spec4_score;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        robot.autoInit(hardwareMap);
        robot.scoring.threadStart();
        follower.setStartingPose(new Pose(7, 63, Math.toRadians(0)));

        preload = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(7, 63, Point.CARTESIAN),
                                new Point(38, 76.5, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0)).build();

        pushsample = follower.pathBuilder()
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(38.000, 76.500, Point.CARTESIAN),
                                new Point(26.800, 33.000, Point.CARTESIAN),
                                new Point(25.000, 33.000, Point.CARTESIAN),
                                new Point(48.000, 34.700, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(48.000, 34.700, Point.CARTESIAN),
                                new Point(63.000, 35.000, Point.CARTESIAN),
                                new Point(59.000, 22.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(59.000, 22.000, Point.CARTESIAN),
                                new Point(23.000, 22.000, Point.CARTESIAN)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 5
                        new BezierCurve(
                                new Point(23.000, 24.000, Point.CARTESIAN),
                                new Point(58.000, 31.000, Point.CARTESIAN),
                                new Point(58.000, 13.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(58.000, 13.000, Point.CARTESIAN),
                                new Point(23.000, 13.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 7
                        new BezierCurve(
                                new Point(23.000, 13.000, Point.CARTESIAN),
                                new Point(59.000, 26.000, Point.CARTESIAN),
                                new Point(58.000, 7.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 8
                        new BezierLine(
                                new Point(58.000, 7.000, Point.CARTESIAN),
                                new Point(23.000, 7.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec1_collect = follower.pathBuilder()
                .addPath(
                        // Line 9
                        new BezierCurve(
                                new Point(23.000, 10.000, Point.CARTESIAN),
                                new Point(24.000, 38.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec1_score = follower.pathBuilder()
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(36.500, 73.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec2_collect = follower.pathBuilder()
                .addPath(
                        // Line 11
                        new BezierCurve(
                                new Point(36.500, 73.000, Point.CARTESIAN),
                                new Point(20.000, 24.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec2_score = follower.pathBuilder()
                .addPath(
                        // Line 12
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(36.500, 69.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec3_collect = follower.pathBuilder()
                .addPath(
                        // Line 13
                        new BezierCurve(
                                new Point(36.500, 69.000, Point.CARTESIAN),
                                new Point(17.000, 26.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec3_score = follower.pathBuilder()
                .addPath(
                        // Line 14
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(36.500, 67.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


        spec4_collect = follower.pathBuilder()
                .addPath(
                        // Line 15
                        new BezierCurve(
                                new Point(36.500, 66.000, Point.CARTESIAN),
                                new Point(28.000, 24.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec4_score = follower.pathBuilder()
                .addPath(
                        // Line 16
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(36.500, 64.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.telemetryDebug(telemetryA);
        follower.update();
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(preload, true)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1000),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(400),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(pushsample, true)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec1_collect)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitCommand(100),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec1_score)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.liftBack()),
                                                new InstantCommand(() -> robot.scoring.armToCollect())
                                        )

                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(100),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec2_collect)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec2_score)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.liftBack()),
                                                new InstantCommand(() -> robot.scoring.armToCollect())
                                        )
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(100),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec3_collect)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec3_score)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.liftBack()),
                                                new InstantCommand(() -> robot.scoring.armToCollect())
                                        )
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(100),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec4_collect)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec4_score)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.liftBack()),
                                                new InstantCommand(() -> robot.scoring.armToCollect())
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public void stop() {
        robot.scoring.threadStop();
    }
}
