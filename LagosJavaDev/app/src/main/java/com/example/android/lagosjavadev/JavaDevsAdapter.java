package com.example.android.lagosjavadev;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kayode Agboola on 12-Sep-17.
 */
  public class JavaDevsAdapter extends ArrayAdapter<JavaDevs> {
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_URL = "url";

    /**
     * Constructs a new {@link JavaDevsAdapter}.
     *
     * @param mContext of the app
     * @param devsList is the list of java developers which is the data source of the adapter
     */
    public JavaDevsAdapter(Context mContext, ArrayList<JavaDevs> devsList) {
        super(mContext, 0, devsList);
    }

    /**
     * Returns a list item view that displays information about the developer
     * at the given position in the list of java devs list.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.java_devs_list, parent, false);
        }

        // Find the developer at the given position in the list of developers
        JavaDevs currentElement = getItem(position);

        // Find the ImageView with view ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);

        // If an image is available, load the image with picasso builder based on the image url
        Picasso.Builder picassoBuilder = new Picasso.Builder(getContext());
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e("TAG","Failed");
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(true);
        picasso.load(currentElement.getImageUri()).transform(new RoundedTransformation(200, 2)).placeholder(R.drawable.loading_img).into(imageView);

        // Find the TextView with view ID username
        TextView username = (TextView) listItemView.findViewById(R.id.username_text_view);

        // Display the username in that TextView
        username.setText(currentElement.getUsername());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}