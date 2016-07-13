package com.app.jonatan.enteratechihuahua.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.app.jonatan.enteratechihuahua.adapters.AdapterPromotion;
import com.app.jonatan.enteratechihuahua.adapters.AdapterSubcategory;
import com.app.jonatan.enteratechihuahua.callbacks.PromotionsLoadedListener;
import com.app.jonatan.enteratechihuahua.extras.Subcategory;
import com.app.jonatan.enteratechihuahua.logging.L;
import com.app.jonatan.enteratechihuahua.pojo.Promotion;
import com.app.jonatan.enteratechihuahua.tasks.TaskLoadPromotions;
import com.app.jonatan.enteratechihuahua.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by armando on 6/29/16.
 */
public class FragmentCoffee extends Fragment implements PromotionsLoadedListener,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private static final String STATE_PROMOTIONS = "state_promotions";
    private ArrayList<Promotion> mListPromotions = new ArrayList<>();
    private List<Promotion> mListRestaurants = new ArrayList<>();
    private AdapterPromotion mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerPromotions;
    private TextView mTextError;
    private List<Subcategory> subcategories;

    public FragmentCoffee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRestaurants.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCoffee newInstance(String param1, String param2) {
        FragmentCoffee fragment = new FragmentCoffee();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_coffee, container, false);
        mTextError = (TextView) layout.findViewById(R.id.textVolleyError);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipePromotions);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerPromotions = (RecyclerView) layout.findViewById(R.id.listPromotions);
        mRecyclerPromotions.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new AdapterPromotion(getActivity());
        mRecyclerPromotions.setAdapter(mAdapter);

        RecyclerView subcategoriesRv = (RecyclerView)layout.findViewById(R.id.subcategorybarsrecycler);
        LinearLayoutManager llm =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        subcategoriesRv.setLayoutManager(llm);

        subcategories = new ArrayList<>();

        subcategories.add(new Subcategory(R.mipmap.ic_petfriendly, "Pet Friendly"));


        AdapterSubcategory adapter = new AdapterSubcategory(subcategories);

        subcategoriesRv.setAdapter(adapter);

        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            mListPromotions = savedInstanceState.getParcelableArrayList(STATE_PROMOTIONS);
            System.out.println("parcelableList"+mListPromotions);
        } else {
            if (mListPromotions.isEmpty()) {
                L.m("FragmenPromotions: executing a task");
                new TaskLoadPromotions(this).execute();

            }
        }

        mAdapter.setPromotions(mListPromotions);
        return layout;
    }

    @Override
    public void onRefresh() {
        new TaskLoadPromotions(this).execute();
    }

    @Override
    public void onPromotionsLoaded(ArrayList<Promotion> listPromotions) {
        L.m("FragmentBoxOffice: onPromotionsLoaded Fragment");
        //update the Adapter to contain the new movies downloaded from the web
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        System.out.println("loaded"+mListPromotions);

        ArrayList<Promotion>  promotionsRestaurants = new ArrayList<>();
        for(Promotion promotion: listPromotions) {
            final String text = promotion.getPlace().getCategory();
            if (text.contains("Antros")) {
                promotionsRestaurants.add(promotion);
            }
        }
        mListPromotions = promotionsRestaurants;
        mAdapter.setPromotions(promotionsRestaurants);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_PROMOTIONS, mListPromotions);
    }

    private void handleVolleyError(VolleyError error) {
        //if any error occurs in the network operations, show the TextView that contains the error message
        mTextError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            mTextError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof ServerError) {
            mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof NetworkError) {
            mTextError.setText(R.string.error_network);
            //TODO
        } else if (error instanceof ParseError) {
            mTextError.setText(R.string.error_parser);
            //TODO
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("antesF"+mListPromotions);
        final List<Promotion> filteredListPromotion = filter(mListPromotions, newText);
        mAdapter.setFilter(filteredListPromotion);
        System.out.println("despues"+mListPromotions);
        return true;
    }

    private List<Promotion> filter(List<Promotion> models, String query) {
        query = query.toLowerCase();

        System.out.println("query-->"+query);

        final List<Promotion> filteredModelList = new ArrayList<>();
        for(Promotion model: models) {
            final String text = model.getName().toLowerCase();
            final String namePlace = model.getPlace().getName().toLowerCase();

            if (text.contains(query) || namePlace.contains(query)) {
                filteredModelList.add(model);
            }
        }

        return filteredModelList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        System.out.println("filterOn" + mListPromotions);
                        mAdapter.setFilter(mListPromotions);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }
}



