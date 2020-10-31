package com.example.hosninoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        mLinearLayoutManager = new LinearLayoutManager(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);


        FloatingActionButton button_add = findViewById(R.id.btn_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNote.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //load saved notes into the recyclerView
        //first, reset the recyclerView
        recyclerView.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(getApplicationContext());

        //sort notes from new to old
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                if(lhs.getDateTime() > rhs.getDateTime()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        if(notes != null && notes.size() > 0) { //check if we have any notes!
            final MyAdapter na = new MyAdapter(notes);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            na.notifyDataSetChanged();
            recyclerView.setAdapter(na);


            //set click listener for items in the list, by clicking each item the note should be loaded into NoteActivity
//            recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //run the NoteActivity in view/edit mode
//                    String fileName = ((Note) mListNotes.getItemAtPosition(position)).getDateTime()
//                            + Utilities.FILE_EXTENSION;
//                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
//                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
//                    startActivity(viewNoteIntent);
//                }
//            });


        }
    }
    private void actionDelete() {
        Note mLoadedNote = new Note();
        Intent i = getIntent();
        String s = i.getStringExtra("file");
        //ask user if he really wants to delete the note!
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("delete note")
                .setMessage("really delete the note?")
                .setPositiveButton("YES", (dialog, which) -> {
                    if(mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), s)) {
                        Toast.makeText(MainActivity.this, mLoadedNote.getTitle() + " is deleted"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "can not delete the note '" + mLoadedNote.getTitle() + "'"
                                , Toast.LENGTH_SHORT).show();
                    }
                    finish();
                })
                .setNegativeButton("NO", null); //do nothing on clicking NO button :P

        dialogDelete.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.meun,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_mde){
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            item.setVisible(false);
            menu.findItem(R.id.action_list).setVisible(true);
            return true;
        }else if (item.getItemId() == R.id.action_list){
            recyclerView.setLayoutManager(mLinearLayoutManager);
            item.setVisible(false);
            menu.findItem(R.id.action_mde).setVisible(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
