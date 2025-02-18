package auto;

import static java.lang.Boolean.TRUE;

import static constants.RobotConstants.EXTEND_LEFT_OUT;
import static constants.RobotConstants.EXTEND_LEFT_TRANS;
import static constants.RobotConstants.EXTEND_RIGHT_OUT;
import static constants.RobotConstants.EXTEND_RIGHT_TRANS;

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
    private int pathState;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(7.00, 80, Math.toRadians(0)));
        robot.autoInit(hardwareMap);

        pathChain = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(7, 77.000, Point.CARTESIAN),
                                new Point(30.000, 77.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(30.000, 77.000, Point.CARTESIAN),
                                new Point(20.000, 26.000, Point.CARTESIAN),
                                new Point(37.000, 39.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-37))
                .addPath(
                        new BezierLine(
                                new Point(37.000, 39.800, Point.CARTESIAN),
                                new Point(21.943, 38.008, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(-117))
                .addPath(
                        new BezierLine(
                                new Point(21.943, 38.008, Point.CARTESIAN),
                                new Point(27.037, 28.016, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-117), Math.toRadians(-26))
                .addPath(
                        new BezierLine(
                                new Point(27.037, 28.016, Point.CARTESIAN),
                                new Point(20.767, 28.016, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-26), Math.toRadians(-117))
                .addPath(
                        new BezierCurve(
                                new Point(20.767, 28.016, Point.CARTESIAN),
                                new Point(50.155, 29.780, Point.CARTESIAN),
                                new Point(61.714, 8.816, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-117), Math.toRadians(0))
                .addPath(
                        new BezierLine(
                                new Point(61.714, 8.816, Point.CARTESIAN),
                                new Point(17.829, 8.229, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        new BezierLine(
                                new Point(17.829, 8.229, Point.CARTESIAN),
                                new Point(10.971, 33.894, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        new BezierLine(
                                new Point(10.971, 33.894, Point.CARTESIAN),
                                new Point(7.837, 33.894, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        new BezierLine(
                                new Point(7.837, 33.894, Point.CARTESIAN),
                                new Point(40.359, 69.355, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .addPath(
                        new BezierLine(
                                new Point(40.359, 69.355, Point.CARTESIAN),
                                new Point(10.971, 33.894, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        follower.followPath(pathChain, TRUE);
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.update();
    }
    private boolean preload_action1 = true;
    private boolean preload_action2 = true;
    private boolean preload_action3 = true;

    private boolean get1st_action1 = true;
    private boolean get1st_action2 = true;
    @Override
    public void loop() {
        follower.update();
        //preload upper action
        if (follower.atParametricEnd()) {
            follower.followPath(pathChain);
        }
        if(follower.getPose().getX() >= 7.00 && preload_action1){
            preload_action1 = false;
            robot.scoring.armToChamber();
            robot.scoring.liftToHighChamber();
        }
        if(follower.getPose().getX() >= 30.00 && preload_action2 && !preload_action1){
            preload_action2 = false;
            robot.scoring.liftToChamberOpen();
            robot.scoring.scoreOpen();
        }
        if(follower.getPose().getY() <= 70.00 && preload_action3 && !preload_action2){
            preload_action3 = false;
            robot.scoring.armToCollect();
            robot.scoring.liftBack();
        }
        //get 1st action
        if(follower.getPose().getY() <= 47 && get1st_action1 && !preload_action3){
            get1st_action1 = false;
            robot.intake.setExtendPosition(EXTEND_RIGHT_OUT, EXTEND_LEFT_OUT);
        }
        if(follower.getPose().getX() <= 23 && get1st_action2 && !get1st_action1){
            get1st_action2 = false;
            robot.intake.setExtendPosition(EXTEND_RIGHT_TRANS, EXTEND_LEFT_TRANS);
        }
        follower.telemetryDebug(telemetryA);
        super.stop();
    }
}
