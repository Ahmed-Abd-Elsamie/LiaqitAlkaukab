package com.fitkeke.root.socialapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.activities.AddEditAlarmActivity;
import com.fitkeke.root.socialapp.modules.Alarm;
import com.fitkeke.root.socialapp.utilities.AlarmUtils;

import java.util.List;
/*
public final class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private List<Alarm> mAlarms;
    private String[] mDays;
    private int mAccentColor = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context c = parent.getContext();
        final View v = LayoutInflater.from(c).inflate(R.layout.item_alarm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Context c = holder.itemView.getContext();

        if(mAccentColor == -1) {
            mAccentColor = ContextCompat.getColor(c, R.color.accent);
        }

        if(mDays == null){
            mDays = c.getResources().getStringArray(R.array.days_abbreviated);
        }

        final Alarm alarm = mAlarms.get(position);

        holder.time.setText(AlarmUtils.getReadableTime(alarm.getTime()));
        holder.amPm.setText(AlarmUtils.getAmPm(alarm.getTime()));

        if(alarm.getLabel().equals("")){
            holder.label.setVisibility(View.GONE);
        }else {
            holder.label.setVisibility(View.VISIBLE);
        }
        if (alarm.getProtin().equals("")){
            holder.protin.setVisibility(View.GONE);
        }else {
            holder.protin.setVisibility(View.VISIBLE);
        }
        if (alarm.getFats().equals("")){
            holder.fats.setVisibility(View.GONE);
        }else {
            holder.fats.setVisibility(View.VISIBLE);
        }
        if (alarm.getAliaf().equals("")){
            holder.aliaf.setVisibility(View.GONE);
        }else {
            holder.aliaf.setVisibility(View.VISIBLE);
        }

        holder.label.setText(alarm.getLabel());
        holder.protin.setText(alarm.getProtin());
        holder.fats.setText(alarm.getFats());
        holder.aliaf.setText(alarm.getAliaf());
        holder.score.setText(alarm.getMyscore() + "/" + alarm.getTotalscore());

        holder.days.setText(buildSelectedDays(alarm));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context c = view.getContext();
                final Intent launchEditAlarmIntent =
                        AddEditAlarmActivity.buildAddEditAlarmActivityIntent(
                                c, AddEditAlarmActivity.EDIT_ALARM
                        );
                launchEditAlarmIntent.putExtra(AddEditAlarmActivity.ALARM_EXTRA, alarm);
                c.startActivity(launchEditAlarmIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mAlarms == null) ? 0 : mAlarms.size();
    }

    private Spannable buildSelectedDays(Alarm alarm) {

        final int numDays = 7;
        final SparseBooleanArray days = alarm.getDays();

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        ForegroundColorSpan span;

        int startIndex, endIndex;
        for (int i = 0; i < numDays; i++) {

            startIndex = builder.length();

            final String dayText = mDays[i];
            builder.append(dayText);
            builder.append(" ");

            endIndex = startIndex + dayText.length();

            final boolean isSelected = days.valueAt(i);
            if(isSelected) {
                span = new ForegroundColorSpan(mAccentColor);
                builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return builder;

    }

    public void setAlarms(List<Alarm> alarms) {
        mAlarms = alarms;
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        TextView time, amPm, label, protin, fats, aliaf, days, score;

        ViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.ar_time);
            amPm = (TextView) itemView.findViewById(R.id.ar_am_pm);
            label = (TextView) itemView.findViewById(R.id.ar_label);
            protin = (TextView) itemView.findViewById(R.id.ar_protin);
            fats = (TextView) itemView.findViewById(R.id.ar_fats);
            aliaf = (TextView) itemView.findViewById(R.id.ar_aliaf);
            score = (TextView) itemView.findViewById(R.id.txt_score);

            days = (TextView) itemView.findViewById(R.id.ar_days);

        }
    }

}

*/
public final class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private List<Alarm> mAlarms;
    private String[] mDays;
    private int mAccentColor = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context c = parent.getContext();
        final View v = LayoutInflater.from(c).inflate(R.layout.item_alarm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Context c = holder.itemView.getContext();

        if(mAccentColor == -1) {
            mAccentColor = ContextCompat.getColor(c, R.color.accent);
        }

        if(mDays == null){
            mDays = c.getResources().getStringArray(R.array.days_abbreviated);
        }

        final Alarm alarm = mAlarms.get(position);

        holder.time.setText(AlarmUtils.getReadableTime(alarm.getTime()));
        holder.amPm.setText(AlarmUtils.getAmPm(alarm.getTime()));

        if(alarm.getLabel().equals("")){
            holder.label.setVisibility(View.GONE);
        }else {
            holder.label.setVisibility(View.VISIBLE);
        }
        if (alarm.getProtin().equals("")){
            holder.protin.setVisibility(View.GONE);
        }else {
            holder.protin.setVisibility(View.VISIBLE);
        }
        if (alarm.getFats().equals("")){
            holder.fats.setVisibility(View.GONE);
        }else {
            holder.fats.setVisibility(View.VISIBLE);
        }
        if (alarm.getAliaf().equals("")){
            holder.aliaf.setVisibility(View.GONE);
        }else {
            holder.aliaf.setVisibility(View.VISIBLE);
        }

        holder.label.setText(alarm.getLabel());
        holder.protin.setText(alarm.getProtin());
        holder.fats.setText(alarm.getFats());
        holder.aliaf.setText(alarm.getAliaf());
        holder.score.setText(alarm.getMyscore());
        holder.totalScore.setText(alarm.getTotalscore() + " /");

        holder.days.setText(buildSelectedDays(alarm));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context c = view.getContext();
                final Intent launchEditAlarmIntent =
                        AddEditAlarmActivity.buildAddEditAlarmActivityIntent(
                                c, AddEditAlarmActivity.EDIT_ALARM
                        );
                launchEditAlarmIntent.putExtra(AddEditAlarmActivity.ALARM_EXTRA, alarm);
                c.startActivity(launchEditAlarmIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mAlarms == null) ? 0 : mAlarms.size();
    }

    private Spannable buildSelectedDays(Alarm alarm) {

        final int numDays = 7;
        final SparseBooleanArray days = alarm.getDays();

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        ForegroundColorSpan span;

        int startIndex, endIndex;
        for (int i = 0; i < numDays; i++) {

            startIndex = builder.length();

            final String dayText = mDays[i];
            builder.append(dayText);
            builder.append(" ");

            endIndex = startIndex + dayText.length();

            final boolean isSelected = days.valueAt(i);
            if(isSelected) {
                span = new ForegroundColorSpan(mAccentColor);
                builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return builder;

    }

    public void setAlarms(List<Alarm> alarms) {
        mAlarms = alarms;
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        TextView time, amPm, label, protin, fats, aliaf, days, totalScore, score;

        ViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.ar_time);
            amPm = (TextView) itemView.findViewById(R.id.ar_am_pm);
            label = (TextView) itemView.findViewById(R.id.ar_label);
            protin = (TextView) itemView.findViewById(R.id.ar_protin);
            fats = (TextView) itemView.findViewById(R.id.ar_fats);
            aliaf = (TextView) itemView.findViewById(R.id.ar_aliaf);
            score = (TextView) itemView.findViewById(R.id.txt_score);
            totalScore = (TextView) itemView.findViewById(R.id.txt_total_score);

            days = (TextView) itemView.findViewById(R.id.ar_days);

        }
    }

}
