package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Matthew on 2/20/2016.
 */
public class ExtendedDcMotor extends DcMotor {
    LinearOpMode opMode;

    public ExtendedDcMotor(DcMotor extendedDCMotor, LinearOpMode opMode) {
        super(extendedDCMotor.getController(), extendedDCMotor.getPortNumber());
        this.opMode = opMode;
    }

    @Override
    public void setMode(DcMotorController.RunMode mode) {
        switch (mode) {
            case RESET_ENCODERS:
                super.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                while(super.getMode() != DcMotorController.RunMode.RESET_ENCODERS && opMode.opModeIsActive()) {

                }

                while(super.getCurrentPosition() != 0 && opMode.opModeIsActive()) {
                    Thread.yield();
                }
                break;

            case RUN_USING_ENCODERS:
                super.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                while(super.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS && opMode.opModeIsActive()) {
                    Thread.yield();
                }
                break;

            case RUN_TO_POSITION:
                super.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                while(super.getMode() != DcMotorController.RunMode.RUN_TO_POSITION && opMode.opModeIsActive()) {
                    Thread.yield();
                }
                break;

            case RUN_WITHOUT_ENCODERS:
            default:
                super.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                while(super.getMode() != DcMotorController.RunMode.RUN_WITHOUT_ENCODERS && opMode.opModeIsActive()) {
                    Thread.yield();
                }
                break;
        }
    }
}




