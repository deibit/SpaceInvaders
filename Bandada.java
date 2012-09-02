package spaceinvaders;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Bandada {

    public void getDisparo(ArrayList<Disparo> disparos) {
        ArrayList<Enemigo> enemigosDisparables = new ArrayList<Enemigo>();
        for (Enemigo e: enemigos) {
            if (e.isDisparable() && e.isActivo()) {
                enemigosDisparables.add(e);
            }
        }
        Enemigo e = enemigosDisparables.get(new Random().nextInt(enemigosDisparables.size()));
        Disparo d = new Disparo(Disparo.Tipo.ENEMIGO);
        d.setPosicion(e.getX() + (e.getAncho()/2) + 4, e.getY() + 4);
        d.activar();
        disparos.add(d);
    }

    private enum Direccion {
        IZQUIERDA, DERECHA
    }

    static private int FILAS = 4;
    static private int COLUMNAS = 10;

    static private int VELOCIDAD_HORIZONTAL = 2;
    static private int VELOCIDAD_VERTICAL = 4;

    private Direccion sentido;
    private boolean sentidoCambiado;

    private int distHorizontal;
    private int distVertical;

    private int desplHorizontal;
    private int desplVertical;

    private int limiteIzquierdo;
    private int limiteDerecho;
    private int limiteVertical;

    private ArrayList<Enemigo> enemigos;

    public Bandada() {

        distHorizontal = 32 + 16;
        distVertical = 24 + 16;

        desplHorizontal = VELOCIDAD_HORIZONTAL;
        desplVertical = 0;

        limiteIzquierdo = desplHorizontal;
        limiteDerecho = Pantalla.ANCHO - (distHorizontal - 16);
        limiteVertical = 0;

        sentido = Direccion.DERECHA;
        sentidoCambiado = false;

        enemigos = new ArrayList<Enemigo>();
    }

    public void iniciar() {

        int posicionVertical = 0;
        int posicionHorizontal;
        Enemigo.Tipo t = Enemigo.Tipo.TIPO_1;

        for (int i = 0; i < FILAS; ++i) {

            posicionHorizontal = (Pantalla.ANCHO - (distHorizontal * COLUMNAS)) / 2;
            posicionVertical += distVertical;

            switch (i % 3) {
                case 0:
                    t = Enemigo.Tipo.TIPO_1;
                    break;
                case 1:
                    t = Enemigo.Tipo.TIPO_2;
                    break;
                case 2:
                    t = Enemigo.Tipo.TIPO_3;
                    break;
            }

            for (int j = 0; j < COLUMNAS; ++j) {
                Enemigo enemigo = new Enemigo(t, i, j);
                enemigo.setPosicion(posicionHorizontal, posicionVertical);
                if (i == FILAS - 1) {
                    enemigo.setDisparable(true);
                }
                enemigos.add(enemigo);
                posicionHorizontal += distHorizontal;
            }
        }
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }


    public void actualizar() {

        if (sentido == Direccion.DERECHA) {
            desplHorizontal = VELOCIDAD_HORIZONTAL;
        }

        if (sentido == Direccion.IZQUIERDA) {
            desplHorizontal = -VELOCIDAD_HORIZONTAL;
        }

        if (sentidoCambiado) {
            desplVertical = VELOCIDAD_VERTICAL;
            sentidoCambiado = false;
        } else {
            desplVertical = 0;
        }

        for (Enemigo e: enemigos) {
            e.setPosicion(e.getX() + desplHorizontal, e.getY() + desplVertical);

            if (limiteVertical < e.getY()) {
                limiteVertical = e.getY() + 32;
            }

            if (e.getX() > limiteDerecho) {
                sentido = Direccion.IZQUIERDA;
                sentidoCambiado = true;
            }
            if (e.getX() < limiteIzquierdo) {
                sentido = Direccion.DERECHA;
                sentidoCambiado = true;
            }
        }
    }

    public boolean comprobarColision(Rectangle rect) {
        for (Enemigo e: enemigos) {
            Rectangle r = new Rectangle(e.getX(), e.getY(), e.getAlto(), e.getAncho());
            if (r.intersects(rect) && e.isActivo()) {
                if (e.isDisparable() && e.getFila() != 0) {
                    legarDisparo(e);
                }
                e.setActivo(false);
                return true;
            }
        }
        return false;
    }

    private void legarDisparo(Enemigo e) {
        int filas = e.getFila();
        while(filas != 0) {
            Enemigo ee = enemigos.get(enemigos.indexOf(e) - COLUMNAS);
            if (ee.isActivo()) {
                ee.setDisparable(true);
                return;
            }
            filas--;
        }
    }

    public int getCoordenadaDisparoX(Rectangle rect) {
        int y = (int) rect.getY();
        for (Enemigo e: enemigos) {
            int x = e.getX() - (e.getAncho() / 2);
            if (rect.contains(x, y)) {
                return x;
            }
        }
        return 0;
    }

    public int getCoordenadaDisparoY() {
        return limiteVertical;
    }

}
