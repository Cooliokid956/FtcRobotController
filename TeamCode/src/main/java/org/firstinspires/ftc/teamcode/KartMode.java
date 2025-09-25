package org.firstinspires.ftc.teamcode;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.lib.Manual;

@TeleOp
public class KartMode extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    Manual manual = new Manual(
            "Welcome to Kart Mode!",
            "Turn: Left Stick",
            "Accelerate: Right Trigger",
            "Reverse: Left Trigger",
            "Drift: Right Bumper"
    );

    Gamepad fakePad = new Gamepad(); // to puppeteer drive

    int
    LOW_BOOST_TIME = 4,
    MED_BOOST_TIME = 12,
    MAX_BOOST_TIME = 24;

    double // rumble helpers
    RUMBLE_INT_NIL = 0,
    RUMBLE_INT_LOW = .05,
    RUMBLE_INT_MED = .2,
    RUMBLE_INT_MAX = 1;

    private Gamepad.RumbleEffect generate_rumble(double intensity, int duration) {
        return new Gamepad.RumbleEffect.Builder()
        .addStep(intensity, intensity, duration)
        .build();
    }
    private Gamepad.RumbleEffect generate_rumble(double intensity) {
        return new Gamepad.RumbleEffect.Builder()
        .addStep(intensity, intensity, 1000)
        .build();
    }

    private Gamepad.RumbleEffect generate_drift_rumble_sequence() {
        return new Gamepad.RumbleEffect.Builder()
        .addStep(RUMBLE_INT_NIL, RUMBLE_INT_NIL, ((LOW_BOOST_TIME               )*1000)/6)
        .addStep(RUMBLE_INT_LOW, RUMBLE_INT_LOW, ((MED_BOOST_TIME-LOW_BOOST_TIME)*1000)/6)
        .addStep(RUMBLE_INT_MED, RUMBLE_INT_MED, ((MAX_BOOST_TIME-MED_BOOST_TIME)*1000)/6)
        .addStep(RUMBLE_INT_MAX, RUMBLE_INT_MAX, 100000)
        .build();
    }

    Gamepad.RumbleEffect
            RUMBLE_HOP = generate_rumble(1, 100),
            RUMBLE_BOOST_SEQ = generate_drift_rumble_sequence(),
            RUMBLE_LOW = generate_rumble(RUMBLE_INT_LOW),
            RUMBLE_MED = generate_rumble(RUMBLE_INT_MED),
            RUMBLE_MAX = generate_rumble(RUMBLE_INT_MAX);

    // LED helpers
    private Gamepad.LedEffect generate_blinker
            (double r, double g, double b, double dim, int length, double duty) {
        return new Gamepad.LedEffect.Builder()
        .setRepeating(true)
        .addStep(r, g, b, (int)(length * duty))
        .addStep(r * dim, g * dim, b * dim, (int)(length * (1 - duty)))
        .build();
    }

    Gamepad.LedEffect
            LOW_BOOST = generate_blinker(0, 1, .8, .6, 300, 0.5),
            MED_BOOST = generate_blinker(1, .5, 0, .6, 300, 0.5),
            MAX_BOOST = generate_blinker(1, 0, .8, .6, 300, 0.5),
            LIGHT_OFF = generate_blinker(0, 0, 0, 0, 10, 0);

    Gamepad.LedEffect currEffect;
    void set_led_effect(Gamepad gp, Gamepad.LedEffect effect) {
        if (currEffect != effect) {
            gp.runLedEffect(effect);
            currEffect = effect;
        }
    }

    float power, accel, rotOffsetDrift, rotOffsetTurn, rotOffset, rot;

    int drift; float driftMag;

    @Override
    public void init() { drive.config.init(hardwareMap); }
    @Override
    public void init_loop() { manual.print(telemetry); }

    @Override
    public void start() { fakePad.back = true; } // enable turbo

    @Override
    public void loop() {
        boolean lTrig = gamepad1. left_trigger > .9f;
        boolean rTrig = gamepad1.right_trigger > .9f && !lTrig;
        if (rTrig) accel += .014f;
        if (lTrig) accel -= .010f;

        accel *= .7f;
        accel = MathUtils.clamp(accel, -.2f, .33f);

        power += accel;
        power *= .95f;

        rot = gamepad1.left_stick_x/2 + rotOffsetDrift/2;
        rot *= drift != 0 ? 0.2f : 1;

        if ((gamepad1.leftBumperWasPressed() || gamepad1.rightBumperWasPressed()) && drift == 0) {
            if (power > .4f) {
                if (gamepad1.left_stick_x > .5f) drift = 1;
                if (gamepad1.left_stick_x <-.5f) drift =-1;
                if (drift != 0) {
                    gamepad1.runRumbleEffect(RUMBLE_BOOST_SEQ);
                    resetRuntime();
                } else gamepad1.runRumbleEffect(RUMBLE_HOP);
            }
        } else if (drift == 2) {
            power = driftMag;
            if (!rTrig || getRuntime() > 2)
                drift = 0;
        } else if (drift != 0) {
            int timer = (int) Math.floor(getRuntime()*6);
            if (!rTrig) drift = 0;
            rotOffsetDrift = drift == 1 ? -.5f : .5f;

            if (timer > MAX_BOOST_TIME) {
                set_led_effect(gamepad1, MAX_BOOST);
                driftMag = 1.f;
            } else if (timer > MED_BOOST_TIME) {
                set_led_effect(gamepad1, MED_BOOST);
                driftMag = .8f;
            } else if (timer > LOW_BOOST_TIME) {
                set_led_effect(gamepad1, LOW_BOOST);
                driftMag = .6f;
            } else {
                set_led_effect(gamepad1, LIGHT_OFF);
                driftMag = .0f;
            }

            telemetry.addData("drift timer", timer);

            if (!(gamepad1.left_bumper || gamepad1.right_bumper)) {
                resetRuntime();
                drift = driftMag > 0 ? 2 : 0;
                if (drift == 2) gamepad1.runRumbleEffect(RUMBLE_MAX);
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
        telemetry.addLine();
        telemetry.addLine();

        drive.drive(fakePad, telemetry);
    }
}
