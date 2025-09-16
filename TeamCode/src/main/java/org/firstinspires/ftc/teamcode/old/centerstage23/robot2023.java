package org.firstinspires.ftc.teamcode.old.centerstage23;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.ArmConfig;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

//@TeleOp
public class robot2023 extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    ArmConfig arm = new ArmConfig();

    Servo drone;
    double dronePos = 0f;

    double midPos;
    int basePos;
    int baseOffset = 0;
    final int BASE_LENGTH = 9;
    final int JOINT_LENGTH = 8;
    float armRadius = 0f;
    boolean armMode = true;
    boolean armDown = false;
    boolean claw = true;
    boolean clawDown = false;

//    public int bti(boolean bool) {
//        return bool ? 1 : 0;
//    }

    @Override
    public void init() {
        drive.config.init(hardwareMap);
        drone = hardwareMap.get(Servo.class, "servoDrone");
        arm.init(hardwareMap);
    }

    @Override
    public void start() {
        arm.clawMini.scaleRange(.2f,.3f);
        if (gamepad1.guide) {
            midPos = .384;
            basePos = 0;
        } else {
            midPos = .42;
            midPos = .0;
            basePos = 300;
        }
        armRadius = .433f;
    }

    private void armStep() {
        /*
        if (gamepad1.a) {
            basePos += 1;
        }
        if (gamepad1.b) {
            basePos -= 1;
        }*/
        if (gamepad1.x||gamepad1.dpad_up) {
            midPos += .001;
        }
        if (gamepad1.y||gamepad1.dpad_down) {
            midPos -= .001;
        }
        /*
        max servo: 0.483
         */

        if (gamepad1.right_trigger > 0.05) {
            armDown = true;
            armRadius = MathUtils.clamp(armRadius + (armMode ? 0.01f : -0.01f),0f,1.7f);
        } else if (armDown) {
            armMode = !armMode;
            armDown = false;
        }

        if (gamepad1.left_bumper && !clawDown) {
            clawDown = true;
            claw = !claw;
        } else if (!gamepad1.left_bumper) {
            clawDown = false;
        }
        if (gamepad1.dpad_down) {
            baseOffset = 0;
            armRadius = 0f;
        }
        if (gamepad1.dpad_right) {
            baseOffset = 434;
            armRadius = .18f;
            claw = false;
        }
        if (gamepad1.dpad_up) {
            baseOffset = 150;
            armRadius = .433f;
        }
        if (gamepad1.dpad_left) {
            baseOffset = 250;
            armRadius = .55f;
        }
        baseOffset -= Math.abs(gamepad1.right_stick_y) > 0.05 ? gamepad1.right_stick_y*2 : 0;
//        midPos = .384 + armRadius*.1;
//        dronePos += gamepad1.left_trigger/20;
        drone.setPosition(gamepad1.right_bumper ? .045 : 0);
//        drone.setPosition(dronePos);
        basePos = (int)(300*armRadius)+baseOffset;
        arm.jointBase.setPower(.25);
        arm.jointBase.setTargetPosition(basePos);
        arm.jointMid1.setPosition(midPos);
        arm.jointMid2.setPosition(midPos);

        arm.clawMini.setPosition(claw?0:1);
        // 0 - gripped, 1 - closed
        telemetry.addData("Radius", armRadius);
        telemetry.addData("Base Offset", baseOffset);
        telemetry.addData("Base Pos", basePos);
        telemetry.addData("Servo", midPos);
        telemetry.addData("Reported Base Pos", arm.jointBase.getCurrentPosition());
        telemetry.addData("Drone Pos", dronePos);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        armStep();
    }
}
