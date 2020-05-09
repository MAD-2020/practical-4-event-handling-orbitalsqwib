package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    private CountDownTimer readyCDT;
    private CountDownTimer placerCDT;
    private TextView resultTextView;
    private static final String TAG = "Whack-A-Mole!";

    private final List<Button> buttonList = new ArrayList<>();
    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9
    };

    private Model.Game advancedGame;

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        readyCDT = new CountDownTimer(10*1000, 1000) {
            @Override
            public void onTick(long l) {
                Long timeRemainingInSeconds = l/1000;
                Log.v(TAG, "Ready CountDown!" + l/ 1000);
                String text = "Get ready in " + timeRemainingInSeconds.toString() + " seconds!";
                final Toast t = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                t.show();

                Timer killToast = new Timer();
                killToast.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        t.cancel();
                    }
                }, 1000);
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                readyCDT.cancel();
            }
        };

        readyCDT.start();
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */

        placerCDT = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New Mole Location!");
                advancedGame.ResetAndRespawnMole();
            }

            @Override
            public void onFinish() {
                placerCDT.start();
            }
        };

        placerCDT.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent dataReciever = getIntent();
        Integer advancedScore = dataReciever.getIntExtra("score", 0);
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<BUTTON_IDS.length; i++) {
                    if(BUTTON_IDS[i] == view.getId()) {
                        advancedGame.HandleHoleHit(i);
                        placerCDT.cancel();
                        placerCDT.start();
                        break;
                    }
                }
            }
        };

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button b = (Button) findViewById(id);
            b.setOnClickListener(listener);
            buttonList.add(b);
        }

        resultTextView = (TextView) findViewById(R.id.resultTextView);

        advancedGame = new Model.Game(buttonList, advancedScore);
        advancedGame.ResetAllMoles();
        advancedGame.LinkTextViewAsResultView(resultTextView);

        readyTimer();

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

}

