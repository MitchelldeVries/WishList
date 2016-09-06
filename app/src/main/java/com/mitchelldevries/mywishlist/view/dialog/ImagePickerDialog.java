package com.mitchelldevries.mywishlist.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mitchelldevries.mywishlist.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePickerDialog extends DialogFragment {

    @BindView(R.id.gridview)
    GridView gridview;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_image_picker, null);
        ButterKnife.bind(this, view);
        builder.setView(view);

        final ImageAdapter adapter = new ImageAdapter();
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent imageId = new Intent();
                imageId.putExtra(AddGoalDialog.EXTRA_IMAGE_ID, (Integer) adapter.getItem(position));
                getTargetFragment().onActivityResult(AddGoalDialog.REQUEST_IMAGE, Activity.RESULT_OK, imageId);
                ImagePickerDialog.this.dismiss();
            }
        });

        return builder.create();
    }

    public class ImageAdapter extends BaseAdapter {

        Field[] drawables = com.mitchelldevries.mywishlist.R.drawable.class.getFields();
        private List<Integer> thumbs;

        public ImageAdapter() {
            setThumbs();
        }

        public int getCount() {
            return thumbs.size();
        }

        public Object getItem(int position) {
            return thumbs.get(position);
        }

        public long getItemId(int position) {
            return thumbs.get(position);
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(thumbs.get(position));
            return imageView;
        }

        private void setThumbs() {
            thumbs = new ArrayList<>();
            for (Field f : drawables) {
                try {
                    if (f.getName().contains("ic_img_")) {
                        thumbs.add(f.getInt(f.getName()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}