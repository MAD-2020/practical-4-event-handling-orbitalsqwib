package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function doCheck() decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    private static final String TAG = "Whack-A-Mole!";

    private TextView resultTextView;
    private Button holeButton1;
    private Button holeButton2;
    private Button holeButton3;

    private Model.Game basicGame;
    private List<Button> holeButtons;
    private List<Integer> holeButtonIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        holeButton1 = (Button)findViewById(R.id.holeButton1);
        holeButton2 = (Button)findViewById(R.id.holeButton2);
        holeButton3 = (Button)findViewById(R.id.holeButton3);
        Log.v(TAG, "Finished Pre-initialisation!\n");
    }
    @Override
    protected void onStart(){
        super.onStart();
        holeButtons = new ArrayList<>();
        holeButtons.add(holeButton1);
        holeButtons.add(holeButton2);
        holeButtons.add(holeButton3);

        holeButtonIDs = new ArrayList<>();
        holeButtonIDs.add(R.id.holeButton1);
        holeButtonIDs.add(R.id.holeButton2);
        holeButtonIDs.add(R.id.holeButton3);

        basicGame = new Model.Game(holeButtons, 0);
        basicGame.LinkTextViewAsResultView(resultTextView);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int holeClickedIndex = holeButtonIDs.indexOf(v.getId());
                basicGame.HandleHoleHit(holeClickedIndex);
                doCheck(basicGame.getScore());
            }
        };

        holeButton1.setOnClickListener(listener);
        holeButton2.setOnClickListener(listener);
        holeButton3.setOnClickListener(listener);

        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Integer score) {
        /* Checks score against requirements and proceeds accordingly.
            Triggers nextLevelQuery().
         */
        if (score > 0 && score % 10 == 0) {
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });

        AlertDialog alert = builder.create();
        Log.v(TAG, "Advance option given to user!");
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */

        Intent main2Activity = new Intent(this, Main2Activity.class);
        main2Activity.putExtra("score", basicGame.getScore());
        startActivity(main2Activity);
    }
}