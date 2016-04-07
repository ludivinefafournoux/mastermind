package mastermind.lj.unice.fr.mastermindgame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {


    private int[] arr_images= {
        R.drawable.bleu,
        R.drawable.rouge,
        R.drawable.jaune,
        R.drawable.vert
    };

    String[] strings = {"Bleu","Rouge",
            "Jaune", "Vert"};

    private int redflag = 0;
    private int whiteflag = 0;

    private ListView lv;
    private List<String> historic;
    private CustomAdapter customAdapter;

    //Tab qui va prendre la proposition de l'user
    private int prop[] = {0, 0, 0, 0};

    //Tab de match
    private int match[] = {0, 0, 0, 0};

    //Tab comprenant la solution
    private int test[] = {0, 0, 0, 0};

    private int solution[] = {1, 2, 3, 4};
    String[] tab={"1","2","3"};


    //Fonction qui génère 4 nombres aléatoires
    public int nbrand() {
        Random rand = new Random();
        int nb = (rand.nextInt(9 - 1)) + 1;
        return nb;

    }


    /**
     * Fonction qui compare la solution et la proposition
     * @param v Vue
     */
    public void comparer(View v) {

        //Ajouter a l'historique la proposition courante
        String combinaison = "";
        for (int entry : prop) {
            combinaison += Integer.toString(entry) + " ";
        }

        historic.add(combinaison);
        TextView tv = (TextView)findViewById(R.id.historic);
        String tmp = "Dernier coup joué : " + historic.get(historic.size() -  1);
        tv.setText(tmp);


        //On boucle pour voir s'il y a un pion bien placé en vérifiant si solution[i]==prop[i]
        for (int i = 0; i < 4; i++) {
            //Si oui, on marque le drapeau rouge
            if (solution[i] == prop[i]) {
                redflag = redflag + 1;
                match[i] = 1;
            }
        }

        //On boucle pour voir s'il y a d'autres pions mal placés
        for (int u = 0; u < 4; u++) {
            for (int y = 0; y < 4; y++) {
                //Si match[a]==0, cela veut dire qu'on a pas trouvé précédemment que solution[i]==prop[i]
                //Si on trouve qu'un des nombres restants dans prop est présent dans solution, et qu'il ne se trouve pas au même rang dans le 2 tableau, alors la condition est valide
                if (prop[u] == solution[y] && match[y] == 0) {
                    whiteflag = whiteflag + 1;
                }
            }
        }

        TextView affich = (TextView) findViewById(R.id.affich);

        for(int n=0;n<4;n++){
            tab[1]=tab[1]+Integer.toString(prop[n]);
        }

        affich.setText("Pions bien placés : " + redflag + ", Pions mal placés : " + whiteflag);

        //Remets les pions bien/mal placés à 0
        for (int z = 0; z < 4; z++) {
            match[z] = 0;

            redflag = 0;
            whiteflag = 0;
        }

        scrollMyListViewToBottom();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historic = new ArrayList<>();

        lv = (ListView) findViewById(R.id.coups);
        customAdapter = new CustomAdapter(this,historic,arr_images);
        lv.setAdapter(customAdapter);

        //Genere la solution alétoirement
        // -------------------> IL FAUDRA REMPLACER test[x] PAR solution[x] PLUS TARD, J'UTILISE UN TABLEAU CONNU POUR VERIFIER L'ALGO <--------------------
        for (int x = 0; x < 4; x++) {
            test[x] = nbrand();
            System.out.println(test[x]);
        }


        final Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        final Spinner mySpinner1 = (Spinner)findViewById(R.id.spinner1);
        final Spinner mySpinner2 = (Spinner)findViewById(R.id.spinner2);
        final Spinner mySpinner3 = (Spinner)findViewById(R.id.spinner3);

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

    public class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);

            ImageView icon = (ImageView)row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}
