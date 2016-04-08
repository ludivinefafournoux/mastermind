package mastermind.lj.unice.fr.mastermindgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import mastermind.lj.unice.fr.mastermindgame.Aide;
import mastermind.lj.unice.fr.mastermindgame.MainActivity;
import mastermind.lj.unice.fr.mastermindgame.R;


public class Principale extends Activity {

    public static ImageButton imageButton2Play, imageButton3Aide, imageButton4Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);

        //declarations des methodes des boutons

        Play();
        Aide();
        Exit();
    }




// fonctionnement pour le bouton Play


    public void Play(){
        imageButton2Play=(ImageButton)findViewById(R.id.imageButton2Play);
        imageButton2Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent play = new Intent(Principale.this, MainActivity.class );
                        startActivity(play);
            }
        });
    }

    // fonctionnement pour le bouton Exit

    public void Exit(){
        imageButton4Exit=(ImageButton)findViewById(R.id.imageButton4Exit);
        imageButton4Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

   //  fonctionnement pour le bouton aide

    public void Aide(){
        imageButton3Aide=(ImageButton)findViewById(R.id.imageButton3Aide);
        imageButton3Aide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aide = new Intent(Principale.this, Aide.class);
                startActivity(aide);
            }
        });
    }


}
