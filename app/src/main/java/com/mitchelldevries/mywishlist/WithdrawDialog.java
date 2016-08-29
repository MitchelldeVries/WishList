package com.mitchelldevries.mywishlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Mitchell de Vries.
 */
public class WithdrawDialog extends DialogFragment {

    @BindView(R.id.dialog_edit_text)
    EditText editText;
    @BindView(R.id.save_action)
    ImageButton saveButton;
    @BindView(R.id.cancel_action)
    ImageButton cancelButton;

    public static WithdrawDialog newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        WithdrawDialog fragment = new WithdrawDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        return builder.create();
    }

    @OnClick(R.id.save_action)
    void onPositiveClick() {
        WishStorage storage = WishStorage.getInstance(getActivity());
        Wish wish = storage.findOne(getArguments().getInt("id", 0));
        storage.withdraw(wish, Double.parseDouble(String.valueOf(editText.getText())));
        getTargetFragment().onActivityResult(WishListFragment.REQUEST_WISH, Activity.RESULT_OK, null);
    }

    @OnClick(R.id.save_action)
    void onCancelClick() {
        WithdrawDialog.this.getDialog().cancel();
    }
}
