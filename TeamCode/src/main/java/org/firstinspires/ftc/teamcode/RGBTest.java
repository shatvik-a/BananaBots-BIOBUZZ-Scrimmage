package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class RGBTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Servo rgb = hardwareMap.get(Servo.class, "rgb");
        rgb.scaleRange(0.277, 0.772);
        double color = 0;
        waitForStart();
        while (opModeIsActive()) {
            if (color != 1){
                color += 0.01;
            } else {
                color = 0;
            }
            rgb.setPosition(color);
        }
    }

}
