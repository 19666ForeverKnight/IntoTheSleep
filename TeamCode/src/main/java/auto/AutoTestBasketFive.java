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
import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
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

import commands.DriveThreePoints;
import commands.DriveToPoint;
import commands.GrowAppleInFarm;
import constants.RobotConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;
import subsystems.SleepyStuffff.Util.Vector2d;

@Autonomous
public class AutoTestBasketFive extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    private Vision vision = new Vision();
    Robot robot = new Robot();

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(7, 112, Math.toRadians(-90)));
        robot.autoInit(hardwareMap);
        vision.Init(hardwareMap, telemetryA);
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
                                new DriveToPoint(follower, new Vector2d(21.5, 123), 0, 1).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT)),
                        new WaitCommand(150),
                        new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Blue),
                        new WaitCommand(750),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(900),
                        // drop first apple
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
                                new DriveToPoint(follower, new Vector2d(21.5, 126.5),0, 1).setHoldEnd(false),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT)),
                        new WaitCommand(150),
                        new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Blue),
                        new WaitCommand(750),
                        new InstantCommand(()-> robot.trans()),
                        new WaitCommand(900),
                        // drop second apple
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
//                        // ---------------  Third Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, new Vector2d(21.5, 135.5),0, 1).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT)),
                        new WaitCommand(150),
                        new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Blue),
                        new WaitCommand(750),
                        new InstantCommand(()-> robot.trans()),
                        new WaitCommand(850),
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
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        // Go to Sub
                        new ParallelCommandGroup(
                                new DriveThreePoints(follower, new Vector2d(62.6, 97.6), new Vector2d(72.5, 115), 270, 0),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
                        ),
                        new WaitCommand(150),
                        new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Blue),
                        new WaitCommand(750),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(900),
                        // drop 5th apple
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
                        //Go to sub
                        new ParallelCommandGroup(
                                new DriveThreePoints(follower, new Vector2d(62.6, 97.6), new Vector2d(72.5, 115), 270, 0),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
                        ),
                        new WaitCommand(150),
                        new GrowAppleInFarm(vision, robot.intake, RobotConstants.AllianceColour.Blue),
                        new WaitCommand(750),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(900),
                        // drop 4th apple
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
                        new InstantCommand(() -> robot.scoring.scoreOpen())
                )
        );
    }
    @Override
    public void stop() {
        robot.scoring.threadStop();
        CommandScheduler.getInstance().reset();
    }
//    private Command CollectFirstApple(){
//        return new SequentialCommandGroup(
//                new ParallelCommandGroup(
//                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE)),
//                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
//                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
//                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE))
//                ),
//                new WaitCommand(400),
//                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
//                new WaitCommand(500),
//                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
//                new WaitCommand(100),
//                new InstantCommand(() -> robot.trans()),
//                new WaitCommand(300)
//        );
//    }
//    private Command CollectSecondApple(){
//        return new SequentialCommandGroup(
//                new ParallelCommandGroup(
//                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE)),
//                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
//                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
//                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE))
//                ),
//                new WaitCommand(400),
//                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
//                new WaitCommand(500),
//                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
//                new WaitCommand(100),
//                new InstantCommand(() -> robot.trans()),
//                new WaitCommand(300)
//        );
//    }
//    private Command CollectThirdApple(){
//        return new SequentialCommandGroup(
//                new ParallelCommandGroup(
//                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_Third_APPLE)),
//                        new InstantCommand(() -> robot.intake.setExtendPosition(EXTEND_RIGHT_IN, EXTEND_LEFT_IN)),
//                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
//                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_Third_APPLE))
//                ),
//                new WaitCommand(400),
//                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_COLLECT_APPLE_DOWN)),
//                new WaitCommand(500),
//                new InstantCommand(() -> robot.intake.intakeClawCloseAuto()),
//                new WaitCommand(100),
//                new InstantCommand(() -> robot.trans()),
//                new WaitCommand(300)
//        );
//    }
}
