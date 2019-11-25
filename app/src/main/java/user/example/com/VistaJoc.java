package user.example.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Vector;

public class VistaJoc extends View {
    private Grafic nau;
    private int girNau;
    private double acceleracioNau;
    private static final int MAX_VELOCITAT_NAU = 20;
    private static final int PAS_GIR_NAU = 5;
    private static final float PAS_ACCELERACIO_NAU = 0.5f;
    private ThreadJoc fil = new ThreadJoc();
    private static int PERIODE_PROCES=50;
    private long darrerProces = 0;

    private Vector<Grafic> asteroides;
    private int numAsteroides = 5;
    private int numFragments = 3;

    public VistaJoc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Drawable drawableNau, drawableAsteroide, drawableMissil;

        //drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(preferences.getString("graficos", "1").equals("0")){
            drawableAsteroide = getAsteroidVector();
            drawableNau = getShipVector();
        } else {
            drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
            drawableNau = context.getResources().getDrawable(R.drawable.nau);
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        nau = new Grafic(this, drawableNau);
        asteroides = new Vector<>();

        for(int i = 0; i < numAsteroides; i++){
            Grafic asteroide = new Grafic(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngle((int)(Math.random() * 360));
            asteroide.setRotacio((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
    }

    private Drawable getAsteroidVector(){
        Path pathAsteroide = new Path();
        pathAsteroide.moveTo((float)0.3, (float)0.0);
        pathAsteroide.lineTo((float)0.6, (float)0.0);
        pathAsteroide.lineTo((float)0.6, (float)0.3);
        pathAsteroide.lineTo((float)0.8, (float)0.2);
        pathAsteroide.lineTo((float)1.0, (float)0.4);
        pathAsteroide.lineTo((float)0.8, (float)0.6);
        pathAsteroide.lineTo((float)0.9, (float)0.9);
        pathAsteroide.lineTo((float)0.8, (float)1.0);
        pathAsteroide.lineTo((float)0.4, (float)1.0);
        pathAsteroide.lineTo((float)0.0, (float)0.6);
        pathAsteroide.lineTo((float)0.0, (float)0.2);
        pathAsteroide.lineTo((float)0.3, (float)0.0);
        ShapeDrawable dAsteroide = new ShapeDrawable(new PathShape(pathAsteroide, 1, 1));
        dAsteroide.getPaint().setColor(Color.WHITE);
        dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
        dAsteroide.setIntrinsicHeight(50);
        dAsteroide.setIntrinsicWidth(50);
        setBackgroundColor(Color.BLACK);
        return dAsteroide;
    }

    private Drawable getShipVector(){
        Path pathNave = new Path();
        pathNave.moveTo((float)0.0, (float)0.0);
        pathNave.lineTo((float)1, (float)0.5);
        pathNave.lineTo((float)0, (float)1);
        pathNave.lineTo((float)0, (float)0);
        ShapeDrawable dNave = new ShapeDrawable(new PathShape(pathNave, 1, 1));
        dNave.getPaint().setColor(Color.WHITE);
        dNave.getPaint().setStyle(Paint.Style.STROKE);
        dNave.setIntrinsicWidth(40);
        dNave.setIntrinsicHeight(35);
        setBackgroundColor(Color.BLACK);
        return dNave;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        nau.setCentreX(w / 2);
        nau.setCentreY( h / 2);

        for(Grafic asteroide : asteroides){
            do {
                asteroide.setCentreX((int)(Math.random() * w));
                asteroide.setCentreY((int)(Math.random() * h));
            } while (asteroide.distancia(nau) < (w + h) / 5);

        }

        darrerProces = System.currentTimeMillis();
        fil.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        nau.dibuixaGrafic(canvas);
        for(Grafic asteroide : asteroides){
            asteroide.dibuixaGrafic(canvas);
        }
    }

    protected synchronized void actualitzaFisica(){
        long ara = System.currentTimeMillis();
        if(darrerProces + PERIODE_PROCES > ara){
            return;
        }

        double retard = (ara-darrerProces) / PERIODE_PROCES;
        darrerProces = ara;

        nau.setAngle((int) (nau.getAngle() + girNau * retard));
        double nIncX = nau.getIncX() + acceleracioNau * Math.cos(Math.toRadians(nau.getAngle())) * retard;
        double nIncY = nau.getIncY() + acceleracioNau * Math.sin(Math.toRadians(nau.getAngle())) * retard;

        if(Math.hypot(nIncX, nIncY) <= MAX_VELOCITAT_NAU) {
            nau.setIncX(nIncX);
            nau.setIncY(nIncY);
        }

        nau.incrementaPosicio(retard);
        for(int i = 0; i < asteroides.size(); i++){
            asteroides.get(i).incrementaPosicio(retard);
        }
    }

    class ThreadJoc extends Thread {
        public void run(){
            while (true){
                actualitzaFisica();
            }
        }
    }

    @Override
    public boolean onKeyDown(int codiTecla, KeyEvent event){
        super.onKeyDown(codiTecla, event);

        boolean processada = true;
        switch (codiTecla){
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.d("DIRECTION", "KEYCODE_DPAD_UP");
                acceleracioNau =+ PAS_ACCELERACIO_NAU;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.d("DIRECTION", "KEYCODE_DPAD_LEFT");
                girNau =- PAS_GIR_NAU;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Log.d("DIRECTION", "KEYCODE_DPAD_RIGHT");
                girNau =+ PAS_GIR_NAU;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                //ActivaMisil();
                break;
                default:
                    processada = false;
                break;
        }
        return processada;
    }

    public boolean onKeyUp(int codiTecla, KeyEvent event){
        super.onKeyUp(codiTecla, event);
        boolean processada = true;
        switch (codiTecla){
            case KeyEvent.KEYCODE_DPAD_UP:
                acceleracioNau = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                girNau = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                girNau = 0;
                break;
                default:
                    processada = false;
                    break;
        }
        return processada;
    }
}
