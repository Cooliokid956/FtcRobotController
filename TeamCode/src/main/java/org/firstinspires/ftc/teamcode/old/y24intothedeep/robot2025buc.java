package org.firstinspires.ftc.teamcode.old.y24intothedeep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.component.SuperArm;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

//@TeleOp
public class robot2025buc extends OpMode {
    // Drive
    MecanumDrive drive  = new MecanumDrive();

    // Components
    SuperArm arm = new SuperArm();

    void setPitchAndLength(int p, int l) {
        arm.armPos = p;
        arm.slidePos = l;
    }

    @Override
    public void init() {
        drive.config.init(hardwareMap);
        arm  .config.init(hardwareMap);
    }

    @Override
    public void init_loop() {
        drive.manual.print(telemetry);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        arm.moveArm((int)((gamepad1.right_trigger - gamepad1.left_trigger) * 15.f));
        telemetry.addData("lt", gamepad1.left_trigger);
        telemetry.addData("rt", gamepad1.right_trigger);
        arm.update(gamepad1);

        if (gamepad1.dpad_up  ) setPitchAndLength(3700,1600);
        if (gamepad1.dpad_down) setPitchAndLength(0,0);

        arm.config.slide.setPower(gamepad1.left_bumper ? .55 : .2);
        telemetry.addData("pitch", arm.config.arm.getCurrentPosition());
    }
}
