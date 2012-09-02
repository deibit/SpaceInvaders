package spaceinvaders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Juego extends JPanel implements ActionListener {

    private Nave nave;
    private Bandada bandada;
    private Disparo disparoNave;
    private ArrayList<Disparo> disparosEnemigo;

    public Juego() {
        nave = new Nave();
        bandada = new Bandada();
        disparoNave = new Disparo(Disparo.Tipo.NAVE);
        disparosEnemigo = new ArrayList<Disparo>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                nave.teclaPulsada(evento);
            }

            @Override
            public void keyReleased(KeyEvent evento) {
                nave.teclaLiberada(evento);
            }
        });

        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        Timer timer = new Timer(15, this);
        timer.start();

        iniciarJuego();
    }

    public void iniciarJuego() {
        nave.iniciar();
        bandada.iniciar();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(nave.getImagen(), nave.getX(), nave.getY(), this);

        if (disparoNave.estaActivado()) {
            g2d.drawImage(disparoNave.getImagen(), disparoNave.getX(), disparoNave.getY(), this);
        }

        for (Disparo d: disparosEnemigo) {
            d.actualizar();
            g2d.drawImage(d.getImagen(), d.getX(), d.getY(), this);
        }

        for (Enemigo e: bandada.getEnemigos()) {
            if (e.isActivo()) {
                g2d.drawImage(e.getImagen(), e.getX(), e.getY(), this);
            }
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        nave.actualizar();
        disparoNave.actualizar();
        for (Disparo d: disparosEnemigo) {
            d.actualizar();
        }
        bandada.actualizar();
        gestionarDisparos();
        gestionarColisiones();
        repaint();
    }

    private void gestionarColisiones() {
        if (disparoNave.getY() < 0) {
            disparoNave.desactivar();
        }

        if (disparoNave.estaActivado()) {
            Rectangle r = new Rectangle(disparoNave.getX(), disparoNave.getY(), disparoNave.getAlto(), disparoNave.getAncho());
            if (bandada.comprobarColision(r)) {
                disparoNave.desactivar();
            }
        }

        Iterator<Disparo> i = disparosEnemigo.iterator();
        while(i.hasNext()) {
            Disparo d = i.next();
            if (d.getY() > Pantalla.ALTO) {
                i.remove();
            }
        }

        for (Disparo d: disparosEnemigo) {
            Rectangle r = new Rectangle(d.getX(), d.getY(), d.getAlto(), d.getAncho());
            nave.comprobarColision(r);
        }
    }

    private void gestionarDisparos() {
        if (nave.getDisparando() && !disparoNave.estaActivado()) {
            disparoNave.setPosicion(nave.getX() + (nave.getAncho() / 2) - 5, nave.getY() + 2);
            disparoNave.activar();
            nave.setDisparando(false);
        }

        if (new Random().nextInt(100) == 2) {
            bandada.getDisparo(disparosEnemigo);
        }

    }
}
