package pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = "lf";
        FollowerConstants.leftRearMotorName = "lb";
        FollowerConstants.rightFrontMotorName = "rf";
        FollowerConstants.rightRearMotorName = "rb";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.REVERSE;

        FollowerConstants.mass = 12.00;

        FollowerConstants.xMovement = 75;
        FollowerConstants.yMovement = 73.0151;

        FollowerConstants.forwardZeroPowerAcceleration = -35.2745;
        FollowerConstants.lateralZeroPowerAcceleration = -64.62;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(FastPIDConst.transP1, FastPIDConst.transI1, FastPIDConst.transD1, FastPIDConst.transF1);
        FollowerConstants.useSecondaryTranslationalPID = FastPIDConst.transSecond;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(FastPIDConst.transP2, FastPIDConst.transI2, FastPIDConst.transD2, FastPIDConst.transF2); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(FastPIDConst.headingP1, FastPIDConst.headingI1, FastPIDConst.headingD1, FastPIDConst.headingF1);
        FollowerConstants.useSecondaryHeadingPID = FastPIDConst.headingSecond;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(FastPIDConst.headingP2, FastPIDConst.headingI2, FastPIDConst.headingD2, FastPIDConst.headingF2); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(FastPIDConst.driveP1, FastPIDConst.driveI1, FastPIDConst.driveD1, FastPIDConst.driveT1, FastPIDConst.driveF1);
        FollowerConstants.useSecondaryDrivePID = FastPIDConst.driveSecond;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(FastPIDConst.driveP2, FastPIDConst.driveI2, FastPIDConst.driveD2, FastPIDConst.driveT2, FastPIDConst.driveF2); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = FastPIDConst.zeroPower;
        FollowerConstants.centripetalScaling = FastPIDConst.centripetal;
        FollowerConstants.pathEndTimeoutConstraint = 400;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;

        FollowerConstants.drivePIDFSwitch = 30;
        FollowerConstants.headingPIDFSwitch = 0.225;
        FollowerConstants.translationalPIDFSwitch = 4.5;

        FollowerConstants.holdPointTranslationalScaling = 1.0;
        FollowerConstants.holdPointHeadingScaling = 0.6;

        FollowerConstants.useVoltageCompensationInAuto = true;
    }
}