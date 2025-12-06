package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


@TeleOp
public abstract class DriveTrain extends OpMode {
    private DcMotorEx frontLeftMotor, rearLeftMotor, rearRightMotor, frontRightMotor;

    DcMotorEx[] motors = new DcMotorEx[4];

    public void init(HardwareMap hwMap) {
        frontLeftMotor = hwMap.get(DcMotorEx.class, "flm");
        rearLeftMotor = hwMap.get(DcMotorEx.class, "blm");
        rearRightMotor = hwMap.get(DcMotorEx.class, "brm");
        frontRightMotor = hwMap.get(DcMotorEx.class, "frm");

        motors[0] = frontLeftMotor;
        motors[1] = rearLeftMotor;
        motors[2] = rearRightMotor;
        motors[3] = frontRightMotor;

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotorEx motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void drive(double y, double x, double turn) {
        double frontLeftPower = y + x + turn;
        double rearLeftPower = y - x + turn;
        double rearRightPower = y + x - turn;
        double frontRightPower = y - x + turn;

        double maxRawPower = Math.max(Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(rearLeftPower)),
                Math.max(Math.abs(rearRightPower), Math.abs(frontRightPower))), 1);

            frontLeftMotor.setPower(frontLeftPower / maxRawPower);
            rearLeftMotor.setPower(rearLeftPower / maxRawPower);
            rearRightMotor.setPower(rearRightPower / maxRawPower);
            frontRightMotor.setPower(frontRightPower / maxRawPower);
    }

}
