package com.fitkeke.root.socialapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitkeke.root.socialapp.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

public class PostPreview extends AppCompatActivity {

    private TextView txtDate;
    private TextView txtTitle;
    private TextView txtDesc;
    private ImageView imgPost;
    private Button videoBtn;
    private YouTubeThumbnailView youTubeThumbnailView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_preview);

        // init views
        initViews();

        Intent postIntent = getIntent();
        String imgurl = postIntent.getStringExtra("imgurl");
        final String videourl = postIntent.getStringExtra("videourl");
        String date = postIntent.getStringExtra("date");
        String title = postIntent.getStringExtra("title");
        String desc = postIntent.getStringExtra("desc");

        // hide video button if not exist
        if (videourl.equals("default")){
            videoBtn.setVisibility(View.INVISIBLE);
            videoBtn.setClickable(false);

        }else {

            youTubeThumbnailView.initialize("AIzaSyB_moC4HscwoI7D8vlqpDg2Z7mHJTpBGKc", new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(videourl);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });

            videoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + videourl)));
                }
            });
        }

        // setting values
        txtDate.setText(date);
        txtTitle.setText(title);
        txtDesc.setText(desc);
        Picasso.with(PostPreview.this).load(imgurl).into(imgPost);
    }

    private void initViews() {
        txtDate = findViewById(R.id.post_date);
        txtTitle = findViewById(R.id.post_title);
        txtDesc = findViewById(R.id.post_desc);
        imgPost = findViewById(R.id.post_img);
        videoBtn = findViewById(R.id.btn_video);
        youTubeThumbnailView = findViewById(R.id.video_preview);
    }
}
