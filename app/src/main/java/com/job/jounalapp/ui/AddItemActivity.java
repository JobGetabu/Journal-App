package com.job.jounalapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.jounalapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.job.jounalapp.util.Constants.DAIRYCOL;
import static com.job.jounalapp.util.Constants.DAIRYIDEXTRA;

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.item_happy)
    Chip itemHappy;
    @BindView(R.id.item_fear)
    Chip itemFear;
    @BindView(R.id.item_anger)
    Chip itemAnger;
    @BindView(R.id.item_sad)
    Chip itemSad;
    @BindView(R.id.item_joy)
    Chip itemJoy;
    @BindView(R.id.item_disgust)
    Chip itemDisgust;
    @BindView(R.id.chipGroup2)
    ChipGroup chipGroup2;
    @BindView(R.id.item_details)
    TextInputLayout itemDetails;
    @BindView(R.id.item_save)
    MaterialButton itemSave;
    @BindView(R.id.item_toolbar)
    Toolbar itemToolbar;
    @BindView(R.id.item_anticipation)
    Chip itemAnticipation;
    @BindView(R.id.item_trust)
    Chip itemTrust;
    @BindView(R.id.item_love)
    Chip itemLove;
    @BindView(R.id.item_shame)
    Chip itemShame;

    private String stringIdExtra = "";
    private String moods = "";
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);

        setSupportActionBar(itemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //firebase
        mFirestore = FirebaseFirestore.getInstance();

        stringIdExtra = getIntent().getStringExtra(DAIRYIDEXTRA);

        if (!stringIdExtra.isEmpty()) {
            //load ui data for editing

            mFirestore.collection(DAIRYCOL).document(stringIdExtra).get()
                    .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                            } else {

                            }
                        }
                    });
        }
    }

    @OnClick(R.id.item_save)
    public void onSave(View view) {

        String details = itemDetails.getEditText().getText().toString();

                finish();
    }

    private void saveToDb() {


    }

    @OnClick({R.id.item_happy, R.id.item_fear, R.id.item_anger, R.id.item_sad, R.id.item_joy, R.id.item_disgust, R.id.item_anticipation, R.id.item_trust, R.id.item_love, R.id.item_shame})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_happy:
                moods = "Happy";
                break;
            case R.id.item_fear:
                moods = "Fear";
                break;
            case R.id.item_anger:
                moods = "anger";
                break;
            case R.id.item_sad:
                moods = "Sad";
                break;
            case R.id.item_joy:
                moods = "Joy";
                break;
            case R.id.item_disgust:
                moods = "Disgust";
                break;
            case R.id.item_anticipation:
                moods = "Anticipation";
                break;
            case R.id.item_trust:
                moods = "Trust";
                break;
            case R.id.item_love:
                moods = "Love";
                break;
            case R.id.item_shame:
                moods = "Shame";
                break;
        }
    }
}
