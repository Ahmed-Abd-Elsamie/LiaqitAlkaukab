package com.fitkeke.root.socialapp.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.modules.Alarm;
import com.fitkeke.root.socialapp.services.AlarmReceiver;
import com.fitkeke.root.socialapp.utilities.DatabaseHelper;

import static android.content.Context.MODE_PRIVATE;

public final class AlarmLandingPageFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_alarm_landing_page, container, false);
        Alarm alarm = getAlarm(getAlarmId());
        final Button btnEat = (Button) v.findViewById(R.id.eat_now);
        final Button btnNotEat = (Button) v.findViewById(R.id.not_now);
        final Button delay = (Button) v.findViewById(R.id.delay_btn);
        final ImageView imgFood = (ImageView) v.findViewById(R.id.img_food);
        final TextView txtDesc = (TextView) v.findViewById(R.id.txt_desc);

        imgFood.setImageResource(R.drawable.food);
        if(!alarm.getLabel().equals("")){
            txtDesc.setText(alarm.getLabel());
        }
        if (!alarm.getProtin().equals("")){
            txtDesc.append(" + " + alarm.getProtin());
        }
        if (alarm.getFats().equals("")){
            txtDesc.append(" + " + alarm.getFats());
        }
        if (alarm.getAliaf().equals("")){
            txtDesc.append(" + " + alarm.getAliaf());
        }
        btnEat.setOnClickListener(this);
        btnNotEat.setOnClickListener(this);
        delay.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.eat_now:

                // eat now
                final Alarm alarm = getAlarm(getAlarmId());
                alarm.setMyscore((Integer.parseInt(alarm.getMyscore()) + 1 ) + "");
                alarm.setTotalscore((Integer.parseInt(alarm.getTotalscore()) + 1 ) + "");
                // save
                save(alarm);
                startActivity(new Intent(getContext(), FoodProgramActivity.class));
                getActivity().finish();

                break;
            case R.id.not_now:

                // don't eat
                final Alarm alarm2 = getAlarm(getAlarmId());
                alarm2.setTotalscore((Integer.parseInt(alarm2.getTotalscore()) + 1 ) + "");
                getActivity().finish();
                //save
                save(alarm2);

                break;

            case R.id.delay_btn:
                save();
                break;
        }

    }

    private long getAlarmId() {
        SharedPreferences prefs = getContext().getSharedPreferences("alarm_id", MODE_PRIVATE);
        long id = prefs.getLong("id", 0);
        Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
        return id;
    }

    private void save() {
        final Alarm alarm = getAlarm(getAlarmId());
        alarm.setTime(System.currentTimeMillis() + 5 * 60 * 1000);
        final int rowsUpdated = DatabaseHelper.getInstance(getContext()).updateAlarm(alarm);
        final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;
        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
        AlarmReceiver.setReminderAlarm(getContext(), alarm);
        Toast.makeText(getContext(), "تم تأجيل الوجبه", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    private void save(Alarm alarm) {
        final int rowsUpdated = DatabaseHelper.getInstance(getContext()).updateAlarm(alarm);
        final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;
        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
        AlarmReceiver.setReminderAlarm(getContext(), alarm);
        getActivity().finish();
    }

    private Alarm getAlarm(long id) {

        for (int i = 0; i < DatabaseHelper.getInstance(getContext()).getAlarms().size(); i++){
            if (DatabaseHelper.getInstance(getContext()).getAlarms().get(i).getId() == id){
                return DatabaseHelper.getInstance(getContext()).getAlarms().get(i);
            }
        }
        return null;
    }

}