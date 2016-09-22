package com.app.enterate.enteratechihuahua.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.enterate.enteratechihuahua.adapters.AdapterPromotion;
import com.app.enterate.enteratechihuahua.callbacks.PromotionsLoadedListener;
import com.app.enterate.enteratechihuahua.logging.L;
import com.app.enterate.enteratechihuahua.pojo.Promotion;
import com.app.enterate.enteratechihuahua.tasks.TaskLoadPromotions;
import com.app.enterate.enteratechihuahua.test.R;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements PromotionsLoadedListener,
        SwipeRefreshLayout.OnRefreshListener {

    String query;
    private RecyclerView mRecyclerPromotions;
    private AdapterPromotion mAdapter;
    private ArrayList<Promotion> mListPromotions = new ArrayList<>();
    private ArrayList<Promotion> searchPromotions = new ArrayList<>();
    private RecyclerView.LayoutManager lManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.search_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipePromotions);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerPromotions = (RecyclerView) findViewById(R.id.searchList);
        mRecyclerPromotions.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AdapterPromotion(this);


        Intent searchIntent = getIntent();

        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            query = searchIntent.getStringExtra(SearchManager.QUERY).toLowerCase();
            getSupportActionBar().setTitle(query);

            if (mListPromotions.isEmpty()) {
                L.m("Searcher");
                new TaskLoadPromotions(this).execute();
            }

            for(Promotion promotion: mListPromotions) {
                query.toLowerCase();
                final String text = promotion.getName().toLowerCase();
                final String namePlace = promotion.getPlace().getName().toLowerCase();

                if (text.contains(query) || namePlace.contains(query)) {
                    searchPromotions.add(promotion);
                }
            }

            System.out.println("serachch"+searchPromotions);
            mAdapter.setPromotions(searchPromotions);

            Toast.makeText(SearchResultsActivity.this, "Realizando Busqueda....", Toast.LENGTH_SHORT).show();


        }


        mRecyclerPromotions.setAdapter(mAdapter);

    }

    @Override
    public void onPromotionsLoaded(ArrayList<Promotion> listPromotions) {

        searchPromotions = new ArrayList<>();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        Intent searchIntent = getIntent();
        query = searchIntent.getStringExtra(SearchManager.QUERY);

        for(Promotion promotion: listPromotions) {
            final String text = promotion.getName().toLowerCase();
            final String namePlace = promotion.getPlace().getName().toLowerCase();

            if (text.contains(query) || namePlace.contains(query)) {
                searchPromotions.add(promotion);
            }
        }

        mAdapter.setPromotions(searchPromotions);
        System.out.println("onopormot"+searchPromotions);
    }

    @Override
    public void onRefresh() {
        new TaskLoadPromotions(this).execute();
        System.out.println("onRefresh"+mListPromotions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
