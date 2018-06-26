package com.job.jounalapp.util;

import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.jounalapp.R;
import com.job.jounalapp.datasource.Dairy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Job on Monday : 6/25/2018.
 * <p>
 * <p>
 * ViewHolder class that injects the Daily pojo into the view.
 */
public class DairyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.single_chip_mood)
    Chip singleChipMood;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.single_details)
    TextView singleDetails;
    @BindView(R.id.single_chip_day)
    Chip singleChipDay;
    @BindView(R.id.single_chip_date)
    Chip singleChipDate;
    @BindView(R.id.single_edit)
    Button singleEdit;
    @BindView(R.id.single_delete)
    Button singleDelete;
    @BindView(R.id.single_bottom)
    LinearLayout singleBottom;
    @BindView(R.id.single_item)
    MaterialCardView singleItem;

    private int on = 0;

    private FirebaseFirestore mFirestore;
    public static final String TAG = "ListVH";

    public DairyViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        //initially hidden
        singleBottom.setVisibility(View.GONE);

        mFirestore = FirebaseFirestore.getInstance();
    }


    @OnClick({R.id.single_chip_mood, R.id.horizontalScrollView, R.id.single_details,
            R.id.single_chip_day, R.id.single_chip_date, R.id.single_bottom, R.id.single_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.single_chip_mood:
                break;
            case R.id.horizontalScrollView:
                break;
            case R.id.single_details:
                break;
            case R.id.single_chip_day:
                break;
            case R.id.single_chip_date:
                break;
            case R.id.single_bottom:
                break;
            case R.id.single_item:
                break;
        }

        if (on == 0) {
            singleBottom.setVisibility(View.VISIBLE);
            on = 1;
        } else {
            singleBottom.setVisibility(View.GONE);
            on = 0;
        }
    }

    @OnClick(R.id.single_delete)
    public void onSingleDeleteClicked() {

        //todo: delete this item in the database

    }

    public void setUpListItem(Dairy model) {
        if (model != null) {
            singleChipMood.setChipText(model.getMoods());
            singleDetails.setText(model.getDetails());

            if (model.getTimestamp() != null) {

                Timestamp timestamp = model.getTimestamp();
                Date date = timestamp.toDate();

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                singleChipDay.setChipText(theDay(dayOfWeek));

                DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                singleChipDate.setChipText(dateFormat.format(date));
            }
        }
    }

    private String theDay(int day) {
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }

}
