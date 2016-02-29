package edu.mobidev.labnotesdatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ViewNoteActivity extends AppCompatActivity {

    private EditText etTitle, etNote;
    private ImageButton buttonAccept, buttonDelete, buttonCancel;

    private DatabaseHelper dbHelper;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        etTitle = (EditText) findViewById(R.id.et_title);
        etNote = (EditText) findViewById(R.id.et_note);
        buttonAccept = (ImageButton) findViewById(R.id.button_accept);
        buttonCancel = (ImageButton) findViewById(R.id.button_cancel);
        buttonDelete = (ImageButton) findViewById(R.id.button_delete);

        dbHelper = new DatabaseHelper(getBaseContext());

        currentNote = dbHelper.queryNote(getIntent().getExtras().getInt(Note.COLUMN_ID));

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String note = etNote.getText().toString();
                dbHelper.updateNote(new Note(currentNote.getId(), title, note));
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextViewDisplay();
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
        etTitle.setText(currentNote.getTitle());
        etNote.setText(currentNote.getNote());
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
