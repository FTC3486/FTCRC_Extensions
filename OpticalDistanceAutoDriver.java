package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

/**
 * Created by Owner_2 on 12/31/2016.
 * Edited by Matthew on 3/6/2017.
 */

public class OpticalDistanceAutoDriver extends AutoDriver
{
    private double lightValue = 0.06;

    public OpticalDistanceAutoDriver(HardwareConfiguration hw)
    {
        super(hw);
    }

    public double getLightValue()
    {
        return lightValue;
    }

    public void setLightValue(double lightValue)
    {
        this.lightValue = lightValue;
    }

    public void squareUpToLine() throws InterruptedException
    {
        setupMotion("Squaring up to a line.");

        hw.drivetrain.setPowers(0.1, 0.1);
        while (hw.leftOpticalDistanceSensor.getLightDetected() < lightValue
                && hw.rightOpticalDistanceSensor.getLightDetected() < lightValue && hw.opMode.opModeIsActive())
        {
            // Continue driving forwards until either sensor finds a line or the opMode ends.
        }

        hw.drivetrain.haltDrive();

        // Check to see which sensor found the line first. If both found it at the same time,
        // the robot is already aligned to the line and will adjust no further.

        // If the left sensor found the line, and the right sensor did not.
        if (hw.leftOpticalDistanceSensor.getLightDetected() >= lightValue &&
            hw.rightOpticalDistanceSensor.getLightDetected() < lightValue)
        {
            // Drive until the right sensor sees the line.
            hw.drivetrain.setPowers(0, .1);
            while (hw.rightOpticalDistanceSensor.getLightDetected() < lightValue) {}
            hw.drivetrain.haltDrive();
            hw.drivetrain.resetMotorEncoders();

            // This short movement adjust the robot back straight, as the pivot turn can slightly
            // adjust the position of the left side of the robot when the right side turns.
            hw.drivetrain.setPowers(-0.1, 0);
            while (hw.drivetrain.getLeftEncoderCount() > -50 && hw.opMode.opModeIsActive()) {}
            hw.drivetrain.haltDrive();
        }

        // If the right sensor found the line, and the left sensor did not.
        else if (hw.rightOpticalDistanceSensor.getLightDetected() >= lightValue &&
                 hw.leftOpticalDistanceSensor.getLightDetected() < lightValue)
        {
            hw.drivetrain.setPowers(0.1, 0);
            while (hw.leftOpticalDistanceSensor.getLightDetected() < lightValue) {}
            hw.drivetrain.haltDrive();

            hw.drivetrain.resetMotorEncoders();
            hw.drivetrain.setPowers(0.0, -0.1);
            while (hw.drivetrain.getRightEncoderCount() > -50 && hw.opMode.opModeIsActive()) {}
            hw.drivetrain.haltDrive();
        }
        
        endMotion();
    }
}