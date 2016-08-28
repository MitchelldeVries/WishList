package com.mitchelldevries.mywishlist;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

/**
 * @author Mitchell de Vries.
 */
public class WishListFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private WishAdapter adapter;
    private WishStorage storage;

    public static WishListFragment newInstance() {
        return new WishListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        ButterKnife.bind(this, view);

        storage = WishStorage.getInstance(getActivity());

        adapter = new WishAdapter(getActivity());
        adapter.setWishes(storage.findAll());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @OnClick(R.id.fab)
    public void addNewWish() {
        startActivity(new Intent(getActivity(), AddWishActivity.class));
    }

}
