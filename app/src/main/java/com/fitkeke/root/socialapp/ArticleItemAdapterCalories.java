package com.fitkeke.root.socialapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitkeke.root.socialapp.activities.PostPreview;
import com.fitkeke.root.socialapp.modules.ItemArticleCalories;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleItemAdapterCalories extends RecyclerView.Adapter<ArticleItemAdapterCalories.ViewHolder> {

    private List<ItemArticleCalories> list;
    private Activity context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
        ArticleItemAdapterCalories.ViewHolder vh = new ArticleItemAdapterCalories.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*holder.btnEdit.setVisibility(View.VISIBLE);
        holder.btnEdit.setClickable(true);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDel.setVisibility(View.VISIBLE);
        holder.btnDel.setClickable(true);
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });*/

        final ItemArticleCalories articleItem = list.get(position);
        holder.txtDate.setText(articleItem.getDate());
        holder.txtTitle.setText(articleItem.getTitle());
        holder.txtType.setText(articleItem.getType());
        final String videoUrl = articleItem.getVideo();

        if (videoUrl.equals("default")){
            //holder.youTubePlayerView.setVisibility(View.GONE);
        }else {
            holder.youTubePlayerView.setVisibility(View.VISIBLE);
            holder.youTubePlayerView.initialize("AIzaSyB_moC4HscwoI7D8vlqpDg2Z7mHJTpBGKc", new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(videoUrl);
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
        }

        if (articleItem.getPostImg().equals("default")){
            holder.imgPost.setVisibility(View.GONE);
        }else {
            Picasso.with(context).load(articleItem.getPostImg()).into(holder.imgPost);
        }

        if (articleItem.getDesc().length() > 150){
            holder.txtDesc.setText(articleItem.getDesc().substring(0, 130));

        }else {
            holder.txtDesc.setText(articleItem.getDesc());
        }

        holder.CommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
            }
        });
        holder.LikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();
            }
        });

        // preview full post details
        holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostPreview.class);
                intent.putExtra("imgurl", articleItem.getPostImg());
                intent.putExtra("videourl", articleItem.getVideo());
                intent.putExtra("date", articleItem.getDate());
                intent.putExtra("title", articleItem.getTitle());
                intent.putExtra("desc", articleItem.getDesc());

                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView txtDate;
        public TextView txtType;
        public TextView txtTitle;
        public TextView txtDesc;
        public Button LikeBtn;
        public Button CommentBtn;
        public TextView txtMore;
        public FloatingActionButton btnEdit;
        public FloatingActionButton btnDel;
        public ImageView imgPost;
        public YouTubeThumbnailView youTubePlayerView;


        public ViewHolder(View v) {
            super(v);
            view = v;
            txtDate = (TextView) view.findViewById(R.id.post_date);
            txtDesc = (TextView) view.findViewById(R.id.post_desc);
            txtType = (TextView) view.findViewById(R.id.post_type);
            txtTitle = (TextView) view.findViewById(R.id.post_title);
            LikeBtn = (Button) view.findViewById(R.id.btn_like);
            CommentBtn = (Button) view.findViewById(R.id.btn_comment);
            txtMore = (TextView) view.findViewById(R.id.post_read_more);
            btnEdit = (FloatingActionButton) view.findViewById(R.id.fab_edit);
            btnDel = (FloatingActionButton) view.findViewById(R.id.fab_del);
            imgPost = (ImageView) view.findViewById(R.id.post_img);
            youTubePlayerView = (YouTubeThumbnailView) view.findViewById(R.id.youtubeVid);
            youTubePlayerView.setVisibility(View.VISIBLE);

            btnDel.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);



        }

    }

    public ArticleItemAdapterCalories(List<ItemArticleCalories> postList , Activity mContext) {
        list = postList;
        context = mContext;
    }

}