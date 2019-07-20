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
import com.fitkeke.root.socialapp.activities.general_articles.GeneralArticles;
import com.fitkeke.root.socialapp.activities.online_programs.OnlinePrograms;
import com.fitkeke.root.socialapp.admin.ActivityAddGenArticles;
import com.fitkeke.root.socialapp.admin.ActivityAddOnlineProg;
import com.fitkeke.root.socialapp.admin.ActivityAddPost;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.ArticlesFragment;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.CloriesFragment;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.RecipeFragment;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.SuppFragment;
import com.fitkeke.root.socialapp.modules.ItemArticle;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.ViewHolder> {

    private List<ItemArticle> list;
    private Activity context;

    @NonNull
    @Override
    public ArticleItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
        ArticleItemAdapter.ViewHolder vh = new ArticleItemAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleItemAdapter.ViewHolder holder, int position) {

        final ItemArticle articleItem = list.get(position);

        //holder.btnEdit.setVisibility(View.VISIBLE);
        //holder.btnEdit.setClickable(true);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generalVars.post.equals("genArticles")){
                    Intent intent = new Intent(context, ActivityAddGenArticles.class);
                    intent.putExtra("type", "edit");
                    intent.putExtra("key", articleItem.getKey());
                    context.startActivity(intent);
                }else if (generalVars.post.equals("onlineProg")){
                    Intent intent = new Intent(context, ActivityAddOnlineProg.class);
                    intent.putExtra("type", "edit");
                    intent.putExtra("key", articleItem.getKey());
                    context.startActivity(intent);
                }else {
                    if (generalVars.post.equals("health_articles")){
                        Intent intent = new Intent(context, ActivityAddPost.class);
                        intent.putExtra("type", "edit");
                        intent.putExtra("key", articleItem.getKey());
                        intent.putExtra("childRef", "articles");
                        context.startActivity(intent);
                    }else if (generalVars.post.equals("health_calories")){
                        Intent intent = new Intent(context, ActivityAddPost.class);
                        intent.putExtra("type", "edit");
                        intent.putExtra("key", articleItem.getKey());
                        intent.putExtra("childRef", "calories");
                        context.startActivity(intent);
                    }else if (generalVars.post.equals("health_recipe")){
                        Intent intent = new Intent(context, ActivityAddPost.class);
                        intent.putExtra("type", "edit");
                        intent.putExtra("key", articleItem.getKey());
                        intent.putExtra("childRef", "recipe");
                        context.startActivity(intent);
                    }else if (generalVars.post.equals("health_supp")){
                        Intent intent = new Intent(context, ActivityAddPost.class);
                        intent.putExtra("type", "edit");
                        intent.putExtra("key", articleItem.getKey());
                        intent.putExtra("childRef", "supp");
                        context.startActivity(intent);
                    }
                }
            }
        });
        //holder.btnDel.setVisibility(View.VISIBLE);
        //holder.btnDel.setClickable(true);
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generalVars.post.equals("genArticles")){
                    DatabaseReference databaseRreference = FirebaseDatabase.getInstance().getReference().child("articles");
                    databaseRreference.child(articleItem.getKey()).removeValue();
                    Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                }else if (generalVars.post.equals("onlineProg")){
                    DatabaseReference databaseRreference = FirebaseDatabase.getInstance().getReference().child("posts");
                    databaseRreference.child(articleItem.getKey()).removeValue();
                    Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                }else {
                    if (generalVars.post.equals("health_articles")){
                        DatabaseReference databaseRreference = FirebaseDatabase.getInstance().getReference().child("body_health").child("articles");
                        databaseRreference.child(articleItem.getKey()).removeValue();
                        Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                    }else if (generalVars.post.equals("health_calories")){
                        DatabaseReference databaseRreference = FirebaseDatabase.getInstance().getReference().child("body_health").child("calories");
                        databaseRreference.child(articleItem.getKey()).removeValue();
                        Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                    }else if (generalVars.post.equals("health_recipe")){
                        DatabaseReference databaseRreference = FirebaseDatabase.getInstance().getReference().child("body_health").child("recipe");
                        databaseRreference.child(articleItem.getKey()).removeValue();
                        Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                    }else if (generalVars.post.equals("health_supp")){
                        DatabaseReference databaseRreference = FirebaseDatabase.getInstance().getReference().child("body_health").child("supp");
                        databaseRreference.child(articleItem.getKey()).removeValue();
                        Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.txtDate.setText(articleItem.getDate());
        holder.txtTitle.setText(articleItem.getTitle());
        holder.txtType.setText(articleItem.getType());
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

        Picasso.with(context).load(articleItem.getPostImg()).into(holder.imgPost);


        // preview full post details
        holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostPreview.class);
                intent.putExtra("imgurl", articleItem.getPostImg());
                intent.putExtra("videourl", "default");
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
        public YouTubeThumbnailView youTubePlayerView;
        public ImageView imgPost;

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

            youTubePlayerView.setVisibility(View.GONE);

            // gide for users
            btnEdit.setClickable(false);
            btnEdit.setVisibility(View.GONE);
            btnDel.setClickable(false);
            btnDel.setVisibility(View.GONE);



        }

    }


    public ArticleItemAdapter(List<ItemArticle> postList , Activity mContext) {
        list = postList;
        context = mContext;
    }
}