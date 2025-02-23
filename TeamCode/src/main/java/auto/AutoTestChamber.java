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
        robot.autoInitRed(hardwareMap);
        robot.scoring.threadStart();
        follower.setStartingPose(new Pose(7, 63, Math.toRadians(0)));

        preload = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(7.000, 63.000, Point.CARTESIAN),
                                new Point(41.000, 74.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0)).build();

        pushsample = follower.pathBuilder()
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(41.000, 74.500, Point.CARTESIAN),
                                new Point(26.800, 33.000, Point.CARTESIAN),
                                new Point(25.000, 33.000, Point.CARTESIAN),
                                new Point(47.000, 34.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(47.000, 34.000, Point.CARTESIAN),
                                new Point(80.000, 25.000, Point.CARTESIAN),
                                new Point(25.000, 22.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 4
                        new BezierCurve(
                                new Point(25.000, 22.000, Point.CARTESIAN),
                                new Point(76.700, 23.600, Point.CARTESIAN),
                                new Point(77.000, 11.000, Point.CARTESIAN),
                                new Point(25.000, 13.000, Point.CARTESIAN)
                        )

                ).setConstantHeadingInterpolation(Math.toRadians(0))
//                .addPath(
//                        // Line 5
//                        new BezierCurve(
//                                new Point(25.000, 13.000, Point.CARTESIAN),
//                                new Point(81.500, 24.000, Point.CARTESIAN),
//                                new Point(82.090, 0.000, Point.CARTESIAN),
//                                new Point(22.000, 6.000, Point.CARTESIAN)
//                        )
//                )
//                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec1_collect = follower.pathBuilder()
                .addPath(
                        // Line 9
                        new BezierCurve(
                                new Point(22.000, 6.000, Point.CARTESIAN),
                                new Point(26.600, 40.600, Point.CARTESIAN),
                                new Point(8.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setPathEndVelocityConstraint(0.4)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec1_score = follower.pathBuilder()
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(8.000, 33.000, Point.CARTESIAN),
                                new Point(41.000, 72.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec2_collect = follower.pathBuilder()
                .addPath(
                        // Line 11
                        new BezierCurve(
                                new Point(41.000, 72.000, Point.CARTESIAN),
                                new Point(25.000, 30.000, Point.CARTESIAN),
                                new Point(6.600, 32.000, Point.CARTESIAN)
                        )
                )
                .setPathEndVelocityConstraint(0.1)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec2_score = follower.pathBuilder()
                .addPath(
                        // Line 12
                        new BezierLine(
                                new Point(8.000, 33.000, Point.CARTESIAN),
                                new Point(41.000, 69.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec3_collect = follower.pathBuilder()
                .addPath(
                        // Line 13
                        new BezierCurve(
                                new Point(41.000, 69.000, Point.CARTESIAN),
                                new Point(26.000, 31.000, Point.CARTESIAN),
                                new Point(6.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setPathEndVelocityConstraint(0.1)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        spec3_score = follower.pathBuilder()
                .addPath(
                        // Line 14
                        new BezierLine(
                                new Point(8.000, 33.000, Point.CARTESIAN),
                                new Point(41.000, 67.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


//        spec4_collect = follower.pathBuilder()
//                .addPath(
//                        // Line 15
//                        new BezierCurve(
//                                new Point(41.000, 66.000, Point.CARTESIAN),
//                                new Point(22.000, 28.000, Point.CARTESIAN),
//                                new Point(6.000, 33.000, Point.CARTESIAN)
//                        )
//                )
//                .setPathEndVelocityConstraint(0.1)
//                .setConstantHeadingInterpolation(Math.toRadians(0))
//                .build();
//
//        spec4_score = follower.pathBuilder()
//                .addPath(
//                        // Line 16
//                        new BezierLine(
//                                new Point(8.000, 33.000, Point.CARTESIAN),
//                                new Point(41.000, 64.500, Point.CARTESIAN)
//                        )
//                )
//                .setConstantHeadingInterpolation(Math.toRadians(0))
//                .build();

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
                                new InstantCommand(() -> follower.followPath(preload)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1150),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(400),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.setMaxPower(0.9)),
                                new InstantCommand(() -> follower.followPath(pushsample, true)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> follower.setMaxPower(0.9)),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec1_collect, true)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new WaitCommand(1200),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec1_score)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new WaitCommand(400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(700),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.liftBack()),
                                                new InstantCommand(() -> robot.scoring.armToCollect())
                                        )

                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> follower.setMaxPower(0.9)),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec2_collect, true)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new WaitCommand(700),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> follower.setMaxPower(1)),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec2_score)),
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new WaitCommand(550),
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
                        new InstantCommand(() -> follower.setMaxPower(0.9)),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec3_collect, true)),
                                new SequentialCommandGroup(
                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
                                        new WaitCommand(700),
                                        new InstantCommand(() -> robot.scoring.scoreClose())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> follower.setMaxPower(1)),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(spec3_score)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
                                        new WaitCommand(400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.liftBack()),
                                                new InstantCommand(() -> robot.scoring.armToCollect())
                                        )
                                )
                        )
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(0.9)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec4_collect, true)),
//                                new SequentialCommandGroup(
//                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
//                                        new WaitCommand(700),
//                                        new InstantCommand(() -> robot.scoring.scoreClose())
//                                )
//                        ),
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(1)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec4_score)),
//                                new SequentialCommandGroup(
//                                        new InstantCommand(() -> robot.scoring.armToChamber()),
//                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
//                                        new WaitCommand(400),
//                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
//                                        new WaitCommand(500),
//                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                                        new ParallelCommandGroup(
//                                                new InstantCommand(() -> robot.scoring.liftBack()),
//                                                new InstantCommand(() -> robot.scoring.armToCollect())
//                                        )
//                                )
//                        )
                )
        );
    }

    @Override
    public void stop() {
        robot.scoring.threadStop();
    }
}
