package com.example.groupassignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoItem> video_Items;
    private Context context;
    public VideoAdapter(List<VideoItem> video_Items,Context context){
        this.video_Items = video_Items;
        this.context = context;
    }
    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.videoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String current_video_name = video_Items.get(position).getVideoName();
                String current_video_uri = video_Items.get(position).getVideoUri();
                Intent intent = new Intent(view.getContext(), PlayActivity.class);
                intent.putExtra("toPlayName",current_video_name);
                intent.putExtra("toPlayView",current_video_uri);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        String videoName = video_Items.get(position).getVideoName();
        holder.video_Names_Text.setText(videoName);
    }

    @Override
    public int getItemCount() {
        System.out.println(video_Items.size());
        return video_Items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View videoview;
        TextView video_Names_Text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            video_Names_Text = itemView.findViewById(R.id.video_name_text_view);
            videoview = itemView;

        }
    }
}
