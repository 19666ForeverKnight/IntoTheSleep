package auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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
    private PathChain pathChain;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(7.00, 109.00, Math.toRadians(-90)));
        robot.autoInit(hardwareMap);

        pathChain = follower.pathBuilder()
                .addPath(
                        // drop 1
                        new BezierLine(
                                new Point(7.000, 109.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .addPath(
                        // collect 2
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(32.000, 120.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .addPath(
                        // drop 2
                        new BezierLine(
                                new Point(32.000, 120.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .addPath(
                        // collect 3
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(32.000, 130.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .addPath(
                        // drop 3
                        new BezierLine(
                                new Point(32.000, 130.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .addPath(
                        // collect 4
                        new BezierLine(
                                new Point(17.000, 127.000, Point.CARTESIAN),
                                new Point(32.000, 134.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(20))
                .addPath(
                        // drop 4
                        new BezierLine(
                                new Point(32.000, 134.000, Point.CARTESIAN),
                                new Point(17.000, 127.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(20), Math.toRadians(-45))
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

        follower.followPath(pathChain);
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
    }

    @Override
    public void loop() {
        follower.update();
        if (follower.atParametricEnd()) {
            follower.followPath(pathChain);
        }
        follower.telemetryDebug(telemetryA);
        super.stop();
    }
}
