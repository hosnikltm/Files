package com.example.hosninoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

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
        button_add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateNote.class);
            startActivity(intent);
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
        Collections.sort(notes, (lhs, rhs) -> {
            if(lhs.getDateTime() > rhs.getDateTime()) {
                return -1;
            } else {
                return 1;
            }
        });

        if(notes != null && notes.size() > 0) { //check if we have any notes!
            final MyAdapter na = new MyAdapter(notes, (ItemLongClickListener) position -> {
                String fileName = ((Note) notes.get(position)).getDateTime()
                        + Utilities.FILE_EXTENSION;
                Intent viewNoteIntent = new Intent(getApplicationContext(), CreateNote.class);
                viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                startActivity(viewNoteIntent);
            });
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            na.notifyDataSetChanged();
            recyclerView.setAdapter(na);





        }
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
