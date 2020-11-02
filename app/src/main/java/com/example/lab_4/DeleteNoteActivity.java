package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadSpinner();
    }
    public void onBtnClickDeleteNote(View view) {
        int number=getNumberOfNotes();
        Spinner spNotesList = (Spinner) findViewById(R.id.spinnerNotesList);
        String spValue = spNotesList.getSelectedItem().toString();
        String title=null;
        for(int i=0; i<number;i++){
            title = getStringFromFile("notesTitle" + String.valueOf(i) + ".txt");
            if (title.equals(spValue)){
                Log.d("onBtnClickDeleteNote2", String.valueOf(number));
                try {
                    RenameNotes(number-1, i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        number--;
        writeNumber(number);
        Log.d("MethodUsed", "deleteNotes");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loadSpinner() {
        ArrayList<String> notesList = new ArrayList<String>();
        String title = null;
        int number = 0;
        number = getNumberOfNotes();
        for (int i = 0; i < number; i++) {
            try {
                title = getStringFromFile("notesTitle" + String.valueOf(i) + ".txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
            notesList.add(title);
        }
        Spinner spNotesList = (Spinner) findViewById(R.id.spinnerNotesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNotesList.setAdapter(adapter);
    }
    public String getStringFromFile(String filePath){
        String text = null;
        FileInputStream fis = null;
        try {
            fis = openFileInput(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            text = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        text = text.replace(text.substring(text.length()-1), "");
        return text;
    }
    public int getNumberOfNotes() {
        int number = 0;
        FileInputStream fis = null;
        try {
            fis = openFileInput("number.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            text = sb.toString();
            text = text.replaceAll("[^0-9]", "");
            number=Integer.valueOf(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return number;
    }
    public void writeNumber(int number) {
        String sNumber=Integer.toString(number);
        FileOutputStream fosTitle = null;
        try {
            fosTitle = openFileOutput("number.txt", MODE_PRIVATE);
            fosTitle.write(sNumber.getBytes());
            fosTitle.write(System.getProperty("line.separator").getBytes());
            fosTitle.flush();
            fosTitle.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fosTitle != null) {
                try {
                    fosTitle.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void RenameNotes(int number, int renameNumber) throws IOException {
        String title= null;
        title = getStringFromFile("notesTitle" + String.valueOf(number) + ".txt");
        String content = null;
        content = getStringFromFile("notesTitle" + String.valueOf(number) + ".txt");
        String FILENAME = "notesTitle" + String.valueOf(renameNumber) + ".txt";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(title.getBytes());
        fos.close();

        FILENAME = "notesContent" + String.valueOf(renameNumber) + ".txt";
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(content.getBytes());
        fos.close();
        deleteFile("notesTitle"+String.valueOf(number)+".txt");
        deleteFile("notesContent"+String.valueOf(number)+".txt");
    }
}
