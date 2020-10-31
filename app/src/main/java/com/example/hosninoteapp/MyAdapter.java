package com.example.hosninoteapp;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Adapter> {
    private final ArrayList<Note> notes;

    public MyAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new Adapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter holder, int position) {
//        Note note = notes.get(position);
//        holder.title.setText(note.getTitle());
//        holder.subTitle.setText(note.getSubTitle());
        holder.setNote(notes.get(position));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class Adapter extends RecyclerView.ViewHolder {
        TextView title,date,subTitle;
        LinearLayout layoutNote;
        public Adapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            date = itemView.findViewById(R.id.list_note_date);
            subTitle = itemView.findViewById(R.id.textView_subTitle);
            layoutNote = itemView.findViewById(R.id.layoutNote);
        }
        void setNote(Note note){
            title.setText(note.getTitle());
            date.setText(note.getDateTimeFormatted(itemView.getContext()));
            subTitle.setText(note.getSubTitle());
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            }else{
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
        }
    }
}
