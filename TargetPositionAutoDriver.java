package org.firstinspires.ftc.teamcode.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FTCRC_Extensions.DriveTrain;

/**
 * Created by Matthew on 3/6/2017.
 */

public class TargetPositionAutoDriver {
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;
    static final double     DRIVE_GEAR_REDUCTION    = 0.8 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    private ElapsedTime runtime = new ElapsedTime();
    private LinearOpMode opMode;
    private DriveTrain driveTrain;

    public TargetPositionAutoDriver(LinearOpMode inputOpMode, DriveTrain inputDriveTrain)
    {
        opMode = inputOpMode;
        driveTrain = inputDriveTrain;
    }

    public void driveToTarget(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = (int) driveTrain.getLeftEncoderCount() + (int) (leftInches * COUNTS_PER_INCH);
        newRightTarget = (int) driveTrain.getRightEncoderCount() + (int) (rightInches * COUNTS_PER_INCH);

        driveTrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrain.setTargetPosition(newLeftTarget, newRightTarget);


        // reset the timeout time and start motion.
        runtime.reset();

        driveTrain.setPowers(Math.abs(speed), Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        while (opMode.opModeIsActive() &&
                (runtime.seconds() < timeoutS)
                && driveTrain.isBusy()) {
            //Wait for completion
        }
        driveTrain.haltDrive();
        opMode.sleep(200);
        driveTrain.resetMotorEncoders();

        // Turn off RUN_TO_POSITION
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
