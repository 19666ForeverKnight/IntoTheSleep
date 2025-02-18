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
public class AutoTestChamber extends OpMode {
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
                        // Line 1
                        new BezierLine(
                                new Point(9.000, 67.000, Point.CARTESIAN),
                                new Point(39.000, 77.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(39.000, 77.000, Point.CARTESIAN),
                                new Point(30.000, 23.000, Point.CARTESIAN),
                                new Point(48.000, 36.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(48.000, 36.000, Point.CARTESIAN),
                                new Point(63.000, 35.000, Point.CARTESIAN),
                                new Point(59.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(59.000, 24.000, Point.CARTESIAN),
                                new Point(21.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .addPath(
                        // Line 5
                        new BezierCurve(
                                new Point(21.000, 24.000, Point.CARTESIAN),
                                new Point(58.000, 31.000, Point.CARTESIAN),
                                new Point(58.000, 14.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(58.000, 14.000, Point.CARTESIAN),
                                new Point(20.000, 14.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .addPath(
                        // Line 7
                        new BezierCurve(
                                new Point(20.000, 14.000, Point.CARTESIAN),
                                new Point(59.000, 26.000, Point.CARTESIAN),
                                new Point(58.000, 6.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .addPath(
                        // Line 8
                        new BezierLine(
                                new Point(58.000, 6.000, Point.CARTESIAN),
                                new Point(20.000, 64.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 9
                        new BezierCurve(
                                new Point(20.000, 64.000, Point.CARTESIAN),
                                new Point(24.000, 38.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(39.000, 73.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 11
                        new BezierCurve(
                                new Point(39.000, 73.000, Point.CARTESIAN),
                                new Point(20.000, 24.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 12
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(39.000, 69.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 13
                        new BezierCurve(
                                new Point(39.000, 69.000, Point.CARTESIAN),
                                new Point(17.000, 26.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 14
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(39.000, 66.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 15
                        new BezierCurve(
                                new Point(39.000, 66.000, Point.CARTESIAN),
                                new Point(28.000, 24.000, Point.CARTESIAN),
                                new Point(9.000, 33.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 16
                        new BezierLine(
                                new Point(9.000, 33.000, Point.CARTESIAN),
                                new Point(39.000, 62.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
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
