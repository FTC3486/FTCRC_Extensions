package com.FTC3486.FTCRC_Extensions;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Matthew on 2/2/2016.
 */
public class ContinuousServo extends Servo {

    public ContinuousServo(Servo servo)
    {
        super(servo.getController(), servo.getPortNumber());
    }

    public void setPower(double power) {
        this.setPosition(power/2 + 0.5);
    }
}
