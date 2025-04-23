package auto;

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
import com.qualcomm.robotcore.hardware.Servo;

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
public class Auto6Stable_1_blue extends OpMode {
    private Telemetry telemetryA;
    private FollowerSubsystem follower;
    private Servo led = null;
    private Vision vision = new Vision();
    Robot robot = new Robot();
    RobotConstants.AllianceColour colour = RobotConstants.AllianceColour.Blue;
    Vector2d start = new Vector2d(6, 112);
    double startHeading = -90;
    Vector2d dropBasket = new Vector2d(15.5, 128.5);
    Vector2d dropBasket2 = new Vector2d(15.5, 126.5);
    double dropHeading = -45;
    Vector2d collect1 = new Vector2d(26.5, 121);
    double collect1Heading = 0;
    Vector2d collect2 = new Vector2d(26.5, 130);
    double collect2Heading = 0;
    Vector2d collect3 = new Vector2d(30, 132);
    double collect3Heading = 15;
    Vector2d sub = new Vector2d(63, 95.5);
    double subHeading = -90;

    double collect1Turret = 90;
    double collect1Rotate = 180 - collect1Turret;
    double collect1Extend = 20;

    double collect2Turret = 90;
    double collect2Rotate = 180 - collect2Turret;
    double collect2Extend = 20;

    double collect3Turret = 52;
    double collect3Rotate = 180 - collect3Turret + collect3Heading;
    double collect3Extend = 20;


    /*
    0417 Houston update:
    TODO: 1) Check if the first-three-sample pick up point is correct
          2) Debug the thrown-to-basket process
          3) Check the camera calibration in edison fields.
     */

    @Override
    public void init() {
        Constants.setConstants(FConstants.class, LConstants.class);
        led = hardwareMap.get(Servo.class, "led");
//        FollowerConstants.xMovement = 83;
//        FollowerConstants.yMovement = 83;
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        follower = new FollowerSubsystem(hardwareMap, telemetryA);
        follower.setStartingPose(new Pose(start.x, start.y, Math.toRadians(startHeading)));
        robot.autoInit(hardwareMap);
        vision.init(hardwareMap, telemetryA);
        telemetryA.update();
        CommandScheduler.getInstance().reset();
        led.setPosition(1);
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
                                        new WaitCommand(675),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveToPoint(follower, dropBasket, dropHeading, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                                        new InstantCommand(() -> robot.intake.setTurretDegree(collect1Turret)),
                                        new InstantCommand(() -> robot.intake.setRotateDegree(collect1Rotate)),
                                        new InstantCommand(() -> robot.intake.setExtendPercent(collect1Extend))
                                )
                        ),
                        new WaitCommand(500),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(230),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // ---------------  First Apple ---------------
                        new ParallelCommandGroup(
                                new DriveToPoint(follower, collect1, collect1Heading, 0).setHoldEnd(true),
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(200),
                        new InstantCommand(() -> robot.collectAppleNoLift()),
                        new WaitCommand(300),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        // drop first apple
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new SequentialCommandGroup(
                                        new WaitCommand(675),
                                        new InstantCommand(() -> robot.scoring.armToBasket()),
                                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                                        new InstantCommand(() -> robot.intake.setTurretDegree(collect2Turret)),
                                        new InstantCommand(() -> robot.intake.setRotateDegree(collect2Rotate)),
                                        new InstantCommand(() -> robot.intake.setExtendPercent(collect2Extend))
                                ),
                                new DriveToPoint(follower, dropBasket, dropHeading, 0).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // ---------------  Second Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, collect2, collect2Heading, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(200),
                        new InstantCommand(() -> robot.collectAppleNoLift()),
                        new WaitCommand(300),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        // drop second apple
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.armToBasket()),
                                        new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP)),
                                        new InstantCommand(() -> robot.intake.setTurretDegree(collect3Turret)),
                                        new InstantCommand(() -> robot.intake.setRotateDegree(collect3Rotate)),
                                        new InstantCommand(() -> robot.intake.setExtendPercent(collect3Extend))
                                ),
                                new DriveToPoint(follower, dropBasket, dropHeading, 0).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // ---------------  Third Apple ---------------
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(350),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new DriveToPoint(follower, collect3, collect3Heading, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.armToTrans())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(200),
                        new InstantCommand(() -> robot.collectAppleNoLift()),
                        new WaitCommand(300),
                        new InstantCommand(() -> robot.trans()),
                        new WaitCommand(950),
                        // drop third apple
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(500),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveToPoint(follower, dropBasket2, dropHeading, 0).setHoldEnd(true),
                                new InstantCommand(() -> robot.scoring.liftToHighBasket())
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        // Go to Sub
                        new ParallelCommandGroup(
                                //todo：when we run it in auto， change this DriveToPoint method to 62.6, 97.5
                                new DriveThreePoints(follower, sub, new Vector2d(72.5, 115), subHeading, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.setExtendPercent(0)),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
                        ),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, true))),
                        new WaitCommand(250),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(450),
                        // drop 5th apple
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
//                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.trans()),
                                        new WaitCommand(1000),
                                        new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveThreePoints(follower, dropBasket2, new Vector2d(72.5, 115), dropHeading, 0).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(150),
                        new InstantCommand(() -> robot.scoring.scoreOpen()),
                        new WaitCommand(40),
                        //Go to sub
                        new ParallelCommandGroup(
                                new DriveThreePoints(follower, sub, new Vector2d(72.5, 115), subHeading, 0).setHoldEnd(false),
                                new SequentialCommandGroup(
                                        new WaitCommand(300),
                                        new InstantCommand(() -> robot.scoring.liftBack())
                                ),
                                new InstantCommand(() -> robot.scoring.armToTrans()),
                                new InstantCommand(() -> robot.intake.setArmPosition(INTAKE_CLAW_ARM_AUTO_INIT))
                        ),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.grabApple(vision.getLatestResult(colour, true))),
                        new WaitCommand(250),
                        new InstantCommand(() -> robot.collectApple()),
                        new WaitCommand(450),
                        // drop 6th apple
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
//                                        new WaitCommand(200),
                                        new InstantCommand(() -> robot.trans()),
                                        new WaitCommand(1000),
                                        new InstantCommand(() -> robot.scoring.liftToHighBasket()),
                                        new WaitCommand(600),
                                        new InstantCommand(() -> robot.scoring.armToBasket())
                                ),
                                new DriveThreePoints(follower, dropBasket2, new Vector2d(72.5, 115), dropHeading, 0).setHoldEnd(true)
                        ),
                        new WaitUntilCommand(() -> !follower.isBusy()),
                        new WaitCommand(150),
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
