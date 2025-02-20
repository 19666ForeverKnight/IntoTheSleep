package pedroPathing.examples;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
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

/**
 * This is the Circle autonomous OpMode. It runs the robot in a PathChain that's actually not quite
 * a circle, but some Bezier curves that have control points set essentially in a square. However,
 * it turns enough to tune your centripetal force correction and some of your heading. Some lag in
 * heading is to be expected.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/12/2024
 */
@Config
@Autonomous
public class CurveTest extends OpMode {
    private Telemetry telemetryA;

    public static double RADIUS = 10;

    private Follower follower;

    private PathChain curve;

    /**
     * This initializes the Follower and creates the PathChain for the "circle". Additionally, this
     * initializes the FTC Dashboard telemetry.
     */
    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);

        curve = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(0.000, 0.000, Point.CARTESIAN),
                                new Point(36.000, 0.000, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(36.000, 0.000, Point.CARTESIAN),
                                new Point(60.000, 0.000, Point.CARTESIAN),
                                new Point(60.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(60.000, 24.000, Point.CARTESIAN),
                                new Point(60.000, 60.000, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(60.000, 60.000, Point.CARTESIAN),
                                new Point(60.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed(true)
                .addPath(
                        // Line 5
                        new BezierCurve(
                                new Point(60.000, 24.000, Point.CARTESIAN),
                                new Point(60.000, 0.000, Point.CARTESIAN),
                                new Point(36.000, 0.000, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed(true)
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(36.000, 0.000, Point.CARTESIAN),
                                new Point(0.000, 0.000, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed(true)
                .build();

        follower.followPath(curve);

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addLine("This will run in a roughly circular shape of radius " + RADIUS
                            + ", starting on the right-most edge. So, make sure you have enough "
                            + "space to the left, front, and back to run the OpMode.");
        telemetryA.update();
    }

    /**
     * This runs the OpMode, updating the Follower as well as printing out the debug statements to
     * the Telemetry, as well as the FTC Dashboard.
     */
    @Override
    public void loop() {
        follower.update();
        if (follower.atParametricEnd()) {
            follower.followPath(curve);
        }

        follower.telemetryDebug(telemetryA);
    }
}
