package com.fitkeke.root.socialapp.activities.sports_excersises;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitkeke.root.socialapp.R;

import java.util.LinkedList;

public class GymActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recycler_exc);

        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.add("تمارين الصدر");
        linkedList.add("تمارين الظهر");
        linkedList.add("تمارين الكتف");
        linkedList.add("تمارين التراي");
        linkedList.add("تمارين الرجل");
        linkedList.add("تمارين السمانه");
        linkedList.add("تمارين الساعد والريست");



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecAdapter(this, linkedList);
        recyclerView.setAdapter(adapter);

    }

    class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{

        LinkedList<String> list;
        LayoutInflater inflater;


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.item_excersises, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String name = list.get(i);
            viewHolder.textView.setText(name);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.txt_exc);
            }
        }

        RecAdapter(Context context, LinkedList<String> list){
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }
    }
}
