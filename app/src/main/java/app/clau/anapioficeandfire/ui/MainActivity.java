package app.clau.anapioficeandfire.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.clau.anapioficeandfire.R;
import app.clau.anapioficeandfire.models.MovieCharacter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar pb;
    private ListView list;
    private List<MovieCharacter> characterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listView);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        getContent();
        registerClickCallBack();

    }

    private void toggleRefresh() {
        if (pb.getVisibility() == View.INVISIBLE) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.INVISIBLE);

        }
    }

    public void getContent() {
        String apiURL = "https://www.anapioficeandfire.com/api/characters";

        if (isOnline()) {
            client = new OkHttpClient();
            Request request = new Request.Builder().url(apiURL).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        JSONArray jsonArrayCharacters = new JSONArray(jsonData);
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArrayCharacters.length(); i++) {
                            JSONObject object = jsonArrayCharacters.optJSONObject(i);
                            JsonElement jsonElement = gson.fromJson(object.toString(), JsonElement.class);
                            MovieCharacter movieCharacter = gson.fromJson(jsonElement, MovieCharacter.class);
                            characterList.add(movieCharacter);
                        }
                        if (response.isSuccessful()) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    populateListView();
                                    toggleRefresh();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }

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

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private void registerClickCallBack() {
        // ListView list= (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                MovieCharacter tappedMovieCharacter= characterList.get(position);
                String message = "You clicked position "+ position + tappedMovieCharacter.getUrl();

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DetailedCharacterActivity.class);
                intent.putExtra("position", tappedMovieCharacter);

                startActivity(intent);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<MovieCharacter> {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, characterList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            MovieCharacter currentCharacter = characterList.get(position);

            TextView displayTextView = (TextView) itemView.findViewById(R.id.displayTextView);
            displayTextView.setText(currentCharacter.getName());

            TextView playedByTextView = (TextView) itemView.findViewById(R.id.playedByTextView);
            playedByTextView.setText(String.valueOf(currentCharacter.getCulture()));

            TextView genderTextView = (TextView) itemView.findViewById(R.id.genderTextView);
            genderTextView.setText(String.valueOf(currentCharacter.getGender()));

            return itemView;
        }
    }
}


