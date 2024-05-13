package com.example.groupassignment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupassignment.DAO.AssignmentItem;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder>{
    private List<AssignmentItem> assignmentItemList;
    private Context context;
    public AssignmentAdapter(List<AssignmentItem> assignmentItemList, Context context){
        this.assignmentItemList = assignmentItemList;
        this.context = context;
    }
    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.assignmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String current_PDF_name = assignmentItemList.get(position).getPDFName();
                String current_PDF_uri = assignmentItemList.get(position).getPDFUri();
                openPDF(view.getContext(), Uri.parse(current_PDF_uri));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position) {
        String PDFName = assignmentItemList.get(position).getPDFName();
        holder.assignment_Text_Bind.setText(PDFName);
    }

    @Override
    public int getItemCount() {
        return assignmentItemList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public View assignmentView;
        TextView assignment_Text_Bind;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignment_Text_Bind = itemView.findViewById(R.id.pdf_name_text_view);
            assignmentView = itemView;
        }
    }
    private void openPDF(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooser = Intent.createChooser(intent, "Open PDF");
        context.startActivity(chooser);
    }
}
