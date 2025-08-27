package org.firstinspires.ftc.teamcode;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp
public class kartmode extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    Gamepad fakePad = new Gamepad();

    int LO  = 8;
    int MED = 11;
    int HI  = 15;
    int intensity;
    int count;
    void rumble_intensity(int i, int counts) {
        intensity = i;
        count = counts;
    }
    void update_rumble() {
        if (--count > 0) gamepad1.rumble(intensity);
    }

    float power;
    float accel;
    float rotOffsetDrift;
    float rotOffsetTurn;
    float rotOffset;
    float rot;

    int drift;
    float driftMag;
    int LO_BOOST = 1;
    int MID_BOOST = 3;
    int HI_BOOST = 6;

    @Override
    public void init() {
        drive.config.init(hardwareMap);
    }

    @Override
    public void start() {
        // enable turbo
        fakePad.back = true;
    }
    @Override
    public void loop() {
        boolean lTrig = gamepad1.left_trigger  > .9f;
        boolean rTrig = gamepad1.right_trigger > .9f;
        if (rTrig)  accel += .014f;
        if (lTrig)  accel -= .01f;

        accel *= .7f;
        accel = MathUtils.clamp(accel, -.2f, .33f);

        power += accel;
        power *= .95f;

        rot = gamepad1.left_stick_x/2 + rotOffsetDrift/2;

        if ((gamepad1.left_bumper || gamepad1.right_bumper) && drift == 0) {
            rumble_intensity(MED, 3);
            if (power > .4f) {
                if (gamepad1.left_stick_x > .5f) drift = 1;
                if (gamepad1.left_stick_x <-.5f) drift =-1;
                if (drift != 0) resetRuntime();
            }
        } else if (drift == 2) {
            power = driftMag;
            if (!rTrig) drift = 0;
            rumble_intensity(HI, 3);
            if (getRuntime() > 2) {
                drift = 0;
            }
        } else if (drift != 0) {
            int timer = (int) Math.floor(getRuntime()*6);
            double r,g,b;
            if (!rTrig) drift = 0;
            rotOffsetDrift = drift == 1 ? -.5f : .5f;

            if (timer > HI_BOOST*4) {
                r = 1; g = 0; b =.8f;
                rumble_intensity(HI, 3);
                driftMag = 1;
            } else if (timer > MID_BOOST*4) {
                r = 1; g = .5f; b = 0;
                rumble_intensity(MED, 3);
                driftMag = .8f;
            } else if (timer > LO_BOOST*4) {
                r = 0; g = 1; b =.8f;
                rumble_intensity(LO, 3);
                driftMag = .6f;
            } else {
                r = 0; g = 0; b = 0;
                driftMag = 0;
            }

            if ((timer % 4) > 2) {
                r *= .6; g *= .6; b *= .6;
            }
            gamepad1.setLedColor(r,g,b,1000);
            telemetry.addData("drift timer", timer/4);

            if (!(gamepad1.left_bumper || gamepad1.right_bumper)) {
                resetRuntime();
                drift = driftMag > 0 ? 2 : 0;
                rotOffsetDrift = 0;
            }
        } else rotOffsetDrift = 0;

        rotOffsetTurn = Math.abs(rot) > Math.abs(rotOffsetTurn) ? rot : rotOffsetTurn;
        rotOffsetTurn *= .3f;

        rotOffset = rotOffsetTurn + rotOffsetDrift;

        // manip gamepad
        fakePad.left_stick_y = (float) (power * Math.cos(rotOffset));
        fakePad.left_stick_x = (float) (power * Math.sin(rotOffset));
        fakePad.right_stick_x = (rot - rotOffset) * power;

        telemetry.addData("drifting", drift == 0 ? "No" : (drift == 1 ? "right" : "left"));
        telemetry.addData("power", power);
        telemetry.addData("accel", accel);
        telemetry.addData("rot", rot);
        telemetry.addData("rotOffset", rotOffset);
        telemetry.addLine("");
        telemetry.addLine("");
        drive.drive(fakePad, telemetry);

        update_rumble();
    }
}
