package com.mitchelldevries.mywishlist;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @author Mitchell de Vries.
 */
public class AddWishActivity extends SingleFragmentActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return AddWishFragment.newInstance();
    }
}
