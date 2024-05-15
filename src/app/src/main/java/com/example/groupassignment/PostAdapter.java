package com.example.groupassignment;
/**
 * @author Wenzhao Zheng u7705888
 * This class referenced other adapter classes including VideoAdapter and AssignmentAdapter
 * */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<String> posts;

    /**
     * Constructor of post adapter
     * @param posts
     */
    public PostAdapter(List<String> posts) {
        this.posts = posts;
    }

    /**
     * Method for creating new view-holder to hold the individual post item
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    /**
     * Method for updating the post content to respective position
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.post_content.setText(posts.get(position));
    }

    /**
     * Getter method of item count
     * @return
     */
    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * Inner class to customise view-holder to post view-holder
     */
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView post_content;

        PostViewHolder(View itemView) {
            super(itemView);
            post_content = itemView.findViewById(R.id.post_content);
        }
    }
}
