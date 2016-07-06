package com.app.jonatan.enteratechihuahua.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.jonatan.enteratechihuahua.extras.Subcategory;
import com.app.jonatan.enteratechihuahua.test.R;

import java.util.List;

/**
 * Created by armando on 6/26/16.
 */
public class AdapterSubcategory extends
        RecyclerView.Adapter<AdapterSubcategory.SubcategoryViewHolder> {

    List<Subcategory> sub;
    private int mPreviousPosition = 0;

    public AdapterSubcategory(List<Subcategory> sub){
        this.sub = sub;
    }

    @Override
    public SubcategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategory, parent, false);
        SubcategoryViewHolder svh = new SubcategoryViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SubcategoryViewHolder holder, int position) {
        holder.subName.setText(sub.get(position).getSubName());
        holder.subIcon.setImageResource(sub.get(position).getIcon());


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("presiono cardview");
            }
        });
    }

    @Override
    public int getItemCount() {
        return sub.size();
    }

    public static class SubcategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView subName;
        ImageView subIcon;

        SubcategoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            subName = (TextView)itemView.findViewById(R.id.subName);
            subIcon = (ImageView)itemView.findViewById(R.id.subIcon);
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
