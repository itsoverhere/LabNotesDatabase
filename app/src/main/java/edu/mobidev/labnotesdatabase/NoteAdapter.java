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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        if(convertView == null){
            convertView = ((LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.note_item, parent, false);

            TextView tvNote = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(tvNote);
        }

        ((TextView)convertView.getTag()).setText(
                getItem(position).getTitle());

        return convertView;
    }
}
