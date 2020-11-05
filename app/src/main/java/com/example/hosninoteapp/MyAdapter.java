package com.example.hosninoteapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Note> notes;
    private final ItemLongClickListener mItemLongClickListener;

    public MyAdapter(ArrayList<Note> notes,ItemLongClickListener mItemLongClickListener) {
        this.notes = notes;
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       if (this.getItemViewType(viewType) == 0){
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
           return new Adapter(v,mItemLongClickListener);
       }else {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_check_box,parent,false);
           return new AdapterCheckBox(view,mItemLongClickListener);
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == 0){
            ((Adapter) holder).setNote(notes.get(position));
            ((Adapter) holder).position = position;

        }else {
            ((AdapterCheckBox) holder).setNoteCheck(notes.get(position));
            ((AdapterCheckBox) holder).position = position;
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (notes.get(position) != null){
            return 0;
        }
        return 1;
    }

    static class Adapter extends RecyclerView.ViewHolder {
        TextView title,date,subTitle;
        LinearLayout layoutNote;
        private int position;
        public Adapter(@NonNull View itemView,ItemLongClickListener mItemLongClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            date = itemView.findViewById(R.id.list_note_date);
            subTitle = itemView.findViewById(R.id.textView_subTitle);
            layoutNote = itemView.findViewById(R.id.layoutNote);

            itemView.setOnLongClickListener(v -> {
                mItemLongClickListener.onLongClickItem(position);
                return true;
            });
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
    static class AdapterCheckBox extends RecyclerView.ViewHolder {
        TextView check;
        CheckBox checkBox;
        private int position;
        public AdapterCheckBox(@NonNull View itemView,ItemLongClickListener mItemLongClickListener) {
            super(itemView);
            check = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkBox);



            itemView.setOnLongClickListener(v -> {
                mItemLongClickListener.onLongClickItem(position);
                return true;
            });
        }
        void setNoteCheck(Note note){
            check.setText(note.getCheckBoxText());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (checkBox.isChecked()) {
                    check.setPaintFlags(check.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else  {
                    check.setPaintFlags(check.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            });
        }
    }
}
