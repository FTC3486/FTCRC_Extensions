package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

/**
 * Created by Jacob on 2/24/16.
 */
public class EncoderAutoDriver extends AutoDriver
{
    public EncoderAutoDriver(HardwareConfiguration hw)
    {
        super(hw);
    }

    public void driveForwardToEncoderCount(int encoderCount)
    {
        setupMotion("Driving forward to encoder count.");
        hw.drivetrain.setPowers(power, power);
        while(hw.drivetrain.getLeftEncoderCount() < encoderCount &&
               !eStop && hw.opMode.opModeIsActive()) {}
        endMotion();
    }

    public void driveBackwardToEncoderCount(int encoderCount)
    {
        setupMotion("Driving backwards to encoder count.");
        hw.drivetrain.setPowers(-power, -power);
        while(hw.drivetrain.getLeftEncoderCount() > encoderCount &&
               !eStop && hw.opMode.opModeIsActive()) {}
        endMotion();
    }

    public void turnClockwiseToEncoderCount(int encoderCount)
    {
        setupMotion("Turning clockwise to encoder count.");
        hw.drivetrain.setPowers(power, -power);
        while(hw.drivetrain.getLeftEncoderCount() < encoderCount &&
              !eStop && hw.opMode.opModeIsActive()) {}
        endMotion();
    }

    public void turnCounterclockwiseToEncoderCount(int encoderCount)
    {
        setupMotion("Turning counterclockwise to encoder count.");
        hw.drivetrain.setPowers(-power, power);
        while(hw.drivetrain.getRightEncoderCount() < encoderCount &&
              !eStop && hw.opMode.opModeIsActive()) {}
        endMotion();
    }
}

