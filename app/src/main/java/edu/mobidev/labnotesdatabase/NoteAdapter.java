package edu.mobidev.labnotesdatabase;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by courtneyngo on 10/24/15.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    private ArrayList<Note> noteArrayList;

    public NoteAdapter(Context context, int resource, ArrayList<Note> noteArrayList) {
        super(context, resource, noteArrayList);
        this.noteArrayList = noteArrayList;
    }

    TextView tvNote;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.note_item, parent, false);

            tvNote = (TextView) convertView.findViewById(R.id.tv_note);
            convertView.setTag(0, tvNote); // set 0 as the tag of tvNote
        }

        ((TextView)convertView.getTag(0)).setText(getItem(position).getTitle());

        return convertView;
    }
}
