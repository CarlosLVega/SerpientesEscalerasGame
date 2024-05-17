import java.awt.Color;

public class Jugadores {
    private String nombre;
    private Color color;
    private int posicion;

    public Jugadores(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        this.posicion = 0; 
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
