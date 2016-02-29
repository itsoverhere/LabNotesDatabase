package edu.mobidev.labnotesdatabase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    ImageButton buttonAdd;
    NoteCursorAdapter noteAdapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNotes = (RecyclerView) findViewById(R.id.lv_notes);
        buttonAdd = (ImageButton) findViewById(R.id.button_add);

        dbHelper = new DatabaseHelper(getBaseContext());
        noteAdapter = new NoteCursorAdapter(getBaseContext(), null);

        rvNotes.setAdapter(noteAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddNoteActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = dbHelper.queryAllNotesAsCursor();
        noteAdapter.swapCursor(cursor);
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
