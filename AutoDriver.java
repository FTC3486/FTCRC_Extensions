package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by developer on 2/24/16.
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

    abstract AutoDriver drive_forward(int encoderCounts);
    abstract AutoDriver drive_backward(int encoderCounts);
    abstract AutoDriver turn_clockwise(int degrees);
    abstract AutoDriver turn_counterclockwise(int degrees);
}
