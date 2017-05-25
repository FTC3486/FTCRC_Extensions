package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Jacob on 2/24/16.
 */
public class OpticalAutoDriver extends AutoDriver {
    OpticalDistanceSensor opticalDistanceSensor;

    public OpticalAutoDriver(LinearOpMode linearOpMode, Drivetrain drivetrain,
                             String opticalDistanceSensor, HardwareMap hardwareMap) {
        super(linearOpMode, drivetrain);
        this.opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get(opticalDistanceSensor);

    }

    @Override
    public AutoDriver drive_forward_implementation(int encoderCount) {
        return this;
    }

    @Override
    public AutoDriver drive_backward_implementation(int lightValue) {
        drivetrain.setPowers(-power, -power);
        while (this.opticalDistanceSensor.getLightDetected() <= lightValue &&
                !eStop && opMode.opModeIsActive()) {}
        drivetrain.haltDrive();
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