package com.example.aziz.movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aziz.movie.Details;
import com.example.aziz.movie.Model.movies;
import com.example.aziz.movie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class customadapter extends RecyclerView.Adapter<customadapter.ViewHold> {
    private Context mcontext;
    private List<movies.movie> llist;


    public customadapter(Context mcontext, List<movies.movie> llist) {
        this.mcontext = mcontext;
        this.llist = llist;
    }

    public void setData(List<movies.movie> list)
    {
        this.llist=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public customadapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);

        return new ViewHold(vv) ;
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter.ViewHold holder, int position) {

        Picasso.with(mcontext)
                .load("http://image.tmdb.org/t/p/w342"+llist.get(position).getBackdropPath())
                .placeholder(R.drawable.searching)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return llist.size();
    }


    class ViewHold extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHold(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_custom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, Details.class);
                    movies.movie movie = llist.get(getAdapterPosition());
                    intent.putExtra("MovieItem", movie);
                    mcontext.startActivity(intent);
                }
            });
        }


    }
}
