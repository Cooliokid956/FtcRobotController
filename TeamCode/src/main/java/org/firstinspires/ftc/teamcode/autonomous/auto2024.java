package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.pipelines.pipelineCVrgb;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.pipelines.pipelineCV2023;

@Autonomous
public class auto2024 extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    Gamepad fakeGamepad = new Gamepad();

    @Override
    public void init() {
        drive.config.init(hardwareMap);
        resetRuntime();
    }

    @Override
    public void loop() {
        fakeGamepad.left_stick_x = (getRuntime() < 2) ? 1 : 0;
        drive.drive(fakeGamepad, telemetry);
    }
}
