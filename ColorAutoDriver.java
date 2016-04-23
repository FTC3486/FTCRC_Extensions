package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Jacob on 2/24/16.
 */
public class ColorAutoDriver extends AutoDriver {
    ColorSensor colorSensor;

    public ColorAutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain,
                             String colorSensor, HardwareMap hardwareMap) {
        super(linearOpMode, driveTrain);
        this.colorSensor = hardwareMap.colorSensor.get(colorSensor);

    }

    @Override
    public AutoDriver drive_forward_implementation(int lightValue) {
        driveTrain.setPowers(power, power);

       while (driveTrain.getLeftEncoderCount() < 400 &&
                !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e){ }
        }

        while (colorSensor.argb() <= lightValue &&
                !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e){ }
        }

        driveTrain.haltDrive();
        return this;
    }

    @Override
    public AutoDriver drive_backward_implementation(int lightValue) {
        driveTrain.setPowers(-power, -power);

        while (driveTrain.getLeftEncoderCount() > -400 &&
                !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e){ }
        }

        while (colorSensor.argb() <= lightValue &&
                !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e){ }
        }

        driveTrain.haltDrive();
        return this;
    }

    @Override
    public AutoDriver turn_clockwise_implementation(int encoderCount) {
        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise_implementation(int encoderCount) {
        return this;
    }
}