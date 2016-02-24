package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Matthew on 2/20/2016.
 */
public class ExtendedDcMotor extends DcMotor {

    public ExtendedDcMotor(DcMotor extendedDCMotor) {
        super(extendedDCMotor.getController(), extendedDCMotor.getPortNumber());
    }

    public void changeMode(String runMode) {
        switch (runMode) {
            case "RESET_ENCODERS":
                this.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                while(this.getMode() != DcMotorController.RunMode.RESET_ENCODERS) { }

                while(this.getCurrentPosition() != 0) { }
                break;

            case "RUN_USING_ENCODERS":
                this.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
                while(this.getMode() != DcMotorController.RunMode.RUN_USING_ENCODERS) { }
                break;

            case "RUN_TO_POSITION":
                this.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                while(this.getMode() != DcMotorController.RunMode.RUN_TO_POSITION) { }
                break;

            case "RUN_WITHOUT_ENCODERS":
            default:
                this.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
                while(this.getMode() != DcMotorController.RunMode.RUN_WITHOUT_ENCODERS) { }
                break;
        }
    }
}




