package constants;

import com.pedropathing.localization.Pose;

public class AutoDriveConstant {
    public static class SpecimenDrivePose {
        public final Pose ALLIANCE_START_POSE = new Pose(18,-61, Math.toRadians(-90));
        public final Pose ALLIANCE_DROP_PRELOAD = new Pose(10,-32, Math.toRadians(-90));
        public final Pose ALLIANCE_PREP_SWEEP_FIRST = new Pose(31,-38, Math.toRadians(45));
        public final Pose ALLIANCE_SWEEP_FIRST = new Pose(37,-47, Math.toRadians(-34));
        public final Pose ALLIANCE_PREP_SWEEP_SECOND = new Pose(48, -36, Math.toRadians(56));
        public final Pose ALLIANCE_SWEEP_SECOND = new Pose(51, -47, Math.toRadians(-56));
        public final Pose ALLIANCE_PREP_PUSH_THIRD = new Pose(65, -17, Math.toRadians(90));
        public final Pose ALLIANCE_PUSH_THIRD = new Pose(62, -61, Math.toRadians(90));

    }
}
