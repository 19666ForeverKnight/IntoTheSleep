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
import subsystems.SleepyStuffff.Util.Vector2d;

public class DriveToPoint extends CommandBase {
    private FollowerSubsystem follower;
    @Setter private FollowerType currentFollowerType = FollowerType.FAST;
    private double maxSpeed = 1;
    private Vector2d targetPos;
    private boolean holdEnd = true;

    public DriveToPoint(FollowerSubsystem follower, Vector2d pos, int a) {
        targetPos = new Vector2d(pos.x, pos.y);
        this.follower = follower;
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
        this.follower.breakFollowing();
        PathBuilder builder = new PathBuilder();
        builder.addPath(
                new BezierLine(
                        new Point(follower.getPose().getX(), follower.getPose().getY(), 1),
                        new Point(targetPos.x, targetPos.y,1)
                )
        ).setConstantHeadingInterpolation(follower.getPose().getHeading());
        this.follower.breakFollowing();
        this.follower.followPath(builder.build(), this.holdEnd);
    }

    @Override
    public boolean isFinished() {
        return follower.getCurrentTValue() > 0.85;
    }
    @Override
    public void end(boolean interrupted){
    }
}
