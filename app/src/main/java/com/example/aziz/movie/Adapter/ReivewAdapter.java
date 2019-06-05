package com.example.aziz.movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aziz.movie.Model.reviews;
import com.example.aziz.movie.R;

import java.util.List;

public class ReivewAdapter extends RecyclerView.Adapter<ReivewAdapter.ViewHold> {
    private Context mContext;
    private List<reviews.Review> lList;

    public ReivewAdapter(Context mcontext, List<reviews.Review> llist) {
        this.mContext = mcontext;
        this.lList = llist;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv= LayoutInflater.from(mContext).inflate(R.layout.reviews_row,parent,false);
        return new ViewHold(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        reviews.Review rr=lList.get(position);
        holder.rName.setText(rr.getAuthor());
        holder.rContent.setText(rr.getContent());
    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    class ViewHold extends RecyclerView.ViewHolder {
        TextView rName,rContent;
        ViewHold(View itemView) {
            super(itemView);
            rName=itemView.findViewById(R.id.review_Preson);
            rContent=itemView.findViewById(R.id.review_Content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    Uri PostContent=Uri.parse(lList.get(pos).getUrl());
                    Intent intent=new Intent(Intent.ACTION_VIEW,PostContent);
                    if(intent.resolveActivity(mContext.getPackageManager())!=null)
                    {
                        mContext.startActivity(intent);
                    }


                }
            });
        }
    }
}
