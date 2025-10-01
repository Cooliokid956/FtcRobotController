package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.old.y22powerplay.config.HDriveConfig;
import org.firstinspires.ftc.teamcode.pipelines.pipelineCVrgb;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

//@Autonomous
public class Autonomial extends OpMode {

    HDriveConfig robot = new HDriveConfig();
    OpenCvWebcam webcam;

    pipelineCVrgb pipeline = new pipelineCVrgb();

    // States:
    // - 1: move to cone
    // - 2: detect color
    // - 3: when color is detected move to proper parking spot
    // - 4: stop

    private int state = 1;

    @Override
    public void init() {
        robot.init(hardwareMap);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Gabe"), cameraMonitorViewId);
        webcam.setPipeline(pipeline);
        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    @Override
    public void start() {
        resetRuntime();
    }

    private void chill(double door){
        resetRuntime();
        while (getRuntime()<door) {}
    }

    private void resetMotors() {
        robot.lMotor.setPower(0);
        robot.rMotor.setPower(0);
        robot.cMotor.setPower(0);
        robot.lMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.cMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.cMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void loop() {
        // The code placed inside the init() function runs only
        // once, here it keeps running until you stop the robot.

        telemetry.addLine(pipeline.colorDetected);
        switch (state) {
            case 1:
                while (getRuntime()<=3) {
                    robot.cMotor.setPower(-0.5);
                }

                resetMotors();
                while (!(robot.lMotor.getCurrentPosition()==2525 && robot.rMotor.getCurrentPosition()==2525)){
                    robot.lMotor.setPower(0.5);
                    robot.rMotor.setPower(0.5);
                }

                resetMotors();
                chill(2);
                while (!(robot.lMotor.getCurrentPosition()==-950 && robot.rMotor.getCurrentPosition()==-950)){
                    robot.lMotor.setPower(-0.5);
                    robot.rMotor.setPower(0.5);
                }
                chill(99);
                break;
            case 2:
                if (pipeline.colorDetected == "green") {

                } else if (pipeline.colorDetected == "red") {

                } else if (pipeline.colorDetected == "blue") {

                }
                state = 3;
                break;
            case 3:

        }
    }
}