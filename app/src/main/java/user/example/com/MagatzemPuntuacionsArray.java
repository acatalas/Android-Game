package user.example.com;

import java.util.Vector;

public class MagatzemPuntuacionsArray implements MagatzemPuntuacions {
    private Vector<String> puntuacions;

    public MagatzemPuntuacionsArray(){
        puntuacions = new Vector<String>();
        puntuacions.add("123000 Pepito Dominguez");
        puntuacions.add("111000 Pedro Martinez");
        puntuacions.add("011000 Paco Pérez");
        puntuacions.add("211213 Maria Martinez");
        puntuacions.add("345543 Antonio Josçe");
        puntuacions.add("434243 Pepe Papas");
        puntuacions.add("432343 Jose Maria");
        puntuacions.add("234431 Maria Jose");
        puntuacions.add("454531 Maria Isabel");
        puntuacions.add("011254 Marta LaMarta");
        puntuacions.add("444205 Rata Martinez");
        puntuacions.add("738242 Reina Sofia");
        puntuacions.add("015645 Reina Iglesias");
        puntuacions.add("057246 Iñigo The Brave");
        puntuacions.add("000222 Kharjo Onos");
        puntuacions.add("004558 Elvis Presley");
        puntuacions.add("211213 Boy George");
        puntuacions.add("448654 Elton John");
        puntuacions.add("444565 Freddie Mercury");
        puntuacions.add("100000 Brian Murray");
        puntuacions.add("785420 Bill Weasley");
        puntuacions.add("254624 Ginny Weasley");
        puntuacions.add("565564 Harry Potter");
        puntuacions.add("000011 Hermione Granger");
        puntuacions.add("002214 Ronald Weasley");
    }

    @Override
    public void guardarPuntuacio(int punts, String nom, long data) {
        puntuacions.add(0, punts + " " + nom);
    }

    @Override
    public Vector<String> llistaPuntuacions(int quantitat) {
        return puntuacions;
    }
}
