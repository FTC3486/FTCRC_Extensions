package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Subsystems.GlyphGrabber;
import org.firstinspires.ftc.teamcode.Subsystems.GlyphLift;
import org.firstinspires.ftc.teamcode.Subsystems.RelicArm;
import org.firstinspires.ftc.teamcode.Subsystems.RelicClaw;
import org.firstinspires.ftc.teamcode.Subsystems.RelicLift;
import org.firstinspires.ftc.teamcode.Subsystems.Spinner;

public class TWAHardwareConfiguration
{
    OpMode opMode;
    //Robot Components
    public Drivetrain drivetrain;
    public Spinner spinner;
    public GlyphLift glyphLift;
    public GlyphGrabber glyphGrabber;
    public RelicLift relicLift;
    public RelicArm relicArm;
    public RelicClaw relicClaw;
    //Sensors


    //Auto Drivers
    public GyroAutoDriver gyroAutoDriver;
    public EncoderAutoDriver2017 encoderAutoDriver;
    public RangeAutoDriver rangeAutoDriver;
    public OpticalDistanceAutoDriver opticalDistanceAutoDriver;

    TWAHardwareConfiguration(OpMode opMode)
    {
        this.opMode = opMode;
        //Define robot components
        DcMotor left1 = opMode.hardwareMap.dcMotor.get("left1");
        DcMotor left2 = opMode.hardwareMap.dcMotor.get("left2");
        DcMotor right1 = opMode.hardwareMap.dcMotor.get("right1");
        DcMotor right2 = opMode.hardwareMap.dcMotor.get("right2");
        left1.setDirection(DcMotor.Direction.REVERSE);
        left2.setDirection(DcMotor.Direction.REVERSE);
        right1.setDirection(DcMotor.Direction.FORWARD);
        right2.setDirection(DcMotor.Direction.FORWARD);


        drivetrain = new Drivetrain.Builder()
                .addLeftMotorWithEncoder(left1)
                .addLeftMotorWithEncoder(left2)
                .addRightMotorWithEncoder(right1)
                .addRightMotorWithEncoder(right2)
                .build();

        glyphGrabber = new GlyphGrabber("leftservo1","leftservo2","rightservo1","rightservo2", opMode.hardwareMap);
        spinner = new Spinner("spinner", "SpinnerRotation", opMode.hardwareMap);
        glyphLift = new GlyphLift("GlyphLift", "LiftTouch", opMode.hardwareMap);
        relicLift = new RelicLift("RelicLift", opMode.hardwareMap);
        relicArm = new RelicArm("RelicArm", opMode.hardwareMap);
       // relicClaw = new RelicClaw("ClawServo1","ClawServo2", opMode.hardwareMap);

        //Define sensors


        //Define auto drivers

        //gyroAutoDriver = new GyroAutoDriver(this);
        //encoderAutoDriver = new EncoderAutoDriver2017(this);
    }

    void init()
    {

        // :) oh happy day, there is nothing here. :)
    }
}

