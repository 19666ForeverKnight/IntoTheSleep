package teleop;

import static constants.RobotConstants.EXTEND_LEFT_IN;
import static constants.RobotConstants.EXTEND_LEFT_OUT;
import static constants.RobotConstants.EXTEND_RIGHT_IN;
import static constants.RobotConstants.EXTEND_RIGHT_OUT;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ARM_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_MID;
import static constants.RobotConstants.INTAKE_CLAW_ROTATE_RIGHT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_TURRET_RIGHT_LIMIT;
import static constants.RobotConstants.LIFT_HIGH_BASKET;
import static constants.RobotConstants.LIFT_HIGH_CHAMBER;
import static constants.RobotConstants.LIFT_LOW_BASKET;
import static constants.RobotConstants.LIFT_LOW_CHAMBER;
import static constants.RobotConstants.LIFT_OPEN_SPECIMEN_CLAW;
import static constants.RobotConstants.ROTATE_DELTA;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_OPEN;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.Robot;


@TeleOp(name = "Competition TaleOp", group = "Experiment")
public class Competition extends OpMode {
    private Telemetry telemetryA;
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;

    Robot robot = new Robot();
    Servo led = null;
    private final Pose startPose = new Pose(0,0,0); // add limelight init pos reading support

    private ElapsedTime timer = new ElapsedTime();
    private boolean intakeOut = false, intaking = false, trans = false, intakeIn = false, yHold = false, xHold = false, basket = false, chamber = false,back = true, manual = true, rbHold = false, specimenOpen = true, locked = false, pad = false, intakeOpen = false, rotateHor = true, thrown = false;
    private double intakeArmPos = INTAKE_CLAW_ARM_TRANS;
    public double intakeExtendPosRight = EXTEND_RIGHT_IN, intakeExtendPosLeft = EXTEND_LEFT_IN, intakeRotatePos = INTAKE_CLAW_ROTATE_MID, intakeTurretPos = INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
    private double prevRT = 0, prevLT = 0;
    private int basketIndex=0, chamberIndex=0, liftPos = 0, extendIndex = 0;
    private final int[] basketPos = {LIFT_HIGH_BASKET, LIFT_LOW_BASKET};
    private final int[] chamberPos = {LIFT_HIGH_CHAMBER, LIFT_LOW_CHAMBER};
    private final double[][] extendPos = {{EXTEND_LEFT_OUT, EXTEND_RIGHT_OUT}, {EXTEND_LEFT_IN + (EXTEND_LEFT_OUT - EXTEND_LEFT_IN) / 2, EXTEND_RIGHT_IN + (EXTEND_RIGHT_OUT - EXTEND_RIGHT_IN) / 2}};
    private boolean prevRB = false, prevUp = false;
    private double x, y, rx, p = 1;

