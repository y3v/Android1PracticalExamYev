package com.example.yev.android1_practical_project_yev;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    ListView listView;
    Button addNote;
    ArrayList<String> notes = new ArrayList<>();

    //Database
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Get view Elements
        listView = findViewById(R.id.listviewNotes);
        addNote = findViewById(R.id.buttonAddNote);

        //DB Instantiation
        myDb = new DatabaseHelper(this);

        listView.setAdapter(new CustomAdapter(this, R.layout.custom_list_row, notes));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Log.d("LIST ITEM", "CLICKED!!!!!!!!!!!!!!!!!!!!");
            }
        });

        viewAll();


        Log.d("ARRAYLIST SIZE:::", notes.size() + "");
        for (String str: notes) {
            Log.d("STRING:::::::", str);
        }

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout main = findViewById(R.id.main);
                int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;

                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,width,height, true);

                popupWindow.showAtLocation(main, Gravity.CENTER, 0,0);

                Button textSnippetButton = popupView.findViewById(R.id.textSnippetButton);
                Button photoButton = popupView.findViewById(R.id.photoSnippetButton);
                Button cancelButton = popupView.findViewById(R.id.cancelButton);

                textSnippetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("START ", "NEW INTENT!!!!!!!!!!!!!!!!!!!!");
                        Intent intent = new Intent(getApplicationContext(), GetNote.class);
                        popupWindow.dismiss();
                        startActivityForResult(intent, 1);
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                photoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "NEXT CLASS!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String note = data.getStringExtra("note");
                Log.d("NOTE IN HOMEPAGE:::", note);
                notes.add(note);
                myDb.insertNote(note);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("NOTE:::", "EMPTY");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView.setAdapter(new CustomAdapter(this, R.layout.custom_list_row, notes));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Log.d("LIST ITEM", "CLICKED!!!!!!!!!!!!!!!!!!!!");
            }
        });
    }

    private void viewAll(){
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0){
            Log.d("DATABASE::::", "FRESH PAGE");
        }
        else{
            while(res.moveToNext()){
                notes.add(res.getString(1));
            }
        }
    }
}
