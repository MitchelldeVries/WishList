package com.mitchelldevries.mywishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        adapter = new WishAdapter();
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

    public class WishAdapter extends RecyclerView.Adapter<WishAdapter.WishViewHolder> {

        @Override
        public WishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wish, parent, false);
            return new WishViewHolder(view);
        }

        @Override
        public void onBindViewHolder(WishViewHolder holder, int position) {
            holder.bindWish(wishes.get(position), position);
        }

        @Override
        public int getItemCount() {
            return wishes.size();
        }

        public class WishViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.relative_layout_list_item)
            RelativeLayout layout;
            @BindView(R.id.list_item_image)
            ImageView wishImage;
            @BindView(R.id.list_item_title)
            TextView wishTitle;
            @BindView(R.id.list_item_target)
            TextView wishTarget;
            @BindView(R.id.list_item_current)
            TextView wishCurrent;
            @BindView(R.id.progress)
            ProgressBar progressBar;

            private Wish wish;

            public WishViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindWish(Wish wish, int position) {
                this.wish = wish;

                int[] colors = getActivity().getResources().getIntArray(R.array.colors);
                int color = position % colors.length;
                int backgroundColor = colors[color];
                layout.setBackgroundColor(backgroundColor);

                wishTitle.setText(wish.getTitle());
                wishImage.setImageResource(wish.getImage());
                wishTarget.setText(String.format("€%s", String.valueOf(wish.getTarget())));
                wishCurrent.setText(String.format("€%s", String.valueOf(wish.getCurrent())));
                double progress = (wish.getCurrent() / wish.getTarget()) * 100;
                Log.i("TAG", "bindWish: " + progress);
                progressBar.setProgress((int) progress);
            }

            @OnClick(R.id.list_item_deposit)
            void deposit() {
                DialogFragment newFragment = DepositDialog.newInstance(wish.getId());
                newFragment.show(getActivity().getSupportFragmentManager(), "deposit");
            }

            @OnClick(R.id.list_item_withdraw)
            void withdraw() {
                DialogFragment newFragment = WithdrawDialog.newInstance(wish.getId());
                newFragment.setTargetFragment(WishListFragment.this, REQUEST_WISH);
                newFragment.show(getActivity().getSupportFragmentManager(), "withdraw");
            }
        }
    }
}
