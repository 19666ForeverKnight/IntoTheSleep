package commands;

import static kotlin.text.Typography.degree;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;

import constants.AutoConstants;
import lombok.Setter;
import pedroPathing.constants.FastPIDConst;
import pedroPathing.constants.MedPIDConst;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.ChassisSubsystem.FollowerSubsystem.FollowerType;
import subsystems.SleepyStuffff.Util.Vector2d;

public class DriveThreePoints extends CommandBase {
    private FollowerSubsystem follower;
    @Setter private FollowerType currentFollowerType = FollowerType.FAST;
    private double maxSpeed = 1;
    private Vector2d targetPos;
    private Vector2d middlePos;
    private double targetAngle;
    private boolean holdEnd = true;

    public DriveThreePoints(FollowerSubsystem follower, Vector2d pointEnd, Vector2d pointMiddle, double degreeEnd, int a) {
        targetPos = new Vector2d(pointEnd.x, pointEnd.y);
        targetAngle = degreeEnd;
        middlePos = new Vector2d(pointMiddle.x, pointMiddle.y);
        this.follower = follower;
        switch (a) {
            case 0:
                currentFollowerType = FollowerType.FAST;
                break;
            case 1:
                currentFollowerType = FollowerType.MED;
                break;
        }
        switch (currentFollowerType) {
            case FAST:
                FollowerConstants.drivePIDFCoefficients = FastPIDConst.FastDrivePIDF;
                FollowerConstants.secondaryDrivePIDFCoefficients = FastPIDConst.FastSecondaryDrivePIDF;
                FollowerConstants.translationalPIDFCoefficients = FastPIDConst.FastTransPIDF;
                FollowerConstants.secondaryTranslationalPIDFCoefficients = FastPIDConst.FastSecondaryTransPIDF;
                FollowerConstants.headingPIDFCoefficients = FastPIDConst.FastHeadingPIDF;
                FollowerConstants.secondaryHeadingPIDFCoefficients = FastPIDConst.FastSecondaryHeadingPIDF;
                FollowerConstants.xMovement = 75;
                FollowerConstants.yMovement = 73.0151;
                FollowerConstants.forwardZeroPowerAcceleration = -35.2745;
                FollowerConstants.lateralZeroPowerAcceleration = -64.62;
                FollowerConstants.maxPower = 1.0;
                break;
            case MED:
                FollowerConstants.drivePIDFCoefficients = MedPIDConst.MedDrivePIDF;
                FollowerConstants.secondaryDrivePIDFCoefficients = MedPIDConst.MedSecondaryDrivePIDF;
                FollowerConstants.translationalPIDFCoefficients = MedPIDConst.MedTransPIDF;
                FollowerConstants.secondaryTranslationalPIDFCoefficients = MedPIDConst.MedSecondaryTransPIDF;
                FollowerConstants.headingPIDFCoefficients = MedPIDConst.MedHeadingPIDF;
                FollowerConstants.secondaryHeadingPIDFCoefficients = MedPIDConst.MedSecondaryHeadingPIDF;
                FollowerConstants.xMovement = 60;
                FollowerConstants.yMovement = 58.41208;
                FollowerConstants.forwardZeroPowerAcceleration = -31.74705;
                FollowerConstants.lateralZeroPowerAcceleration = -58.158;
                FollowerConstants.maxPower = 0.8;
                break;
        }
    }
    public DriveThreePoints setHoldEnd (boolean holdEnd){
        this.holdEnd = holdEnd;
        return this;
    }
    @Override
    public void initialize() {
        this.follower.breakFollowing();
        PathBuilder builder = new PathBuilder();
        builder.addPath(
                new BezierCurve(
                        new Point(follower.getPose().getX(), follower.getPose().getY(), 1),
                        new Point(middlePos.x, middlePos.y, 1),
                        new Point(targetPos.x, targetPos.y,1)
                )
        ).setLinearHeadingInterpolation(follower.getPose().getHeading(), Math.toRadians(targetAngle));
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
