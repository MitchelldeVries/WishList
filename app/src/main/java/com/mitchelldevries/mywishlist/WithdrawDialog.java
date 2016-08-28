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

import butterknife.ButterKnife;

/**
 * @author Mitchell de Vries.
 */
public class WithdrawDialog extends DialogFragment {

    private EditText editText;

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
        editText = ButterKnife.findById(view, R.id.dialog_edit_text);
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        WishStorage storage = WishStorage.getInstance(getActivity());
                        Wish wish = storage.findOne(getArguments().getInt("id", 0));
                        storage.withdraw(wish, Double.parseDouble(String.valueOf(editText.getText())));
                        getTargetFragment().onActivityResult(WishListFragment.REQUEST_WISH, Activity.RESULT_OK, null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WithdrawDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
