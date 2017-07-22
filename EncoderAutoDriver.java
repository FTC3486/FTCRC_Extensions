package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

/**
 * Filename: EncoderAutoDriver.java
 *
 * Description:
 *     This class contains the methods that use the encoders for predefined autonomous movements.
 *
 * Use:
 *     An encoder auto driver is created in a hardware configuration and accessed
 * in an autonomous program for use. Distances in inches.
 *
 * Example: robot.hardwareConfiguration.encoderAutoDriver.driveLeftSideToDistance()
 *
 * Requirements:
 *     -Drive motors with encoders
 *     -A universe
 *
 * Changelog:
 *     -Edited and tested by Team 3486 on 7/8/2017.
 *     -Edited file description and documentation 7/22/17
 */

public class EncoderAutoDriver extends AutoDriver
{
    public EncoderAutoDriver(HardwareConfiguration hw)
    {
        super(hw);
    }

    public void driveLeftSideToDistance(double distance)
    {
        setupMotion("Driving to set distance.");
        
        hw.drivetrain.setPowers(0.3, 0.0);
        while(hw.drivetrain.getLeftEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance)
                && hw.opMode.opModeIsActive())
        {}
        hw.drivetrain.haltDrive();

        endMotion();
    }

    public void driveRightSideToDistance(double distance)
    {
        setupMotion("Driving to set distance.");

        hw.drivetrain.setPowers(0.0, 0.3);
        while(hw.drivetrain.getRightEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance)
                && hw.opMode.opModeIsActive())
        {}
        hw.drivetrain.haltDrive();

        endMotion();
    }

    public void driveToDistance(double distance)
    {
        setupMotion("Driving to set distance.");

        hw.drivetrain.setPowers(0.3, 0.3);
        while(hw.drivetrain.getLeftEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance)
                && hw.drivetrain.getRightEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance)
                && hw.opMode.opModeIsActive()) {}

        endMotion();
    }
}

