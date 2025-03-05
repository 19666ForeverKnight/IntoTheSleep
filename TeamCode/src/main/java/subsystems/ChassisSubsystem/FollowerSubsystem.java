package subsystems.ChassisSubsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FollowerSubsystem extends SubsystemBase {

    private static FollowerSubsystem instance;
    private Telemetry telemetry;
    public  Follower follower;

    private FollowerSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        follower = new Follower(hardwareMap);
        this.telemetry = telemetry;
        //registry
        register();
    }

    public static synchronized FollowerSubsystem getInstance(HardwareMap hardwareMap, Telemetry telemetry){
        instance = new FollowerSubsystem(hardwareMap, telemetry);
        return instance;
    }

    @Override
    public void periodic() {
        follower.update();
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
    }

    public void followPath(Path path, boolean holdEnd){
        follower.followPath(path, holdEnd);
    }

    public void followPath(PathChain path, boolean holdEnd){
        follower.followPath(path, holdEnd);
    }

    public Pose getPose(){
        return follower.getPose();
    }

    public boolean isRobotStuck(){
        return follower.isRobotStuck();
    }

    public boolean isBusy(){
        return follower.isBusy();
    }

    public double getCurrentTValue(){
        return follower.getCurrentTValue();
    }

    public PathBuilder pathBuilder(){
        return follower.pathBuilder();
    }

}
