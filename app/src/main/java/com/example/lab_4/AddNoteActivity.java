package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class AddNoteActivity extends AppCompatActivity {
    private static final String FILE_NAME_number = "number.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MethodUsed", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_notes);
    }
    public void onBtnClickSaveNotes(View view) {
        Log.d("MethodUsed", "onBtnClickSaveNotes");
        EditText editTextGet = (EditText) findViewById(R.id.etTitle);
        String title = editTextGet.getText().toString();
        editTextGet = (EditText) findViewById(R.id.etContent);
        String content = editTextGet.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                saveNotesToFile(title, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void saveNotesToFile(String title, String content) throws IOException {
        Log.d("MethodUsed", "saveNotesToFile");
        int number=0;
        number=getNumberOfNotes();
        String FILENAME = "notesTitle" + String.valueOf(number) + ".txt";
        Log.d("save: ",FILENAME);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(title.getBytes());
        fos.close();

        FILENAME = "notesContent" + String.valueOf(number) + ".txt";
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(content.getBytes());
        fos.close();
        number++;
        writeNumber(number);
    }
    public void writeNumber(int number) {
        Log.d("MethodUsed", "writeNumber");
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
    public int getNumberOfNotes() {
        Log.d("MethodUsed", "getNumberOfNotes");
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
}
