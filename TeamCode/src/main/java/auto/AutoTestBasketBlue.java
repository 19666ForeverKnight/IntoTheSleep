package auto;

import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_LEFT_TRANS;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_TRANS;

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
public class AutoTestBasketBlue extends OpMode {
    private Telemetry telemetryA;
    private Follower follower;
    private PathChain preload, samp1_collect, samp1_score, samp2_collect, samp2_score, samp3_collect, samp3_score, park, toSub, intakeDown, cv;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(7.00, 112.00, Math.toRadians(-90)));
        robot.autoInitBlue(hardwareMap);

        preload = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(7.000, 112.000, Point.CARTESIAN),
                                new Point(14.500, 126.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .setPathEndVelocityConstraint(5)
                .build();

        samp1_collect = follower.pathBuilder()
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(14.500, 126.000, Point.CARTESIAN),
                                new Point(2.000, 115.500, Point.CARTESIAN),
                                new Point(30.000, 120.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-1))
                .setPathEndVelocityConstraint(5)
                .build();

        samp1_score = follower.pathBuilder()
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(30.000, 120.500, Point.CARTESIAN),
                                new Point(15.000, 126.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-1), Math.toRadians(-45))
                .setPathEndVelocityConstraint(5)
                .build();

        samp2_collect = follower.pathBuilder()
                .addPath(
                        // Line 4
                        new BezierCurve(
                                new Point(15.000, 126.500, Point.CARTESIAN),
                                new Point(1.000, 137.000, Point.CARTESIAN),
                                new Point(31.500, 132.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-5.5))
                .setPathEndVelocityConstraint(5)
                .build();

        samp2_score = follower.pathBuilder()
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(31.500, 132.500, Point.CARTESIAN),
                                new Point(15.000, 126.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-5.5), Math.toRadians(-45))
                .setPathEndVelocityConstraint(5)
                .build();

        samp3_collect = follower.pathBuilder()
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(15.000, 126.500, Point.CARTESIAN),
                                new Point(17.700, 132.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(16.5))
                .build();
        samp3_score = follower.pathBuilder()
                .addPath(
                        // Line 7
                        new BezierLine(
                                new Point(16.700, 132.800, Point.CARTESIAN),
                                new Point(15.000, 126.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(20), Math.toRadians(-45))
                .build();

        park = follower.pathBuilder()
                .addPath(
                        // ascent
                        new BezierCurve(
                                new Point(16.000, 128.000, Point.CARTESIAN),
                                new Point(67.000, 106.000, Point.CARTESIAN),
                                new Point(65.000, 94.00, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(90))
                .build();

        toSub = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(16.000, 128.000, Point.CARTESIAN),
                                new Point(67.000, 106.000, Point.CARTESIAN),
                                new Point(65.000, 93.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-90))
                .build();

        intakeDown = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(65.000, 93.500, Point.CARTESIAN),
                                new Point(65.000, 95.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-90))
                .build();

        cv = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(65.000, 95.000, Point.CARTESIAN),
                                new Point(80.000, 95.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(-90))
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
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new InstantCommand(() -> follower.followPath(preload, true)),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(500),
                        // collect & score 1st
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp1_collect)),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.intakeClawOpenAuto()),
                                new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                new InstantCommand(() -> robot.intake.intakeIntake())
                        ),
                        new WaitCommand(800),
                        new InstantCommand(() -> robot.scoring.liftBack()),
                        new WaitCommand(400),
//                        new InstantCommand(() -> robot.autoextendtocollect(1)),
//                        new WaitCommand(300),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp1_score)),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(600),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                        // collect & score 2rd
                        new ParallelCommandGroup(

                                new InstantCommand(() -> follower.followPath(samp2_collect)),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.intakeClawOpen()),
                                new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                new InstantCommand(() -> robot.intake.intakeIntake())
                        ),
                        new WaitCommand(1000),
                        new InstantCommand(() -> robot.scoring.liftBack()),
                        new WaitCommand(400),
//                        new InstantCommand(() -> robot.autoextendtocollect(1)),
//                        new WaitCommand(300),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp2_score)),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(600),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                        // collect & score 3nd
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> follower.followPath(samp3_collect)),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.intakeClawOpenAuto()),
                                new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                new InstantCommand(() -> robot.intake.intakeIntake())
                        ),
                        new WaitCommand(1200),
                        new InstantCommand(() -> robot.autoextendtocollect(3)),
                        new WaitCommand(300),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> follower.followPath(samp3_score)),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(600),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                        // park
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(cv)),
//                                    new SequentialCommandGroup(
//                                            new InstantCommand(() -> robot.scoring.liftBack()),
//                                            new InstantCommand(() -> robot.scoring.armToCollect())
//                                    )
//                        ),
//                        new InstantCommand(() -> robot.scoring.armToPark()),
//                        new WaitCommand(5000)
                        // CV
                        new ParallelCommandGroup(
                            new InstantCommand(() -> follower.followPath(toSub)),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToTrans())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(600),
                        new ParallelCommandGroup(
//                                new InstantCommand(() -> robot.vision.start()),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.intake.sweepOut()),
                                        new WaitCommand(250),
                                        new InstantCommand(() -> robot.intake.sweepIn()),
                                        new InstantCommand(() -> follower.followPath(intakeDown)),
                                        new InstantCommand(() -> robot.intake.intakeClawIntakeDown()),
                                        new InstantCommand(() -> robot.intake.intakeClawOpen())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new InstantCommand(() -> follower.followPath(cv)),
                        new WaitUntilCommand(() -> robot.vision.thereIsAnApple()),
                        new InstantCommand(() -> follower.breakFollowing()),
                        new InstantCommand(() -> robot.intake.intakeIntake()),
                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_TRANS, EXTEND_LEFT_TRANS)),
                        new WaitCommand(300),
                        new InstantCommand(() -> robot.intake.intakeClawClose()),
                        new WaitCommand(200),
                        new InstantCommand(() -> robot.trans())
//                        new InstantCommand(() -> robot.intake.intakeStop()),
//                        new InstantCommand(() -> robot.scoring.scoreClose()),
//                        new WaitCommand(400),
//                        new InstantCommand(() -> robot.intake.intakeClawOpen())
//                        new InstantCommand(follower.followPath()
                )

        );
    }

    @Override
    public void stop() {
        robot.scoring.threadStop();
    }
}
