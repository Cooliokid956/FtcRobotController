package org.firstinspires.ftc.teamcode.old.powerplay22;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.HDriveConfig;
import org.firstinspires.ftc.teamcode.config.LiftConfig;

//@TeleOp
public class HDriveOpMode extends OpMode {
    HDriveConfig robot = new HDriveConfig();

    LiftConfig lift = new LiftConfig();

    boolean modeMove;
    boolean modeMag;
    boolean backHeld;
    boolean clawToggle;
    boolean LBHeld;
    boolean RBHeld;
    double magnitude;
    double middlePower;
    double forwardPower;
    double turn;
    double max;
    int position;
    public int bti(boolean bool) {
        return bool ? 1 : 0;
    }

    private void claw() {
        if (gamepad1.right_bumper && !RBHeld){
            clawToggle = !clawToggle;
            RBHeld = true;
        } else if (!gamepad1.right_bumper){
            RBHeld = false;
        }

        robot.claw.setPosition(bti(clawToggle));
    }

    @Override
    public void init() {
        robot.init(hardwareMap);
        lift.init(hardwareMap);
        lift.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.lift.setTargetPosition(0);
        lift.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        modeMove = true;
        backHeld = false;
//        magnitude = 1;
//        middlePower = gamepad1.left_stick_x*bti((gamepad1.left_stick_x>=0.04||gamepad1.left_stick_x<=-0.04))*bti(modeMove)+gamepad1.right_stick_x*bti((gamepad1.right_stick_x>=0.04||gamepad1.right_stick_x<=-0.04))*bti(!modeMove);
//        forwardPower = gamepad1.left_stick_y*-1;
//        turn = gamepad1.right_stick_x*bti(modeMove)+gamepad1.left_stick_x*bti(!modeMove);
//        max = Math.max(Math.abs(Math.max(forwardPower + turn, forwardPower - turn)), 1.0);
    }

    @Override
    public void loop() {
        magnitude = 0.35+bti(modeMag)*0.65;
        middlePower = gamepad1.left_stick_x*bti((gamepad1.left_stick_x>=0.04||gamepad1.left_stick_x<=-0.04))*bti(modeMove)+gamepad1.right_stick_x*bti((gamepad1.right_stick_x>=0.04||gamepad1.right_stick_x<=-0.04))*bti(!modeMove);
        forwardPower = -gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x*bti(modeMove)+gamepad1.left_stick_x*bti(!modeMove);
        max = Math.max(Math.abs(Math.max(forwardPower + turn, forwardPower - turn)), 1.0);

        if (gamepad1.back && !backHeld){
            modeMove = !modeMove;
            backHeld = true;
        } else if (!gamepad1.back){
            backHeld = false;
        }
        if (gamepad1.left_bumper && !LBHeld){
            modeMag = !modeMag;
            LBHeld = true;
        } else if (!gamepad1.left_bumper){
            LBHeld = false;
        }


//        if (gamepad1.right_bumper) {
//            robot.claw.setPosition(.395);
//        } else if (gamepad1.left_bumper) {
//            robot.claw.setPosition(.415);
//        }

        claw();

        lift.lift.setPower(1);//previous speed was .75//
        lift.lift.setTargetPosition(position);
        if(gamepad1.a) {
            position = 1850;
        }
        else if(gamepad1.x) {
            position = 3200;
        }
        else if(gamepad1.y) {
            position = 4200;
        }
        else if(gamepad1.b) {
            position = 230;
        }
        position += (gamepad1.left_trigger-gamepad1.right_trigger)*7.5;

        if (position <= -1000){
            position = -1000;
        }

        if (gamepad1.dpad_up){
            robot.lMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.rMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.cMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.lMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.cMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        robot.lMotor.setPower((forwardPower + turn) / max * magnitude);
        robot.rMotor.setPower((forwardPower - turn) / max * magnitude);
        robot.cMotor.setPower((middlePower) * magnitude);
        telemetry.addLine("y: " + forwardPower);
        telemetry.addLine("x:" + turn);
        telemetry.addLine("right x:" + middlePower);
        telemetry.addLine(String.valueOf(robot.claw.getPosition()));
        telemetry.addData("lift", position);
        telemetry.addData("l", robot.lMotor.getCurrentPosition());
        telemetry.addData("r", robot.rMotor.getCurrentPosition());
        telemetry.addData("c", robot.cMotor.getCurrentPosition());
    }
}