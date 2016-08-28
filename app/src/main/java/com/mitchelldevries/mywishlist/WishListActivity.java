package com.mitchelldevries.mywishlist;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
