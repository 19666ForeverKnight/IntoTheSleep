package teleop;

import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_DOWN;
import static constants.RobotConstants.INTAKE_CLAW_ARM_INTAKE_UP;
import static constants.RobotConstants.INTAKE_CLAW_ARM_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_CLOSE;
import static constants.RobotConstants.INTAKE_CLAW_OPEN;
import static constants.RobotConstants.INTAKE_CLAW_PITCH_INTAKE;
import static constants.RobotConstants.INTAKE_CLAW_PITCH_TRANS;
import static constants.RobotConstants.INTAKE_CLAW_PITCH_TRANS_PREP;
import static constants.RobotConstants.INTAKE_CLAW_YAW_LEFT_LIMIT;
import static constants.RobotConstants.INTAKE_CLAW_YAW_MID;
import static constants.RobotConstants.INTAKE_CLAW_INTAKE;
import static constants.RobotConstants.INTAKE_CLAW_YAW_RIGHT_LIMIT;
import static constants.RobotConstants.LIFT_HIGH_BASKET;
import static constants.RobotConstants.LIFT_HIGH_CHAMBER;
import static constants.RobotConstants.LIFT_LOW_BASKET;
import static constants.RobotConstants.LIFT_LOW_CHAMBER;
import static constants.RobotConstants.LIFT_OPEN_SPECIMEN_CLAW;
import static constants.RobotConstants.SCORE_CLAW_ARM_DROP_TELEOP;
import static constants.RobotConstants.SCORE_CLAW_ARM_TRANS;
import static constants.RobotConstants.SCORE_CLAW_CLOSE;
import static constants.RobotConstants.SCORE_CLAW_FLIP_DROP;
import static constants.RobotConstants.SCORE_CLAW_FLIP_TRANS;
import static constants.RobotConstants.SCORE_CLAW_OPEN;
import static constants.RobotConstants.SPECIMEN_CLAW_CLOSE;
import static constants.RobotConstants.SPECIMEN_CLAW_OPEN;

//import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import subsystem.Robot;


@TeleOp(name = "Competition TaleOp", group = "Experiment")
public class Competition extends OpMode {
    Robot robot = new Robot();
    //    private Follower follower;
    private final Pose startPose = new Pose(0,0,0); // add limelight init pos reading support

    private ElapsedTime timer = new ElapsedTime();
    private boolean intakeOut = false, intaking = false, trans = false, intakeIn = false, yHold = false, xHold = false, basket = false, back = true, manual = true, rbHold = false, specimenOpen = true, locked = false, pad = false;
    private double intakeArmPos = INTAKE_CLAW_ARM_TRANS;
    private double intakeYaw = INTAKE_CLAW_YAW_MID;
    private int basketIndex=0, chamberIndex = 0, liftPos = 0;
    private final int[] basketPos = {LIFT_HIGH_BASKET, LIFT_LOW_BASKET};
    private final int[] chamberPos = {LIFT_HIGH_CHAMBER, LIFT_LOW_CHAMBER};
    private boolean clawClose = false;
    private double x, y, rx, p;

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
//        Constants.setConstants(FConstants.class,LConstants.class);
//        follower = new Follower(hardwareMap);
//        follower.setStartingPose(startPose);
        robot.init(hardwareMap);
        timer.reset();
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

        //Drivetrain
//        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        y = -gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;
        rx = gamepad1.right_stick_x;
        p = (gamepad1.left_bumper || robot.intake.getExtendPosition() < -200) ? 0.3 : 0.9;
        robot.drivetrain.drive(y, x, rx, p);
//        robot.drivetrain.driveFieldOriented(y, x, rx, p);

        // Reset heading
//        if (gamepad1.touchpad) robot.drivetrain.pinPoint.resetPosAndIMU();

        // Intake Control
        if (gamepad2.left_bumper) {
            intakeOut = true;
            intaking = false;
            clawClose = false;
            timer.reset();
        }

        if (intakeOut) {
            if (timer.milliseconds() > 150) {
                robot.intake.setPitchPosition(INTAKE_CLAW_PITCH_INTAKE);
                intakeArmPos = INTAKE_CLAW_ARM_INTAKE_UP;
                intakeOut = false;
                intaking = true;
            } else {
                robot.intake.setArmPosition(INTAKE_CLAW_ARM_INTAKE_UP);
                robot.intake.setClawPosition(INTAKE_CLAW_INTAKE);
                intakeYaw = INTAKE_CLAW_YAW_MID;
            }
        }

