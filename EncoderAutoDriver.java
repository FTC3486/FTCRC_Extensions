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

    public void driveLeftSideToDistance(double distance)
    {
        distance /= 2.54;
        setupMotion("Driving to set distance.");
        hw.drivetrain.setPowers(0.3, 0.0);
        while(hw.drivetrain.getLeftEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance) && hw.opMode.opModeIsActive())
        {}
        hw.drivetrain.haltDrive();

        endMotion();
    }

    public void driveRightSideToDistance(double distance)
    {
        distance /= 2.54;
        setupMotion("Driving to set distance.");
        hw.drivetrain.setPowers(0.0, 0.3);
        while(hw.drivetrain.getRightEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance) && hw.opMode.opModeIsActive())
        {}
        hw.drivetrain.haltDrive();

        endMotion();
    }

    public void driveToDistance(double distance)
    {
        distance /= 2.54;
        setupMotion("Driving backwards to encoder count.");
        hw.drivetrain.setPowers(0.3, 0.3);
        while(hw.drivetrain.getLeftEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance)
                && hw.drivetrain.getRightEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance)
                && !eStop && hw.opMode.opModeIsActive()) {}
        endMotion();
    }
}

