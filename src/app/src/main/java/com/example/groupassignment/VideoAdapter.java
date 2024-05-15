package com.example.groupassignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupassignment.DAO.VideoItem;
import com.example.groupassignment.activity.PlayActivity;

import java.util.List;

/**
 * Adapter class for a RecyclerView to display video items.
 * Each item in the recyclerview is linked to a videoItem which contain the detailed information like uri and name
 * it also contain the event handler like click
 * @author Tianyi Xu u7780366
 * This file reference to this website, I used the basic format provide by it, the website is as shown below
 * https://blog.csdn.net/indeedes/article/details/120014927
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoItem> video_Items;
    private Context context;

    /**
     * Consttructor for the VideoAdapter
     * @param video_Items The ArrayList to store all the VideoItems to be displayed on the recyclerView
     * @param context The current context, used to inflate the layout and start activities.
     */
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
            // Set up the onClickListener to open the playActivity to play the video
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
        // print out the total number of videos in the list
        System.out.println(video_Items.size());
        return video_Items.size();
    }

    /**
     * ViewHolder for the recyclerView, holds reference to the UI component
     * include a view to show the video and a textView to show the video name
     */
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
