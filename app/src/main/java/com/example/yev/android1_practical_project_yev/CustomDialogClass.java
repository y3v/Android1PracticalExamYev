package com.example.yev.android1_practical_project_yev;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yev on 2018-03-26.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener{

    String note;
    String newNote;
    Context context;
    public Dialog d;
    CustomAdapter adapter;

    public CustomDialogClass(String note, Context context, CustomAdapter adapter){
        super(context);
        this.note = note;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_get_note);

        TextView mNote = this.findViewById(R.id.mNote);
        newNote = mNote.getText().toString();

        Button submit = this.findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.i("DATABASE::::","UPDATE:" + newNote);
        DatabaseHelper myDb = new DatabaseHelper(context);
        myDb.updateEntry(note, newNote);
        adapter.notifyDataSetChanged();
        dismiss();
    }
}
