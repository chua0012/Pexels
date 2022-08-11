package com.cst2335.pexels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Pics> picture = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        TextView editText = findViewById(R.id.search_text_sp);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        String previous = sharedPreferences.getString(MainActivity.SEARCH, "");
        editText.setText(previous);
        Intent fromPrevious = getIntent();
        String input = fromPrevious.getStringExtra(MainActivity.SEARCH);
        editText.setText(input);


        SharedPreferences.Editor writer = sharedPreferences.edit();
        writer.putString(MainActivity.SEARCH, editText.getText().toString());
        writer.apply();

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        //loadDataFromDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        String msg = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                message = "Author Kyle Chua, Austin Toner, Nicko Omadto";
                break;

            case R.id.picture:
                message = "Author Picture";
                break;

            case R.id.help_item:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help");
                alert.setMessage("Type something on the search field to filter the results.");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity2.this, "Help is closing", Toast.LENGTH_SHORT).show();
                    }
                });
                /*alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "closing now", Toast.LENGTH_LONG).show();
                    }
                });*/
                alert.create().show();
                break;
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }


    private void loadDataFromDatabase() {
        //get a database connection:
        PexelsOpenHelper dbOpener = new PexelsOpenHelper(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {PexelsOpenHelper.PIC_NAME, PexelsOpenHelper.WIDTH, PexelsOpenHelper.HEIGHT, PexelsOpenHelper.AUTHOR_NAME};
        //query all the results from the database:
        Cursor results = db.query(false, PexelsOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int picColIndex = results.getColumnIndex(PexelsOpenHelper.PIC_NAME);
        int widthColIndex = results.getColumnIndex(PexelsOpenHelper.WIDTH);
        int heightColIndex = results.getColumnIndex(PexelsOpenHelper.HEIGHT);
        int authorColIndex = results.getColumnIndex(PexelsOpenHelper.AUTHOR_NAME);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String pic = results.getString(picColIndex);
            String wid = results.getString(widthColIndex);
            String hei = results.getString(heightColIndex);
            String aut = results.getString(authorColIndex);


            //add the new Contact to the array list:
            picture.add(new Pics(pic, wid, hei, aut));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }

    public static class Pics {
        String picName;
        String width;
        String height;
        String author;


        public Pics(String picName,  String width, String height,  String author) {
            this.picName = picName;
            this.width = width;
            this.height = height;
            this.author = author;

        }

       /* public void update(String m) {
            picName = m;
        }*/

        /**
         * Chaining constructor:
         */

        public String getPicName() {
            return picName;
        }
        public String getWidth() {
            return width;
        }
        public String getHeight() {
            return height;
        }
        public String getAuthor() {
            return author;
        }



    }
}



