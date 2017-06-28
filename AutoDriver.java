package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

/**
 * Created by Jacob on 2/24/16.
 */
public abstract class AutoDriver {
    HardwareConfiguration hw;
    private StallMonitor stallMonitor = new StallMonitor(this);
    protected double power = 1.0D;
    private int wait_time_ms = 500;
    protected boolean eStop = false;


    public AutoDriver(HardwareConfiguration hw)
    {
        this.hw = hw;
    }

    public void setWaitTimeBetweenMovements(int milliseconds)
    {
        if (milliseconds < 0) throw new IllegalArgumentException("the wait time should always be positive");
        this.wait_time_ms = milliseconds;
    }

    protected void setupMotion(String motion_description)
    {
        eStop = false;
        hw.opMode.telemetry.addData("AutoDriver", motion_description);
        hw.drivetrain.resetMotorEncoders();
        stallMonitor.startMonitoring();
    }

    protected void endMotion()
    {
        hw.drivetrain.haltDrive();
        stallMonitor.stopMonitoring();
        hw.opMode.telemetry.addData("AutoDriver", "Halting");
        hw.opMode.sleep(wait_time_ms);
    }

    protected void eStop()
    {
        eStop = true;
    }
}
