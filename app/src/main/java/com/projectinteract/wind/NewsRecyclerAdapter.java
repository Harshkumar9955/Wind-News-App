package com.projectinteract.wind;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter
        extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private List<Article> articleList;

    public NewsRecyclerAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_recycler_row, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull NewsViewHolder holder, int position) {

        Article article = articleList.get(position);

        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource_id());

        if (article.getImage_url() != null && !article.getImage_url().isEmpty()) {
            Picasso.get()
                    .load(article.getImage_url())
                    .placeholder(R.drawable.no_image_icon)
                    .error(R.drawable.no_image_icon)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.no_image_icon);
        }


        holder.itemView.setOnClickListener(v -> {
            if (article.getLink() != null && !article.getLink().isEmpty()) {
                Intent intent = new Intent(v.getContext(), NewsFullActivity.class);
                intent.putExtra("url", article.getLink());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void updateData(List<Article> data) {
        articleList.clear();
        articleList.addAll(data);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, sourceTextView;
        ImageView imageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);
        }
    }
}
