package commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;

import constants.AutoConstants;
import lombok.Setter;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.ChassisSubsystem.FollowerSubsystem.FollowerType;

public class DriveToPoint extends CommandBase {
    private FollowerSubsystem follower;
    @Setter private FollowerType currentFollowerType = FollowerType.FAST;
    private double maxSpeed = 1;
    private double relX;
    private double relY;
    private boolean holdEnd = true;

    public DriveToPoint(FollowerSubsystem follower, double rX, double rY, int a) {
        this.follower = follower;
        relX = rX;
        relY = rY;
        switch (a) {
            case 0:
                currentFollowerType = FollowerType.FAST;
                break;
            case 1:
                currentFollowerType = FollowerType.MED;
                break;
            case 2:
                currentFollowerType = FollowerType.SLOW;
                break;
        }
        switch (currentFollowerType) {
            case FAST:
                FollowerConstants.drivePIDFCoefficients = AutoConstants.FAST_DRIVE_PID;
                FollowerConstants.maxPower = 1.0;
                break;
            case MED:
                FollowerConstants.drivePIDFCoefficients = AutoConstants.MED_DRIVE_PID;
                FollowerConstants.maxPower = 0.8;
                break;
            case SLOW:
                FollowerConstants.drivePIDFCoefficients = AutoConstants.SLOW_DRIVE_PID;
                FollowerConstants.maxPower = 0.6;
                break;

        }
    }
    public DriveToPoint setHoldEnd (boolean holdEnd){
        this.holdEnd = holdEnd;
        return this;
    }
    @Override
    public void initialize() {
        double x = follower.getPose().getX();
        double y = follower.getPose().getY();
        double r = follower.getPose().getHeading();
        double rx = relY * Math.cos(r) + relX * Math.sin(r);
        double ry = relX * Math.cos(r) + relY * Math.sin(r);

        double tx = x + rx;
        double ty = y - ry;

        PathBuilder builder = new PathBuilder();
        builder.addPath(
                new BezierLine(
                        new Point(x, y, 1),
                        new Point(tx, ty, 1)
                )
        ).setConstantHeadingInterpolation(follower.getPose().getHeading());
        follower.breakFollowing();
        follower.followPath(builder.build(), true);
    }
    @Override
    public boolean isFinished() {
        return follower.getCurrentTValue() > 0.85;
    }
    @Override
    public void end(boolean interrupted){
    }
}
