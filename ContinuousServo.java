package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Matthew on 2/2/2016.
 */
public class ContinuousServo {
    private Servo servo;

    public ContinuousServo(Servo servo)
    {
       this.servo = servo;
    }

    public void setPower(double power) {
        servo.setPosition(power/2 + 0.5);
    }
}
