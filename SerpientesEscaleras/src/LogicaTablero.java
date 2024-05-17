import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

public class LogicaTablero {
    private int filas;
    private int columnas;
    private int numCasillas;
    private Map<Integer, Integer> casillasEspeciales;

    public LogicaTablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.numCasillas = filas * columnas;
        casillasEspeciales = new HashMap<>();
        inicializarCasillasEspeciales();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public int generaNumeroAleatorio(int minimo, int maximo) {
        return (int) (Math.random() * (maximo - minimo + 1) + minimo);
    }

    public int aplicarCasillaEspecial(int posicion) {
        if (casillasEspeciales.containsKey(posicion + 1)) {
            int destino = casillasEspeciales.get(posicion + 1);
            if (destino > posicion + 1) {
                JOptionPane.showMessageDialog(null, "El jugador ha encontrado una escalera!");
            } else {
                JOptionPane.showMessageDialog(null, "El jugador ha encontrado una serpiente!");
            }
            return destino - 1;
        }
        return posicion;
    }

    private void inicializarCasillasEspeciales() {
        ArrayList<Integer> posiciones = generarPosicionesAleatorias(0, numCasillas - 1, numCasillas / 10);
        Random random = new Random();
        for (int i = 0; i < posiciones.size(); i++) {
            int posicion = posiciones.get(i);
            int tipo = random.nextInt(2);
            int destino = tipo == 0 ? posicion - random.nextInt(posicion) : posicion + random.nextInt(numCasillas - posicion);
            casillasEspeciales.put(posicion + 1, destino + 1);
        }
    }

    private ArrayList<Integer> generarPosicionesAleatorias(int minimo, int maximo, int cantidad) {
        ArrayList<Integer> posiciones = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int posicion = random.nextInt(maximo - minimo + 1) + minimo;
            if (!posiciones.contains(posicion)) {
                posiciones.add(posicion);
            } else {
                i--;
            }
        }
        return posiciones;
    }
}
