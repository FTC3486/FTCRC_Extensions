package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.LinkedList;

public class DriveTrain
{
    private ElapsedTime timer = new ElapsedTime(2) ;

    private double wheelDiameter;
    private double gearRatio;
    private int encoderCountsPerDriverGearRotation;
    private LinkedList<DcMotor> leftMotors;
    private LinkedList<DcMotor> rightMotors;
    private LinkedList<ExtendedDcMotor> leftMotorsWithEncoders;
    private LinkedList<ExtendedDcMotor> rightMotorsWithEncoders;
    private double leftSpeed;
    private double rightSpeed;

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
        private final LinkedList<ExtendedDcMotor> leftMotorsWithEncoders =
                new LinkedList<>();
        private final LinkedList<ExtendedDcMotor> rightMotorsWithEncoders =
                new LinkedList<>();

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

        public Builder addLeftMotorWithEncoder(ExtendedDcMotor leftMotor)
        {
            leftMotorsWithEncoders.add(leftMotor);
            return this;
        }

        public Builder addRightMotor(DcMotor rightMotor)
        {
            rightMotors.add(rightMotor);
            return this;
        }

        public Builder addRightMotorWithEncoder(ExtendedDcMotor rightMotor)
        {
            rightMotorsWithEncoders.add(rightMotor);
            return this;
        }

        public DriveTrain build()
        {
            return new DriveTrain(this);
        }
    }

    protected LinkedList<ExtendedDcMotor> getLeftMotorsWithEncoders()
    {
        return leftMotorsWithEncoders;
    }

    protected LinkedList<ExtendedDcMotor> getRightMotorsWithEncoders()
    {
        return rightMotorsWithEncoders;
    }

    protected void haltDrive()
    {
        for (ExtendedDcMotor motor : leftMotorsWithEncoders)
        {
            motor.setPower(0);
        }

        for (ExtendedDcMotor motor : rightMotorsWithEncoders)
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

    public double getLeftEncoderCount() {
        double sumValue = 0;
        double encoderAverage = 0;

        for(ExtendedDcMotor leftMotorWithEncoder: leftMotorsWithEncoders) {
            sumValue += leftMotorWithEncoder.getCurrentPosition();
        }

        sumValue = sumValue / (leftMotorsWithEncoders.size());
        return  sumValue;
    }

    public double getRightEncoderCount() {
        double sumValue = 0;

        for(ExtendedDcMotor rightMotorWithEncoder: rightMotorsWithEncoders) {
            sumValue += rightMotorWithEncoder.getCurrentPosition();
        }

        sumValue = sumValue / leftMotorsWithEncoders.size();
        return  sumValue;
    }

    public void resetMotorEncoders() {
        for(ExtendedDcMotor leftMotorWithEncoders: leftMotorsWithEncoders) {
            leftMotorWithEncoders.changeMode("RESET_ENCODERS");
            leftMotorWithEncoders.changeMode("RUN_USING_ENCODERS");
        }

        for(ExtendedDcMotor rightMotorWithEncoders: rightMotorsWithEncoders) {
            rightMotorWithEncoders.changeMode("RESET_ENCODERS");
            rightMotorWithEncoders.changeMode("RUN_USING_ENCODERS");
        }
    }

    @Override
    public String toString() {
        return "left pwr: " + String.format("%.2f", leftSpeed) +
                "\nright pwr: " + String.format("%.2f", rightSpeed);
    }
}