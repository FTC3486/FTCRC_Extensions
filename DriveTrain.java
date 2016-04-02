package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.LinkedList;

public class DriveTrain
{
    private double wheelDiameter;
    private double gearRatio;
    private int encoderCountsPerDriverGearRotation;
    private LinkedList<DcMotor> leftMotors;
    private LinkedList<DcMotor> rightMotors;
    private LinkedList<DcMotor> leftMotorsWithEncoders;
    private LinkedList<DcMotor> rightMotorsWithEncoders;
    private double leftSpeed;
    private double rightSpeed;
    private int leftEncoderCountThreshold;
    private int rightEncoderCountThreshold;
    protected long timeoutMillis;
    protected int checkMotorsFrequency = 60;
    private double leftPower;
    private double rightPower;

    private DriveTrain(Builder builder)
    {
        this.wheelDiameter = builder.wheelDiameter;
        this.gearRatio = builder.gearRatio;
        this.encoderCountsPerDriverGearRotation = builder.encoderCountsPerDriverGearRotation;
        this.leftMotors = builder.leftMotors;
        this.rightMotors = builder.rightMotors;
        this.leftMotorsWithEncoders = builder.leftMotorsWithEncoders;
        this.rightMotorsWithEncoders = builder.rightMotorsWithEncoders;
    }

    public static class Builder
    {
        private double wheelDiameter = 4.0;
        private double gearRatio = 1.0;
        // 1120 is the number for the AndyMark motors. Tetrix Motors are 1440 PPR
        private int encoderCountsPerDriverGearRotation = 1120;
        private final LinkedList<DcMotor> leftMotors = new LinkedList<DcMotor>();
        private final LinkedList<DcMotor> rightMotors = new LinkedList<DcMotor>();
        private final LinkedList<DcMotor> leftMotorsWithEncoders =
                new LinkedList<DcMotor>();
        private final LinkedList<DcMotor> rightMotorsWithEncoders =
                new LinkedList<DcMotor>();

        public Builder setWheelDiameter(double wheelDiameter)
        {
            this.wheelDiameter = wheelDiameter;
            return this;
        }

        public Builder setGearRatio(double gearRatio)
        {
            this.gearRatio = gearRatio;
            return this;
        }

        public Builder setEncoderCountsPerDriverGearRotation(int encoderCountsPerDriverGearRotation)
        {
            this.encoderCountsPerDriverGearRotation = encoderCountsPerDriverGearRotation;
            return this;
        }

        public Builder addLeftMotor(DcMotor leftMotor)
        {
            leftMotors.add(leftMotor);
            return this;
        }

        public Builder addLeftMotorWithEncoder(DcMotor leftMotor)
        {
            leftMotorsWithEncoders.add(leftMotor);
            return this;
        }

        public Builder addRightMotor(DcMotor rightMotor)
        {
            rightMotors.add(rightMotor);
            return this;
        }

        public Builder addRightMotorWithEncoder(DcMotor rightMotor)
        {
            rightMotorsWithEncoders.add(rightMotor);
            return this;
        }

        public DriveTrain build()
        {
            return new DriveTrain(this);
        }
    }

    protected LinkedList<DcMotor> getLeftMotorsWithEncoders()
    {
        return leftMotorsWithEncoders;
    }

    protected LinkedList<DcMotor> getRightMotorsWithEncoders()
    {
        return rightMotorsWithEncoders;
    }

    protected void haltDrive()
    {
        for (DcMotor motor : leftMotorsWithEncoders)
        {
            motor.setPower(0);
        }

        for (DcMotor motor : rightMotorsWithEncoders)
        {
            motor.setPower(0);
        }

        for (DcMotor motor : leftMotors)
        {
            motor.setPower(0);
        }

        for (DcMotor motor : rightMotors)
        {
            motor.setPower(0);
        }
    }

    protected void setPowers(double leftSpeed, double rightSpeed)
    {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;

        for (DcMotor motor : leftMotorsWithEncoders)
        {
            motor.setPower(leftSpeed);
        }

        for (DcMotor motor : rightMotorsWithEncoders)
        {
            motor.setPower(rightSpeed);
        }

        for (DcMotor motor : leftMotors)
        {
            motor.setPower(leftSpeed);
        }

        for (DcMotor motor : rightMotors)
        {
            motor.setPower(rightSpeed);
        }
    }

