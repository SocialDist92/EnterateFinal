package com.app.jonatan.enteratechihuahua.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.jonatan.enteratechihuahua.extras.Subcategory;
import com.app.jonatan.enteratechihuahua.pojo.Promotion;
import com.app.jonatan.enteratechihuahua.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by armando on 6/26/16.
 */
public class AdapterSubcategory extends
        RecyclerView.Adapter<AdapterSubcategory.SubcategoryViewHolder> {

    List<Subcategory> sub;
    List<Promotion> promotions;
    AdapterPromotion adapterPromotions;

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public void setAdapterPromotions(AdapterPromotion adapterPromotions) {
        this.adapterPromotions = adapterPromotions;
    }

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
        final int pos = position;


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("presiono cardview");
                System.out.println("promotions ---"+ promotions );
                System.out.println("=>" + sub.get(pos).getSubName());
                ArrayList<Promotion> promotionsSub = new ArrayList<>();
                for(Promotion promotion: promotions) {
                    final String text = promotion.getPlace().getSubCategory();
                    if (text.contains(sub.get(pos).getSubName())) {
                        promotionsSub.add(promotion);
                    }
                }
                //mListPromotions = promotionsRestaurants;
                System.out.println("promotions filtered ---"+ promotionsSub );
                adapterPromotions.setPromotions(promotionsSub);
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
