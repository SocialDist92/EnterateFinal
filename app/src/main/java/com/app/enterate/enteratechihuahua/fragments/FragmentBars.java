package com.app.enterate.enteratechihuahua.fragments;

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

import com.app.enterate.enteratechihuahua.adapters.AdapterPromotion;
import com.app.enterate.enteratechihuahua.adapters.AdapterSubcategory;
import com.app.enterate.enteratechihuahua.callbacks.PromotionsLoadedListener;
import com.app.enterate.enteratechihuahua.extras.SortListener;
import com.app.enterate.enteratechihuahua.extras.Subcategory;
import com.app.enterate.enteratechihuahua.logging.L;
import com.app.enterate.enteratechihuahua.pojo.Promotion;
import com.app.enterate.enteratechihuahua.tasks.TaskLoadPromotions;
import com.app.enterate.enteratechihuahua.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by armando on 6/28/16.
 */
public class FragmentBars extends Fragment implements SortListener, PromotionsLoadedListener,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private static final String STATE_PROMOTIONS = "state_promotions";
    private ArrayList<Promotion> mListPromotions = new ArrayList<>();
    private List<Promotion> mListEvents = new ArrayList<>();
    private AdapterPromotion mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerPromotions;
    private TextView mTextError;
    private List<Subcategory> subcategories;
    private AdapterSubcategory adapter;

    public FragmentBars() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEvents.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBars newInstance(String param1, String param2) {
        FragmentBars fragment = new FragmentBars();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bars, container, false);
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

        subcategories.add(new Subcategory(R.mipmap.ic_sport, "Sport Bar"));
        subcategories.add(new Subcategory(R.mipmap.ic_contemporary, "Cantina Contemporanea"));
        subcategories.add(new Subcategory(R.mipmap.ic_musical, "Genero Musical"));
        subcategories.add(new Subcategory(R.mipmap.ic_cantina, "Cantinas"));
        subcategories.add(new Subcategory(R.mipmap.ic_drive, "Drive Inn"));


        adapter = new AdapterSubcategory(subcategories);

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
        adapter.setPromotions(mListPromotions);
        adapter.setAdapterPromotions(mAdapter);

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_PROMOTIONS, mListPromotions);
    }

    @Override
    public void onSortByName() {

    }

    @Override
    public void onSortByDate() {

    }

    @Override
    public void onSortByRating() {

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


        ArrayList<Promotion>  promotionsEventos = new ArrayList<>();
        for(Promotion promotion: listPromotions) {
            final String text = promotion.getPlace().getCategory();
            if (text.contains("Bares")) {
                promotionsEventos.add(promotion);
            }
        }

        mListPromotions = promotionsEventos;
        mAdapter.setPromotions(promotionsEventos);
        adapter.setPromotions(mListPromotions);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem item = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
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
}
