package auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import subsystems.Scoring;

@TeleOp(name = "ScoreClawManual", group = "Experiment")
public class ScoreClawManual extends OpMode {
    public Scoring scoring = new Scoring();

    @Override
    public void init(){
        scoring.init(hardwareMap);
        scoring.scoreOpen();
    }
    @Override
    public void loop(){
        if(gamepad1.cross){
            scoring.scoreOpen();
        }
        if(gamepad1.circle){
            scoring.scoreClose();
        }
    }
}
