package com.fitkeke.root.socialapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fitkeke.root.socialapp.activities.FoodProgramActivity;
import com.fitkeke.root.socialapp.modules.ItemFood;
import com.fitkeke.root.socialapp.utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>  {

    private List<ItemFood> list;
    private Activity context;
    private DBHelper dbHelper;

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup, false);
        FoodAdapter.ViewHolder vh = new FoodAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodAdapter.ViewHolder viewHolder, int i) {

        final ItemFood itemFood = list.get(i);
        viewHolder.txtCarb.setText(itemFood.getCarb());
        viewHolder.txtProtin.setText(itemFood.getProtin());
        viewHolder.txtFats.setText(itemFood.getFats());
        viewHolder.txtAliaf.setText(itemFood.getAliaf());

        viewHolder.txtTime.setText(itemFood.getTime());
        if (itemFood.getState().equals("eaten")){
            viewHolder.ImgState.setVisibility(View.VISIBLE);
            viewHolder.ImgState.setBackgroundResource(R.drawable.ic_done_black_24dp);
            viewHolder.btnEdit.setVisibility(View.GONE);

        }else if (itemFood.getState().equals("wait")){
            // keep unchanged
        }else if (itemFood.getState().equals("noeat")){
            viewHolder.ImgState.setVisibility(View.VISIBLE);
            viewHolder.ImgState.setBackgroundResource(R.drawable.ic_close_black_24dp);
            viewHolder.btnEdit.setVisibility(View.GONE);
        }


        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(context, FoodAlarmSet.class);
                intent.putExtra("id", itemFood.getId());
                intent.putExtra("name", itemFood.getName());
                intent.putExtra("time", itemFood.getTime());
                intent.putExtra("state", itemFood.getState());
                intent.putExtra("type", "edit");
                context.startActivity(intent);*/

                // create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                View view = inflater.inflate(R.layout.add_food_for_day,null);
                builder.setView(view);
                Button btnAdd = (Button)view.findViewById(R.id.btn_add_food_dialog);
                Button btnDel = (Button)view.findViewById(R.id.btn_del_food_dialog);
                final EditText txtCarb = (EditText)view.findViewById(R.id.txt_food_day_carb);
                final EditText txtProtin = (EditText)view.findViewById(R.id.txt_food_day_protin);
                final EditText txtFats = (EditText)view.findViewById(R.id.txt_food_day_fats);
                final EditText txtAliaf = (EditText)view.findViewById(R.id.txt_food_day_aliaf);
                final TimePicker timePicker = (TimePicker)view.findViewById(R.id.time_pick);

                txtCarb.setText(itemFood.getCarb());
                txtProtin.setText(itemFood.getProtin());
                txtFats.setText(itemFood.getFats());
                txtAliaf.setText(itemFood.getAliaf());

                String t = itemFood.getTime();
                int hr = Integer.parseInt(t.substring(0, t.indexOf(":")));
                int mins = Integer.parseInt(t.substring(t.indexOf(":") + 1, t.length()));
                timePicker.setCurrentHour(hr);
                timePicker.setCurrentMinute(mins);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dbHelper.updateFood(itemFood.getId(), txtCarb.getText().toString(), txtProtin.getText().toString(),txtFats.getText().toString(),txtAliaf.getText().toString(),timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), itemFood.getRequest(), "wait");

                    }
                });

                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // delete item
                        dbHelper.deleteFood(itemFood.getId());
                        // get last request code
                        SharedPreferences prefs = context.getSharedPreferences("food_alarm_counter", MODE_PRIVATE);
                        int request = prefs.getInt("count",102);
                        // increment counter
                        SharedPreferences.Editor editor = context.getSharedPreferences("food_alarm_counter", MODE_PRIVATE).edit();
                        editor.putInt("count", request + 1);
                        editor.apply();
                        Toast.makeText(context, "تم الحذف !", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public FoodAdapter(ArrayList<ItemFood> foodList , Activity mContext) {
        list = foodList;
        context = mContext;
        dbHelper = new DBHelper(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCarb;
        public TextView txtProtin;
        public TextView txtFats;
        public TextView txtAliaf;

        public TextView txtTime;
        public Button btnEdit;
        public ImageView ImgState;
        public ViewHolder(View v) {
            super(v);
            txtCarb = (TextView) v.findViewById(R.id.food_carb);
            txtProtin = (TextView) v.findViewById(R.id.food_protin);
            txtFats = (TextView) v.findViewById(R.id.food_fats);
            txtAliaf = (TextView) v.findViewById(R.id.food_aliaf);

            txtTime = (TextView) v.findViewById(R.id.food_time);
            btnEdit = (Button) v.findViewById(R.id.btn_edit_food);
            ImgState = (ImageView) v.findViewById(R.id.img_food_state);

        }
    }
}