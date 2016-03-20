package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Matthew on 3/19/2016.
 */
public class ProtectedMotorAutoDriver extends AutoDriver {

    private long leftEncoderTarget;
    private long rightEncoderTarget;
    private double leftPower;
    private double rightPower;

    public ProtectedMotorAutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain) {
        super(linearOpMode, driveTrain);
    }


    @Override
    public AutoDriver drive_forward_implementation(int distance) {
        assert ((0.0 < power) && (power <= 1.0));

        leftPower = Math.signum(distance) * power;
        rightPower = Math.signum(distance) * power;
        leftEncoderTarget = driveTrain.convertInchesToEncoderCounts(distance);
        rightEncoderTarget = driveTrain.convertInchesToEncoderCounts(distance);

        // For the Techno Warriors static settings, this is a bit redundant,
        // but I wanted to follow the intent of the function.
        driveTrain.calculateThreshold(leftPower);
        driveTrain.calculateThreshold(rightPower);

        try {
            driveTrain.pMotorRun(leftEncoderTarget, rightEncoderTarget);
        } catch(DriveTrain.MotorStallException motorStallException) {
            opMode.telemetry.addData("ERROR:", "MOTOR STALL EXCEPTION");
        }

        return this;
    }

    @Override
    public AutoDriver drive_backward_implementation(int encoderCount) {

        return this;
    }

    @Override
    public AutoDriver turn_clockwise_implementation(int encoderCount) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(power, -power);
        while(driveTrain.getLeftEncoderCount() < encoderCount && opMode.opModeIsActive()) {}
        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise_implementation(int encoderCount) {

        return this;
    }
}
