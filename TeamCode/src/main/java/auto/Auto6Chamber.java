package auto;

import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
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
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import commands.DriveToPoint;
import commands.DriveToPointSLB;
import constants.RobotConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;
import subsystems.SleepyStuffff.Util.Vector2d;

@Autonomous
public class Auto6Chamber extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    private Vision vision = new Vision();
    Robot robot = new Robot();
    RobotConstants.AllianceColour colour = RobotConstants.AllianceColour.Red;

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(10, 63, Math.toRadians(0)));
        robot.chamberAutoInit(hardwareMap);
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
                        // ---------------  Preload and grab one ---------------
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new DriveToPointSLB(follower, new Vector2d(44, 76), new Vector2d(10, 63), 0, 0, 0).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(800),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                                new InstantCommand(() -> robot.scoring.liftBack())
                                        )
                                ),
                                new SequentialCommandGroup(
                                        new WaitCommand(1000),
                                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, false))),
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.collectApple())
                                )
                        ),
                        new WaitCommand(450),
                        // ---------------  Collect Second Specimen and thrown one sample---------------
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.intake.setExtendPercent(0)),
                                new DriveToPoint(follower, new Vector2d(12, 32), 0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(1100),
                                        new InstantCommand(() -> robot.thrownRight()),
                                        new WaitCommand(550),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT)),
                                                new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_MID))
                                        )
                                )
                        ),
                        new WaitCommand(50),
                        // ---------------   Second Specimen ---------------
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new WaitCommand(100),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new DriveToPoint(follower, new Vector2d(44, 74),0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                                new InstantCommand(() -> robot.scoring.liftBack())
                                        )
                                )
                        ),
                        // ---------------  Pick up First ASpple ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(22, 16.3), 0, 0).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.liftBack()),
                                        new InstantCommand(() -> robot.scoring.armToCollect())
                                ),
                                new SequentialCommandGroup(
                                        new WaitCommand(800),
                                        this.CollectFirstASpple()
                                )
                        ),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(400),
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(15, 16.3), 0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(70),
                                        new InstantCommand(() -> robot.thrownRightStay())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPercent(10))
                        ),
                        new DriveToPoint(follower, new Vector2d(22, 16.3), 0, 0).setHoldEnd(true),
                        // ---------------  Pick up Second ASpple ---------------
                        this.CollectSecondASpple(),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(400),
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(15, 16.3), 0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(20),
                                        new InstantCommand(() -> robot.thrownRightStay())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPercent(10))
                        ),
                        new WaitCommand(300),
                        // ---------------  Pick up Third ASpple ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(30, 8),0, 0).setHoldEnd(true),
                                this.CollectThirdASpple()
                        ),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(400),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.thrownRight())
                                ),
                                new InstantCommand(() -> robot.intake.setExtendPercent(7)),
                                new DriveToPoint(follower, new Vector2d(12, 32), 0, 0).setHoldEnd(false)
                        ),
                        new WaitCommand(250),
                        // ---------------  Drop Third Specimen ---------------
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new WaitCommand(250),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.intake.setExtendPercent(0)),
                                new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_INTAKE_AND_TRANS)),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT)),
                                new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_MID)),
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new DriveToPoint(follower, new Vector2d(44, 74),0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.scoreOpen())
                                )
                        ),
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(12, 32), 0, 0).setHoldEnd(false),
                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                new InstantCommand(() -> robot.scoring.liftBack())
                        ),
                        // ---------------  Drop Four Specimen ---------------
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new WaitCommand(250),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new DriveToPoint(follower, new Vector2d(44, 74),0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                                new InstantCommand(() -> robot.scoring.liftBack())
                                        )
                                )
                        ),
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(12, 32), 0, 0).setHoldEnd(false),
                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                new InstantCommand(() -> robot.scoring.liftBack())
                        ),
                        // ---------------  Drop Five Specimen ---------------
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new WaitCommand(250),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new DriveToPoint(follower, new Vector2d(44, 74),0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                                new InstantCommand(() -> robot.scoring.liftBack())
                                        )
                                )
                        ),
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(12, 32), 0, 0).setHoldEnd(false),
                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                new InstantCommand(() -> robot.scoring.liftBack())
                        ),
                        // ---------------  Drop Six Specimen ---------------
                        new InstantCommand(() -> robot.scoring.scoreClose()),
                        new WaitCommand(250),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighChamber()),
                                new DriveToPoint(follower, new Vector2d(44, 74),0, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(100),
                                        new InstantCommand(() -> robot.scoring.armToChamber()),
                                        new WaitCommand(1400),
                                        new InstantCommand(() -> robot.scoring.liftToChamberOpenAuto()),
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                                        new ParallelCommandGroup(
                                                new InstantCommand(() -> robot.scoring.armToCollect()),
                                                new InstantCommand(() -> robot.scoring.liftBack())
                                        )
                                )
                        )
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
                new WaitCommand(50)
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
                new WaitCommand(100)
        );
    }
}
