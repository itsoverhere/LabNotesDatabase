package edu.mobidev.labnotesdatabase;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by courtneyngo on 2/26/16.
 */
public class NoteCursorAdapter extends CursorRecyclerViewAdapter<NoteCursorAdapter.NoteViewHolder> {

    public NoteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder viewHolder, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE));
        viewHolder.tvTitle.setText(title);

        int noteId = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID));
        viewHolder.container.setTag(noteId);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewNoteIntent = new Intent(v.getContext(), ViewNoteActivity.class);
                viewNoteIntent.putExtra(Note.COLUMN_ID, Integer.parseInt(v.getTag().toString()));
                v.getContext().startActivity(viewNoteIntent);
            }
        });
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(v);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        View container;

        public NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            container = itemView.findViewById(R.id.container);
        }
    }


}
