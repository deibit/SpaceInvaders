package spaceinvaders;

public class Enemigo extends Entidad {

    private int fila;
    private int columna;

    private boolean disparable;
    private boolean activo;

    public boolean isDisparable() {
        return disparable;
    }

    public void setDisparable(boolean disparable) {
        this.disparable = disparable;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public enum Tipo {
        TIPO_1, TIPO_2, TIPO_3
    }

    public Enemigo(Tipo t, int fila, int columna) {
        super();

        this.fila = fila;
        this.columna = columna;

        disparable = false;
        activo = true;

        if (t == Tipo.TIPO_1) {
            setImagen("enemigo1.png");
        }

        if (t == Tipo.TIPO_2) {
            setImagen("enemigo2.png");
        }

        if (t == Tipo.TIPO_3) {
            setImagen("enemigo3.png");
        }
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
