package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class SpaceFestExit extends LinearOpMode {

    @Override
    public void runOpMode() {

        // Webcam -> webcam
        // ControlHub -> Motors -> 1:Hex 40:1 OuttakeMotor1, 2:goBILDA 5202/3/4 frm, 3:goBILDA 5202/3/4 brm
        // ExpansionHub -> Motors -> 0:goBILDA 5202/3/4 blm, 1:goBILDA 5202/3/4 flm, 2:Hex Motor 40:1 IntakeMotor, 3:goBILDA 5202/3/4 outtakeMotor2

        DcMotorEx frontLeftMotor = hardwareMap.get(DcMotorEx.class, "flm");
        DcMotorEx frontRightMotor = hardwareMap.get(DcMotorEx.class, "frm");
        DcMotorEx backLeftMotor = hardwareMap.get(DcMotorEx.class, "blm");
        DcMotorEx backRightMotor = hardwareMap.get(DcMotorEx.class, "brm");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (opModeIsActive()) {
            // Simple drive-forward exit routine
            frontLeftMotor.setPower(0.3);
            frontRightMotor.setPower(0.3);
            backLeftMotor.setPower(0.3);
            backRightMotor.setPower(0.3);

            sleep(2000); // adjust duration to move robot fully off the starting tile

            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backLeftMotor.setPower(0);
            backRightMotor.setPower(0);
        }
    }
}