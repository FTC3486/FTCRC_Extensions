package org.firstinspires.ftc.teamcode.FTCRC_Extensions;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Subsystems.HardwareConfiguration;

/**
 * Created by Owner_2 on 12/31/2016.
 * Edited by Matthew on 3/6/2017.
 */

public class GyroAutoDriver {
    private HardwareConfiguration hw;

    public GyroAutoDriver(HardwareConfiguration hw) {
        this.hw = hw;
    }

    public void driveStraightForwards(int targetPosition, double power) {
        double leftSpeed; //Power to feed the motors
        double rightSpeed;

        double target = hw.gyroSensor.getIntegratedZValue();  //Starting direction
        double zAccumulated;
        double startPositionLeft = hw.driveTrain.getLeftEncoderCount();
        double startPositionRight = hw.driveTrain.getRightEncoderCount();

        while (hw.driveTrain.getLeftEncoderCount() < targetPosition + startPositionLeft
                && hw.driveTrain.getRightEncoderCount() < targetPosition + startPositionRight && hw.opMode.opModeIsActive()) {
            zAccumulated = hw.gyroSensor.getIntegratedZValue();  //Current direction

            leftSpeed = power + (zAccumulated - target) / 20;  //Calculate speed for each side
            rightSpeed = power - (zAccumulated - target) / 20;  //See Gyro Straight video for detailed explanation

            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            hw.driveTrain.setPowers(leftSpeed, rightSpeed);
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }

    public void driveStraightBackwards(int targetPosition, double power) {
        double leftSpeed; //Power to feed the motors
        double rightSpeed;

        double target = hw.gyroSensor.getIntegratedZValue();  //Starting direction
        double zAccumulated;
        double startPositionLeft = hw.driveTrain.getLeftEncoderCount();//Starting position
        double startPositionRight = hw.driveTrain.getRightEncoderCount();

        while (hw.driveTrain.getLeftEncoderCount() > targetPosition + startPositionLeft &&
                hw.driveTrain.getRightEncoderCount() > targetPosition + startPositionRight && hw.opMode.opModeIsActive())
        {  //While we have not passed out intended distance
            zAccumulated = hw.gyroSensor.getIntegratedZValue();  //Current direction

            leftSpeed = power + (zAccumulated - target) / 20;  //Calculate speed for each side
            rightSpeed = power - (zAccumulated - target) / 20;  //See Gyro Straight video for detailed explanation

            leftSpeed = Range.clip(leftSpeed, -1, 1);
            rightSpeed = Range.clip(rightSpeed, -1, 1);

            hw.driveTrain.setPowers(leftSpeed, rightSpeed);
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }

    public void turn(int target) {
        hw.gyroSensor.resetZAxisIntegrator();
        double gyroHeading = this.getAdjustedHeading();

        while(gyroHeading  < target - 1 || gyroHeading > target + 1 && hw.opMode.opModeIsActive())
        {
            double power = ( (target - gyroHeading) / Math.abs(target)) * (1.0/4.0);

            power = Math.signum(power) * Range.clip(Math.abs(power), 0.05, 1.0);

            hw.driveTrain.setPowers(power, -power);
            gyroHeading = this.getAdjustedHeading();
        }
        hw.driveTrain.haltDrive();
        hw.gyroSensor.resetZAxisIntegrator();
        hw.opMode.sleep(200);
        hw.driveTrain.resetMotorEncoders();
    }

    private double getAdjustedHeading() {
        double sensorValue = hw.gyroSensor.getHeading();

        if(sensorValue > 180)
        {
            sensorValue -= 360;
        }
        return sensorValue;
    }
}
