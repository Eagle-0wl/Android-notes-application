package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addNotes) {
            openActivityAddNotes();
        } else if (id == R.id.removeNotes) {
            deleteNotes();
        }
        return true;
    }
    public void openActivityAddNotes() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
        Log.d("MethodUsed", "openActivityAddNotes");
    }
    public void deleteNotes() {
        Intent intent = new Intent(this, DeleteNoteActivity.class);
        startActivity(intent);
        /*for(int i=0; i<=8;i++){
            deleteFile("notesTitle"+String.valueOf(i)+".txt");
            deleteFile("notesContent"+String.valueOf(i)+".txt");
        }
        writeNumber(0);
        Toast.makeText(MainActivity.this, "Delete notes caled", Toast.LENGTH_SHORT).show();
        loadListView();*/

        Log.d("MethodUsed", "deleteNotes");
    }
    public void loadListView() {
        String title = null;
        String content = null;
        int number = 0;
        number = getNumberOfNotes();
        Toast.makeText(MainActivity.this, String.valueOf(number), Toast.LENGTH_SHORT).show();
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
            SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.noteslist, new String[]{"First Line", "Second Line"}, new int[]{R.id.text1, R.id.text2});
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

}