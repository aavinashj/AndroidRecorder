package avinash.pet.project.androidrecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder audioRecorder;
    private boolean mIsRecording;
    private boolean isRecording;
    private boolean isPlaying;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button record = (Button) findViewById(R.id.btntoggelRec);
        Button playBack = (Button) findViewById(R.id.btnPlayRec);

        // set recording boolean flag to false
        isRecording = false;
        // set playing flag to false
        isPlaying = false;

        //get the output file where you want the recording to be stored
        final String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        // step 1 : setup MediaRecorder
        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(outputFile);
        // End step 1

        //step 2 : setup recording start/stop
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){
                    Toast.makeText(MainActivity.this,"Recording Stoped",Toast.LENGTH_SHORT).show();
                    audioRecorder.stop();
                    audioRecorder.release();
                    isRecording=false;
                    ((Button)v).setText("Record");


                }else {
                    try {
                        Toast.makeText(MainActivity.this,"Recording Started",Toast.LENGTH_SHORT).show();
                        audioRecorder.prepare();
                        audioRecorder.start();
                        isRecording=true;
                        ((Button)v).setText("Stop");
                    }catch (Exception ex){
                        Toast.makeText(MainActivity.this,"Recording Error",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // end of step 2

        player = new MediaPlayer();

        // step 3 : setup playback start/Stop

        playBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    player.stop();
                    player.release();
                    isPlaying = false;
                    ((Button)v).setText("Play");
                }else {

                    try {
                        player.setDataSource(outputFile);
                        player.prepare();
                        player.start();
                        isPlaying=true;
                        ((Button)v).setText("Stop");
                        Toast.makeText(MainActivity.this, "PlayBack Started", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"No recording to play please record first",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }
}
