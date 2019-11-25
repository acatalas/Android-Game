package user.example.com;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Grafic {
    private Drawable drawable;
    private int centreX, centreY;
    private int amplada, altura;
    private double incX, incY;
    private double angle, rotacio;
    private int radiColisio;
    private int xAnterior, yAnterior;
    private  int radiInval;
    private View view;

    public Grafic(View view, Drawable drawable){
        this.view = view;
        this.drawable = drawable;
        amplada = drawable.getIntrinsicWidth();
        altura = drawable.getIntrinsicHeight();
        radiColisio = (altura+amplada) / 4;
        radiInval = (int)Math.hypot(amplada/2, altura/2);
    }

    public synchronized void dibuixaGrafic(Canvas canvas){
        int x = centreX - amplada/2;
        int y = centreY - altura/2;
        drawable.setBounds(x, y, x+amplada, y+altura);
        canvas.save();
        canvas.rotate((float)angle, centreX, centreY);
        drawable.draw(canvas);
        canvas.restore();
        view.invalidate(centreX - radiInval, centreY - radiInval, centreX + radiInval, centreY+radiInval);
        view.invalidate(xAnterior - radiInval, yAnterior - radiInval, xAnterior+radiInval, yAnterior+radiInval);
        xAnterior = centreX;
        yAnterior = centreY;
    }

    public void incrementaPosicio(double factor){
        centreX += incX * factor;
        centreY += incY * factor;
        angle += rotacio * factor;
        if(centreX < 0) {centreX = view.getWidth();}
        if(centreX > view.getWidth()) {centreX = 0;}
        if(centreY < 0) {centreY = view.getHeight();}
        if(centreY > view.getWidth()) {centreY = 0;}
    }

    public double distancia(Grafic grafic){
        return Math.hypot(centreX - grafic.centreX, centreY - grafic.centreY);
    }

    public boolean verificaColisio(Grafic g){
        return (distancia(g) < (radiColisio + g.radiColisio));
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getCentreX() {
        return centreX;
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public int getCentreY() {
        return centreY;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    public int getAmplada() {
        return amplada;
    }

    public void setAmplada(int amplada) {
        this.amplada = amplada;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public double getIncX() {
        return incX;
    }

    public void setIncX(double incX) {
        this.incX = incX;
    }

    public double getIncY() {
        return incY;
    }

    public void setIncY(double incY) {
        this.incY = incY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRotacio() {
        return rotacio;
    }

    public void setRotacio(double rotacio) {
        this.rotacio = rotacio;
    }

    public int getRadiColisio() {
        return radiColisio;
    }

    public void setRadiColisio(int radiColisio) {
        this.radiColisio = radiColisio;
    }

    public int getxAnterior() {
        return xAnterior;
    }

    public void setxAnterior(int xAnterior) {
        this.xAnterior = xAnterior;
    }

    public int getyAnterior() {
        return yAnterior;
    }

    public void setyAnterior(int yAnterior) {
        this.yAnterior = yAnterior;
    }

    public int getRadiInval() {
        return radiInval;
    }

    public void setRadiInval(int radiInval) {
        this.radiInval = radiInval;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
