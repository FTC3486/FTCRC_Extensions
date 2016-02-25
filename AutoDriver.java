package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Jacob on 2/24/16.
 */
public abstract class AutoDriver {
    protected LinearOpMode opMode;
    protected DriveTrain driveTrain;
    protected double power = 1.0;


    public AutoDriver(LinearOpMode opMode, DriveTrain driveTrain) {
        this.opMode = opMode;
        this.driveTrain = driveTrain;
    }

    public void set_power(double power) {
        this.power = power;
    }

    public abstract AutoDriver drive_forward(int encoderCounts);
    public abstract AutoDriver drive_backward(int encoderCounts);
    public abstract AutoDriver turn_clockwise(int degrees);
    public abstract AutoDriver turn_counterclockwise(int degrees);
}
