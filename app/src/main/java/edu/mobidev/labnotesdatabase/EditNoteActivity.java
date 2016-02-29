package edu.mobidev.labnotesdatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditNoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etNote;
    private ImageButton buttonSave;
    private ImageButton buttonCancel;

    private Note currentNote;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etTitle = (EditText) findViewById(R.id.et_title);
        etNote = (EditText) findViewById(R.id.et_note);
        buttonSave = (ImageButton) findViewById(R.id.button_save);
        buttonCancel = (ImageButton) findViewById(R.id.button_cancel);

        dbHelper = new DatabaseHelper(getBaseContext(), "", null, -1);

        currentNote = dbHelper.queryNote(getIntent().getExtras().getInt(Note.COLUMN_ID));

        etTitle.setText(currentNote.getTitle());
        etNote.setText(currentNote.getNote());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNote.setTitle(etTitle.getText().toString());
                currentNote.setNote(etNote.getText().toString());
                dbHelper.updateNote(currentNote);
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
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
