package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by developer on 2/24/16.
 */
public abstract class AutoDriver {
    protected LinearOpMode opMode;
    protected DriveTrain driveTrain;
    protected ElapsedTime timer;

    public AutoDriver(LinearOpMode opMode, DriveTrain driveTrain) {
        this.opMode = opMode;
        this.driveTrain = driveTrain;
        this.timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    public void waitMilliseconds(int waitTime) throws InterruptedException{
        timer.reset();
        while(timer.time() < waitTime && opMode.opModeIsActive()) { opMode.sleep(1); }
    }

    abstract AutoDriver set_power(double power);
    abstract AutoDriver drive_forward(int encoderCounts);
    abstract AutoDriver drive_backward(int encoderCounts);
    abstract AutoDriver turn_clockwise(int degrees);
    abstract AutoDriver turn_counterclockwise(int degrees);
}
