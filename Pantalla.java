package spaceinvaders;

import javax.swing.JFrame;

public class Pantalla extends JFrame{

    public static final int ALTO = 480;
    public static final int ANCHO = 640;

    private Pantalla()
    {
        add(new Juego());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO);
        setLocationRelativeTo(null);
        setTitle("Space Invaders");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Pantalla();
    }
}