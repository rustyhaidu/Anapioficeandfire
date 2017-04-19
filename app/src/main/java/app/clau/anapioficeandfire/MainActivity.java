package app.clau.anapioficeandfire;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.clau.anapioficeandfire.models.MovieCharacter;

public class MainActivity extends AppCompatActivity {

    private List<MyTask> tasks;
    private ProgressBar pb;
    private ListView list;
    private List<MovieCharacter> characterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.listView);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();

        if(isOnline()){
            requestData("https://www.anapioficeandfire.com/api/characters");
        }
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        // task.execute("Param 1","Param 2","Param 3");
        task.execute(uri);
    }

    private void populateListView() {
        ArrayAdapter<MovieCharacter> adapter = new MyListAdapter();
        // ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyListAdapter extends ArrayAdapter<MovieCharacter>{

        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, characterList);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;

            if (itemView== null){
                itemView = getLayoutInflater().inflate(R.layout.item_view,parent,false);
            }
            MovieCharacter currentCharacter = characterList.get(position);

            TextView displayTextView = (TextView)itemView.findViewById(R.id.displayTextView);
            displayTextView.setText(currentCharacter.getName());

            TextView playedByTextView = (TextView)itemView.findViewById(R.id.playedByTextView);
            playedByTextView.setText(String.valueOf(currentCharacter.getPlayedBy()));

            TextView genderTextView = (TextView)itemView.findViewById(R.id.genderTextView);
            genderTextView.setText(String.valueOf(currentCharacter.getGender()));

            return itemView;
        }
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            Toast.makeText(MainActivity.this, "Starting task!", Toast.LENGTH_SHORT).show();
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            populateListView();

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
            Toast.makeText(MainActivity.this, "Finished task!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(MainActivity.this, "Working" + values[0], Toast.LENGTH_LONG).show();
        }
    }
}
