package org.firstinspires.ftc.teamcode.old.y22powerplay;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ProgrammingBoard1 {
    private DcMotor motor;

    public void init(HardwareMap hwMap) {
        motor = hwMap.get(DcMotor.class, "delacruz");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setPower(double speed) {
        motor.setPower(speed);
    }
}
/*
Run behaviors:
Run with Encoder: Motor is ran in terms of ticks in a motor
Run without Encoder: Motor is ran in term of voltage
Run ot Position: Acts like a servo

End Behaviors:
Brake: Motor provides resistence to stop the motor from running
Float: Stops power so its slows to a stop
 */
