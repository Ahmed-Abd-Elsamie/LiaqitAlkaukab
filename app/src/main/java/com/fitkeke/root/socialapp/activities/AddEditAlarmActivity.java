package com.fitkeke.root.socialapp.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.modules.Alarm;
import com.fitkeke.root.socialapp.services.AlarmReceiver;
import com.fitkeke.root.socialapp.services.LoadAlarmsService;
import com.fitkeke.root.socialapp.utilities.DatabaseHelper;
import com.fitkeke.root.socialapp.utilities.ViewUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

public final class AddEditAlarmActivity extends AppCompatActivity {

    public static final String ALARM_EXTRA = "alarm_extra";
    public static final String MODE_EXTRA = "mode_extra";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EDIT_ALARM,ADD_ALARM,UNKNOWN})
    @interface Mode{}
    public static final int EDIT_ALARM = 1;
    public static final int ADD_ALARM = 2;
    public static final int UNKNOWN = 0;

    private TimePicker mTimePicker;
    private EditText carb;
    private EditText protin;
    private EditText fats;
    private EditText aliaf;
    private CheckBox mMon, mTues, mWed, mThurs, mFri, mSat, mSun;
    private int mode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_alarm);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getToolbarTitle());

        //init views
        initViews();
        //check mode
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode_extra", 0);

        Toast.makeText(this, mode + "", Toast.LENGTH_SHORT).show();
        if (mode == 2){
            // add
        }else if (mode == 1){
            // edit
            final Alarm alarm = getAlarm();
            ViewUtils.setTimePickerTime(mTimePicker, alarm.getTime());
            carb.setText(alarm.getLabel());
            protin.setText(alarm.getProtin());
            fats.setText(alarm.getFats());
            aliaf.setText(alarm.getAliaf());
            setDayCheckboxes(alarm);
        }


    }


    private void setDayCheckboxes(Alarm alarm) {
        mMon.setChecked(alarm.getDay(Alarm.MON));
        mTues.setChecked(alarm.getDay(Alarm.TUES));
        mWed.setChecked(alarm.getDay(Alarm.WED));
        mThurs.setChecked(alarm.getDay(Alarm.THURS));
        mFri.setChecked(alarm.getDay(Alarm.FRI));
        mSat.setChecked(alarm.getDay(Alarm.SAT));
        mSun.setChecked(alarm.getDay(Alarm.SUN));
    }

    private void initViews() {
        mTimePicker = (TimePicker) findViewById(R.id.edit_alarm_time_picker);

        carb = (EditText) findViewById(R.id.edit_alarm_label);
        protin = (EditText) findViewById(R.id.edit_alarm_protin);
        fats = (EditText) findViewById(R.id.edit_alarm_fats);
        aliaf = (EditText) findViewById(R.id.edit_alarm_aliaf);


        mMon = (CheckBox) findViewById(R.id.edit_alarm_mon);
        mTues = (CheckBox) findViewById(R.id.edit_alarm_tues);
        mWed = (CheckBox) findViewById(R.id.edit_alarm_wed);
        mThurs = (CheckBox) findViewById(R.id.edit_alarm_thurs);
        mFri = (CheckBox) findViewById(R.id.edit_alarm_fri);
        mSat = (CheckBox) findViewById(R.id.edit_alarm_sat);
        mSun = (CheckBox) findViewById(R.id.edit_alarm_sun);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_alarm_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                break;
            case R.id.action_delete:
                delete();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Alarm getAlarm() {
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode_extra",UNKNOWN);
        switch (mode) {
            case EDIT_ALARM:
                return getIntent().getParcelableExtra(ALARM_EXTRA);
            case ADD_ALARM:
                final long id = DatabaseHelper.getInstance(this).addAlarm();
                LoadAlarmsService.launchLoadAlarmsService(this);
                return new Alarm(id);
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
    }

    private @Mode int getMode() {
        final @Mode int mode = getIntent().getIntExtra(MODE_EXTRA, UNKNOWN);
        return mode;
    }

    private String getToolbarTitle() {
        int titleResId;
        switch (getMode()) {
            case EDIT_ALARM:
                titleResId = R.string.edit_alarm;
                break;
            case ADD_ALARM:
                titleResId = R.string.add_alarm;
                break;
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
        return getString(titleResId);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    public static Intent buildAddEditAlarmActivityIntent(Context context, @Mode int mode) {
        final Intent i = new Intent(context, AddEditAlarmActivity.class);
        i.putExtra(MODE_EXTRA, mode);
        return i;
    }




    private void save() {

        final Alarm alarm = getAlarm();

        final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE, ViewUtils.getTimePickerMinute(mTimePicker));
        time.set(Calendar.HOUR_OF_DAY, ViewUtils.getTimePickerHour(mTimePicker));
        alarm.setTime(time.getTimeInMillis());

        alarm.setLabel(carb.getText().toString());
        alarm.setProtin(protin.getText().toString());
        alarm.setFats(fats.getText().toString());
        alarm.setAliaf(aliaf.getText().toString());
        if (mode == 2){
            alarm.setTotalscore("0");
            alarm.setMyscore("0");
        }else if (mode == 1){

        }


        alarm.setDay(Alarm.MON, mMon.isChecked());
        alarm.setDay(Alarm.TUES, mTues.isChecked());
        alarm.setDay(Alarm.WED, mWed.isChecked());
        alarm.setDay(Alarm.THURS, mThurs.isChecked());
        alarm.setDay(Alarm.FRI, mFri.isChecked());
        alarm.setDay(Alarm.SAT, mSat.isChecked());
        alarm.setDay(Alarm.SUN, mSun.isChecked());

        final int rowsUpdated = DatabaseHelper.getInstance(AddEditAlarmActivity.this).updateAlarm(alarm);
        final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;

        Toast.makeText(AddEditAlarmActivity.this, messageId, Toast.LENGTH_SHORT).show();

        AlarmReceiver.setReminderAlarm(AddEditAlarmActivity.this, alarm);

        AddEditAlarmActivity.this.finish();

    }

    private void delete() {

        final Alarm alarm = getAlarm();

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(AddEditAlarmActivity.this, R.style.DeleteAlarmDialogTheme);
        builder.setTitle(R.string.delete_dialog_title);
        builder.setMessage(R.string.delete_dialog_content);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Cancel any pending notifications for this alarm
                AlarmReceiver.cancelReminderAlarm(AddEditAlarmActivity.this, alarm);

                final int rowsDeleted = DatabaseHelper.getInstance(AddEditAlarmActivity.this).deleteAlarm(alarm);
                int messageId;
                if(rowsDeleted == 1) {
                    messageId = R.string.delete_complete;
                    Toast.makeText(AddEditAlarmActivity.this, messageId, Toast.LENGTH_SHORT).show();
                    LoadAlarmsService.launchLoadAlarmsService(AddEditAlarmActivity.this);
                    AddEditAlarmActivity.this.finish();
                } else {
                    messageId = R.string.delete_failed;
                    Toast.makeText(AddEditAlarmActivity.this, messageId, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();

    }

}