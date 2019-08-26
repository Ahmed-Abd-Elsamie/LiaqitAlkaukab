package com.fitkeke.root.socialapp.modules;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.util.SparseBooleanArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Alarm implements Parcelable {

    private Alarm(Parcel in) {
        id = in.readLong();
        time = in.readLong();
        label = in.readString();
        protin = in.readString();
        fats = in.readString();
        aliaf = in.readString();
        myscore = in.readString();
        totalscore = in.readString();

        allDays = in.readSparseBooleanArray();
        isEnabled = in.readByte() != 0;
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(time);
        parcel.writeString(label);
        parcel.writeString(protin);
        parcel.writeString(fats);
        parcel.writeString(aliaf);
        parcel.writeString(myscore);
        parcel.writeString(totalscore);
        parcel.writeSparseBooleanArray(allDays);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MON,TUES,WED,THURS,FRI,SAT,SUN})
    @interface Days{}
    public static final int MON = 1;
    public static final int TUES = 2;
    public static final int WED = 3;
    public static final int THURS = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
    public static final int SUN = 7;

    private static final long NO_ID = -1;

    private final long id;
    private long time;
    private String label;
    private String protin;
    private String fats;
    private String aliaf;
    private String myscore;
    private String totalscore;
    private SparseBooleanArray allDays;
    private boolean isEnabled;

    public Alarm() {
        this(NO_ID);
    }

    public Alarm(long id) {
        this(id, System.currentTimeMillis());
    }

    public Alarm(long id, long time, @Days int... days) {
        this(id, time,null, null, null, null, null, null, days);
    }

    public Alarm(long id, long time, String label, String protin, String fats, String aliaf, String myscore, String totalscore, @Days int... days) {
        this.id = id;
        this.time = time;
        this.label = label;
        this.protin = protin;
        this.fats = fats;
        this.aliaf = aliaf;
        this.myscore = myscore;
        this.totalscore = totalscore;
        this.allDays = buildDaysArray(days);
    }

    public long getId() {
        return id;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getProtin() {
        return protin;
    }

    public void setProtin(String protin) {
        this.protin = protin;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getAliaf() {
        return aliaf;
    }

    public void setAliaf(String aliaf) {
        this.aliaf = aliaf;
    }

    public String getMyscore() {
        return myscore;
    }

    public void setMyscore(String myscore) {
        this.myscore = myscore;
    }

    public String getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(String totalscore) {
        this.totalscore = totalscore;
    }

    public void setDay(@Days int day, boolean isAlarmed) {
        allDays.append(day, isAlarmed);
    }

    public SparseBooleanArray getDays() {
        return allDays;
    }

    public boolean getDay(@Days int day){
        return allDays.get(day);
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public int notificationId() {
        final long id = getId();
        return (int) (id^(id>>>32));
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", time=" + time +
                ", label='" + label + '\'' +
                ", protin='" + protin + '\'' +
                ", fats='" + fats + '\'' +
                ", aliaf='" + aliaf + '\'' +
                ", myscore='" + myscore + '\'' +
                ", totalscore='" + totalscore + '\'' +
                ", allDays=" + allDays +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (id^(id>>>32));
        result = 31 * result + (int) (time^(time>>>32));
        result = 31 * result + label.hashCode();
        result = 31 * result + protin.hashCode();
        result = 31 * result + fats.hashCode();
        result = 31 * result + aliaf.hashCode();
        result = 31 * result + myscore.hashCode();
        result = 31 * result + totalscore.hashCode();
        for(int i = 0; i < allDays.size(); i++) {
            result = 31 * result + (allDays.valueAt(i)? 1 : 0);
        }
        return result;
    }

    private static SparseBooleanArray buildDaysArray(@Days int... days) {

        final SparseBooleanArray array = buildBaseDaysArray();

        for (@Days int day : days) {
            array.append(day, true);
        }

        return array;

    }

    private static SparseBooleanArray buildBaseDaysArray() {

        final int numDays = 7;

        final SparseBooleanArray array = new SparseBooleanArray(numDays);

        array.put(MON, false);
        array.put(TUES, false);
        array.put(WED, false);
        array.put(THURS, false);
        array.put(FRI, false);
        array.put(SAT, false);
        array.put(SUN, false);

        return array;

    }

}