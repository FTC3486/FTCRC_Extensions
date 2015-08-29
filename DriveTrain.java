package com.jacobamason.FTCRC_Extensions;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.LinkedList;

public class DriveTrain
{
    private double wheelDiameter = 4.0;
    private double gearRatio = 1.0;
    // 1120 is the number for the AndyMark motors. Tetrix Motors are 1440 PPR
    private final int encoderCountsPerDriverGearRotation = 1120;
    private final LinkedList<DcMotor> leftMotors = new LinkedList<DcMotor>();
    private LinkedList<DcMotor> rightMotors = new LinkedList<DcMotor>();
    private final LinkedList<DcMotor> leftMotorsWithEncoders =
            new LinkedList<DcMotor>();
    private final LinkedList<DcMotor> rightMotorsWithEncoders =
            new LinkedList<DcMotor>();

    public DriveTrain()
    {
    }

    public LinkedList<DcMotor> getLeftMotorsWithEncoders()
    {
        return leftMotorsWithEncoders;
    }

    public LinkedList<DcMotor> getRightMotorsWithEncoders()
    {
        return rightMotorsWithEncoders;
    }

    public void setWheelDiameter(double wheelDiameter)
    {
        this.wheelDiameter = wheelDiameter;
    }

    public void setGearRatio(double gearRatio)
    {
        this.gearRatio = gearRatio;
    }

    public void addLeftMotor(DcMotor leftMotor)
    {
        leftMotors.add(leftMotor);
    }

    public void addLeftMotorWithEncoder(DcMotor leftMotor)
    {
        leftMotorsWithEncoders.add(leftMotor);
    }

    public void addRightMotor(DcMotor rightMotor)
    {
        rightMotors.add(rightMotor);
    }

    public void addRightMotorWithEncoder(DcMotor rightMotor)
    {
        rightMotorsWithEncoders.add(rightMotor);
    }// function used to stop the drive train for the next movement.

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
}