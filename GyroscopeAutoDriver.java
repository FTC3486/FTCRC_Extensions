package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew on 2/20/2016.
 */
public class GyroscopeAutoDriver extends AutoDriver {
    private GyroSensor gyroSensor;

    // This stores the intended heading of the robot. Some turns might not completely stop on the
    // correct heading, so reading the value from the gyro would result in accumulating drift (bad).
    private int gyroTarget = 0;

    public GyroscopeAutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain, String gyroSensor,
                               HardwareMap hardwaremap) {
        super(linearOpMode, driveTrain);
        this.gyroSensor = hardwaremap.gyroSensor.get(gyroSensor);
    }

    @Override
    public AutoDriver drive_forward(int encoderCounts) {
        driveTrain.resetMotorEncoders();
        gyroSensor.resetZAxisIntegrator();

        while (driveTrain.getLeftEncoderCount() < encoderCounts &&
               driveTrain.getRightEncoderCount() < encoderCounts &&
               opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            if (gyroSensor.getHeading() >=180 && gyroSensor.getHeading() <=357) {
                driveTrain.setPowers(power, power - (0.75 * power));
            } else if (gyroSensor.getHeading() >= 2 && gyroSensor.getHeading() <= 179) {
                driveTrain.setPowers(power - (0.75 * power), power);
            } else {
                driveTrain.setPowers(power, power);
            }
        }
        driveTrain.haltDrive();

        return this;
    }

    @Override
    public AutoDriver drive_backward(int encoderCounts) {
        driveTrain.resetMotorEncoders();
        gyroSensor.resetZAxisIntegrator();

        while (driveTrain.getLeftEncoderCount() > encoderCounts &&
                driveTrain.getRightEncoderCount() > encoderCounts &&
                opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            if (gyroSensor.getHeading() >=180 && gyroSensor.getHeading() <=354) {
                driveTrain.setPowers(power, power + (0.75 * power) );
            } else if (gyroSensor.getHeading() >= 5 && gyroSensor.getHeading() <= 179) {
                driveTrain.setPowers(power + (0.75 * power), power);
            } else {
                driveTrain.setPowers(power, power);
            }
        }

        driveTrain.haltDrive();

        return null;
    }

    @Override
    public AutoDriver turn_clockwise(int degrees) {
        set_gyro_target(degrees);
        driveTrain.setPowers(power, -power);

        while(gyroSensor.getHeading() > (gyroTarget - 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            //Thread.yield();
        }
        while(gyroSensor.getHeading() < (gyroTarget + 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            //Thread.yield();
        }
        driveTrain.haltDrive();

        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise(int degrees) {
        set_gyro_target(degrees);
        driveTrain.setPowers(-power, power);

        while(gyroSensor.getHeading() < (gyroTarget - 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            //Thread.yield();
        }

        while(gyroSensor.getHeading() > (gyroTarget + 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            // Thread.yield();
        }
        driveTrain.haltDrive();

        return this;
    }


    /**
     * When you call .getHeading() for a gyro object, you get some integer between 0 and 359. This
     * isn't helpful at all when trying to determine if the robot is veering left or right of the
     * intended target because of the edge cases. If the gyro is on the 0 mark, all values on the
     * gyro will be considered "greater than" it. This makes basic comparison really tough.
     * @return Gyro heading as a signed integer relative to gyroTarget
     */
    private int get_gyro_drift() {
        throw new UnsupportedOperationException("Not implemented");
    }

    private void set_gyro_target(int degrees) {
        gyroTarget += degrees;
        if (gyroTarget < 0) {
            gyroTarget += 360;
        } else if (gyroTarget > 359) {
            gyroTarget -= 360;
        }
    }
}

