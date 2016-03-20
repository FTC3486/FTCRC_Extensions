package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Jacob on 2/24/16.
 */
public class OpticalAutoDriver extends AutoDriver {
    OpticalDistanceSensor opticalDistanceSensor;

    public OpticalAutoDriver(LinearOpMode linearOpMode, DriveTrain driveTrain,
                             String opticalDistanceSensor, HardwareMap hardwareMap) {
        super(linearOpMode, driveTrain);
        this.opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get(opticalDistanceSensor);

    }

    @Override
    public AutoDriver drive_forward_implementation(int encoderCount) {
        return this;
    }

    @Override
    public AutoDriver drive_backward_implementation(int lightValue) {
        while(this.opticalDistanceSensor.getLightDetected() <= lightValue) {
            driveTrain.setPowers(-power, -power);
        }
        driveTrain.setPowers(0.0, 0.0);
        return this;
    }

    @Override
    public AutoDriver turn_clockwise_implementation(int encoderCount) {
        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise_implementation(int encoderCount) {
        return this;
    }
}