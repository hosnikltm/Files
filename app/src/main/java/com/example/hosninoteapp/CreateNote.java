package com.example.hosninoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CreateNote extends AppCompatActivity {

    private boolean mIsViewingOrUpdating; //state of the activity
    private long mNoteCreationTime;
    private String mFileName;
    private Note mLoadedNote = null;


    private EditText mEditTextTitle,mEditTextSubTitle;
    private View viewSubTitleIndicator;

    private String selectedNoteColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        mEditTextTitle = findViewById(R.id.inputNoteTitle);
        mEditTextSubTitle = findViewById(R.id.editTextTextSubTitle);
        viewSubTitleIndicator = findViewById(R.id.viewSubTitleIndicator);

        //check if view/edit note bundle is set, otherwise user wants to create new note
        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            mLoadedNote = Utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null) {
                //update the widgets from the loaded note
                mEditTextTitle.setText(mLoadedNote.getTitle());
                mEditTextSubTitle.setText(mLoadedNote.getSubTitle());
                mNoteCreationTime = mLoadedNote.getDateTime();
                mIsViewingOrUpdating = true;
            }
        } else { //user wants to create a new note
            mNoteCreationTime = System.currentTimeMillis();
            mIsViewingOrUpdating = false;
        }


        ImageView mImageViewBack = findViewById(R.id.image_back);
        mImageViewBack.setOnClickListener(v -> actionCancel());

        ImageView mImageViewSave = findViewById(R.id.image_done);
        mImageViewSave.setOnClickListener(v -> validateAndSaveNote());

        ImageView mImageViewCheckBox = findViewById(R.id.checkBoxNote);
        mImageViewCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNote.this,CreateCheckBoxNote.class);
                startActivity(intent);
                finish();
            }
        });

        selectedNoteColor = "#333333";

        initMiscellaneous();
        setSubTitleIndicatorColor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //load menu based on the state we are in (new, view/update/delete)
        if(mIsViewingOrUpdating) { //user is viewing or updating a note
            getMenuInflater().inflate(R.menu.menu_note_view, menu);
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

    private void initMiscellaneous(){
        final LinearLayout linearLayout = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        linearLayout.findViewById(R.id.textMiscellaneous).setOnClickListener(v -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        final ImageView imageViewColor1 = findViewById(R.id.imageColor1);
        final ImageView imageViewColor2 = findViewById(R.id.imageColor2);
        final ImageView imageViewColor3 = findViewById(R.id.imageColor3);
        final ImageView imageViewColor4 = findViewById(R.id.imageColor4);
        final ImageView imageViewColor5 = findViewById(R.id.imageColor5);
        final ImageView imageViewColor6 = findViewById(R.id.imageColor6);
        final ImageView imageViewColor7 = findViewById(R.id.imageColor7);
        final ImageView imageViewColor8 = findViewById(R.id.imageColor8);
        final ImageView imageViewColor9 = findViewById(R.id.imageColor9);
        final ImageView imageViewColor10 = findViewById(R.id.imageColor10);
        final ImageView imageViewColor11 = findViewById(R.id.imageColor11);
        final ImageView imageViewColor12 = findViewById(R.id.imageColor12);
        final ImageView imageViewColor13 = findViewById(R.id.imageColor13);
        final ImageView imageViewColor14 = findViewById(R.id.imageColor14);
        linearLayout.findViewById(R.id.viewColor1).setOnClickListener(v -> {
            selectedNoteColor = "#333333";
            imageViewColor1.setImageResource(R.drawable.ic_done);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor2).setOnClickListener(v -> {
            selectedNoteColor = "#FDBE3B";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(R.drawable.ic_done);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor3).setOnClickListener(v -> {
            selectedNoteColor = "#FF4842";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(R.drawable.ic_done);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor4).setOnClickListener(v -> {
            selectedNoteColor = "#3A52FC";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(R.drawable.ic_done);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor5).setOnClickListener(v -> {
            selectedNoteColor = "#000000";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(R.drawable.ic_done);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor6).setOnClickListener(v ->{
                selectedNoteColor = "#a0ef9a";
                imageViewColor1.setImageResource(0);
                imageViewColor2.setImageResource(0);
                imageViewColor3.setImageResource(0);
                imageViewColor4.setImageResource(0);
                imageViewColor5.setImageResource(0);
                imageViewColor6.setImageResource(R.drawable.ic_done);
                imageViewColor7.setImageResource(0);
                imageViewColor8.setImageResource(0);
                imageViewColor9.setImageResource(0);
                imageViewColor10.setImageResource(0);
                imageViewColor11.setImageResource(0);
                imageViewColor12.setImageResource(0);
                imageViewColor13.setImageResource(0);
                imageViewColor14.setImageResource(0);
                setSubTitleIndicatorColor();

        });
        linearLayout.findViewById(R.id.viewColor7).setOnClickListener(v -> {
            selectedNoteColor = "#e91e63";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(R.drawable.ic_done);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor8).setOnClickListener(v -> {
            selectedNoteColor = "#9c27b0";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(R.drawable.ic_done);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor9).setOnClickListener(v -> {
            selectedNoteColor = "#ea80fc";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(R.drawable.ic_done);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor10).setOnClickListener(v -> {
            selectedNoteColor = "#465191";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(R.drawable.ic_done);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor11).setOnClickListener(v -> {
            selectedNoteColor = "#1976d2";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(R.drawable.ic_done);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor12).setOnClickListener(v -> {
            selectedNoteColor = "#18ffff";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(R.drawable.ic_done);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor13).setOnClickListener(v -> {
            selectedNoteColor = "#4caf50";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(R.drawable.ic_done);
            imageViewColor14.setImageResource(0);
            setSubTitleIndicatorColor();
        });
        linearLayout.findViewById(R.id.viewColor14).setOnClickListener(v -> {
            selectedNoteColor = "#607d8b";
            imageViewColor1.setImageResource(0);
            imageViewColor2.setImageResource(0);
            imageViewColor3.setImageResource(0);
            imageViewColor4.setImageResource(0);
            imageViewColor5.setImageResource(0);
            imageViewColor6.setImageResource(0);
            imageViewColor7.setImageResource(0);
            imageViewColor8.setImageResource(0);
            imageViewColor9.setImageResource(0);
            imageViewColor10.setImageResource(0);
            imageViewColor11.setImageResource(0);
            imageViewColor12.setImageResource(0);
            imageViewColor13.setImageResource(0);
            imageViewColor14.setImageResource(R.drawable.ic_done);
            setSubTitleIndicatorColor();
        });


    }
    private void setSubTitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubTitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }
    private void actionDelete() {
        //ask user if he really wants to delete the note!
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("delete note")
                .setMessage("really delete the note?")
                .setPositiveButton("YES", (dialog, which) -> {
                    if(mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), mFileName)) {
                        Toast.makeText(CreateNote.this, mLoadedNote.getTitle() + " is deleted"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateNote.this, "can not delete the note '" + mLoadedNote.getTitle() + "'"
                                , Toast.LENGTH_SHORT).show();
                    }
                    finish();
                })
                .setNegativeButton("NO", null); //do nothing on clicking NO button :P

        dialogDelete.show();
    }

    /**
     * Handle cancel action
     */
    private void actionCancel() {

        if(!checkNoteAltred()) {
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
     * @return true if note is changed, otherwise false
     */
    private boolean checkNoteAltred() {
        if(mIsViewingOrUpdating) { //if in view/update mode
            return mLoadedNote != null && (!mEditTextTitle.getText().toString().equalsIgnoreCase(mLoadedNote.getTitle())
                    || !mEditTextSubTitle.getText().toString().equalsIgnoreCase(mLoadedNote.getSubTitle()));
        } else { //if in new note mode
            return !mEditTextTitle.getText().toString().isEmpty() || !mEditTextSubTitle.getText().toString().isEmpty();
        }
    }

    /**
     * Validate the title and content and save the note and finally exit the activity and go back to MainActivity
     */
    private void validateAndSaveNote() {

        //get the content of widgets to make a note object
        String title = mEditTextTitle.getText().toString();
        String content = mEditTextSubTitle.getText().toString();

        //see if user has entered anything :D lol
        if(title.isEmpty()) { //title
            Toast.makeText(CreateNote.this, "please enter a title!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.isEmpty()) { //content
            Toast.makeText(CreateNote.this, "please enter a content for your note!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        //set the creation time, if new note, now, otherwise the loaded note's creation time
        if(mLoadedNote != null) {
            mNoteCreationTime = mLoadedNote.getDateTime();
        } else {
            mNoteCreationTime = System.currentTimeMillis();
        }

        //finally save the note!
        if(Utilities.saveNote(this, new Note(mNoteCreationTime, title, content,selectedNoteColor))) { //success!
            //tell user the note was saved!
            Toast.makeText(this, "note has been saved", Toast.LENGTH_SHORT).show();
        } else { //failed to save the note! but this should not really happen :P :D :|
            Toast.makeText(this, "can not save the note. make sure you have enough space " +
                    "on your device", Toast.LENGTH_SHORT).show();
        }

        finish(); //exit the activity, should return us to MainActivity
    }
}
