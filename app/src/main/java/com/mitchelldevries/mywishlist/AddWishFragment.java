package com.mitchelldevries.mywishlist;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Mitchell de Vries.
 */
public class AddWishFragment extends Fragment {

    @BindView(R.id.add_title)
    EditText title;
    @BindView(R.id.add_target)
    EditText target;

    private WishStorage storage;

    public static AddWishFragment newInstance() {
        return new AddWishFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_wish, container, false);
        ButterKnife.bind(this, view);
        storage = WishStorage.getInstance(getActivity());

        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.add_button)
    void addNewWish() {

        Wish wish = new Wish();
        wish.setId(storage.incrementId());
        wish.setTitle(String.valueOf(title.getText()));
        wish.setTarget(Double.parseDouble(String.valueOf(target.getText())));
        wish.setImage(R.drawable.ic_laptop_mac);
        wish.setCurrent(0.0);
        storage.save(wish);

        startActivity(new Intent(getActivity(), WishListActivity.class));
    }
}
