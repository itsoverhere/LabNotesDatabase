package edu.mobidev.labnotesdatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddNoteActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etNote;
    ImageButton buttonSave;
    ImageButton buttonCancel;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new DatabaseHelper(getBaseContext(), "", null, -1);
        // last 3 parameters will be overridden in DatabaseHelper's constructor

        etTitle = (EditText) findViewById(R.id.et_title);
        etNote = (EditText) findViewById(R.id.et_note);
        buttonSave = (ImageButton) findViewById(R.id.button_save);
        buttonCancel = (ImageButton) findViewById(R.id.button_cancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                note.setTitle(etTitle.getText().toString());
                note.setNote(etNote.getText().toString());
                dbHelper.insertNote(note);
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
        getMenuInflater().inflate(R.menu.menu_add_bookmark, menu);
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
