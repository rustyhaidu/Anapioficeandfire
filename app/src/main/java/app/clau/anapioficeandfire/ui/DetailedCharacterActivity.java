package app.clau.anapioficeandfire.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import app.clau.anapioficeandfire.R;
import app.clau.anapioficeandfire.models.MovieCharacter;

/**
 * Created by Claudiu on 4/20/2017.
 * For viewing the Details of a Movie Character
 */

public class DetailedCharacterActivity extends AppCompatActivity {
    private MovieCharacter movieCharacter;
    private TextView characterUrlTextView;
    private TextView characterNameTextView;
    private TextView characterGenderTextView;
    private TextView characterCultureTextView;
    private TextView characterBornTextView;
    private TextView characterDiedTextView;
    private TextView CharacterTitlesTextView;
    private TextView CharacterAliasesTextView;
    private TextView CharacterFatherTextView;
    private TextView CharacterMotherTextView;
    private TextView CharacterSpouseTextView;
    private TextView CharacterAllegiancesTextView;
    private TextView CharacterBooksTextView;
    private TextView CharacterPovBooksTextView;
    private TextView CharacterTvSeriesTextView;
    private TextView CharacterPlayedByTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedcharacter_activity);

        movieCharacter = (MovieCharacter) getIntent().getSerializableExtra("position");

        characterUrlTextView = (TextView) findViewById(R.id.characterURL);
        characterNameTextView = (TextView) findViewById(R.id.characterName);
        characterGenderTextView = (TextView) findViewById(R.id.characterGender);
        characterCultureTextView = (TextView) findViewById(R.id.characterCulture);
        characterBornTextView= (TextView) findViewById(R.id.characterBorn);
        characterDiedTextView= (TextView) findViewById(R.id.characterDied);
        CharacterTitlesTextView= (TextView) findViewById(R.id.characterTitles);
        CharacterAliasesTextView= (TextView) findViewById(R.id.characterAliases);
        CharacterFatherTextView= (TextView) findViewById(R.id.characterFather);
        CharacterMotherTextView= (TextView) findViewById(R.id.characterMother);
        CharacterSpouseTextView= (TextView) findViewById(R.id.characterSpouse);
        CharacterAllegiancesTextView= (TextView) findViewById(R.id.characterAllegiances);
        CharacterBooksTextView= (TextView) findViewById(R.id.characterBooks);
        CharacterPovBooksTextView= (TextView) findViewById(R.id.characterPovBooks);
        CharacterTvSeriesTextView= (TextView) findViewById(R.id.characterTvSeries);
        CharacterPlayedByTextView= (TextView) findViewById(R.id.characterPlayedBy);

        characterUrlTextView.setText(movieCharacter.getUrl());
        characterNameTextView.setText(movieCharacter.getName());
        characterGenderTextView.setText(movieCharacter.getGender());
        characterCultureTextView.setText(movieCharacter.getCulture());
        characterBornTextView.setText(movieCharacter.getBorn());
        characterDiedTextView.setText(movieCharacter.getDied());

        String allTitles="";
        for(int i=0;i<movieCharacter.getTitles().length;i++){
            allTitles = allTitles + " ; " + movieCharacter.getTitles()[i];
        }

        CharacterTitlesTextView.setText(allTitles);

        String allAliases="";
        for(int i=0;i<movieCharacter.getAliases().length;i++){
            allAliases =  movieCharacter.getAliases()[i] + " ; " + allAliases ;
        }

        CharacterAliasesTextView.setText(allAliases);

        CharacterFatherTextView.setText(movieCharacter.getFather());
        CharacterMotherTextView.setText(movieCharacter.getMother());
        CharacterSpouseTextView.setText(movieCharacter.getSpouse());

        String allAllegiances="";
        for(int i=0;i<movieCharacter.getAllegiances().length;i++){
            allAllegiances =  movieCharacter.getAllegiances()[i] + " ; " + allAllegiances ;
        }

        CharacterAllegiancesTextView.setText(allAllegiances);

        String allBooks="";
        int lengthBooks = movieCharacter.getBooks().length;
        for(int i=0; i<lengthBooks; i++){
            allBooks =  movieCharacter.getBooks()[i] + " ; " + allBooks ;
        }
        Log.v("ALLBOOKS",allBooks);

        CharacterBooksTextView.setText(allBooks);

        String allPovBooks="";
        for(int i=0; i<movieCharacter.getPovBooks().length; i++){
            allPovBooks =  movieCharacter.getPovBooks()[i] + " ; " + allPovBooks ;
        }

        CharacterPovBooksTextView.setText(allPovBooks);

        String allTvSeries="";
        for(int i=0; i<movieCharacter.getTvSeries().length; i++){
            allTvSeries = movieCharacter.getTvSeries()[i] + " ; " + allTvSeries ;
        }

        CharacterTvSeriesTextView.setText(allTvSeries);

        String allPlayedBy="";
        for(int i=0; i<movieCharacter.getPlayedBy().length; i++){
            allPlayedBy = movieCharacter.getPlayedBy()[i] + " ; " + allPlayedBy;
        }

        CharacterPlayedByTextView.setText(allPlayedBy);


    }
}
