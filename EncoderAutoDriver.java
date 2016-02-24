package com.FTC3486.FTCRC_Extensions;

/**
 * Created by developer on 2/24/16.
 */
public class EncoderAutoDriver implements AutoDriver {
    @Override
    public AutoDriver set_power(double power) {
        return null;
    }

    @Override
    public AutoDriver drive_forward(int inches) {
        return null;
    }

    @Override
    public AutoDriver drive_backward(int inches) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(power, power);
        while (driveTrain.getLeftEncoderCount() > encoderCount && driveTrain.getRightEncoderCount()
                > encoderCount && opMode.opModeIsActive()) {}
        driveTrain.haltDrive();
        return null;
    }

    @Override
    public AutoDriver turn_clockwise(int degrees) {
        return null;
    }

    @Override
    public AutoDriver turn_counterclockwise(int degrees) {
        return null;
    }
}
