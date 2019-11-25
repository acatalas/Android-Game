package user.example.com;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Puntuacions extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorPuntuacions adaptador;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Assigna layout a l'activitat
        setContentView(R.layout.puntuacions);

        //Configura el recyclerview
        recyclerView = findViewById(R.id.recycler_view);

        //Especifica el tipus de format de visualització del RecyclerView i l'assigna
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Crea i assigna l'adaptador del RecycleView
        adaptador = new AdaptadorPuntuacions(this, MainActivity.magatzem.llistaPuntuacions(10));
        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(v -> {
            int pos = recyclerView.getChildAdapterPosition(v);
            String s = MainActivity.magatzem.llistaPuntuacions(10).get(pos);
            Toast.makeText(Puntuacions.this, "Selecció: " + pos + " - " + s, Toast.LENGTH_LONG).show();

        });

    }
}
