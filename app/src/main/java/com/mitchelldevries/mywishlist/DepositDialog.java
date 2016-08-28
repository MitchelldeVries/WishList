package com.mitchelldevries.mywishlist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class DepositDialog extends DialogFragment {

    private EditText editText;

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
        View view = inflater.inflate(R.layout.dialog, null);
        editText = ButterKnife.findById(view, R.id.dialog_edit_text);
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        WishStorage storage = WishStorage.getInstance(getActivity());
                        Wish wish = storage.findOne(getArguments().getInt("id", 0));
                        storage.deposit(wish, Double.parseDouble(String.valueOf(editText.getText())));
                        startActivity(new Intent(getActivity(), WishListActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DepositDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}