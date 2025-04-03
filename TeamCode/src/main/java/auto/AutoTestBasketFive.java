package auto;
import static constants.AutoConstants.INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_Third_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_Third_APPLE;
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
                        // ---------------  Preload ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 1).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // ---------------  First Apple ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(34.5, 126.2), 0, 1).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        this.CollectFirstApple(),
                        new WaitCommand(500),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 1).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // ---------------  Second Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, new Vector2d(31.5, 126.5),0, 1).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        this.CollectSecondApple(),
                        new WaitCommand(500),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 1).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // ---------------  Third Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, new Vector2d(33.5, 135),0, 1).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        this.CollectThirdApple(),
                        new WaitCommand(500),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.armToBasketDive())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 1).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> follower.getatParametricEnd()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen())
//                        // ---------------  Park ---------------
//                        new ParallelCommandGroup(
//                                new SequentialCommandGroup(
//                                        new WaitCommand(350),
//                                        new InstantCommand(() -> robot.scoring.liftBack())
//                                ),
//                                new DriveToPoint(follower, new Vector2d(30.5, 135),0, 1).setHoldEnd(true),
//                                new InstantCommand(() -> robot.scoring.armToPark())
//                        )
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
                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE))
                ),
                new WaitCommand(400),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(500),
                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
                new WaitCommand(100),
                new InstantCommand(() -> robot.trans()),
                new WaitCommand(300)
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
                new WaitCommand(500),
                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
                new WaitCommand(100),
                new InstantCommand(() -> robot.trans()),
                new WaitCommand(300)
        );
    }
    private Command CollectThirdApple(){
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_Third_APPLE)),
                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_Third_APPLE))
                ),
                new WaitCommand(400),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(500),
                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
                new WaitCommand(100),
                new InstantCommand(() -> robot.trans()),
                new WaitCommand(300)
        );
    }
}
