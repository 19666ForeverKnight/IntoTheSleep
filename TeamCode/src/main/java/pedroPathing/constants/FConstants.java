package pedroPathing.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
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

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(PIDConst.transP1, PIDConst.transI1, PIDConst.transD1, PIDConst.transF1);
        FollowerConstants.useSecondaryTranslationalPID = PIDConst.transSecond;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(PIDConst.transP2, PIDConst.transI2, PIDConst.transD2, PIDConst.transF2); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(PIDConst.headingP1, PIDConst.headingI1, PIDConst.headingD1, PIDConst.headingF1);
        FollowerConstants.useSecondaryHeadingPID = PIDConst.headingSecond;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(PIDConst.headingP2, PIDConst.headingI2, PIDConst.headingD2, PIDConst.headingF2); // Not being used, @see useSecondaryHeadingPID

//        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.015,0,0.0025,0.6,0);
        FollowerConstants.drivePIDFCoefficients.setCoefficients(PIDConst.driveP1, PIDConst.driveI1, PIDConst.driveD1, PIDConst.driveT1, PIDConst.driveF1);
        FollowerConstants.useSecondaryDrivePID = PIDConst.driveSecond;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(PIDConst.driveP2, PIDConst.driveI2, PIDConst.driveD2, PIDConst.driveT2, PIDConst.driveF2); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = PIDConst.zeroPower;
        FollowerConstants.centripetalScaling = PIDConst.centripetal;
        FollowerConstants.pathEndTimeoutConstraint = 75;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;

        FollowerConstants.holdPointTranslationalScaling = 0.45;
        FollowerConstants.holdPointHeadingScaling = 0.35;

        FollowerConstants.useVoltageCompensationInAuto = true;
    }
}