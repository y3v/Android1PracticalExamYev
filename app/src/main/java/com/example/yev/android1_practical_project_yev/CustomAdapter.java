package com.example.yev.android1_practical_project_yev;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yev on 2018-03-26.
 */

public class CustomAdapter extends ArrayAdapter<String>{

    private Context context;
    private List<String> notes;
    DatabaseHelper myDb;
    private CustomAdapter adapter;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.notes = objects;
        this.context = context;
        this.adapter = this;
    }

    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_row, parent, false);
        myDb = new DatabaseHelper(context);

        final TextView note = rowView.findViewById(R.id.myNote);
        note.setText(notes.get(pos));
        Button delete = rowView.findViewById(R.id.buttonDelete);
        Button edit = rowView.findViewById(R.id.buttonEdit);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteEntry(note.getText().toString());
                notes.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_get_note);

                final TextView mNote = dialog.findViewById(R.id.mNote);
                Log.i("mNOTE::::","ID:" + mNote.getId());
                mNote.setText(note.getText().toString());

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;


                Button submit = dialog.findViewById(R.id.buttonSubmit);
                submit.setText("Update");
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newNote = mNote.getText().toString();
                        String oldNote = note.getText().toString();
                        notes.set(pos, newNote);
                        Log.i("DATABASE::::","UPDATE:" + newNote);
                        DatabaseHelper myDb = new DatabaseHelper(context);
                        myDb.updateEntry(oldNote, newNote);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });

        return rowView;
    }
}
