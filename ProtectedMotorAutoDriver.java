package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Matthew on 3/19/2016.
 */
public class ProtectedMotorAutoDriver extends AutoDriver {

    private double leftSpeed;
    private double rightSpeed;
    private long leftEncoderTarget;
    private long rightEncoderTarget;

    private int leftEncoderCountThreshold;
    private int rightEncoderCountThreshold;
    private long timeoutMillis;
    private final int checkMotorsFrequency = 60;

    private class MotorStallException extends Exception
    {
        public MotorStallException(String errorMessage)
        {
            super(errorMessage);
        }
    }

    public ProtectedMotorAutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain) {
        super(linearOpMode, driveTrain);
    }

    private boolean is_target_reached()
    {
        boolean targetIsReached = false;

        for (DcMotor motor : driveTrain.getLeftMotorsWithEncoders())
        {
            if (leftEncoderTarget < motor.getCurrentPosition())
            {
                targetIsReached = true;
            }
        }

        for (DcMotor motor : driveTrain.getRightMotorsWithEncoders())
        {
            if (rightEncoderTarget < motor.getCurrentPosition())
            {
                targetIsReached = true;
            }
        }

        return targetIsReached;
    }

    private boolean is_stall_detected(int previousLeftCounts,
                                      int previousRightCounts)
    {
        boolean isStallDetected = false;

        for (DcMotor motor : driveTrain.getLeftMotorsWithEncoders())
        {
            if (Math.abs(previousLeftCounts - motor.getCurrentPosition()) <=
                    leftEncoderCountThreshold)
            {
                isStallDetected = true;
            }
        }

        for (DcMotor motor : driveTrain.getRightMotorsWithEncoders())
        {
            if (Math.abs(previousRightCounts - motor.getCurrentPosition()) <=
                    rightEncoderCountThreshold)
            {
                isStallDetected = true;
            }
        }

        return isStallDetected;
    }

    private void pMotorRun() throws MotorStallException
    {
        int previousLeftCounts = 100;
        int previousRightCounts = 100;
        long previousTime = System.currentTimeMillis();
        long timeStalled = 0;
        driveTrain.setPowers(leftSpeed, rightSpeed);

        while (!is_target_reached())
        {
            long elapsedTime = System.currentTimeMillis() - previousTime;

            if (elapsedTime > checkMotorsFrequency)
            {
                if (is_stall_detected(previousLeftCounts, previousRightCounts))
                {
                    timeStalled += elapsedTime;
                }
                else
                {
                    timeStalled = 0;
                }

                if (timeStalled >= timeoutMillis)
                {
                    throw new MotorStallException(String.format(
                            "A motor has remained under the " +
                                    "threshold for %ld ms.", timeStalled));
                }
            }

            previousTime = System.currentTimeMillis();
        }

        driveTrain.haltDrive();
    }

    private int calculateThreshold(double speed)
    {
        timeoutMillis = 100;
        int threshold = (int) (50 * speed);
        return threshold;
    }


    @Override
    public AutoDriver drive_forward_implementation(int encoderCount) {
        leftSpeed = this.power;
        rightSpeed = this.power;
        leftEncoderTarget = encoderCount;
        rightEncoderTarget = encoderCount;

        leftEncoderCountThreshold = calculateThreshold(leftSpeed);
        rightEncoderCountThreshold = calculateThreshold(rightSpeed);

        try {
            pMotorRun();
        } catch (MotorStallException e) {
            opMode.telemetry.addData("pMotor", e.toString());
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