    private int counthighbasket = 0;
    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        led = hardwareMap.get(Servo.class, "led");
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
        led.setPosition(0);
    }

    /** This method is called continuously after Init while waiting to be started. **/
    @Override
    public void init_loop() {
    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
//        follower.startTeleopDrive();
    }

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {
        poseUpdater.update();
        dashboardPoseTracker.update();

        //Drivetrain
        y = gamepad1.left_stick_y * 0.9;
        x = -gamepad1.left_stick_x * 0.9;
        rx = -gamepad1.right_stick_x * 0.85;
//        if (Math.abs(robot.intake.getClawArmPos() - INTAKE_CLAW_ARM_INTAKE_DOWN) < 0.05) p = 0.45;
//        else p = 1;
        robot.drivetrain.drive(y, x, rx, p);

        if (gamepad1.left_bumper) {
            robot.sweep();
            gamepad1.rumble(1, 0, 100);
        }

        if (gamepad1.cross) led.setPosition(1);
        else if (gamepad1.triangle) led.setPosition(0);

        if (gamepad2.left_bumper) {
            intakeOut = true;
            intaking = false;
            rotateHor = true;
            intakeRotatePos = INTAKE_CLAW_ROTATE_MID;
            extendIndex = 0;
            timer.reset();
            if (!chamber) {
                back = true;
                robot.scoring.armToPreTrans();
            }
        }

        if (intakeOut) {
            //FIXME: FIX THIS F**KING LOGIC @xzh
            //I don't know why, i dont want to know why, i shouldn't have to wonder why, but for whatever reason this stupid claw isn't rotating correctly
            if (timer.milliseconds() > 150) {
                robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
                intakeOut = false;
                intaking = true;
            } else {
                robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
                robot.intake.intakeClawOpen();
            }
        }

        if (intaking) {
            if (gamepad2.right_trigger - prevRT > 0 || gamepad2.right_trigger > 0.95) {
                robot.collectApple();
            }
            prevRT = gamepad2.right_trigger;

            if(gamepad2.dpad_left && robot.intake.getClawRotatePosition() > INTAKE_CLAW_ROTATE_LEFT_LIMIT) {
                intakeRotatePos -= ROTATE_DELTA;
            } else if(gamepad2.dpad_right && robot.intake.getClawRotatePosition() < INTAKE_CLAW_ROTATE_RIGHT_LIMIT) {
                intakeRotatePos += ROTATE_DELTA;
            }
            if (gamepad2.left_trigger > 0 && prevLT == 0) {
                if (rotateHor) {
                    rotateHor = false;
                    intakeRotatePos = INTAKE_CLAW_ROTATE_RIGHT_LIMIT;
                } else {
                    rotateHor = true;
                    intakeRotatePos = INTAKE_CLAW_ROTATE_MID;
                }
            }
            prevLT = gamepad2.left_trigger;
            robot.intake.setRotatePosition(intakeRotatePos);


//            if(gamepad2.left_stick_x > 0 && intakeTurretPos > INTAKE_CLAW_TURRET_RIGHT_LIMIT) {
//                intakeTurretPos -= TURRET_DELTA;
//            } else if(gamepad2.left_stick_x < 0 && intakeTurretPos < INTAKE_CLAW_TURRET_LEFT_LIMIT) {
//                intakeTurretPos += TURRET_DELTA;
//            }
            if(gamepad2.left_stick_x > 0.5) {
                intakeTurretPos = INTAKE_CLAW_TURRET_RIGHT_LIMIT;
            } else if(gamepad2.left_stick_x < -0.5) {
                intakeTurretPos = INTAKE_CLAW_TURRET_LEFT_LIMIT;
            } else if (gamepad2.left_stick_y < -0.5) {
                intakeTurretPos = INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
            }
            robot.intake.setTurretPosition(intakeTurretPos);

        }

        if (gamepad2.dpad_up && !prevUp) {
            extendIndex = (extendIndex + 1) % 2;
            intakeExtendPosLeft = extendPos[extendIndex][0];
            intakeExtendPosRight = extendPos[extendIndex][1];
            p = 0.45;
        }
        if (gamepad2.dpad_down) {
            intakeExtendPosLeft = EXTEND_LEFT_IN;
            intakeExtendPosRight = EXTEND_RIGHT_IN;
            extendIndex = 0;
            p = 1;
        }
        prevUp = gamepad2.dpad_up;

        robot.intake.setExtendPosition(intakeExtendPosRight, intakeExtendPosLeft);

        // Extend control
        if (!trans) {
//            if (gamepad2.left_stick_y < 0 && intakeExtendPosLeft > EXTEND_LEFT_OUT && intakeExtendPosRight < EXTEND_RIGHT_OUT) {
//                intakeExtendPosRight += (EXTEND_RIGHT_OUT - EXTEND_RIGHT_IN) / 40;
//                intakeExtendPosLeft -= (EXTEND_LEFT_IN - EXTEND_LEFT_OUT) / 40;
//            } else if (gamepad2.left_stick_y > 0 && intakeExtendPosLeft < EXTEND_LEFT_IN && intakeExtendPosRight >  EXTEND_RIGHT_IN) {
//                intakeExtendPosRight -= (EXTEND_RIGHT_OUT - EXTEND_RIGHT_IN) / 40;
//                intakeExtendPosLeft += (EXTEND_LEFT_IN - EXTEND_LEFT_OUT) / 40;
//            }

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
            intakeRotatePos = INTAKE_CLAW_ROTATE_MID;
            intakeTurretPos = INTAKE_CLAW_TURRET_INTAKE_AND_TRANS;
            intakeExtendPosRight = EXTEND_RIGHT_IN;
            intakeExtendPosLeft = EXTEND_LEFT_IN;
            basketIndex = 1;
            p = 1;
            timer.reset();
            robot.trans();
        }

        if (trans) {
            if (timer.milliseconds() > 630) {
                trans = false;
                gamepad2.rumble(1, 1, 100);
            }
        }

//         Scoring Control
        if (gamepad2.y && !yHold && !locked) {
            if (!basket) basketIndex = 1;
            basket = true;
            manual = false;
            back = false;
            chamber = false;
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
            chamber = true;
            chamberIndex = (chamberIndex + 3) % 2;
            intakeExtendPosRight = EXTEND_RIGHT_IN;
            intakeExtendPosLeft = EXTEND_LEFT_IN;
            robot.intake.intakeClawAvoid();
        } else if (!gamepad2.a && xHold) {
            xHold = false;
        }

        if (gamepad2.b && !locked) {
            back = true;
            manual = false;
            basketIndex = 1;
            timer.reset();
        }

        if (gamepad2.right_stick_y > 0.2 && robot.scoring.getLiftPosition() >= 0) {  // 软件限位
            robot.scoring.setLiftPower(-1);
            manual = true;
        } else if (gamepad2.right_stick_y < -0.2 && robot.scoring.getLiftPosition() <= LIFT_HIGH_BASKET) {
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
                if (Math.abs(robot.scoring.getLiftPosition() - liftPos) < 350){
                    if (counthighbasket > 6 && basketIndex == 0) {
                        robot.scoring.armToBasket();
                    } else {
                        robot.scoring.armToBasketDive();
                    }
                }
            } else {
                if (chamberIndex == 0) {
                    robot.scoring.armToChamber();
                }
                else if (chamberIndex == 1) robot.scoring.armToCollect();
                liftPos = chamberPos[chamberIndex];
            }
            robot.scoring.runToPosition(liftPos);
        }

        if (gamepad1.right_bumper && !prevRB) {
            specimenOpen = true;
            gamepad1.rumble(0, 1, 100);
            counthighbasket++;
        }
        prevRB = gamepad1.right_bumper;

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

        // Throw specimen to the left
        if(gamepad1.right_trigger > 0 && prevRT == 0){
            robot.thrownRight();
            intaking = false;
        }

//        telemetryA.addData("liftrightheight", robot.scoring.getrightliftheight());
//        telemetryA.addData("liftleftheight", robot.scoring.getleftliftheight());
//        telemetryA.addData("liftmiddleheight", robot.scoring.getmiddleliftheight());
//        telemetryA.addData("left", intakeExtendPosLeft);
//        telemetryA.addData("right", intakeExtendPosRight);
//        telemetryA.addData("x", poseUpdater.getPose().getX());
//        telemetryA.addData("y", poseUpdater.getPose().getY());
//        telemetryA.addData("heading", poseUpdater.getPose().getHeading());
//        telemetryA.addData("total heading", poseUpdater.getTotalHeading());
        if(counthighbasket > 6){
            telemetryA.addData("", "--------------------");
            telemetryA.addData("High Basket:", counthighbasket);
            telemetryA.addData("", "--------------------");
        }
        else {
            telemetryA.addData("", "--------------------");
            telemetryA.addData("High Basket:", counthighbasket);
            telemetryA.addData("", "--------------------");
        }
        telemetryA.addData("Thrown", thrown);
        //make this draw a big ascii art letter T , else draw a big ascii art letter F
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