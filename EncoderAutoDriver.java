package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


/**
 * Created by Jacob on 2/24/16.
 */
public class EncoderAutoDriver extends AutoDriver {

    public EncoderAutoDriver(LinearOpMode linearOpMode, Drivetrain drivetrain) {
        super(linearOpMode, drivetrain);
    }

    @Override
    public AutoDriver drive_forward_implementation(int encoderCount) {

        drivetrain.setPowers(power, power);
        while(drivetrain.getLeftEncoderCount() < encoderCount &&
               !eStop && opMode.opModeIsActive()) {
        }
        return this;
    }

    @Override
    public AutoDriver drive_backward_implementation(int encoderCount) {
        drivetrain.setPowers(-power, -power);
        while(drivetrain.getLeftEncoderCount() > encoderCount &&
               !eStop && opMode.opModeIsActive()) {
        }
        return this;
    }

    @Override
    public AutoDriver turn_clockwise_implementation(int encoderCount) {
        drivetrain.setPowers(power, -power);
        while(drivetrain.getLeftEncoderCount() < encoderCount &&
              !eStop && opMode.opModeIsActive()) {
        }
        return this;
    }

    @Override
    public AutoDriver turn_counterclockwise_implementation(int encoderCount) {
        drivetrain.setPowers(-power, power);
        while(drivetrain.getRightEncoderCount() < encoderCount &&
              !eStop && opMode.opModeIsActive()) {
        }
        return this;
    }
}

