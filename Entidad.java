package spaceinvaders;

import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Entidad {

    protected int x;
    protected int y;

    protected int alto;
    protected int ancho;

    protected int desplazamientoHorizontal;

    protected ImageIcon imagen;

    protected boolean activado;

    protected Entidad() {
        desplazamientoHorizontal = 0;
        activado = false;
    }

    protected void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void actualizar() {

    }

    public Image getImagen() {
        return imagen.getImage();
    }

    protected void setImagen(String rutaImagen) {
        imagen = new ImageIcon(this.getClass().getResource(rutaImagen));
        alto = imagen.getIconHeight();
        ancho = imagen.getIconWidth();
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public boolean disparar() {
        return false;
    }

    public boolean estaActivado() {
        return activado;
    }

    public void activar() {
        activado = true;
    }

    public void desactivar() {
        activado = false;
    }
}
