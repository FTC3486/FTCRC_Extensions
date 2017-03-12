package org.firstinspires.ftc.teamcode.FTCRC_Extensions;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Subsystems.HardwareConfiguration;

/**
 * Created by Matthew on 3/8/2017.
 */

public class RangeAutoDriver {
    private HardwareConfiguration hw;

    public RangeAutoDriver(HardwareConfiguration hw) {
        this.hw = hw;
    }

    public void wallFollowForwards(double power, double rangeCm, int encodercounts){
        double startPositionLeft = hw.driveTrain.getLeftEncoderCount();//Starting position
        double startPositionRight = hw.driveTrain.getRightEncoderCount();

        while(hw.driveTrain.getLeftEncoderCount() < encodercounts + startPositionLeft && hw.driveTrain.getRightEncoderCount() < encodercounts + startPositionRight && hw.opMode.opModeIsActive())  {
            double leftSpeed = power;
            double rightSpeed = power;

            double sideUltrasonicRange = hw.sideRangeSensor.getUltrasonicRange();

            if(sideUltrasonicRange > rangeCm)
            {
                rightSpeed = power - 0.005;
            }
            else if(sideUltrasonicRange < rangeCm)
            {
                leftSpeed = power - 0.005;
            }

            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            hw.driveTrain.setPowers(leftSpeed, rightSpeed);
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }

    public void wallFollowBackwards(double power, double rangeCm, int encodercounts){
        double startPositionLeft = hw.driveTrain.getLeftEncoderCount();//Starting position
        double startPositionRight = hw.driveTrain.getRightEncoderCount();

        while(hw.driveTrain.getLeftEncoderCount() > encodercounts + startPositionLeft && hw.driveTrain.getRightEncoderCount() > encodercounts + startPositionRight && hw.opMode.opModeIsActive())  {
            double leftSpeed = power;
            double rightSpeed = power;

            double sideUltrasonicRange = hw.sideRangeSensor.getUltrasonicRange();
            if(sideUltrasonicRange > rangeCm)
            {
                rightSpeed = power + 0.007;
            }
            else if(sideUltrasonicRange < rangeCm)
            {
                leftSpeed = power + 0.007;
            }

            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            hw.driveTrain.setPowers(leftSpeed, rightSpeed);
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }

    public void driveForwardsUntilDistance(double distance, double power) {
        while (hw.frontRangeSensor.getUltrasonicRange() > distance && hw.opMode.opModeIsActive()) {
            hw.driveTrain.setPowers(power, power);
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }

    public void driveBackwardsUntilDistance(double distance, double power) {
        while (hw.frontRangeSensor.getUltrasonicRange() < distance && hw.opMode.opModeIsActive()) {
            hw.driveTrain.setPowers(power, power);
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }
}
