package org.firstinspires.ftc.teamcode.drivetrain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Robot;
import com.qualcomm.robotcore.hardware.Gamepad;
@TeleOp(name = "RobotCentric", group = "OpModes")
public class RobotCentric extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotorEx flm;
    DcMotorEx frm;
    DcMotorEx blm;
    DcMotorEx brm;
    double lastTime = 0;
    boolean lBumper = false;
    int bumperPressCount = 0;
    boolean usingTrigger = true;


    public void runOpMode() {
        Robot robot = new Robot();
        robot.init(hardwareMap, this);
        flm = hardwareMap.get(DcMotorEx.class, "fl");
        frm = hardwareMap.get(DcMotorEx.class, "fr");
        blm = hardwareMap.get(DcMotorEx.class, "bl");
        brm = hardwareMap.get(DcMotorEx.class, "br");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();
        telemetry.speak("six and seven");
        while(opModeIsActive()) {
            robot.drive(false);
//            if(usingTrigger && gamepad1.left_trigger > 0) {
//                robot.controlFlywheels(gamepad1.left_trigger, gamepad1.left_trigger);
//            } else if (gamepad1.left_bumper && !lBumper) {
//                usingTrigger = false;
//                if (runtime.seconds() - lastTime <= 0.35) {
//                    bumperPressCount++;
//                    if (bumperPressCount == 3) {
//                        robot.controlFlywheels(-1, -1);
//                        bumperPressCount = 0;
//                        lastTime = -1.0;
//                    } else {
//                        lastTime = runtime.seconds();
//                        robot.controlFlywheels(0, 0);
//                    }
//                } else {
//                    bumperPressCount = 1;
//                    lastTime = runtime.seconds();
//                    robot.controlFlywheels(1, 1);
//                }
//            } else if(gamepad1.left_trigger != 0) {usingTrigger = true;}
//            lBumper = gamepad1.left_bumper;
//            telemetry.addData("Bumper Press Count: ", bumperPressCount);

            telemetry.update();
            //code to move mechanisms
        }
    }
}