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

        try {
            opMode.sleep(50);
        } catch (InterruptedException e) {}

        while(super.getMode() != mode && opMode.opModeIsActive()) {
            try {
                opMode.sleep(50);
            } catch (InterruptedException e) {}
        }

        if (mode == DcMotorController.RunMode.RESET_ENCODERS) {
            int currentEncoderCount = super.getCurrentPosition();
            if(currentEncoderCount != 0) {
                opMode.telemetry.addData("Encoder Error; Current Value: ", currentEncoderCount);

                while(getCurrentPosition() != 0 && opMode.opModeIsActive()) {
                    try {
                        opMode.sleep(50);
                    } catch (InterruptedException e) { }
                }
            }
        }
    }
}




