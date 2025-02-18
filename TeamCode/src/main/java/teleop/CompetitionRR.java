package teleop;

import static constants.RobotConstants.EXTEND_DELTA;
import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_LEFT_OUT;
import static constants.RobotConstants.EXTEND_LEFT_TRANS;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_OUT;
import static constants.RobotConstants.EXTEND_RIGHT_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_DOWN;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ARM_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.INTAKE_CLAW_OPEN;
import static constants.RobotConstants.INTAKE_CLAW_PITCH_INTAKE;
import static constants.RobotConstants.INTAKE_CLAW_YAW_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_YAW_MID;
import static constants.RobotConstants.INTAKE_CLAW_YAW_RIGHT_LIMIT;
import static constants.RobotConstants.LIFT_HIGH_BASKET;
import static constants.RobotConstants.LIFT_HIGH_CHAMBER;
import static constants.RobotConstants.LIFT_LOW_BASKET;
import static constants.RobotConstants.LIFT_LOW_CHAMBER;
import static constants.RobotConstants.LIFT_OPEN_SPECIMEN_CLAW;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_OPEN;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystem.Robot;


@TeleOp(name = "RR Competition TaleOp", group = "Experiment")
public class CompetitionRR extends OpMode {
    private Telemetry telemetryA;
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;

    Robot robot = new Robot();
    private final Pose startPose = new Pose(0,0,0); // add limelight init pos reading support

    private ElapsedTime timer = new ElapsedTime();
    private boolean intakeOut = false, intaking = false, trans = false, intakeIn = false, yHold = false, xHold = false, basket = false, back = true, manual = true, rbHold = false, specimenOpen = true, locked = false, pad = false, intakeOpen = false;
    private double intakeArmPos = INTAKE_CLAW_ARM_TRANS;
    public double intakeExtendPosRight = EXTEND_RIGHT_IN, intakeExtendPosLeft = EXTEND_LEFT_IN;
    private double intakeYaw = INTAKE_CLAW_YAW_MID, prevRT = 0;
    private int basketIndex=0, chamberIndex=0,liftPos = 0;
    private final int[] basketPos = {LIFT_HIGH_BASKET, LIFT_LOW_BASKET};
    private final int[] chamberPos = {LIFT_HIGH_CHAMBER, LIFT_LOW_CHAMBER};
    private boolean clawClose = false;
    private double x, y, rx, p;

