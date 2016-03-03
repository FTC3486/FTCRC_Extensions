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
    public AutoDriver drive_forward(int encoderCount) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(-power, -power);
        while (driveTrain.getLeftEncoderCount() > encoderCount &&
                driveTrain.getRightEncoderCount() > encoderCount &&
                opMode.opModeIsActive()) {}
        driveTrain.haltDrive();
        return null;
    }

    @Override
    public AutoDriver drive_backward(int encoderCount) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(-power, -power);
        while (driveTrain.getLeftEncoderCount() > encoderCount &&
               driveTrain.getRightEncoderCount() > encoderCount &&
               opMode.opModeIsActive()) {}
        driveTrain.haltDrive();
        return null;
    }

    @Override
    public AutoDriver turn_clockwise(int encoderCount) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(power, -power);
        while(driveTrain.getLeftEncoderCount() < encoderCount && opMode.opModeIsActive()) {
            opMode.telemetry.addData("LeftMotorEncoders:", this.driveTrain.getLeftEncoderCount());
        }
        driveTrain.haltDrive();
        return null;
    }

    @Override
    public AutoDriver turn_counterclockwise(int encoderCount) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(-power, power);
        while(driveTrain.getRightEncoderCount() > -encoderCount && opMode.opModeIsActive()) {
            opMode.telemetry.addData("RightMotorEncoders:", this.driveTrain.getRightEncoderCount());
        }
        driveTrain.haltDrive();
        return null;
    }
}

