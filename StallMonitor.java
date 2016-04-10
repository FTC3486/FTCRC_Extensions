package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jacob on 4/6/16.
 */
class StallMonitor
{
    AutoDriver autoDriver;
    Timer stallTimer = new Timer();
    StallMonitorTask task;
    private int taskFrequency = 500;
    private int taskDelay = 1000;

    protected StallMonitor(AutoDriver autoDriver) {
        this.autoDriver = autoDriver;
    }

    protected class StallMonitorTask extends TimerTask {
        private int previousLeftCounts;
        private int previousRightCounts;
        int leftThresholdConstant = 125;
        int rightThresholdConstant = 125;

        int getLeftThreshold() {
            return (int) (leftThresholdConstant * autoDriver.driveTrain.getLeftSpeed());
        }

        int getRightThreshold() {
            return (int) (rightThresholdConstant * autoDriver.driveTrain.getRightSpeed());
        }

        private boolean is_stall_detected() {
            boolean isStallDetected = false;

            for (DcMotor motor : autoDriver.driveTrain.getLeftMotorsWithEncoders())
            {
                if (Math.abs(previousLeftCounts - motor.getCurrentPosition()) <= getLeftThreshold())
                {
                    isStallDetected = true;
                }
            }

            for (DcMotor motor : autoDriver.driveTrain.getRightMotorsWithEncoders())
            {
                if (Math.abs(previousRightCounts - motor.getCurrentPosition()) <= getRightThreshold())
                {
                    isStallDetected = true;
                }
            }

            return isStallDetected;
        }

        @Override
        public void run() {
            if (is_stall_detected()) {
                autoDriver.e_stop();
            } else {
                previousLeftCounts = (int) autoDriver.driveTrain.getLeftEncoderCount();
                previousRightCounts = (int) autoDriver.driveTrain.getRightEncoderCount();
            }
        }
    }

    protected void setTaskFrequency(int taskFrequency) {
        if (taskFrequency < 0) throw new IllegalArgumentException("The frequency must be >0");
        this.taskFrequency = taskFrequency;
    }

    protected void setTaskDelay(int taskDelay) {
        if (taskDelay < 0) throw new IllegalArgumentException("The delay must be >0");
        this.taskDelay = taskDelay;
    }

    protected void start_monitoring() {
        task = new StallMonitorTask();
        stallTimer.scheduleAtFixedRate(task, taskDelay, taskFrequency);
    }

    protected void stop_monitoring() {
        task.cancel();
        stallTimer.purge();
    }
}
