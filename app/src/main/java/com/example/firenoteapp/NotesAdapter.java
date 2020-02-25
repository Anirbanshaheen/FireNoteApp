package com.example.firenoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firenoteapp.Data.UserNotes;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    Context context;
    ArrayList<UserNotes> dataList = new ArrayList<>();


    public NotesAdapter(Context context, ArrayList<UserNotes> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    // Inflate our XML file for Showing the View
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_for_allnotes,parent,false);
        NotesHolder notesHolder = new NotesHolder(view);
        return notesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
        UserNotes userNotes = dataList.get(position);
        String title = userNotes.getNoteTitle();
        String description = userNotes.getNoteDescription();
        String date = userNotes.getNoteDate();

        holder.textViewTitle.setText(title);
        holder.textViewDescription.setText(description);
        holder.textViewDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle,textViewDescription,textViewDate;
        ImageView imageViewEdit,imageViewDelete;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_row_title);
            textViewDescription = itemView.findViewById(R.id.text_row_description);
            textViewDate = itemView.findViewById(R.id.text_row_date);
            imageViewEdit = itemView.findViewById(R.id.image_row_edit);
            imageViewDelete = itemView.findViewById(R.id.image_row_delete);
        }
    }
}
