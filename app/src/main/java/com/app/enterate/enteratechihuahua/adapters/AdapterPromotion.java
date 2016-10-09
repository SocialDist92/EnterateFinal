package com.app.enterate.enteratechihuahua.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.app.enterate.enteratechihuahua.activities.PromotionDetail;
import com.app.enterate.enteratechihuahua.extras.Constants;
import com.app.enterate.enteratechihuahua.network.VolleySingleton;
import com.app.enterate.enteratechihuahua.pojo.Promotion;
import com.app.enterate.enteratechihuahua.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 11/05/2016.
 */
public class AdapterPromotion extends RecyclerView.Adapter<AdapterPromotion.ViewHolderPromotion> {

    private ArrayList<Promotion> mListPromotions = new ArrayList<>();
    private LayoutInflater mInflater;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private int mPreviousPosition = 0;

    public AdapterPromotion(Context context) {
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
    }

    public void setPromotions(ArrayList<Promotion> promotions) {
        this.mListPromotions = promotions;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderPromotion onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_item_promotion, parent, false);
        ViewHolderPromotion viewHolder = new ViewHolderPromotion(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderPromotion holder, int position) {
        final Promotion currentPromotion = mListPromotions.get(position);
        holder.promotionName.setText(currentPromotion.getPlace().getName());
        holder.promotionDescription.setText(currentPromotion.getDescription());
        holder.promotionAudienceScore.setRating(4.0F);
        holder.promotionAudienceScore.setAlpha(0.5F);


        mPreviousPosition  = position;

        String urlLogo = currentPromotion.getPlace().getUrlImageLogo();
        loadImages(urlLogo, holder, position);

        holder.promotionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PromotionDetail.class);
                intent.putExtra("promotion", currentPromotion);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListPromotions.size();
    }

    private void loadImages(String urlThumbnail, final ViewHolderPromotion holder, final int pos) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.promotionLogo.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, 85, 85);
        }
    }

    public void setFilter(List<Promotion> models) {
        mListPromotions = new ArrayList<>();
        mListPromotions.addAll(models);
        System.out.println("setFilter");
        notifyDataSetChanged();
    }

    static class ViewHolderPromotion extends RecyclerView.ViewHolder {
        View promotionView;
        ImageView promotionLogo;
        TextView promotionName;
        TextView promotionDescription;
        RatingBar promotionAudienceScore;

        public ViewHolderPromotion(View itemView) {
            super(itemView);
            promotionView = itemView;
            promotionLogo = (ImageView) itemView.findViewById(R.id.promotionLogo);
            promotionName = (TextView) itemView.findViewById(R.id.promotionName);
            promotionDescription = (TextView) itemView.findViewById(R.id.promotionDescription);
            promotionAudienceScore = (RatingBar) itemView.findViewById(R.id.promotionAudienceScore);
        }
    }
}
