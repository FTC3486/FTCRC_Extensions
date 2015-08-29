package com.jacobamason.FTCRC_Extensions;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class pMotor
{
    final private OpMode opMode;
    protected final DriveTrain driveTrain;

    private double leftSpeed;
    private double rightSpeed;
    private long leftEncoderTarget;
    private long rightEncoderTarget;

    private int leftEncoderCountThreshold;
    private int rightEncoderCountThreshold;
    private long timeoutMillis;
    private int checkMotorsFrequency = 60;

    private class MotorStallException extends Exception
    {
        public MotorStallException(String errorMessage)
        {
            super(errorMessage);
        }
    }

    /**
     * .
     * <p/>
     * pMotor should be constructed at the top of the init() block. You
     * should send it 'this' as the first argument and then your DriveTrain
     * object as the second parameter.
     * <p/>
     * It is important that you add all your drive train motors so that
     * pMotor will be able to control them all. It is likewise necessary that
     * you have added at least one motor with an encoder to your DriveTrain.
     *
     * @param opMode     Use the 'this' keyword as the argument here.
     * @param driveTrain A previously constructed DriveTrain with encoders.
     * @see DriveTrain
     */
    public pMotor(OpMode opMode, DriveTrain driveTrain)
    {
        this.driveTrain = driveTrain;
        this.opMode = opMode;
    }

    @SuppressLint("Assert")
    public void setCheckMotorsFrequency(int frequency)
    {
        assert (frequency > 0);

        checkMotorsFrequency = frequency;
    }

    private boolean checkIsTargetReached()
    {
        boolean targetIsReached = false;

        for (DcMotor motor : driveTrain.getLeftMotorsWithEncoders())
        {
            if (Math.abs(leftEncoderTarget - motor.getCurrentPosition()) <
                    leftEncoderCountThreshold)
            {
                targetIsReached = true;
            }
        }

        for (DcMotor motor : driveTrain.getRightMotorsWithEncoders())
        {
            if (Math.abs(rightEncoderTarget - motor.getCurrentPosition()) <
                    rightEncoderCountThreshold)
            {
                targetIsReached = true;
            }
        }

        return targetIsReached;
    }

    private boolean checkForStall(int previousLeftCounts,
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
        int previousLeftCounts = -100;
        int previousRightCounts = -100;
        long previousTime = System.currentTimeMillis();
        long timeStalled = 0;

        while (checkIsTargetReached())
        {
            driveTrain.setPowers(leftSpeed, rightSpeed);

            long elapsedTime = System.currentTimeMillis() - previousTime;

            if (elapsedTime > checkMotorsFrequency)
            {
                if (checkForStall(previousLeftCounts, previousRightCounts))
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

    private void calculateThreshold(double speed)
    {
        // static settings for the Techno Warriors:
        // Left and Right must be at 0 rpm for 30 seconds, however, you can't
        // just pick '0' as the encoder count threshold because that will
        // probably never trigger. Instead, I pick something really small,
        // like 15 degrees of rotation on the drive shaft, which is
        // equivalent to about 50 encoder counts
        leftEncoderCountThreshold = 50;
        rightEncoderCountThreshold = 50;
        timeoutMillis = 30000;
    }

    /**
     * Moves the robot straight forward or backward.
     * <p/>
     * The distance argument will be in whichever units the wheelDiameter
     * parameter was in when you constructed your DriveTrain object.
     * The distance argument should be positive or negative depending on the
     * direction you want the robot to travel. A positive distance should
     * make the robot go forward.
     * <p/>
     * The speed argument is some speed fraction from 0 to 1.0 (anything
     * outside this range and I will turn this car around).
     * <p/>
     * If the distance target cannot be reached, a MotorStallException exception
     * is thrown.
     *
     * @param distance The distance in inches to move forward or backward.
     * @param speed    The speed at which to move.
     * @throws MotorStallException
     */
    @SuppressLint("Assert")
    public void linear(float distance, double speed) throws MotorStallException
    {
        assert ((0.0 < speed) && (speed <= 1.0));

        leftSpeed = Math.signum(distance) * speed;
        rightSpeed = Math.signum(distance) * speed;
        leftEncoderTarget = driveTrain.convertInchesToEncoderCounts(distance);
        rightEncoderTarget = driveTrain.convertInchesToEncoderCounts(distance);

        // For the Techno Warriors static settings, this is a bit redundant,
        // but I wanted to follow the intent of the function.
        calculateThreshold(leftSpeed);
        calculateThreshold(rightSpeed);

        pMotorRun();
    }

    /**
     * Spins the robot in place, performing a point-turn.
     * <p/>
     * The degrees argument should be positive or negative depending on the
     * direction you want the robot to turn. Positive degrees turn the robot
     * counter-clockwise (left).
     * <p/>
     * The speed argument is some speed fraction from 0 to 1.0 (anything
     * outside this range and I will turn this car around).
     * <p/>
     * If the distance target cannot be reached, a MotorStallException exception
     * is thrown.
     *
     * @param degrees The number of degrees to turn.
     * @param speed   The speed at which to move.
     * @throws MotorStallException
     */
    @SuppressLint("Assert")
    public void pointTurn(float degrees, double speed)
            throws MotorStallException
    {
        assert ((0.0 < speed) && (speed <= 1.0));

        leftSpeed = Math.signum(degrees) * speed;
        rightSpeed = Math.signum(degrees) * -speed;

        // TODO: Convert degrees to encoder counts.
        leftEncoderTarget = 0;
        rightEncoderTarget = 0;

        pMotorRun();
    }
}
