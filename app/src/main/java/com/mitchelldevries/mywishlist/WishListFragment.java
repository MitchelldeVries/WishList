package com.mitchelldevries.mywishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Mitchell de Vries.
 */
public class WishListFragment extends Fragment {

    public static final int REQUEST_WISH = 1;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private WishAdapter adapter;
    private WishStorage storage;
    private List<Wish> wishes;

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

        updateUI();

        return view;
    }

    private void updateUI() {
        storage = WishStorage.getInstance(getActivity());

        wishes = storage.findAll();

        adapter = new WishAdapter(getActivity());
        adapter.setWishes(wishes);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Log.i("TAG", "onActivityResult: " + getActivity().toString());
        if (requestCode == REQUEST_WISH) {
            updateUI();
        }
    }
}
