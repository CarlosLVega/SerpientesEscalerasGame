import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SerpientesEscalerasFrame extends JFrame {

    private LogicaTablero tablero;
    private ArrayList<Jugadores> jugadores;
    private int turno = 0;

    private JPanel panelTablero;
    private JButton botonDado;
    private JLabel labelTurno;
    private JLabel[] labelCasillas;

    public SerpientesEscalerasFrame() {
        setTitle("Escaleras y Serpientes");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 230, 250));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(230, 230, 250));
        add(panelPrincipal);

        JPanel panelControl = new JPanel();
        labelTurno = new JLabel("Turno del Jugador 1");
        labelTurno.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        panelControl.add(labelTurno);

        botonDado = new JButton("Lanzar Dado");
        botonDado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugarTurno();
            }
        });
        panelControl.add(botonDado);

        panelPrincipal.add(panelControl, BorderLayout.SOUTH);

        panelTablero = new JPanel(new GridLayout());
        panelTablero.setBackground(new Color(220, 220, 220));
        panelPrincipal.add(panelTablero, BorderLayout.CENTER);

        configurarJugadores();
    }

    private void configurarJugadores() {
        JPanel panelConfiguracion = new JPanel(new GridLayout(1, 3));
        panelConfiguracion.setBorder(BorderFactory.createTitledBorder("Configuracion del Juego"));
        panelConfiguracion.setBackground(Color.WHITE);
    
        JLabel labelFilas = new JLabel("Filas:");
        JTextField fieldFilas = new JTextField("8");
        JLabel labelColumnas = new JLabel("Columnas:");
        JTextField fieldColumnas = new JTextField("8");
        JLabel labelJugadores = new JLabel("Jugadores (2-4):");
        JTextField fieldJugadores = new JTextField("3");
    
        panelConfiguracion.add(labelFilas);
        panelConfiguracion.add(fieldFilas);
        panelConfiguracion.add(labelColumnas);
        panelConfiguracion.add(fieldColumnas);
        panelConfiguracion.add(labelJugadores);
        panelConfiguracion.add(fieldJugadores);
    
        JButton botonConfigurar = new JButton("Configurar");
        botonConfigurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filas = Integer.parseInt(fieldFilas.getText());
                int columnas = Integer.parseInt(fieldColumnas.getText());
                int numJugadores = Integer.parseInt(fieldJugadores.getText());
    
                tablero = new LogicaTablero(filas, columnas);
                jugadores = new ArrayList<>();
                for (int i = 0; i < numJugadores; i++) {
                    String nombre = JOptionPane.showInputDialog("Nombre del Jugador " + (i + 1));
                    jugadores.add(new Jugadores(nombre != null ? nombre : "Jugador " + (i + 1), getColorJugador(i)));
                }
    
                inicializarTablero();
            }
        });
        panelConfiguracion.add(botonConfigurar);
    
        JButton botonEmpezarDeNuevo = new JButton("Empezar de Nuevo");
        botonEmpezarDeNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicializarTablero();
            }
        });
        panelConfiguracion.add(botonEmpezarDeNuevo);
    
        getContentPane().add(panelConfiguracion, BorderLayout.NORTH);
    }

    private void inicializarTablero() {
        panelTablero.removeAll();
        panelTablero.setLayout(new GridLayout(tablero.getFilas(), tablero.getColumnas()));
        labelCasillas = new JLabel[tablero.getNumCasillas()];
        for (int i = 0; i < tablero.getNumCasillas(); i++) {
            labelCasillas[i] = new JLabel("" + (i + 1), SwingConstants.CENTER);
            labelCasillas[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            labelCasillas[i].setBackground(Color.WHITE);
            labelCasillas[i].setOpaque(true);
            panelTablero.add(labelCasillas[i]);
        }
        revalidate();
        repaint();
    }

    private void jugarTurno() {
        if (tablero == null || jugadores == null) {
            JOptionPane.showMessageDialog(this, "Primero configure el juego desde el panel de configuracion.", "¡Advertencia!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int dado = tablero.generaNumeroAleatorio(1, 6);
        JOptionPane.showMessageDialog(this, "¡El jugador " + jugadores.get(turno).getNombre() + " ha sacado un " + dado + "!", "Resultado del Dado", JOptionPane.PLAIN_MESSAGE);
        int nuevaPosicion = jugadores.get(turno).getPosicion() + dado;
        if (nuevaPosicion >= tablero.getNumCasillas()) {
            nuevaPosicion = tablero.getNumCasillas() - 1;
        }
        nuevaPosicion = tablero.aplicarCasillaEspecial(nuevaPosicion);
        jugadores.get(turno).setPosicion(nuevaPosicion);
        actualizarTablero();

        if (nuevaPosicion == tablero.getNumCasillas() - 1) {
            JOptionPane.showMessageDialog(this, "¡El jugador " + jugadores.get(turno).getNombre() + " ha ganado el juego!", "¡Felicidades!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        if (dado != 6) {
            turno = (turno + 1) % jugadores.size();
        }

        labelTurno.setText("Turno del Jugador " + jugadores.get(turno).getNombre());
    }

    private void actualizarTablero() {
        for (int i = 0; i < tablero.getNumCasillas(); i++) {
            labelCasillas[i].setBackground(null);
        }
        for (Jugadores jugador : jugadores) {
            labelCasillas[jugador.getPosicion()].setBackground(jugador.getColor());
        }
    }

    private static Color getColorJugador(int jugador) {
        switch (jugador) {
            case 0:
                return new Color(255, 182, 193); 
            case 1:
                return new Color(173, 216, 230); 
            case 2:
                return new Color(152, 251, 152); 
            case 3:
                return new Color(255, 218, 185); 
            default:
                return Color.BLACK;
        }
    }
}
