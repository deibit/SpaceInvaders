package spaceinvaders;

public class Disparo extends Entidad {

    static int VELOCIDAD_NAVE = 4;
    static int VELOCIDAD_ENEMIGO = 2;

    public enum Tipo {
        NAVE, ENEMIGO
    }

    private Tipo tipo;

    public Disparo(Tipo t) {
        super();
        if (t == Tipo.ENEMIGO) {
            setImagen("disparo_enemigo.png");
        }
        if (t == Tipo.NAVE) {
            setImagen("disparo.png");
        }
        tipo = t;
        x = 0;
        y = 0;
    }

    public void setPosicion(int x, int y) {
        super.setPosicion(x, y);
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

    public void actualizar() {
        if (!activado) {
            return;
        }

        if (tipo == Tipo.NAVE) {
            y -= VELOCIDAD_NAVE;
        }

        if (tipo == Tipo.ENEMIGO) {
            y += VELOCIDAD_ENEMIGO;
        }
    }
}
