package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.ArmConfig;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

//@TeleOp
public class MecanumOpMode extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    ArmConfig arm = new ArmConfig();

    int basePos;
    double midPos = .4;

    public int bti(boolean bool) {
        return bool ? 1 : 0;
    }

    public void init() {
        drive.config.init(hardwareMap);
        arm.init(hardwareMap);
    }

    private void armStep() {
        if (gamepad1.a) {
            basePos += 1;
        }
        if (gamepad1.b && basePos != 0) {
            basePos -= 1;
        }
        if (gamepad1.x) {
            midPos += .002;
        }
        if (gamepad1.y) {
            midPos -= .002;
        }
        arm.jointBase.setPower(.2);
        arm.jointBase.setTargetPosition(basePos);
        arm.jointMid1.setPosition(midPos);
        arm.jointMid2.setPosition(midPos);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        armStep();
        telemetry.addLine("Base Pos: " + basePos);
    }
}