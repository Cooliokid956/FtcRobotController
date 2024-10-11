package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.config.HDriveConfig;
import org.firstinspires.ftc.teamcode.config.LiftConfig;
import org.firstinspires.ftc.teamcode.pipelines.pipelineCVrgb;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

//@Autonomous
public class ___Right_ extends LinearOpMode {

    HDriveConfig robot = new HDriveConfig();
    LiftConfig lift = new LiftConfig();
    OpenCvWebcam webcam;

    pipelineCVrgb pipeline = new pipelineCVrgb();

    // States:
    // - 1: move to cone
    // - 2: detect color
    // - 3: when color is detected move to proper parking spot
    // - 4: stop

    private final int leniency = 8;
    private int state = 1;
    private int step = 1;
    private int color = 0;
    public double absDiff(double value, double compValue){
        return Math.abs(value-compValue);
    }

    private void resetMotors() {
        robot.lMotor.setPower(0);
        robot.rMotor.setPower(0);
        robot.cMotor.setPower(0);
        robot.lMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.cMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lMotor.setTargetPosition(0);
        robot.rMotor.setTargetPosition(0);
        robot.cMotor.setTargetPosition(0);
        robot.lMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.cMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        lift.init(hardwareMap);
        robot.cMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.lift.setTargetPosition(0);
        lift.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
        waitForStart();
        if (opModeIsActive()) {
            if (pipeline.colorDetected == "red") {
                color = 1;
            } else if (pipeline.colorDetected == "green") {
                color = 2;
            } else if (pipeline.colorDetected == "blue") {
                color = 3;
            }
            webcam.stopStreaming();
            resetMotors();
            resetRuntime();
            robot.claw.setPosition(0);
            lift.lift.setPower(0.7);
            lift.lift.setTargetPosition(400);
            while (opModeIsActive()) {
                telemetry.addLine(pipeline.colorDetected);
                telemetry.addData("State", state);
                telemetry.addData("Step", step);
                telemetry.addData("l", robot.lMotor.getCurrentPosition());
                telemetry.addData("r", robot.rMotor.getCurrentPosition());

                switch (state) {
                    case 1:
                        switch (step) {
                            case 1:
                                robot.cMotor.setPower(0.8);
                                robot.cMotor.setTargetPosition(-2500);
                                if (absDiff(robot.cMotor.getCurrentPosition(),robot.cMotor.getTargetPosition())<leniency) {
                                    sleep(1000);
                                    resetMotors();
                                    resetRuntime();
                                    step = 2;
                                }
                                break;
                            case 2:
                                robot.lMotor.setPower(0.8);
                                robot.rMotor.setPower(0.8);
                                robot.lMotor.setTargetPosition(4850);
                                robot.rMotor.setTargetPosition(4850);
                                lift.lift.setPower(0.8);
                                lift.lift.setTargetPosition(4200);

                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(1000);
                                    resetMotors();
                                    resetRuntime();
                                    step = 3;
                                }
                                break;
                            case 3:
                                robot.lMotor.setPower(0.8);
                                robot.rMotor.setPower(0.8);
                                robot.lMotor.setTargetPosition(525);
                                robot.rMotor.setTargetPosition(-510);
                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(1000);
                                    resetMotors();
                                    resetRuntime();
                                    step = 4;
                                }
                                break;
                            case 4:
                                robot.lMotor.setPower(0.2);
                                robot.rMotor.setPower(0.2);
                                robot.lMotor.setTargetPosition(410);
                                robot.rMotor.setTargetPosition(410);
                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(700);
                                    resetMotors();
                                    resetRuntime();
                                    step = 7;
                                }
                                break;
                            case 5:
                                sleep(0);
                                step = 6;
                                break;
                            case 6:
                                robot.lMotor.setPower(0.2);
                                robot.rMotor.setPower(0.2);
                                robot.lMotor.setTargetPosition(50);
                                robot.rMotor.setTargetPosition(50);
                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(700);
                                    resetMotors();
                                    resetRuntime();
                                    step = 7;
                                }
                                break;
                            case 7:
                                robot.claw.setPosition(1);
                                sleep(500);
                                step = 8;
                                break;
                            case 8:
                                robot.lMotor.setPower(0.2);
                                robot.rMotor.setPower(0.2);
                                robot.lMotor.setTargetPosition(-50);
                                robot.rMotor.setTargetPosition(-50);
                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    resetMotors();
                                    resetRuntime();
                                    step = 9;
                                }
                                break;
                            case 9:
                                robot.lMotor.setPower(0.2);
                                robot.rMotor.setPower(0.2);
                                robot.lMotor.setTargetPosition(-300);
                                robot.rMotor.setTargetPosition(-350);
                                lift.lift.setTargetPosition(400);
                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(1000);
                                    resetMotors();
                                    resetRuntime();
                                    step = 10;
                                }
                                break;
                            case 10:
                                robot.lMotor.setPower(0.8);
                                robot.rMotor.setPower(0.8);
                                robot.lMotor.setTargetPosition(-520);
                                robot.rMotor.setTargetPosition(520);
                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(750);
                                    resetMotors();
                                    resetRuntime();
                                    step = 11;
                                }
                                break;

                            case 11:
                                robot.lMotor.setPower(1);
                                robot.rMotor.setPower(1);
                                robot.lMotor.setTargetPosition(-220);
                                robot.rMotor.setTargetPosition(-220);

                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(1100);
                                    resetMotors();
                                    resetRuntime();
                                    step = 1;
                                    state = 2;
                                }
                                break;
                                /*
                            case 12:
                                robot.cMotor.setPower(0.8);
                                robot.cMotor.setTargetPosition(2250);
                                if (absDiff(robot.cMotor.getCurrentPosition(),robot.cMotor.getTargetPosition())<leniency) {
                                    sleep(1000);
                                    resetMotors();
                                    resetRuntime();
                                    step = 13;
                                }
                                break;
                            case 13:
                                robot.lMotor.setPower(1);
                                robot.rMotor.setPower(1);
                                robot.lMotor.setTargetPosition(1700);
                                robot.rMotor.setTargetPosition(1700);

                                if (absDiff(robot.lMotor.getCurrentPosition(),robot.lMotor.getTargetPosition())<leniency && absDiff(robot.rMotor.getCurrentPosition(),robot.rMotor.getTargetPosition())<leniency){
                                    sleep(2000);
                                    resetMotors();
                                    resetRuntime();
                                    step = 1;
                                    state = 2;
                                }
                                break;

                                 */
                        }
                    break;

                    case 2:
                        switch (color) {
                            case 1:
                                break;
                            case 2:
                                robot.cMotor.setPower(.5);
                                robot.cMotor.setTargetPosition(2200);
                                break;
                            case 3:
                                robot.cMotor.setPower(.5);
                                robot.cMotor.setTargetPosition(4850);
                                break;
                        }
                        break;
                }
                telemetry.update();
            }
        }
    }
}