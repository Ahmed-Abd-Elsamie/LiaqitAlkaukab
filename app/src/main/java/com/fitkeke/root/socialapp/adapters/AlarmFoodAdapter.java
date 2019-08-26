package com.fitkeke.root.socialapp.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitkeke.root.socialapp.FoodAdapter;
import com.fitkeke.root.socialapp.R;
import com.fitkeke.root.socialapp.modules.Alarm;
import com.fitkeke.root.socialapp.modules.ItemFood;
import com.fitkeke.root.socialapp.utilities.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AlarmFoodAdapter extends RecyclerView.Adapter<AlarmFoodAdapter.ViewHolder> {

    private List<Alarm> list;
    private Activity context;
    private DBHelper dbHelper;

    @NonNull
    @Override
    public AlarmFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup, false);
        AlarmFoodAdapter.ViewHolder vh = new AlarmFoodAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmFoodAdapter.ViewHolder viewHolder, int i) {
        final Alarm itemFood = list.get(i);
        viewHolder.txtCarb.setText(itemFood.getLabel());
        viewHolder.txtProtin.setText(itemFood.getProtin());
        viewHolder.txtFats.setText(itemFood.getFats());
        viewHolder.txtAliaf.setText(itemFood.getAliaf());
    }

    @Override
    public int getItemCount() {
        return list.size();
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

    public AlarmFoodAdapter(ArrayList<Alarm> foodList , Activity mContext){
        list = foodList;
        context = mContext;
        dbHelper = new DBHelper(context);
    }
}
