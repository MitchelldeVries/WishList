package com.mitchelldevries.mywishlist.view.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mitchelldevries.mywishlist.R;
import com.mitchelldevries.mywishlist.domain.Goal;
import com.mitchelldevries.mywishlist.domain.GoalStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Mitchell de Vries.
 */
public class AddGoalDialog extends DialogFragment {

    public static final int REQUEST_IMAGE = 2;
    public static final String EXTRA_IMAGE_ID = "com.mitchelldevries.image";

    @BindView(R.id.add_title)
    TextInputEditText title;
    @BindView(R.id.add_target)
    EditText target;
    @BindView(R.id.add_image_button)
    ImageButton imageButton;

    private GoalStorage storage;
    private int imageId;

    public static AddGoalDialog newInstance(int color) {

        Bundle args = new Bundle();
        args.putInt("color", color);
        AddGoalDialog fragment = new AddGoalDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_wish, null);
        ButterKnife.bind(this, view);
        imageButton.setBackgroundColor(getArguments().getInt("color", 0));

        builder.setView(view);

        storage = GoalStorage.getInstance(getActivity());

        return builder.create();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.add_button)
    void addNewWish() {

        Goal goal = new Goal();
        goal.setId(storage.incrementId());
        goal.setTitle(String.valueOf(title.getText()));
        goal.setTarget(Double.parseDouble(String.valueOf(target.getText())));
        goal.setImage(imageId);
        goal.setCurrent(0.0);
        storage.save(goal);

        AddGoalDialog.this.dismiss();
    }

    @OnClick(R.id.add_image_button)
    void addImage() {
        DialogFragment newFragment = new ImagePickerDialog();
        newFragment.setTargetFragment(AddGoalDialog.this, REQUEST_IMAGE);
        newFragment.show(getActivity().getSupportFragmentManager(), "imageGrid");
    }

    @OnClick(R.id.cancel_action)
    void cancelAdd() {
      AddGoalDialog.this.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_IMAGE) {
            imageId = data.getIntExtra(EXTRA_IMAGE_ID, 0);
            imageButton.setImageResource(imageId);
        }
    }
}
