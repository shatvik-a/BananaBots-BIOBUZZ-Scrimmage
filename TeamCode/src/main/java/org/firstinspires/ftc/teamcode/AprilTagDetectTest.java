package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp
public class AprilTagDetectTest extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        // Initialize Limelight
        limelight = hardwareMap.get(
                Limelight3A.class, "limelight");

        // Select the pipeline configured on Limelight
        limelight.pipelineSwitch(0);

        // Start receiving vision results
        limelight.start();

        telemetry.addLine("Limelight initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            readAprilTags();
            telemetry.update();
            sleep(20);
        }

        limelight.stop();
    }
    private void readAprilTags() {

        LLResult result = limelight.getLatestResult();
        int counter = 0;

        double dist_avg = 0;

        if (result != null && result.isValid()
                && !result.getFiducialResults().isEmpty()) {

            for (LLResultTypes.FiducialResult detection: result.getFiducialResults()) {

                Pose3D pose = detection.getTargetPoseCameraSpace();

                double x = pose.getPosition().x+2.75;
                double y = pose.getPosition().y;
                double z = pose.getPosition().z;
                String poseString = pose.toString();

                double distance = Math.sqrt(x*x + y*y + z*z);

                telemetry.addData("Tag ID", detection.getFiducialId());
                telemetry.addData("Pose", poseString);

                telemetry.addData("Distance (meters)", distance);
                telemetry.addData("Distance (inches)", distance * 39.3701);

                dist_avg +=distance;
                counter+=1;
            }
            dist_avg /= counter;
            telemetry.addData("Average Distance", dist_avg);
        }
    }
}