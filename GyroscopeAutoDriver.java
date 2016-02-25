package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Matthew on 2/20/2016.
 */
public class GyroscopeAutoDriver extends AutoDriver {
    private GyroSensor gyroSensor;

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
            if (is_offcourse_left()) {
                driveTrain.setPowers(power, power - (0.75 * power));
            } else if (is_offcourse_right()) {
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
            if (is_offcourse_left()) {
                driveTrain.setPowers(-power + (0.75 * power), -power );
            } else if (is_offcourse_right()) {
                driveTrain.setPowers(-power, -power + (0.75 * power));
            } else {
                driveTrain.setPowers(-power, -power);
            }
        }
        driveTrain.haltDrive();

        return null;
    }

    private boolean is_offcourse_left() {
        return gyroSensor.getHeading() >= 180 && gyroSensor.getHeading() <= 357;
    }

    private boolean is_offcourse_right() {
        return gyroSensor.getHeading() >= 2 && gyroSensor.getHeading() <= 179;
    }

    @Override
    public AutoDriver turn_clockwise(int degrees) {
        gyroSensor.resetZAxisIntegrator();
        int gyroTarget = get_gyro_target(degrees);
        driveTrain.setPowers(power, -power);

        while(gyroSensor.getHeading() > (gyroTarget - 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
        }
        while(gyroSensor.getHeading() < (gyroTarget + 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
        }
        driveTrain.haltDrive();

        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise(int degrees) {
        gyroSensor.resetZAxisIntegrator();
        int gyroTarget = get_gyro_target(degrees);
        driveTrain.setPowers(-power, power);

        while(gyroSensor.getHeading() < (gyroTarget - 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
        }

        while(gyroSensor.getHeading() > (gyroTarget + 18) && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
        }
        driveTrain.haltDrive();

        return this;
    }

    private int get_gyro_target(int degrees) {
        while (degrees < 0) {
            degrees += 360;
        }
        return degrees;
    }
}

