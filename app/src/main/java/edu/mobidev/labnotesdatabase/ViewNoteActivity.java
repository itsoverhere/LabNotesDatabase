package edu.mobidev.labnotesdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewNoteActivity extends AppCompatActivity {

    public final static int REQUEST_CODE_EDIT = 0;

    private TextView tvTitle;
    private TextView tvNote;
    private ImageButton buttonEdit;
    private ImageButton buttonDelete;

    private DatabaseHelper dbHelper;
    private Note currentNote;

    boolean isNoteEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvNote = (TextView) findViewById(R.id.tv_note);
        buttonEdit = (ImageButton) findViewById(R.id.button_edit);
        buttonDelete = (ImageButton) findViewById(R.id.button_delete);

        dbHelper = new DatabaseHelper(getBaseContext(), "", null, -1);
        // last 3 parameters will be overridden in DatabaseHelper's constructor

        currentNote = dbHelper.getNote(getIntent().getExtras().getInt(Note.COLUMN_ID));

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditNoteActivity.class);
                intent.putExtra(Note.COLUMN_ID, currentNote.getId());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteNote(currentNote.getId());
                finish();
            }
        });

        updateTextViewDisplay();
    }

    public void updateTextViewDisplay(){
        tvTitle.setText(currentNote.getTitle());
        tvNote.setText(currentNote.getNote());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){
            isNoteEdited = true; // set to true so the list in MainActivity will also refresh after finish()
            currentNote = dbHelper.getNote(currentNote.getId()); // update the currentNote
            updateTextViewDisplay(); // update the display for the user to see
        }
    }

    @Override
    public void finish() {
        if(isNoteEdited) {
            setResult(MainActivity.RESULT_EDITED);
        }
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_bookmark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
