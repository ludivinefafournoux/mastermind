package mastermind.lj.unice.fr.mastermindgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {



    private Button btnReset;
    private Button btn;
    private GridLayout gridLayout;
    public MediaPlayer mp1;

    private int[] arr_images = {
            R.drawable.bleu,
            R.drawable.rouge,
            R.drawable.jaune,
            R.drawable.vert,
            R.drawable.rose,
            R.drawable.marron,
            R.drawable.celeste,
            R.drawable.orange
    };

    String[] strings = {"Bleu", "Rouge",
            "Jaune", "Vert", "Rose", "Marron", "Celeste", "Orange"};

    private int redflag = 0;
    private int whiteflag = 0;
    private final int maxTentative = 10;
    private final int maxHole = 4;
    private final int maxColor = 8;


    private ListView lv;
    private List<String> historicColor;
    private List<String> historicScore;
    private CustomAdapter customAdapter;

    //Tab qui va prendre la proposition de l'user
    private int prop[] = {0, 0, 0, 0};

    //Tab de match
    private int match[] = {0, 0, 0, 0};

    //Tab comprenant la solution
    private int[] solution = {0, 0, 0, 0};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //private int solution[] = {1, 2, 3, 4};
    //String[] tab={"1","2","3"};


    /**
     * Fonction qui génère un nombre aléaoire en 1 et maxHole
     */
    public int nbrand() {
        Random rand = new Random();
        return (rand.nextInt(maxColor));
    }

    /**
     * Redemarre la partie
     *
     * @param v Vue
     */
    public void resetGame(View v) {

        //Cacher la solution
        gridLayout.setVisibility(View.INVISIBLE);
        //vider historicColor et historicScore
        historicColor.clear();
        historicScore.clear();
        ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();

        initCombinaison();
        //cacher le bouton reset et enable 'OK'
        btnReset.setVisibility(View.INVISIBLE);
        btn.setEnabled(true);

        //Remets les pions bien/mal placés à 0
        for (int z = 0; z < maxHole; z++) {
            match[z] = 0;
        }
        redflag = 0;
        whiteflag = 0;

        //Reset Affichage
        TextView tv = (TextView) findViewById(R.id.restant);
        String tmp = "Coup(s) restant(s) : " + (maxTentative - historicColor.size());
        tv.setText(tmp);

        TextView affich = (TextView) findViewById(R.id.affich);
        affich.setText("Pion(s) bien placé(s) : " + redflag + ", Pion(s) mal placé(s) : " + whiteflag);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        final Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner mySpinner3 = (Spinner) findViewById(R.id.spinner3);

        mySpinner.setSelection(0);
        mySpinner1.setSelection(0);
        mySpinner2.setSelection(0);
        mySpinner3.setSelection(0);

    }


    /**
     * Fonction qui compare la solution et la proposition
     *
     * @param v Vue
     */
    public void comparer(View v) {

        //Ajouter a l'historique la proposition courante
        String combinaison = "";
        for (int entry : prop) {
            combinaison += Integer.toString(entry) + " ";
        }

        historicColor.add(combinaison);
        TextView tv = (TextView) findViewById(R.id.restant);
        String tmp = "Coup(s) restant(s) : " + (maxTentative - historicColor.size());
        tv.setText(tmp);


        //On boucle pour voir s'il y a un pion bien placé en vérifiant si solution[i]==prop[i]
        for (int i = 0; i < maxHole; i++) {
            //Si oui, on marque le drapeau rouge
            if (solution[i] == prop[i]) {
                redflag = redflag + 1;
                match[i] = 1;
            }
        }

        //On boucle pour voir s'il y a d'autres pions mal placés
        for (int u = 0; u < maxHole; u++) {
            for (int y = 0; y < maxHole; y++) {
                //Si match[a]==0, cela veut dire qu'on a pas trouvé précédemment que solution[i]==prop[i]
                //Si on trouve qu'un des nombres restants dans prop est présent dans solution, et qu'il ne se trouve pas au même rang dans le 2 tableau, alors la condition est valide
                if (prop[u] == solution[y] && match[y] == 0) {
                    whiteflag = whiteflag + 1;
                    match[y] = 1;
                }
            }
        }

        TextView affich = (TextView) findViewById(R.id.affich);

        /*for(int n=0;n<maxHole;n++){
            tab[1]=tab[1]+Integer.toString(prop[n]);
        }*/

        affich.setText("Pion(s) bien placé(s) : " + redflag + ", Pion(s) mal placé(s) : " + whiteflag);
        historicScore.add(redflag + " " + whiteflag);

        //Scroll tout en bas de la liste
        scrollMyListViewToBottom();


        //On teste si c'est la bonne solution
        if (historicColor.size() >= maxTentative) {
            //Disable Bouton
            btn.setEnabled(false);
            //Affichage du bouton reset
            btnReset.setVisibility(View.VISIBLE);
            //Afficher la solution
            displaySolution();
        }

        if (redflag == 4) {
            btn.setEnabled(false);
            //Affichage du bouton reset
            btnReset.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Vous avez gagné", Toast.LENGTH_LONG).show();
            //Afficher la solution
            displaySolution();
        }

        //Remets les pions bien/mal placés à 0
        for (int z = 0; z < maxHole; z++) {
            match[z] = 0;
        }
        redflag = 0;
        whiteflag = 0;
    }

    public void displaySolution() {
        ImageView sol0 = (ImageView) findViewById(R.id.solution0);
        ImageView sol1 = (ImageView) findViewById(R.id.solution1);
        ImageView sol2 = (ImageView) findViewById(R.id.solution2);
        ImageView sol3 = (ImageView) findViewById(R.id.solution3);

        sol0.setImageResource(arr_images[solution[0]]);
        sol1.setImageResource(arr_images[solution[1]]);
        sol2.setImageResource(arr_images[solution[2]]);
        sol3.setImageResource(arr_images[solution[3]]);


        gridLayout.setVisibility(View.VISIBLE);
    }

    private void scrollMyListViewToBottom() {
        lv.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv.setSelection(customAdapter.getCount() - 1);
            }
        });
    }

    private void initCombinaison() {
        Log.d("solution", "====== Nouveau jeu ======");
        for (int x = 0; x < maxHole; x++) {
            solution[x] = nbrand();
            Log.d("solution", Integer.toString(solution[x]));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp1 = MediaPlayer.create(MainActivity.this, R.raw.sound);
        historicColor = new ArrayList<>();
        historicScore = new ArrayList<>();


        btnReset = (Button) findViewById(R.id.reset);
        btn = (Button) findViewById(R.id.valider);
        gridLayout = (GridLayout) findViewById(R.id.solutions);

        lv = (ListView) findViewById(R.id.coups);
        customAdapter = new CustomAdapter(this, historicColor, arr_images, historicScore);
        lv.setAdapter(customAdapter);
        mp1.start();
        mp1.setLooping(true);

        //Genere la solution alétoirement
        // -------------------> IL FAUDRA REMPLACER solution[x] PAR solution[x] PLUS TARD, J'UTILISE UN TABLEAU CONNU POUR VERIFIER L'ALGO <--------------------
        initCombinaison();


        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        final Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner mySpinner3 = (Spinner) findViewById(R.id.spinner3);

        mySpinner.setAdapter(new MyAdapter(this, R.layout.row, strings));
        mySpinner1.setAdapter(new MyAdapter(this, R.layout.row, strings));
        mySpinner2.setAdapter(new MyAdapter(this, R.layout.row, strings));
        mySpinner3.setAdapter(new MyAdapter(this, R.layout.row, strings));

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                prop[0] = position;
//                Toast.makeText(MainActivity.this, Integer.toString(MainActivity.this.prop[0]), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                prop[1] = position;
//                Toast.makeText(MainActivity.this, Integer.toString(MainActivity.this.prop[1]), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        mySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                prop[2] = position;
//                Toast.makeText(MainActivity.this, Integer.toString(MainActivity.this.prop[2]), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        mySpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                prop[3] = position;
//                Toast.makeText(MainActivity.this, Integer.toString(MainActivity.this.prop[3]), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://mastermind.lj.unice.fr.mastermindgame/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://mastermind.lj.unice.fr.mastermindgame/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);

            ImageView icon = (ImageView) row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}


