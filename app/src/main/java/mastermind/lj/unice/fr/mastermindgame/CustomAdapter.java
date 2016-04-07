package mastermind.lj.unice.fr.mastermindgame;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter{

    private List<String> result;
    private Context context;
    private int [] images;

    private static LayoutInflater inflater=null;

    public CustomAdapter(MainActivity mainActivity, List<String> historic, int[] images) {
        this.result = historic;
        this.context = mainActivity;
        this.images = images;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
        ImageView img1;
        ImageView img2;
        ImageView img3;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_element, null);

        String [] tmp = result.get(position).split(" ");
        int[] choix = new int[tmp.length];
        for(int i = 0; i < tmp.length ; ++i){
            choix[i] = Integer.parseInt(tmp[i]);
        }

        holder.tv=(TextView) rowView.findViewById(R.id.resultat);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.img1=(ImageView) rowView.findViewById(R.id.imageView2);
        holder.img2=(ImageView) rowView.findViewById(R.id.imageView3);
        holder.img3=(ImageView) rowView.findViewById(R.id.imageView4);

        holder.tv.setText(result.get(position));
        holder.img.setImageResource(images[choix[0]]);
        holder.img1.setImageResource(images[choix[1]]);
        holder.img2.setImageResource(images[choix[2]]);
        holder.img3.setImageResource(images[choix[3]]);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}