package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType.getMotorType;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;
import java.util.Arrays;

public class Robot {
    private DcMotorEx flm;
    private DcMotorEx frm;
    private DcMotorEx blm;
    private DcMotorEx brm;


    private double ticksPerRotation;
    private IMU imu;
    public void init(HardwareMap hwMap) {
        flm = hwMap.get(DcMotorEx.class, "fl");
        flm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        frm = hwMap.get(DcMotorEx.class, "fr");
        frm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        blm = hwMap.get(DcMotorEx.class, "bl");
        blm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        brm = hwMap.get(DcMotorEx.class, "br");
        brm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        ticksPerRotation = flm.getMotorType().getTicksPerRev();

        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(RevOrientation));

    }

    public IMU getImu() {
        return imu;
    }
    public void setMotorSpeed(double flp, double frp, double blp, double brp) {
        flm.setPower(flp);
        frm.setPower(frp);
        blm.setPower(blp);
        brm.setPower(brp);
    }
    public double getTicksPerRotation() {
        return ticksPerRotation;
    }

    public double[] getMotorRotations() {
        return new double[] {
                flm.getCurrentPosition()/ticksPerRotation,
                frm.getCurrentPosition()/ticksPerRotation,
                blm.getCurrentPosition()/ticksPerRotation,
                brm.getCurrentPosition()/ticksPerRotation
        };
    }
    public double getHeading(AngleUnit angleUnit) {
        return imu.getRobotYawPitchRollAngles().getYaw();
    }
    public ArrayList<TestItem> getTests() {
        ArrayList<TestItem> tests = new ArrayList<>();
        tests.add(new TestMotor("Front Left Motor", 0.5, flm));
        tests.add(new TestMotor("Front Right Motor", 0.5, frm));
        tests.add(new TestMotor("Back Left Motor", 0.5, blm));
        tests.add(new TestMotor("Back Right Motor", 0.5, brm));
        return tests;
    }
}
