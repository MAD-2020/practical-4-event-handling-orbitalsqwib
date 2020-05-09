package sg.edu.np.WhackAMole;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class Model {

    private static final String TAG = "Whack-A-Mole";

    public static class Game {

        public class MoleHole {
            private Boolean hasMole;
            private Button linkedButton;

            public MoleHole(Button b) {
                this.hasMole = false;
                this.linkedButton = b;
            }

            public Boolean getHasMole() {
                return hasMole;
            }

            public void setHasMole(Boolean value) {
                this.hasMole = value;
                UpdateLinkedButtonToReflectModel();
            }

            public void SpawnMole() {
                setHasMole(true);
            }

            public void UpdateLinkedButtonToReflectModel() {
                if (hasMole) {
                    linkedButton.setText(R.string.notempty);
                } else {
                    linkedButton.setText(R.string.empty);
                }
            }
        }

        public List<MoleHole> moleHoleList = new ArrayList<>();
        private TextView resultView;
        private Integer numberOfHoles;
        private Integer score;
        private Boolean logButtonName;

        public Game(List<Button> holeButtonList, Integer initialScore) {
            this.score = initialScore;
            this.numberOfHoles = holeButtonList.size();
            for(int i=0; i<numberOfHoles; i++) {
                this.moleHoleList.add(new MoleHole(holeButtonList.get(i)));
            }
            ResetAndRespawnMole();
        }

        public Integer getScore() { return this.score;}

        private void setScore(int value) {
            this.score = value;
            UpdateResultViewToMatchModel();
        }

        public void ResetAllMoles() {
            for (MoleHole hole: moleHoleList) {
                hole.setHasMole(false);
            }
        }

        public void ResetAndRespawnMole() {
            int randomHoleIndex = (int)(Math.random() * numberOfHoles);
            ResetAllMoles();
            moleHoleList.get(randomHoleIndex).SpawnMole();
        }

        public void HandleHoleHit(int holeIndex) {
            boolean hitMole = moleHoleList.get(holeIndex).getHasMole();
            ResetAndRespawnMole();
            System.out.print("Whack-A-Mole: ");
            if (logButtonName) {
                switch (holeIndex) {
                    case 0:
                        Log.v(TAG, "Button Left Clicked!");
                        break;
                    case 1:
                        Log.v(TAG, "Button Middle Clicked!");
                        break;
                    case 2:
                        Log.v(TAG, "Button Right Clicked!");
                        break;
                    default:
                        Log.v(TAG, "Unknown Button Clicked! Did you forget to add cases for new buttons?");
                }
            }
            if (hitMole) {
                setScore(score+1);
                Log.v(TAG, "Hit, score added!\n");
            } else {
                setScore(score-1);
                Log.v(TAG, "Missed, score deducted!\n");
            }
        }

        public void LinkTextViewAsResultView(TextView t) {
            this.resultView = t;
        }

        public void UpdateResultViewToMatchModel() {
            resultView.setText(score.toString());
        }

    }
}
