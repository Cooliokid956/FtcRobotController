package org.firstinspires.ftc.teamcode.old.y23centerstage;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.ArmConfig;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

//@TeleOp
public class data2023 extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    ArmConfig arm = new ArmConfig();

    public void init() {
        drive.config.init(hardwareMap);
        arm.init(hardwareMap);
        arm.jointBase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.jointBase.setPower(0);
        arm.jointBase.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    private void armStep() {
        telemetry.addData("Reported Base Pos", arm.jointBase.getCurrentPosition());
        telemetry.addData("Reported Servo A", arm.jointMid1.getPosition());
        telemetry.addData("Reported Servo B", arm.jointMid2.getPosition());
    }

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
        armStep();
    }
}
