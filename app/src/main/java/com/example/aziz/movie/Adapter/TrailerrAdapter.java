package com.example.aziz.movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aziz.movie.Model.trailers;
import com.example.aziz.movie.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TrailerrAdapter extends RecyclerView.Adapter<TrailerrAdapter.ViewHold> {
    private Context context;
   private List<trailers.trailer> Trailers;

    public TrailerrAdapter(Context context, List<trailers.trailer> trailers) {
        this.context = context;
        this.Trailers = trailers;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_row,parent,false);
        return new ViewHold(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + Trailers.size());

        int pos=position+1;
            holder.trailer_name.setText(context.getString(R.string.trailer_name).concat(" "+pos));

    }

    @Override
    public int getItemCount() {
        if(Trailers.size()>=3)
        return 3;
        else
        return Trailers.size();
    }


    public class ViewHold extends RecyclerView.ViewHolder{
        private TextView trailer_name;
        public ViewHold(View itemView) {
            super(itemView);
            trailer_name=itemView.findViewById(R.id.trailer_custom_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    Uri web_page=Uri.parse(Trailers.get(pos).getKey());
                    Intent intent=new Intent(Intent.ACTION_VIEW,web_page);

                    if(intent.resolveActivity(context.getPackageManager())!=null)
                    {
                        context.startActivity(intent);
                    }
                }
            });
        }

    }
}
