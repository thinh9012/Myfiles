package com.example.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.People;
import com.example.myapplication.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {
    private Context context;
    private List<People> lstPeople;

    public PeopleAdapter(Context context, List<People> lstPeople) {
        this.context = context;
        this.lstPeople = lstPeople;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View peopleView = layoutInflater.inflate(R.layout.people_row, parent, false);

        ViewHolder holder = new ViewHolder(peopleView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        People people = lstPeople.get(position);

        holder.txtName.setText(people.getName());
        holder.txtOld.setText(people.getOld().toString());
        holder.layoutRecycle.setBackgroundColor(people.getColor());
    }

    @Override
    public int getItemCount() {
        return lstPeople.size();
    }

    /**
     * Define ViewHolder
     */
    public class ViewHolder extends  RecyclerView.ViewHolder{
        private View itemView;
        public ImageView imgAvatar;
        public TextView txtName;
        public TextView txtOld;
        public LinearLayout layoutRecycle;

        public ViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtName);
            txtOld = itemView.findViewById(R.id.txtOld);
            layoutRecycle = itemView.findViewById(R.id.layoutRecycle);

            // Register event click row
            layoutRecycleClick();
        }

        private void layoutRecycleClick()
        {
            layoutRecycle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), txtName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
