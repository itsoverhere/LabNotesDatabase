package edu.mobidev.labnotesdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 0;
    // public static final int REQUEST_CODE_VIEW = 1; // if the user updates/edits the note, we need to notify the adapter
    public static final int RESULT_EDITED = 2;

    ListView lvNotes;
    Button buttonAdd;
    NoteAdapter noteAdapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = (ListView) findViewById(R.id.lv_notes);
        buttonAdd = (Button) findViewById(R.id.button_add);

        dbHelper = new DatabaseHelper(getBaseContext(), "", null, -1);
        // the last three parameters will be overriden anyway
        noteAdapter = new NoteAdapter(getBaseContext(), R.layout.note_item, dbHelper.getAllNotes());

        lvNotes.setAdapter(noteAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getBaseContext(), AddNoteActivity.class), REQUEST_CODE_ADD);
            }
        });

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), ViewNoteActivity.class);
                intent.putExtra(Note.COLUMN_ID, (int)((Note)parent.getItemAtPosition(position)).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) // if a new entry was added
            || (resultCode == RESULT_EDITED) ){ // if a note was viewed and edited, we have to update the title
            // in both scenarios, we have to update the list to the latest database records
            noteAdapter.clear();
            noteAdapter.addAll(dbHelper.getAllNotes());
            noteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
