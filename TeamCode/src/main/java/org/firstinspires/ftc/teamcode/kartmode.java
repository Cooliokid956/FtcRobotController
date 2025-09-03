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

    private Gamepad.RumbleEffect generate_rumble(double intensity, int duration) {
        Gamepad.RumbleEffect.Builder builder = new Gamepad.RumbleEffect.Builder();
        builder.addStep(intensity, intensity, duration);
        return builder.build();
    }
    private Gamepad.RumbleEffect generate_rumble(double intensity) {
        Gamepad.RumbleEffect.Builder builder = new Gamepad.RumbleEffect.Builder();
        builder.addStep(intensity, intensity, 1000);
        return builder.build();
    }
    Gamepad.RumbleEffect
            LOW = generate_rumble(.2),
            MED = generate_rumble(.5),
            MAX = generate_rumble(1);

    private Gamepad.LedEffect generate_blinker
            (double r, double g, double b, double dim, int length, double duty) {
        Gamepad.LedEffect.Builder builder = new Gamepad.LedEffect.Builder();
        builder.setRepeating(true);
        builder.addStep(r, g, b, (int)(length * duty));
        builder.addStep(r * dim, g * dim, b * dim, (int)(length * (1 - duty)));
        return builder.build();
    }
    Gamepad.LedEffect
            LOW_BOOST = generate_blinker(0, 1, .8, .6, 300, 0.5),
            MED_BOOST = generate_blinker(1, .5, 0, .6, 300, 0.5),
            MAX_BOOST = generate_blinker(1, 0, .8, .6, 300, 0.5),
            LIGHT_OFF = generate_blinker(0, 0, 0, 0, 0, 0);

    Gamepad.LedEffect currEffect;
    void set_led_effect(Gamepad gp, Gamepad.LedEffect effect) {
        if (currEffect != effect) {
            gp.runLedEffect(effect);
            currEffect = effect;
        }
    }

    float power;
    float accel;
    float rotOffsetDrift;
    float rotOffsetTurn;
    float rotOffset;
    float rot;

    int drift;
    float driftMag;
    int LOW_BOOST_TIME = 4;
    int MED_BOOST_TIME = 12;
    int MAX_BOOST_TIME = 24;

    @Override
    public void init() {
        drive.config.init(hardwareMap);
    }

    @Override
    public void start() { fakePad.back = true; } // enable turbo

    @Override
    public void loop() {
        boolean lTrig = gamepad1. left_trigger > .9f;
        boolean rTrig = gamepad1.right_trigger > .9f;
        if (rTrig) accel += .014f;
        if (lTrig) accel -= .01f;

        accel *= .7f;
        accel = MathUtils.clamp(accel, -.2f, .33f);

        power += accel;
        power *= .95f;

        rot = gamepad1.left_stick_x/2 + rotOffsetDrift/2;

        if ((gamepad1.left_bumper || gamepad1.right_bumper) && drift == 0) {
            gamepad1.runRumbleEffect(MED);
            if (power > .4f) {
                if (gamepad1.left_stick_x > .5f) drift = 1;
                if (gamepad1.left_stick_x <-.5f) drift =-1;
                if (drift != 0) resetRuntime();
            }
        } else if (drift == 2) {
            power = driftMag;
            if (!rTrig) drift = 0;
            if (getRuntime() > 2) drift = 0;

        } else if (drift != 0) {
            int timer = (int) Math.floor(getRuntime()*6);
            double r,g,b;
            if (!rTrig) drift = 0;
            rotOffsetDrift = drift == 1 ? -.5f : .5f;

            if (timer > MAX_BOOST_TIME) {
                set_led_effect(gamepad1, MAX_BOOST);
                gamepad1.runRumbleEffect(MAX);
                driftMag = 1;
            } else if (timer > MED_BOOST_TIME) {
                set_led_effect(gamepad1, MED_BOOST);
                gamepad1.runRumbleEffect(MED);
                driftMag = .8f;
            } else if (timer > LOW_BOOST_TIME) {
                set_led_effect(gamepad1, LOW_BOOST);
                gamepad1.runRumbleEffect(LOW);
                driftMag = .6f;
            } else {
                set_led_effect(gamepad1, LIGHT_OFF);
                driftMag = 0;
            }

            telemetry.addData("drift timer", timer/4);

            if (!(gamepad1.left_bumper || gamepad1.right_bumper)) {
                resetRuntime();
                drift = driftMag > 0 ? 2 : 0;
                if (drift == 2) gamepad1.runRumbleEffect(MAX);
                rotOffsetDrift = 0;
            }
        } else {
            rotOffsetDrift = 0;
            set_led_effect(gamepad1, LIGHT_OFF);
        }

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
    }
}
