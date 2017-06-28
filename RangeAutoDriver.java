package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

/**
 * Created by Matthew on 3/8/2017.
 */

public class RangeAutoDriver extends AutoDriver
{
    public RangeAutoDriver(HardwareConfiguration hw)
    {
        super(hw);
    }

    public void wallFollowForwards(double power, double rangeCm, float distance)
    {
        setupMotion("Following the wall forwards.");

        while(hw.drivetrain.getLeftEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance) &&
                hw.drivetrain.getRightEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance) &&
                hw.opMode.opModeIsActive())
        {
            double sideUltrasonicRange = hw.sideRangeSensor.getUltrasonicRange();

            if(sideUltrasonicRange > rangeCm)
            {
                hw.drivetrain.setPowers(power, power - 0.005);
            }
            else if(sideUltrasonicRange < rangeCm)
            {
                hw.drivetrain.setPowers(power - 0.005, power);
            }
        }

        endMotion();
    }

    public void wallFollowBackwards(double power, double rangeCm, float distance)
    {
        setupMotion("Following the wall backwards.");

        while(hw.drivetrain.getLeftEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance) &&
                hw.drivetrain.getRightEncoderCount() < hw.drivetrain.convertInchesToEncoderCounts(distance) &&
                hw.opMode.opModeIsActive())
        {
            double sideUltrasonicRange = hw.sideRangeSensor.getUltrasonicRange();

            if(sideUltrasonicRange > rangeCm)
            {
                hw.drivetrain.setPowers(power, power + 0.005);
            }
            else if(sideUltrasonicRange < rangeCm)
            {
                hw.drivetrain.setPowers(power + 0.005, power);
            }
        }

        endMotion();
    }

    public void driveForwardsUntilDistance(double distance, double power)
    {
        setupMotion("Driving forwards until distance");
        hw.drivetrain.setPowers(power, power);
        while (hw.frontRangeSensor.getUltrasonicRange() > distance && hw.opMode.opModeIsActive()) {}
        endMotion();
    }

    public void driveBackwardsUntilDistance(double distance, double power)
    {
        setupMotion("Driving backwards until distance.");
        hw.drivetrain.setPowers(power, power);
        while (hw.frontRangeSensor.getUltrasonicRange() < distance && hw.opMode.opModeIsActive()) {}
        endMotion();
    }
}
