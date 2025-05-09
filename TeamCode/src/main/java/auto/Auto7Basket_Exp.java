package auto;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_ROTATE_AUTO_COLLECT_THIRD_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE;
import static constants.AutoConstants.INTAKE_CLAW_TURRET_AUTO_COLLECT_THIRD_APPLE;
import static constants.RobotConstants.INTAKE_CLAW_ARM_AUTO_INIT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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

import commands.DriveThreePoints;
import commands.DriveToPoint;
import constants.RobotConstants;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.ChassisSubsystem.FollowerSubsystem;
import subsystems.IntakeSubsystem.Vision;
import subsystems.Robot;
import subsystems.SleepyStuffff.Util.Vector2d;

@Autonomous
public class Auto7Basket_Exp extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    private Vision vision = new Vision();
    Robot robot = new Robot();
    RobotConstants.AllianceColour colour = RobotConstants.AllianceColour.Red;

    /*
    0417 Houston update:
    TODO: 1) Check if the first-three-sample pick up point is correct
          2) Debug the thrown-to-basket process
          3) Check the camera calibration in edison fields.
     */

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
//        FollowerConstants.xMovement = 83;
//        FollowerConstants.yMovement = 83;
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(7, 112, Math.toRadians(-90)));
        robot.autoInit(hardwareMap);
        vision.initTest(hardwareMap, telemetryA);
        telemetryA.update();
        CommandScheduler.getInstance().reset();
    }
    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.follower.update();
        follower.follower.telemetryDebug(telemetryA);
    }
    @Override
    public void start() {
        robot.scoring.scoreClose();
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        // ---------------  Preload ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127), -45, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_FIRST_APPLE)),
                                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_FIRST_APPLE)),
                                        new InstantCommand(() -> robot.intake.setExtendPercent(100))
                                )
                        ),
                        new WaitCommand(500),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // ---------------  First Apple ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, new Vector2d(17, 127), -15, 0).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new InstantCommand(() -> robot.scoring.armToPreTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(300),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        // drop first apple
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new SequentialCommandGroup(
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 0).setHoldEnd(true),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_SECOND_APPLE)),
                                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_SECOND_APPLE)),
                                        new InstantCommand(() -> robot.intake.setExtendPercent(100))
                                )
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // ---------------  Second Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),0, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToPreTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(300),
                        new InstantCommand(()-> robot.trans()),
                        new WaitCommand(950),
                        // drop second apple
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new SequentialCommandGroup(
                                        new WaitCommand(400),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 0).setHoldEnd(true),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                                        new InstantCommand(() -> robot.intake.setTurretPosition(INTAKE_CLAW_TURRET_AUTO_COLLECT_THIRD_APPLE)),
                                        new InstantCommand(() -> robot.intake.setRotatePosition(INTAKE_CLAW_ROTATE_AUTO_COLLECT_THIRD_APPLE)),
                                        new InstantCommand(() -> robot.intake.setExtendPercent(100))
                                )
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // ---------------  Third Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, new Vector2d(18, 131),15, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToPreTrans())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(300),
                        new InstantCommand(()-> robot.trans()),
                        new WaitCommand(950),
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveToPoint(follower, new Vector2d(17, 127),-45, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // Go to Sub
                        new ParallelCommandGroup(
                                //todo：when we run it in auto， change this DriveToPoint method to 62.6, 97.5
                                new DriveThreePoints(follower, new Vector2d(63, 91.5), new Vector2d(72.5, 115), 270, 0).setHoldEnd(false),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.scoring.armToPreTrans()),
                                new InstantCommand(() -> robot.intake.setExtendPercent(0)),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
                        ),
                        new WaitCommand(200),
                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, true))),
                        new WaitCommand(250),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(600),
                        // drop 5th apple
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.trans()),
                                        new WaitCommand(1100),
                                        new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveThreePoints(follower, new Vector2d(17, 127), new Vector2d(72.5, 115), -45, 0).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        //Go to sub
                        new ParallelCommandGroup(
                                new DriveThreePoints(follower, new Vector2d(64.5, 91.5), new Vector2d(72.5, 115), 270, 0).setHoldEnd(false),
                                new InstantCommand(() -> robot.scoring.liftBack()),
                                new InstantCommand(() -> robot.scoring.armToPreTrans()),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
                        ),
                        new WaitCommand(200),
                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, true))),
                        new WaitCommand(250),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(400),
                        // drop 6th apple
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.trans()),
                                        new WaitCommand(1100),
                                        new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveThreePoints(follower, new Vector2d(17, 127), new Vector2d(72.5, 115), -45, 0).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(()-> !follower.isBusy()),
                        new WaitCommand(100),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40)
//                        ,
//                        // Go to Sub
//                        new ParallelCommandGroup(
//                                new DriveThreePoints(follower, new Vector2d(66, 91.5), new Vector2d(72.5, 115), 270, 0).setHoldEnd(false),
//                                new InstantCommand(() -> robot.scoring.liftBack()),
//                                new InstantCommand(() -> robot.scoring.armToTrans()),
//                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
//                        ),
//                        new WaitCommand(200),
//                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, true))),
//                        new WaitCommand(250),
//                        new InstantCommand(() -> robot.collectApple()),
//                        new WaitCommand(400),
//                        // drop 7th apple
//                        new ParallelCommandGroup(
//                                new SequentialCommandGroup(
//                                        new WaitCommand(200),
//                                        new InstantCommand(() -> robot.trans()),
//                                        new WaitCommand(1100),
//                                        new InstantCommand(() -> robot.scoring.liftToHighBasket()),
//                                        new WaitCommand(600),
//                                        new InstantCommand(() -> robot.scoring.armToBasket())
//                                ),
//                                new DriveThreePoints(follower, new Vector2d(17, 127), new Vector2d(72.5, 115), -45, 0).setHoldEnd(true)
//                        ),
//                        new WaitUntilCommand(()-> !follower.isBusy()),
//                        new WaitCommand(100),
//                        new InstantCommand(() -> robot.scoring.scoreOpen()),
//                        new WaitCommand(40)
                )
        );
    }
    @Override
    public void stop() {
        robot.scoring.threadStop();
        CommandScheduler.getInstance().reset();
        vision.RIP();
    }
}
