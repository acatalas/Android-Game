package user.example.com;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

//Un adaptador és un mecanisme estàndard en Android que ens permet crear
//una sèrie de vistes que han de ser mostrades dins un contenidor.
//Amb RecyclerView s'ha d'heretar de la classe RecyclerView.Adapter
//per crear l'adaptador
public class AdaptadorPuntuacions extends RecyclerView.Adapter<AdaptadorPuntuacions.ViewHolder> {
    protected View.OnClickListener onClickListener;
    private LayoutInflater inflador; //Crea layouts a partir de l'XML
    private Vector<String> llista; //LLista de puntucacions
    private Context context;
    private int cont = 0;

    //En el constructor s'inicialitza el conjunt de dades a mostrar
    public AdaptadorPuntuacions(Context context, Vector<String> llista){
        //Un inflador permetrà posteriorment crear una vista a partir del XML
        inflador = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.llista = llista;
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    //Aquesta classe conté les vistes que volem modificar d'un element.
    //Aquesta classe s'utilitza per evitar haver de crear les vistes de cada element
    //des de zero. Utilitzarà un objecte d'aquesta classe amb les tres vistes ja creades
    //però sense personalitzar, de tal manera que emprarà el mateix objecte per a tos els
    //elements i simplement ho personalitzarà segons la posició. Aquesta forma de treballar
    //millor el rendiment de l'aplicació.
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titol, subtitol;
        public ImageView icono;

        public ViewHolder(View itemView){
            super(itemView);
            titol = itemView.findViewById(R.id.titol);
            subtitol = itemView.findViewById(R.id.subtitol);
            icono = itemView.findViewById(R.id.icono);
        }
    }

    //Aquest mètode retorna una vista d'un element sense personalitzar.
    //Es crida un número de vegades segons les vistes que hi caben dins la pantalla.
    //Una vegada el sistema ja té les vistes necessàries per anar mostrant els elements
    //ja no en creen més i reutilitza les existents.
    //Podriem definir diferents vistes per a diferents tipus d'elements
    //utilitzant el paràmetre viewType
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //El mètode inflate() crea (infla) la vista des de l'xml.
        //El segon paràmetre indica el layout pare que contindrà a la vista
        //que s'ha de crear. És necessàri indicar-ho perqué volem que la
        //vista filla s'adapti a la grandària del pare ("mathc_parent").
        //El tercer paràmetre permet indicar si volem que la vista sigui
        //insertada en el pare. En el nostre cas serà false perque ho farà el
        //mateix RecyclerView
        View v = inflador.inflate(R.layout.element_puntuacio, parent, false);
        //Toast.makeText(context, "Cont " + (++cont), Toast.LENGTH_SHORT).show();
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    //Aquest mètode personalitza les dades d'un element de tipus ViewHolder segons
    //la seva posició.
    //A partir del ViewHolder personalitzat, el sistema s'encarrega de crear la vista
    //definitiva que serà insertada en el RecyclerView.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
        holder.titol.setText(llista.get(position));
        switch (Math.round((float)Math.random()*3)) {
            case 0:
                holder.icono.setImageResource(R.drawable.asteroide1);
                break;
            case 1:
                holder.icono.setImageResource(R.drawable.asteroide2);
                break;
            default:
                holder.icono.setImageResource(R.drawable.asteroide3);
                break;
        }
    }

    @Override
    public int getItemCount(){
        return llista.size();
    }
}
