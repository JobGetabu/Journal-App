package com.job.jounalapp.ui;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.job.jounalapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.item_happy, R.id.item_fear, R.id.item_anger,
            R.id.item_sad, R.id.item_joy, R.id.item_disgust, R.id.item_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_happy:
                break;
            case R.id.item_fear:
                break;
            case R.id.item_anger:
                break;
            case R.id.item_sad:
                break;
            case R.id.item_joy:
                break;
            case R.id.item_disgust:
                break;
            case R.id.item_save:

                //TODO save item in database

                finish();
                break;
        }
    }
}
