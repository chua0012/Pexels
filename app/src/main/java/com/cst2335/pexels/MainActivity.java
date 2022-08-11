package com.cst2335.pexels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {
    EditText query;
    Button button;
    public static final String SHARED_PREFS = "SP";
    public static final String SEARCH = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button =findViewById(R.id.insert);

        query = findViewById(R.id.srchText);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String previous = prefs.getString(SEARCH, "");
        query.setText(previous);

        String stringURL = "https://api.pexels.com/v1/search?query="+query;
        MyHTTPRequest req = new MyHTTPRequest();
        req.execute(stringURL);  //Type 1

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    Intent nextPage = new Intent(MainActivity.this,   MainActivity2.class  );
                    String userTyped = query.getText().toString();
                    nextPage.putExtra(SEARCH, userTyped);

                    startActivity(    nextPage  );
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

	    /*MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });*/
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
            /*case R.id.search_item:
                message = "You clicked on the search";
                break;*/
            case R.id.picture:
                message = "Clicked on favorites";
                break;

            case R.id.help_item:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Help");
                alert.setMessage("Type something on the search field to filter the results.");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Help is closing", Toast.LENGTH_SHORT).show();
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

}

class MyHTTPRequest extends AsyncTask< String, Integer, String>
{

    String query;
    //Type3                Type1
    public String doInBackground(String ... args)
    {
        try {


            //create a URL object of what server to contact:
            URL url = new URL(args[0]);

            //open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String apiKey = "563492ad6f91700001000001b8d9f1ccbcf142928eaec89e9673a619";
            urlConnection.setRequestProperty("Authorization", apiKey  );
            //wait for data:
            InputStream response = urlConnection.getInputStream();

           /* URL pexelsURL = new URL(stringURL);
            HttpURLConnection pexelsURLConnection = (HttpURLConnection) pexelsURL.openConnection();
            pexelsURLConnection.connect();*/
            publishProgress(100);

        }
        catch (Exception e)
        {

        }

        return "Done";
    }

    //Type 2
    public void onProgressUpdate(Integer ... args)
    {

    }
    //Type3
    public void onPostExecute(String fromDoInBackground)
    {
        Log.i("HTTP", fromDoInBackground);
    }
}

