package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity<list> extends AppCompatActivity {

    private static final String FILE_NAME_title = "notesTitle.txt";
    private static final String FILE_NAME_content = "notesContent.txt";
    List<HashMap<String, String>> notesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MethodUsed", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume() {
        Log.d("MethodUsed", "onResume");
        super.onResume();
        loadListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MethodUsed", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MethodUsed", "onOptionsItemSelected");
        int id = item.getItemId();
        if (id == R.id.addNotes) {
            openActivityAddNotes();
        } else if (id == R.id.removeNotes) {
            deleteNotes();
        }
        return true;
    }
    public void openActivityAddNotes() {
        Log.d("MethodUsed", "openActivityAddNotes");
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
    public void deleteNotes() {
        Log.d("MethodUsed", "deleteNotes");
        Intent intent = new Intent(this, DeleteNoteActivity.class);
        startActivity(intent);
    }
    public void loadListView() {
        Log.d("MethodUsed", " loadListView");
        String title = null;
        String content = null;
        int number = 0;
        number = getNumberOfNotes();
           ListView resultsListView = (ListView) findViewById(R.id.notesListView);
           HashMap<String, String> titleContent = new HashMap<>();
            for (int i = 0; i < number; i++) {
                try {
                    title = getStringFromFile("notesTitle" + String.valueOf(i) + ".txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    content = getStringFromFile("notesContent" + String.valueOf(i) + ".txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                titleContent.put(title, content);
            }
            List<HashMap<String, String>> listItems = notesList;
            SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.noteslist, new String[]{"First Line", "Second Line"}, new int[]{R.id.txtTitle, R.id.txtContent});
            Iterator it = titleContent.entrySet().iterator();
            while (it.hasNext()) {
                HashMap<String, String> resultsMap = new HashMap<>();
                Map.Entry pair = (Map.Entry) it.next();
                resultsMap.put("First Line", pair.getKey().toString());
                resultsMap.put("Second Line", pair.getValue().toString());
                listItems.add(resultsMap);
            }
            resultsListView.setAdapter(adapter);
    }
    public String getStringFromFile(String filePath){
        Log.d("MethodUsed", "getStringFromFile");
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