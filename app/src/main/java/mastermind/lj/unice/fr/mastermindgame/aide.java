package mastermind.lj.unice.fr.mastermindgame;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;


public class aide extends Activity {

    public ImageButton imageButtonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide);
        Exit();
    }
    public void Exit(){
        imageButtonExit=(ImageButton)findViewById(R.id.imageButtonExit);
        imageButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}