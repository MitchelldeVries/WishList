package com.mitchelldevries.mywishlist.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mitchelldevries.mywishlist.R;
import com.mitchelldevries.mywishlist.view.WishListFragment;
import com.mitchelldevries.mywishlist.domain.Goal;
import com.mitchelldevries.mywishlist.domain.GoalStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Mitchell de Vries.
 */
public class DepositDialog extends DialogFragment {

    @BindView(R.id.dialog_edit_text)
    EditText editText;

    public static DepositDialog newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        DepositDialog fragment = new DepositDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.deposit_dialog, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        return builder.create();
    }

    @OnClick(R.id.save_action)
    void onPositiveClick() {
        GoalStorage storage = GoalStorage.getInstance(getActivity());
        Goal goal = storage.findOne(getArguments().getInt("id", 0));

        storage.deposit(goal, convertToDouble(editText.getText().toString()));
        getTargetFragment().onActivityResult(WishListFragment.REQUEST_WISH, Activity.RESULT_OK, null);
        DepositDialog.this.getDialog().dismiss();
    }


    @OnClick(R.id.cancel_action)
    void onCancelClick() {
        DepositDialog.this.getDialog().cancel();
    }

    private double convertToDouble(String amount) {
        if (amount.isEmpty()) {
            return 0.0;
        } else {
            return Double.parseDouble(amount);
        }
    }
}
