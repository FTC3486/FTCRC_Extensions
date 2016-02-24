package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Matthew on 2/20/2016.
 */
public class AutoDriver {
    private LinearOpMode opMode;
    private DriveTrain driveTrain;
    private GyroSensor gyroSensor;
    private ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public AutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain, String gyroSensor,
                      HardwareMap hardwaremap) {
        this.opMode = linearOpMode;
        this.driveTrain = driveTrain;
        this.gyroSensor = hardwaremap.gyroSensor.get(gyroSensor);
    }

    public void driveForwardtoEncoderCountWithCorrection(int encoderCount, double power,
                                                         int gyroTarget) {
        driveTrain.resetMotorEncoders();

        while (driveTrain.getLeftEncoderCount() < encoderCount && driveTrain.getRightEncoderCount()
                < encoderCount && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            if(gyroSensor.getHeading() < gyroTarget) {
                driveTrain.setPowers(power, power - (0.75* power));
            } else if(gyroSensor.getHeading() > gyroTarget) {
                driveTrain.setPowers(power - (0.75 * power) , power);
            } else {
                driveTrain.setPowers(power, power);
            }
        }
        driveTrain.setPowers(0, 0);
    }

    public void driveBackwardtoEncoderCountWithCorrection(int encoderCount, double power,
                                                          int gyroTarget) {
        driveTrain.resetMotorEncoders();

        while (driveTrain.getLeftEncoderCount() > encoderCount && driveTrain.getRightEncoderCount()
                > encoderCount && opMode.opModeIsActive()) {
            //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
            if(gyroSensor.getHeading() < gyroTarget) {
                driveTrain.setPowers(power, power + (0.75* power));
            } else if(gyroSensor.getHeading() > gyroTarget) {
                driveTrain.setPowers(power + (0.75 * power) , power);
            } else {
                driveTrain.setPowers(power, power);
            }
        }
        driveTrain.setPowers(0, 0);
    }

    public void driveBackwardtoEncoderCount(int encoderCount, double power) {
        driveTrain.resetMotorEncoders();
        driveTrain.setPowers(power, power);
        while (driveTrain.getLeftEncoderCount() > encoderCount && driveTrain.getRightEncoderCount()
                > encoderCount && opMode.opModeIsActive()) {}
        driveTrain.setPowers(0, 0);
    }


    public void gyroTurn(String direction, int gyroHeading, double power) {
        switch(direction) {
            default:
            case "CLOCKWISE":
                driveTrain.setPowers(power, -power);

                while(gyroSensor.getHeading() > (gyroHeading - 11) && opMode.opModeIsActive()) {
                    //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                while(gyroSensor.getHeading() < (gyroHeading - 11) && opMode.opModeIsActive()) {
                    //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                driveTrain.setPowers(0.0, 0.0);
                break;

            case "COUNTER_CLOCKWISE":
                driveTrain.setPowers(-power, power);

                while(gyroSensor.getHeading() < (gyroHeading + 11) && opMode.opModeIsActive()) {
                    //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                while(gyroSensor.getHeading() > (gyroHeading + 11) && opMode.opModeIsActive()) {
                    //opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                driveTrain.setPowers(0.0, 0.0);
                break;
        }

    }

    public void waitMilliseconds(int waitTime) throws InterruptedException{
        timer.reset();
        while(timer.time() < waitTime && opMode.opModeIsActive()) { opMode.sleep(1); }
    }

}

