package com.example.android.lagosjavadev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Kayode Agboola on 13-Sep-17.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.java_dev_profile);

        ImageView userProfileImageView = (ImageView) findViewById(R.id.profile_p);
        TextView userNameTextView = (TextView) findViewById(R.id.username);
        Button shareProfile = (Button) findViewById(R.id.share_button);
        TextView gitHubUrl = (TextView) findViewById(R.id.url);

        Intent intent = getIntent();
        final String userName = intent.getStringExtra(JavaDevsAdapter.KEY_NAME);
        String imageString = intent.getStringExtra(JavaDevsAdapter.KEY_IMAGE);
        final String profileUrl = intent.getStringExtra(JavaDevsAdapter.KEY_URL);

        //load the image with picasso based on the image url
        Picasso.Builder picassoBuilder = new Picasso.Builder(getApplicationContext());
        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e("TAG","Failed");
            }
        });
        Picasso picasso = picassoBuilder.build();
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(true);
        picasso.load(imageString).transform(new RoundedTransformation(200, 2)).placeholder(R.drawable.loading_img).into(userProfileImageView);

        userNameTextView.setText(userName);

        gitHubUrl.setText(profileUrl);

        gitHubUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = profileUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //String link = ("<a href=" + profileUrl + ">" + profileUrl + "</a>");

        shareProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer " + "@" + userName +
                        ", " + profileUrl);
                Intent chooser = Intent.createChooser(shareIntent, "Share via");
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });
    }
}