package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Jacob on 2/24/16.
 */
public class EncoderAutoDriver extends AutoDriver {

    public EncoderAutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain) {
        super(linearOpMode, driveTrain);
    }

    @Override
    public AutoDriver drive_forward_implementation(int encoderCount) {

        driveTrain.setPowers(power, power);
        while(driveTrain.getLeftEncoderCount() < encoderCount &&
               !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e) { }
        }
        return this;
    }

    @Override
    public AutoDriver drive_backward_implementation(int encoderCount) {
        driveTrain.setPowers(-power, -power);
        while(driveTrain.getLeftEncoderCount() > encoderCount &&
               !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e) { }
        }
        return this;
    }

    @Override
    public AutoDriver turn_clockwise_implementation(int encoderCount) {
        driveTrain.setPowers(power, -power);
        while(driveTrain.getLeftEncoderCount() < encoderCount &&
              !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e) { }
        }
        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise_implementation(int encoderCount) {
        driveTrain.setPowers(-power, power);
        while(driveTrain.getRightEncoderCount() < encoderCount &&
              !eStop && opMode.opModeIsActive()) {
            try {
                opMode.waitOneFullHardwareCycle();
            } catch (InterruptedException e) { }
        }
        return this;
    }
}