    protected long convertInchesToEncoderCounts(float distance)
    {
        return Math.round(((distance / (Math.PI * wheelDiameter)) * gearRatio) /
                                  encoderCountsPerDriverGearRotation);
    }

    protected double getLeftEncoderCount() {
        double sumValue = 0;

        for(DcMotor leftMotorWithEncoder: leftMotorsWithEncoders) {
            sumValue += leftMotorWithEncoder.getCurrentPosition();
        }

        sumValue = sumValue / (leftMotorsWithEncoders.size());
        return sumValue;
    }

    protected double getRightEncoderCount() {
        double sumValue = 0;

        for(DcMotor rightMotorWithEncoder: rightMotorsWithEncoders) {
            sumValue += rightMotorWithEncoder.getCurrentPosition();
        }

        sumValue = sumValue / leftMotorsWithEncoders.size();
        return sumValue;
    }

    protected void resetMotorEncoders() {
        for(DcMotor leftMotorWithEncoders: leftMotorsWithEncoders) {
            leftMotorWithEncoders.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            leftMotorWithEncoders.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }

        for(DcMotor rightMotorWithEncoders: rightMotorsWithEncoders) {
            rightMotorWithEncoders.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightMotorWithEncoders.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    }

    protected void setCheckMotorsFrequency(int frequency)
    {
        assert (frequency > 0);

        checkMotorsFrequency = frequency;
    }

    protected boolean checkIsTargetReached(long leftEncoderTarget, long rightEncoderTarget)
    {
        boolean targetIsReached = false;

        for (DcMotor motor : leftMotorsWithEncoders)
        {
            if (Math.abs(leftEncoderTarget - motor.getCurrentPosition()) <
                    leftEncoderCountThreshold)
            {
                targetIsReached = true;
            }
        }

        for (DcMotor motor : rightMotorsWithEncoders)
        {
            if (Math.abs(rightEncoderTarget - motor.getCurrentPosition()) <
                    rightEncoderCountThreshold)
            {
                targetIsReached = true;
            }
        }

        return targetIsReached;
    }

    protected boolean checkForStall(int previousLeftCounts,
                                  int previousRightCounts)
    {
        boolean isStallDetected = false;

        for (DcMotor motor : leftMotorsWithEncoders)
        {
            if (Math.abs(previousLeftCounts - motor.getCurrentPosition()) <=
                    leftEncoderCountThreshold)
            {
                isStallDetected = true;
            }
        }

        for (DcMotor motor : rightMotorsWithEncoders)
        {
            if (Math.abs(previousRightCounts - motor.getCurrentPosition()) <=
                    rightEncoderCountThreshold)
            {
                isStallDetected = true;
            }
        }

        return isStallDetected;
    }

    protected void calculateThreshold(double power)
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

    protected class MotorStallException extends Exception
    {
        public MotorStallException(String errorMessage)
        {
            super(errorMessage);
        }
    }

    protected void pMotorRun(long leftEncoderTarget, long rightEncoderTarget, double leftPower,
                             double rightPower) throws MotorStallException
    {
        int previousLeftCounts = -100;
        int previousRightCounts = -100;
        long previousTime = System.currentTimeMillis();
        long timeStalled = 0;

        while (this.checkIsTargetReached(leftEncoderTarget, rightEncoderTarget)) {
            this.setPowers(leftPower, rightPower);

            long elapsedTime = System.currentTimeMillis() - previousTime;

            if (elapsedTime > this.checkMotorsFrequency) {
                if (this.checkForStall(previousLeftCounts, previousRightCounts))
                {
                    timeStalled += elapsedTime;
                }
                else
                {
                    timeStalled = 0;
                }

                if (timeStalled >= this.timeoutMillis)
                {
                    throw new MotorStallException(String.format(
                            "A motor has remained under the threshold for "+ timeStalled + " ms."));
                }
                previousTime = System.currentTimeMillis();
            }

        }

        this.haltDrive();
    }

    @Override
    public String toString() {
        return "left pwr: " + String.format("%.2f", leftSpeed) +
                "\nright pwr: " + String.format("%.2f", rightSpeed);
    }
}