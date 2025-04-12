package auto;

import static constants.AutoConstants.INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_ASPPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_ASPPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_THIRD_ASPPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_ASPPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_ASPPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_THIRD_ASPPLE;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
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

import commands.DriveThreePoints;
import commands.DriveToPoint;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.Robot;
import subsystems.SleepyStuffff.Util.Vector2d;

@Autonomous
public class AutoTestChamber extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(10, 63, Math.toRadians(0)));
        robot.chamberAutoInit(hardwareMap);
        telemetryA.update();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.follower.update();
        follower.follower.telemetryDebug(telemetryA);
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        // ---------------  Preload ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(44, 77),0, 0).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1250),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(400),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        // ---------------  First ASpple ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(22, 15.7), 0, 0).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())
                                )
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        this.CollectFirstASpple(),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(50),
                                        new InstantCommand(() -> robot.thrownRight())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPercent(10))
                        ),
                        new WaitCommand(600),
                        // ---------------  Second ASpple ---------------
                        this.CollectSecondASpple(),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(50),
                                        new InstantCommand(() -> robot.thrownRight())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPercent(10))
                        ),
                        new WaitCommand(600),
                        // ---------------  Third ASpple ---------------
                        new DriveToPoint(follower, new Vector2d(30, 8),0, 0).setHoldEnd(true),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        this.CollectThirdASpple(),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.thrownRight())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPercent(10)),
                                new DriveThreePoints(follower, new Vector2d(10, 32), new Vector2d(26, 36), 0, 0)
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(300),

                        // ---------------  Second Chamber ---------------
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.scoreClose()),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT)),
                                new SequentialCommandGroup(
                                        new WaitCommand(150),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new DriveToPoint(follower, new Vector2d(44, 74),0, 0).setHoldEnd(false),
//                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitCommand(200),

                        // ---------------  Third Spec ---------------
                        new ParallelCommandGroup(
//                                new DriveThreePoints(follower, new Vector2d(12, 32), new Vector2d(23, 33), 0, 0).setHoldEnd(true),
                                new DriveToPoint(follower, new Vector2d(10.5, 32), 0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())

                                )
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(300),

                        // ---------------  Third Chamber ---------------
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.scoreClose()),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new DriveToPoint(follower, new Vector2d(44, 71),0, 0).setHoldEnd(false),
//                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitCommand(200),

                        // ---------------  Fourth Spec ---------------
                        new ParallelCommandGroup(
//                                new DriveThreePoints(follower, new Vector2d(12, 32), new Vector2d(23, 33), 0, 0).setHoldEnd(true),
                                new DriveToPoint(follower, new Vector2d(10.5, 32), 0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())

                                )
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(300),

                        // ---------------  Fourth Chamber ---------------
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.scoreClose()),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new DriveToPoint(follower, new Vector2d(44, 68),0, 0).setHoldEnd(false),
//                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitCommand(200),

                        // ---------------  Fifth Spec ---------------
                        new ParallelCommandGroup(
//                                new DriveThreePoints(follower, new Vector2d(12, 32), new Vector2d(23, 33), 0, 0).setHoldEnd(true),
                                new DriveToPoint(follower, new Vector2d(10.5, 32), 0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())

                                )
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(300),

                        // ---------------  Fifth Chamber ---------------
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.scoreClose()),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new DriveToPoint(follower, new Vector2d(44, 65),0, 0).setHoldEnd(false),
//                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new WaitCommand(200),

                        // ---------------  Park ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(10, 32), -90, 0),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())

                                )
                        )
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(0.9)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec2_collect, true)),
//                                new SequentialCommandGroup(
//                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
//                                        new WaitCommand(700),
//                                        new InstantCommand(() -> robot.scoring.scoreClose())
//                                )
//                        ),
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(1)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec2_score)),
//                                new SequentialCommandGroup(
//                                        new WaitCommand(100),
//                                        new InstantCommand(() -> robot.scoring.armToChamber()),
//                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
//                                        new WaitCommand(550),
//                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
//                                        new WaitCommand(500),
//                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                                        new ParallelCommandGroup(
//                                                new InstantCommand(() -> robot.scoring.liftBack()),
//                                                new InstantCommand(() -> robot.scoring.armToCollect())
//                                        )
//                                )
//                        ),
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(0.9)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec3_collect, true)),
//                                new SequentialCommandGroup(
//                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
//                                        new WaitCommand(700),
//                                        new InstantCommand(() -> robot.scoring.scoreClose())
//                                )
//                        ),
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(1)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec3_score)),
//                                new SequentialCommandGroup(
//                                        new InstantCommand(() -> robot.scoring.armToChamber()),
//                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
//                                        new WaitCommand(400),
//                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
//                                        new WaitCommand(500),
//                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                                        new ParallelCommandGroup(
//                                                new InstantCommand(() -> robot.scoring.liftBack()),
//                                                new InstantCommand(() -> robot.scoring.armToCollect())
//                                        )
//                                )
//                        )
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(0.9)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec4_collect, true)),
//                                new SequentialCommandGroup(
//                                        new WaitUntilCommand(() -> follower.getPose().getX() < 9.5),
//                                        new WaitCommand(700),
//                                        new InstantCommand(() -> robot.scoring.scoreClose())
//                                )
//                        ),
//                        new WaitUntilCommand(() -> !follower.isBusy()),
//                        new InstantCommand(() -> follower.setMaxPower(1)),
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> follower.followPath(spec4_score)),
//                                new SequentialCommandGroup(
//                                        new InstantCommand(() -> robot.scoring.armToChamber()),
//                                        new WaitUntilCommand(() -> follower.getPose().getX() > 36.5),
//                                        new WaitCommand(400),
//                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
//                                        new WaitCommand(500),
//                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                                        new ParallelCommandGroup(
//                                                new InstantCommand(() -> robot.scoring.liftBack()),
//                                                new InstantCommand(() -> robot.scoring.armToCollect())
//                                        )
//                                )
//                        )
                )
        );
    }

    @Override
    public void stop() {
        robot.scoring.threadStop();
        CommandScheduler.getInstance().reset();
    }

    private Command CollectFirstASpple(){
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> robot.intake.setTurretDegree(46.6)),
                        new InstantCommand(() -> robot.intake.setExtendPercent(85)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotateDegree(133.4))
                ),
                new WaitCommand(200),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(200),
                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
                new WaitCommand(100),
                new InstantCommand(() -> robot.intake.intakeClawIntakeUp()),
                new InstantCommand(() -> robot.intake.setTurretDegree(90)),
                new WaitCommand(100)
        );
    }
    private Command CollectSecondASpple(){
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> robot.intake.setTurretDegree(133.4)),
                        new InstantCommand(() -> robot.intake.setExtendPercent(85)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotateDegree(46.6))
                ),
                new WaitCommand(200),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(200),
                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
                new WaitCommand(100),
                new InstantCommand(() -> robot.intake.intakeClawIntakeUp()),
                new InstantCommand(() -> robot.intake.setTurretDegree(90)),
                new WaitCommand(100)
        );
    }
    private Command CollectThirdASpple(){
        return new SequentialCommandGroup(
                new ParallelCommandGroup(
                        new InstantCommand(() -> robot.intake.setTurretDegree(154)),
                        new InstantCommand(() -> robot.intake.setExtendPercent(50)),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                        new InstantCommand(() -> robot.intake.setRotateDegree(26))
                ),
                new WaitCommand(200),
                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
                new WaitCommand(200),
                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
                new WaitCommand(100),
                new InstantCommand(() -> robot.intake.intakeClawIntakeUp()),
                new InstantCommand(() -> robot.intake.setTurretDegree(90)),
                new WaitCommand(100)
        );
    }
}
