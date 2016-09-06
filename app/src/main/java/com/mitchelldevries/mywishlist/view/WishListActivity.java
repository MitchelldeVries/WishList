package com.mitchelldevries.mywishlist.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @author Mitchell de Vries.
 */
public class WishListActivity extends SingleFragmentActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return WishListFragment.newInstance();
    }
}
