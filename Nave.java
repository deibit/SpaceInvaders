package spaceinvaders;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Nave extends Entidad {

    private static int velocidad = 8;
    private static final int TECLA_P = 0x00000011;
    private static final int TECLA_O = 0x00001100;

    private int keys;
    private boolean disparando;

    public Nave() {
        super();
        disparando = false;
        keys = 0x00000000;
    }

    public void iniciar() {
        setImagen("nave.png");
        setPosicion(Pantalla.ANCHO / 2 - getAncho() / 2, Pantalla.ALTO - getAlto() * 2);
    }

    @Override
    public void actualizar() {

        if (keys == 0) desplazamientoHorizontal = 0;
        if (keys == TECLA_P) desplazamientoHorizontal = velocidad;
        if (keys == TECLA_O) desplazamientoHorizontal = -velocidad;

        x += desplazamientoHorizontal;

        if (x < 0) {
            x = 0;
        }

        if (x > Pantalla.ANCHO - getAncho()) {
            x = Pantalla.ANCHO - getAncho();
        }
    }

    public void teclaPulsada(KeyEvent evento) {

        char tecla = evento.getKeyChar();

        if ((tecla == 'o' || tecla == 'O')) {
            desplazamientoHorizontal = -velocidad;
            keys = keys | TECLA_O;
        }

        if ((tecla == 'p' || tecla == 'P')) {
            desplazamientoHorizontal = velocidad;
            keys = keys | TECLA_P;
        }

        if (evento.getKeyCode() == KeyEvent.VK_SPACE) {
            disparando = true;
        }
    }

    public void teclaLiberada(KeyEvent evento) {

        char tecla = evento.getKeyChar();

        if (tecla == 'o' || tecla == 'O') {
            keys = keys ^ TECLA_O;
        }

        if (tecla == 'p' || tecla == 'P'){
            keys = keys ^ TECLA_P;
        }
    }

    public boolean getDisparando() {
        return disparando;
    }

    public void setDisparando(boolean t) {
        disparando = t;
    }

    public boolean comprobarColision(Rectangle r) {
        Rectangle rect_nave = new Rectangle(x, y, alto, ancho);
        if (r.intersects(rect_nave)) {
            return true;
        }
        return false;
    }
}
