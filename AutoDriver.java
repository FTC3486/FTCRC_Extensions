package com.FTC3486.FTCRC_Extensions;

import com.FTC3486.OpModes.RedAutoMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Matthew on 2/20/2016.
 */
public class AutoDriver {
    private LinearOpMode opMode;
    private DriveTrain driveTrain;
    private GyroSensor gyroSensor ;

    public AutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain, String gyroSensor, HardwareMap hardwaremap) {
        this.opMode = linearOpMode;
        this.driveTrain = driveTrain;
        this.gyroSensor = hardwaremap.gyroSensor.get(gyroSensor);
    }

    public void driveForwardtoEncoderCount(int encoderCount, double power) {
        driveTrain.resetMotorEncoders();

        while (driveTrain.getLeftEncoderCount() < encoderCount && driveTrain.getRightEncoderCount() < encoderCount && opMode.opModeIsActive()) {
            driveTrain.setPowers(power, power);
        }
        driveTrain.setPowers(0, 0);
    }

    public void gyroTurn(String direction, int gyroHeading, double power) {
        switch(direction) {
            default:
            case "CLOCKWISE":
                driveTrain.setPowers(power, -power);

                while(gyroSensor.getHeading() > gyroHeading && opMode.opModeIsActive()) {
                    opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                while(gyroSensor.getHeading() < gyroHeading && opMode.opModeIsActive()) {
                    opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                driveTrain.setPowers(0.0, 0.0);
                break;

            case "COUNTER_CLOCKWISE":
                driveTrain.setPowers(-power, power);

                while(gyroSensor.getHeading() < gyroHeading && opMode.opModeIsActive()) {
                    opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                while(gyroSensor.getHeading() > gyroHeading && opMode.opModeIsActive()) {
                    opMode.telemetry.addData("Gyro Heading", gyroSensor.getHeading());
                }

                driveTrain.setPowers(0.0, 0.0);
                break;
        }

    }

}

