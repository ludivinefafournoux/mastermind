package mastermind.lj.unice.fr.mastermindgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {


    private ImageView choix1;
    private ImageView choix2;
    private ImageView choix3;
    private ImageView choix4;
    private int compteur;
    private Button valider;
    private  Map<Integer,Integer> mapImage;
    private int redflag = 0;
    private int whiteflag = 0;

    private ListView lv;
    private ArrayList<Integer> modelelv;
    private ArrayAdapter<Integer> adapteur;

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

    private void initListeImage(){
        mapImage = new HashMap<>();

        mapImage.put(0,R.drawable.bleu);
        mapImage.put(1,R.drawable.vert);
        mapImage.put(2,R.drawable.rouge);
        mapImage.put(3,R.drawable.jaune);

        choix1 = (ImageView) findViewById(R.id.choix1);
        choix2 = (ImageView) findViewById(R.id.choix2);
        choix3 = (ImageView) findViewById(R.id.choix3);
        choix4 = (ImageView) findViewById(R.id.choix4);

        choix1.setImageResource(mapImage.get(0));
        choix2.setImageResource(mapImage.get(1));
        choix3.setImageResource(mapImage.get(2));
        choix4.setImageResource(mapImage.get(3));
    }

    public void changerCouleur(View view){

        if(compteur >= mapImage.size()){
            compteur = 0;
        }

        ImageView choix = (ImageView) view;
        choix.setImageResource(mapImage.get(compteur));
        ++compteur;
    }


    //Fonction qui compare la solution et la proposition
    public void comparer(View v) {

//        //On lie les EditText à un objet
//        choix1 = (ImageView) findViewById(R.id.choix1);
//        choix2 = (ImageView) findViewById(R.id.choix2);
//        choix3 = (ImageView) findViewById(R.id.choix3);
//        choix4 = (ImageView) findViewById(R.id.choix4);
//
//        choix1.setImageResource(R.drawable.bleu);
//        //On récupère les valeurs des champs
////        String a = choix1.getText().toString();
////        String b = choix2.getText().toString();
////        String c = choix3.getText().toString();
////        String d = choix4.getText().toString();
//        //On parse les champs
//        int da = Integer.parseInt(a);
//        int db = Integer.parseInt(b);
//        int dc = Integer.parseInt(c);
//        int dd = Integer.parseInt(d);
        //On range les valeurs dans une tableau
//        prop[0] = da;
//        prop[1] = db;
//        prop[2] = dc;
//        prop[3] = dd;

        //Récuperer l'id / le nombre de l'image choisit par l'utilisateur



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

        affich.setText("Pions bien placés" + redflag + ", Pions mal placés" + whiteflag);

        //Remets les pions bien/mal placés à 0
        for (int z = 0; z < 4; z++) {
            match[z] = 0;

            redflag = 0;
            whiteflag = 0;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView) findViewById(R.id.coups);
        modelelv=new ArrayList<>();
        adapteur=new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1,
                modelelv);
        lv.setAdapter(adapteur);

/*
        //Lié au l'adapteur pour modifier la list
        lv.setAdapter(myarrayAdapter);
*/

        //Genere la solution alétoirement
        // -------------------> IL FAUDRA REMPLACER test[x] PAR solution[x] PLUS TARD, J'UTILISE UN TABLEAU CONNU POUR VERIFIER L'ALGO <--------------------
        for (int x = 0; x < 4; x++) {
            test[x] = nbrand();
            System.out.println(test[x]);
        }

        compteur = 0;
        initListeImage();
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
}