        if (intaking) {
            // Claw control with toggle behavior
            if (gamepad2.left_trigger > 0) {
                clawClose = true;
            } else if (gamepad2.right_trigger > 0) {
                robot.intake.setClawPosition(INTAKE_CLAW_OPEN);
                clawClose = false;
            } else if (clawClose) {
                robot.intake.setClawPosition(INTAKE_CLAW_CLOSE);
            } else {
                robot.intake.setClawPosition(INTAKE_CLAW_INTAKE);
            }

            // Left/Right control
            if (gamepad1.right_trigger > 0) {
                robot.intake.setLeftRightPosition(1, 0);
            } else if (gamepad1.left_trigger > 0) {
                robot.intake.setLeftRightPosition(0, 1);
            } else {
                robot.intake.setLeftRightPosition(0.5, 0.5);
            }

            // Arm and Yaw control
            if (gamepad2.dpad_down && intakeArmPos > INTAKE_CLAW_ARM_INTAKE_DOWN) {
                intakeArmPos -= 0.006;
            } else if (gamepad2.dpad_up && intakeArmPos < INTAKE_CLAW_ARM_INTAKE_UP) {
                intakeArmPos += 0.006;
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
            if (gamepad2.left_stick_y > 0 && robot.intake.getExtendPosition() < -20) {  // && !robot.intake.anyTouched()
//                extendPos += 6;
                robot.intake.setExtendPower(0.6);
            } else if (gamepad2.left_stick_y < 0 && robot.intake.getExtendPosition() > -730) {
//                extendPos -= 6;
                robot.intake.setExtendPower(-0.6);
            } else {
                robot.intake.setExtendPower(0);
            }
        }

        // Transfer sequence
        if (gamepad2.x) {
            trans = true;
            intaking = false;
            intakeOut = false;
            intakeIn = false;
            timer.reset();
        }

        if (trans) {
            if (timer.milliseconds() > 200 && robot.intake.anyTouched()) {
                robot.intake.setExtendPower(0.1);
                robot.intake.setLeftRightPosition(0.5, 0.5);
                robot.intake.setPitchPosition(INTAKE_CLAW_PITCH_TRANS);
                trans = false;
                intakeIn = true;
                timer.reset();
            } else {
//                    extendPos = 0;
                if (robot.intake.getExtendPosition() > -200) {
                    robot.intake.setExtendPower(0.3);
                } else {
                    robot.intake.setExtendPower(1);
                }
                robot.intake.setPitchPosition(INTAKE_CLAW_PITCH_TRANS_PREP);
                robot.intake.setArmPosition(INTAKE_CLAW_ARM_TRANS);
                robot.intake.setClawPosition(INTAKE_CLAW_CLOSE);
                robot.scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
                robot.intake.setYawPosition(INTAKE_CLAW_YAW_MID);
            }
        }

        if (intakeIn) {
            robot.intake.setExtendPower(0.1);
            if (timer.milliseconds() > 750) {
                robot.intake.setClawPosition(INTAKE_CLAW_OPEN);
                intakeIn = false;
            } else if (timer.milliseconds() > 550) {
                robot.scoring.setScoreClawPosition(SCORE_CLAW_CLOSE);
            }
        }

//            robot.intake.extendTo(extendPos);

        // Scoring Control
        if (gamepad2.y && !yHold && !locked) {
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
            if (basket) chamberIndex = 1;
            basket = false;
            manual = false;
            back = false;
            xHold = true;
            chamberIndex = (chamberIndex + 3) % 2;
        } else if (!gamepad2.a && xHold) {
            xHold = false;
        }

        if (gamepad2.b && !locked) {
            back = true;
            manual = false;
            basketIndex = 1;
            chamberIndex = 1;
            timer.reset();
        }

        if (gamepad2.right_stick_y < -0.2 && liftPos > -2000 && !locked) {
            robot.scoring.setLiftPower(-1);
            manual = true;
        } else if (gamepad2.right_stick_y > 0.2) {
            robot.scoring.setLiftPower(locked ? 1 : 0.8);
            manual = true;
            if (robot.scoring.getLiftPosition() > LIFT_OPEN_SPECIMEN_CLAW) {
                specimenOpen = true;
            }
        } else if (manual) {
            robot.scoring.setLiftPower(0);
        }

        if (!manual) {
            if (back) {
                liftPos = 0;
                robot.scoring.setScoreArmPosition(SCORE_CLAW_ARM_TRANS, SCORE_CLAW_FLIP_TRANS);
                if (timer.milliseconds() > 1500) {
                    manual = true;
                }
            } else if (basket) {
                liftPos = basketPos[basketIndex];
                if (Math.abs(robot.scoring.getLiftPosition() - liftPos) < 350) {
                    robot.scoring.setScoreArmPosition(SCORE_CLAW_ARM_DROP_TELEOP, SCORE_CLAW_FLIP_DROP);
                }
            } else {
                liftPos = chamberPos[chamberIndex];
                robot.scoring.setScoreArmPosition(SCORE_CLAW_ARM_TRANS, SCORE_CLAW_FLIP_TRANS);
            }
            robot.scoring.runToPosition(liftPos);
        }

        if (gamepad1.right_bumper) {
            robot.scoring.setScoreClawPosition(SCORE_CLAW_OPEN);
        }

        if (gamepad2.right_bumper && !rbHold) {
            rbHold = true;
            specimenOpen = !specimenOpen;
        } else if (!gamepad2.right_bumper && rbHold) {
            rbHold = false;
        }

        if (specimenOpen) {
            robot.scoring.setSpecimenClawPosition(SPECIMEN_CLAW_OPEN);
        } else {
            robot.scoring.setSpecimenClawPosition(SPECIMEN_CLAW_CLOSE);
        }

        if (gamepad2.touchpad && !pad) {
            pad = true;
            locked = !locked;
        } else if (!gamepad2.touchpad && pad) {
            pad = false;
        }

        /* Telemetry Outputs of our Follower */
//        telemetry.addData("X", follower.getPose().getX());
//        telemetry.addData("Y", follower.getPose().getY());
//        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Update Telemetry to the Driver Hub */

        telemetry.update();


    }

    /** We do not use this because everything automatically should disable **/
    @Override
    public void stop() {
    }
}