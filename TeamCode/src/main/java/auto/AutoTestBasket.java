package auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
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
public class AutoTestBasket extends OpMode {
    private Telemetry telemetryA;
    private Follower follower;
    private PathChain preload, samp1_collect, samp1_score, samp2_collect, samp2_score, samp3_collect, samp3_score, park;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(7.00, 109.00, Math.toRadians(-90)));
        robot.autoInit(hardwareMap);

        preload = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(7.000, 109.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .build();

        samp1_collect = follower.pathBuilder()
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-25))
                .build();

        samp1_score = follower.pathBuilder()
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-25), Math.toRadians(-45))
                .build();

        samp2_collect = follower.pathBuilder()
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(20))
                .build();

        samp2_score = follower.pathBuilder()
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(20), Math.toRadians(-45))
                .build();

        samp3_collect = follower.pathBuilder()
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(24.300, 124.900, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(45))
                .build();
        samp3_score = follower.pathBuilder()
                .addPath(
                        // Line 7
                        new BezierLine(
                                new Point(24.300, 124.900, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(-45))
                .build();

        park = follower.pathBuilder()
                .addPath(
                        // ascent
                        new BezierCurve(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(67.000, 106.000, Point.CARTESIAN),
                                new Point(65.000, 91.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(90))
                .build();

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.update();
        follower.telemetryDebug(telemetryA);
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        // preload
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(preload)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                        new InstantCommand(() -> robot.scoring.armToChamber())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // collect & score 1st
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp1_collect)),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.intakeClawOpen()),
                                new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                new InstantCommand(() -> robot.intake.intakeIntake()),
                                new InstantCommand(() -> robot.autoextendtocollect(1))
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new InstantCommand(() -> robot.intake.toTransPos()),
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp1_score)),
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new InstantCommand(() -> robot.scoring.armToChamber())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // collect & score 2nd
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp2_collect)),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.intakeClawOpen()),
                                new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                new InstantCommand(() -> robot.intake.intakeIntake()),
                                new InstantCommand(() -> robot.autoextendtocollect(1))
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new InstantCommand(() -> robot.intake.toTransPos()),
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp2_score)),
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new InstantCommand(() -> robot.scoring.armToChamber())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // collect & score 3nd
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp3_collect)),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.intakeClawOpen()),
                                new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                new InstantCommand(() -> robot.intake.intakeIntake()),
                                new InstantCommand(() -> robot.autoextendtocollect(1))
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new InstantCommand(() -> robot.intake.toTransPos()),
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp3_score)),
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new InstantCommand(() -> robot.scoring.armToChamber())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> robot.scoring.scoreOpen())
                )
        );
    }
}
