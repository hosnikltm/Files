package com.example.hosninoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateCheckBoxNote extends AppCompatActivity {
    private boolean mIsViewingOrUpdating; //state of the activity
    private long mNoteCreationTime;
    private String mFileName;
    private Note mLoadedNote = null;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_note);
        editText = findViewById(R.id.editTextCheckBox);

        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if (mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            mLoadedNote = Utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null) {
                //update the widgets from the loaded note
                editText.setText(mLoadedNote.getCheckBoxText());
                mNoteCreationTime = mLoadedNote.getDateTime();
                mIsViewingOrUpdating = true;
            }
        } else { //user wants to create a new note
            mNoteCreationTime = System.currentTimeMillis();
            mIsViewingOrUpdating = false;
        }
        ImageView mImageViewCheckBox = findViewById(R.id.image_done_check);
        mImageViewCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSaveNote();
            }
        });
    }

    private void validateAndSaveNote() {

        //get the content of widgets to make a note object
        String title = editText.getText().toString();

        //see if user has entered anything :D lol
        if (title.isEmpty()) { //title
            Toast.makeText(CreateCheckBoxNote.this, "please enter a title!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        //set the creation time, if new note, now, otherwise the loaded note's creation time
        if (mLoadedNote != null) {
            mNoteCreationTime = mLoadedNote.getDateTime();
        } else {
            mNoteCreationTime = System.currentTimeMillis();
        }

        //finally save the note!
        if (Utilities.saveNote(this, new Note(mNoteCreationTime, title))) { //success!
            //tell user the note was saved!
            Toast.makeText(this, "note has been saved", Toast.LENGTH_SHORT).show();
        } else { //failed to save the note! but this should not really happen :P :D :|
            Toast.makeText(this, "can not save the note. make sure you have enough space " +
                    "on your device", Toast.LENGTH_SHORT).show();
        }

        finish(); //exit the activity, should return us to MainActivity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //load menu based on the state we are in (new, view/update/delete)
        if (mIsViewingOrUpdating) { //user is viewing or updating a note
            getMenuInflater().inflate(R.menu.menu_note_check_view, menu);
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.action_update: //or update :P
                validateAndSaveNote();
                break;

            case R.id.action_delete:
                actionDelete();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void actionCancel() {
        if (!checkNoteAltred()) {
            //if note is not altered by user (user only viewed the note/or did not write anything)
            finish(); //just exit the activity and go back to MainActivity
        } else { //we want to remind user to decide about saving the changes or not, by showing a dialog
            AlertDialog.Builder dialogCancel = new AlertDialog.Builder(this)
                    .setTitle("discard changes...")
                    .setMessage("are you sure you do not want to save changes to this note?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        finish(); //just go back to main activity
                    })
                    .setNegativeButton("NO", null); //null = stay in the activity!
            dialogCancel.show();
        }
    }

    /**
     * Check to see if a loaded note/new note has been changed by user or not
     *
     * @return true if note is changed, otherwise false
     */
    private boolean checkNoteAltred() {
        if (mIsViewingOrUpdating) { //if in view/update mode
            return mLoadedNote != null && (!editText.getText().toString().equalsIgnoreCase(mLoadedNote.getCheckBoxText()));
        } else { //if in new note mode
            return !editText.getText().toString().isEmpty();
        }
    }

    private void actionDelete() {
        //ask user if he really wants to delete the note!
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("delete note")
                .setMessage("really delete the note?")
                .setPositiveButton("YES", (dialog, which) -> {
                    if (mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), mFileName)) {
                        Toast.makeText(CreateCheckBoxNote.this, mLoadedNote.getCheckBoxText() + " is deleted"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateCheckBoxNote.this, "can not delete the note '" + mLoadedNote.getTitle() + "'"
                                , Toast.LENGTH_SHORT).show();
                    }
                    finish();
                })
                .setNegativeButton("NO", null); //do nothing on clicking NO button :P

        dialogDelete.show();
    }
}