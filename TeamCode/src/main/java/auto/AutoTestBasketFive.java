package auto;
import static constants.AutoConstants.EXTEND_LEFT_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.EXTEND_RIGHT_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import commands.DriveToPoint;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.Robot;
import subsystems.SleepyStuffff.Util.Vector2d;

@Autonomous
public class AutoTestBasketFive extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    private PathChain preload, samp1_collect, samp1_score, samp2_collect, samp2_score, samp3_collect, samp3_score, park, toSub, intakeDown, cv;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(7, 112, Math.toRadians(-90)));
        robot.autoInit(hardwareMap);
    }
    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
    }
    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(14.5, 127.5), -35, 0),
                                //drive and prepare for dump 1st
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(400),
                        new InstantCommand(() -> robot.scoring.liftBack()),
                        new WaitCommand(500),
                        new DriveToPoint(follower, new Vector2d(29, 126.2), 0, 1),
                        //Drive forward and use turret to collect first apple
                        this.CollectFirstApple(),
                        new WaitCommand(300),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(14.5, 127.5), -35, 0),
                                //drive and prepare for dump 2st
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(400),
                        new InstantCommand(() -> robot.scoring.liftBack()),
                        new WaitCommand(500),
                        new DriveToPoint(follower, new Vector2d(29, 126.2), 0, 1),
                        //Drive forward and use turret to collect second apple
                        this.CollectSecondApple(),
                        new WaitCommand(300),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(14.5, 127.5), -35, 0),
                                //drive and prepare for dump 1st
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(400),
                        new InstantCommand(() -> robot.scoring.liftBack()),
                        new WaitCommand(500)
                )
        );
    }
    @Override
    public void stop() {
        robot.scoring.threadStop();
    }
    private Command CollectFirstApple(){
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE)),
                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_AUTO_COLLECT_FIRST_APPLE, EXTEND_LEFT_AUTO_COLLECT_FIRST_APPLE)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE))
                ),
                new WaitCommand(400),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(400),
                new InstantCommand(() -> robot.trans()),
                new WaitCommand(100)
        );
    }
    private Command CollectSecondApple(){
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE)),
                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE))
                ),
                new WaitCommand(400),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(400),
                new InstantCommand(() -> robot.trans())
        );
    }
}
