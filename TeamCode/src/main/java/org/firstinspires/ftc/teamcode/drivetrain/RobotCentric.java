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
        while(opModeIsActive()) {
            robot.drive(false);
            //code to move mechanisms
        }
    }
}