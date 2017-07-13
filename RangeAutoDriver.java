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
    
    public void squareUpToWall(double distance, double power)
    {
        setupMotion("Squaring up to a wall.");

        int initialLeftReading = hw.leftRangeSensor.getUltrasonicRange();
        int initialRightReading = hw.rightRangeSensor.getUltrasonicRange();
        int distanceToDrive = 0;
        hw.opMode.telemetry.addData("InitialLeftReading:", initialLeftReading);
        hw.opMode.telemetry.addData("InitialRightReading:", initialRightReading);

        if(initialLeftReading > initialRightReading)
        {
            hw.encoderAutoDriver.driveLeftSideToDistance( 1.0 * (initialLeftReading - initialRightReading));
            distanceToDrive = hw.rightRangeSensor.getUltrasonicRange();
            hw.opMode.telemetry.addData("distanceToDrive", distanceToDrive);
        }
        else if(initialRightReading > initialLeftReading)
        {
            hw.encoderAutoDriver.driveRightSideToDistance( 1.0 * (initialRightReading - initialLeftReading));
            distanceToDrive = hw.leftRangeSensor.getUltrasonicRange();
            hw.opMode.telemetry.addData("distanceToDrive", distanceToDrive);
        }
        hw.opMode.telemetry.update();
        hw.opMode.sleep(5000);
        hw.encoderAutoDriver.driveToDistance(distanceToDrive - 20);
        endMotion();
    }
}
