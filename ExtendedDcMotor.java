package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
        super.setMode(mode);
        while(super.getMode() != mode && opMode.opModeIsActive()) {}

        if (mode == DcMotorController.RunMode.RESET_ENCODERS) {
            while (super.getCurrentPosition() != 0 && opMode.opModeIsActive()) {}
        }
    }
}




