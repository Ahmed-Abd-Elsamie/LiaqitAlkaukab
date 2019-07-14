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
import android.widget.TextView;
import android.widget.Toast;

import com.fitkeke.root.socialapp.activities.PostPreview;
import com.fitkeke.root.socialapp.modules.ItemArticle;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

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

        //holder.btnEdit.setVisibility(View.VISIBLE);
        //holder.btnEdit.setClickable(true);
        /*holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
            }
        });*/
        //holder.btnDel.setVisibility(View.VISIBLE);
        //holder.btnDel.setClickable(true);
        /*holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });*/

        final ItemArticle articleItem = list.get(position);
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

        // preview full post details
        holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostPreview.class);
                intent.putExtra("imgurl", articleItem.getImgurl());
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
            youTubePlayerView = (YouTubeThumbnailView) view.findViewById(R.id.youtubeVid);

            youTubePlayerView.setVisibility(View.GONE);
            btnDel.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);

        }

    }


    public ArticleItemAdapter(List<ItemArticle> postList , Activity mContext) {
        list = postList;
        context = mContext;
    }
}