    private Follower follower;

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        Constants.setConstants(FConstants.class, LConstants.class);
        poseUpdater = new PoseUpdater(hardwareMap);
        startPose.setX(-8.99196234275037);
        startPose.setY(73.80454236128199);
        startPose.setHeading(0);
        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);

        robot.init(hardwareMap);
        timer.reset();
        telemetryA.update();
        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();

        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
    }

    /** This method is called continuously after Init while waiting to be started. **/
    @Override
    public void init_loop() {
    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {
        poseUpdater.update();
        dashboardPoseTracker.update();

        //Drivetrain
//        y = gamepad1.left_stick_y * 0.8;
//        x = -gamepad1.left_stick_x * 0.8;
//        rx = -gamepad1.right_stick_x * 0.8;
//        if (Math.abs(robot.intake.getClawArmPos() - INTAKE_CLAW_ARM_INTAKE_DOWN) < 0.05) p = 0.3;
//        else p = 1;
//        robot.drivetrain.drive(y, x, rx, p);
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        if (gamepad1.left_bumper) {
            robot.sweep();
            gamepad1.rumble(1, 0, 100);
        }

        if (gamepad2.left_bumper) {
            intakeOut = true;
            intaking = false;
            clawClose = false;
            back = true;
            timer.reset();
            robot.scoring.armToTrans();
        }

        if (intakeOut) {
            if (timer.milliseconds() > 150) {
                robot.intake.setPitchPosition(INTAKE_CLAW_PITCH_INTAKE);
                intakeArmPos = INTAKE_CLAW_ARM_INTAKE_UP;
                intakeOut = false;
                intaking = true;
            } else {
                robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
                intakeYaw = INTAKE_CLAW_YAW_MID;
            }
        }

        if (intaking) {
            if (gamepad2.right_trigger - prevRT > 0 || gamepad2.right_trigger > 0.95) {
                robot.intake.setClawPosition(INTAKE_CLAW_OPEN);
            } else {
                robot.intake.setClawPosition(INTAKE_CLAW_CLOSE);
            }
            prevRT = gamepad2.right_trigger;
            // Left/Right control
            if (gamepad1.right_trigger > 0) {
                robot.intake.setLeftRightPosition(0, 1);
            } else if (gamepad1.left_trigger > 0) {
                robot.intake.setLeftRightPosition(1, 0);
            } else {
                robot.intake.setLeftRightPosition(0.5, 0.5);
            }

            // Arm and Yaw control
            if (gamepad2.dpad_down && intakeArmPos > INTAKE_CLAW_ARM_INTAKE_DOWN) {
                intakeArmPos -= 0.012;
            } else if (gamepad2.dpad_up && intakeArmPos < INTAKE_CLAW_ARM_INTAKE_UP) {
                intakeArmPos += 0.012;
            }
            if (gamepad2.dpad_left && intakeYaw > INTAKE_CLAW_YAW_LEFT_LIMIT) {
                intakeYaw -= 0.003;
            } else if (gamepad2.dpad_right && intakeYaw < INTAKE_CLAW_YAW_RIGHT_LIMIT) {
                intakeYaw += 0.003;
            }
            robot.intake.setArmPosition(intakeArmPos);
            robot.intake.setYawPosition(intakeYaw);
        }

        // Extend control
        if (!trans) {
            if (gamepad2.left_stick_y < 0 && intakeExtendPosLeft > EXTEND_LEFT_OUT && intakeExtendPosRight < EXTEND_RIGHT_OUT) {
                intakeExtendPosRight += EXTEND_DELTA;
                intakeExtendPosLeft -= EXTEND_DELTA;
            } else if (gamepad2.left_stick_y > 0 && intakeExtendPosLeft < EXTEND_LEFT_IN && intakeExtendPosRight >  EXTEND_RIGHT_IN) {
                intakeExtendPosRight -= EXTEND_DELTA;
                intakeExtendPosLeft += EXTEND_DELTA;
            }
            robot.intake.setExtendPosition(intakeExtendPosRight, intakeExtendPosLeft);

            if (specimenOpen) {
                robot.scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
            } else {
                robot.scoring.setScoreClawPosition(SCORE_CLAW_CLOSE);
            }
        }

        // Transfer sequence
        if (gamepad2.x) {
            trans = true;
            intaking = false;
            intakeOut = false;
            intakeIn = false;
            specimenOpen = false;
            timer.reset();
            robot.trans();
        }

        if (trans) {
            if (timer.milliseconds() > 830) {
                trans = false;
                gamepad2.rumble(1, 1, 100);
                intakeExtendPosRight = EXTEND_RIGHT_TRANS;
                intakeExtendPosLeft = EXTEND_LEFT_TRANS;
            }
        }

//         Scoring Control
        if (gamepad2.y && !yHold && !locked) {
            robot.intake.intakeClawAvoid();
            if (!basket) basketIndex = 1;
            basket = true;
            manual = false;
            back = false;
            yHold = true;
            basketIndex = (basketIndex + 3) % 2;
        } else if (!gamepad2.y && yHold) {
            yHold = false;
        }

        if (gamepad2.a && !xHold && !locked) {
            if (basket) chamberIndex = 0;
            basket = false;
            manual = false;
            back = false;
            xHold = true;
            chamberIndex = (chamberIndex + 3) % 2;
        } else if (!gamepad2.a && xHold) {
            xHold = false;
        }

        if (gamepad2.b && !locked) {
            robot.intake.intakeClawAvoid();
            back = true;
            manual = false;
            basketIndex = 1;
            timer.reset();
        }

        if (gamepad2.right_stick_y > 0.2 && robot.scoring.getLiftPosition() >= 0) {  // 软件限位
            robot.scoring.setLiftPower(-1);
            manual = true;
        } else if (gamepad2.right_stick_y < -0.2 && robot.scoring.getLiftPosition() <= 1800) {
            robot.scoring.setLiftPower(1);
            manual = true;
            if ((robot.scoring.getrightliftheight() + robot.scoring.getleftliftheight()) / 2.0 > LIFT_OPEN_SPECIMEN_CLAW) {
                specimenOpen = true;
            }
        } else if (manual) {
            robot.scoring.setLiftPower(0);
        }

        if (!manual) {
            if (back) {
                robot.scoring.setScoreArmPosition(SCORE_CLAW_ARM_TRANS, SCORE_CLAW_FLIP_TRANS);
                liftPos = 0;
                if (timer.milliseconds() > 1500) {
                    manual = true;
                }
            } else if (basket) {
                liftPos = basketPos[basketIndex];
                if (Math.abs(robot.scoring.getLiftPosition() - liftPos) < 230) {
                    robot.scoring.armToBasket();
                }
            } else {
                if (chamberIndex == 0) robot.scoring.armToChamber();
                else if (chamberIndex == 1) robot.scoring.armToCollect();
                liftPos = chamberPos[chamberIndex];
            }
            robot.scoring.runToPosition(liftPos);
        }

        if (gamepad1.right_bumper) {
            specimenOpen = true;
            gamepad1.rumble(0, 1, 100);
        }

        if (gamepad2.right_bumper && !rbHold) {
            rbHold = true;
            specimenOpen = !specimenOpen;
        } else if (!gamepad2.right_bumper && rbHold) {
            rbHold = false;
        }


//        if (specimenOpen) {
//            robot.scoring.setScoreArmPosition();
//        } else {
//            robot.scoring.setSpecimenClawPosition(SPECIMEN_CLAW_CLOSE);
//        }

        if (gamepad2.touchpad && !pad) {
            pad = true;
            locked = !locked;
        } else if (!gamepad2.touchpad && pad) {
            pad = false;
        }

        telemetryA.addData("liftrightheight", robot.scoring.getrightliftheight());
        telemetryA.addData("liftleftheight", robot.scoring.getleftliftheight());
        telemetryA.addData("liftmiddleheight", robot.scoring.getmiddleliftheight());
        telemetryA.addData("left", intakeExtendPosLeft);
        telemetryA.addData("right", intakeExtendPosRight);
        telemetryA.addData("x", poseUpdater.getPose().getX());
        telemetryA.addData("y", poseUpdater.getPose().getY());
        telemetryA.addData("heading", poseUpdater.getPose().getHeading());
        telemetryA.addData("total heading", poseUpdater.getTotalHeading());

        telemetryA.update();

        Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();
    }

    /** We do not use this because everything automatically should disable **/
    @Override
    public void stop() {
    }
}