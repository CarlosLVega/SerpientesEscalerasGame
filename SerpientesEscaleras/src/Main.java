public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                crearYMostrarInterfaz();
            }
        });
    }

    private static void crearYMostrarInterfaz() {
        SerpientesEscalerasFrame frame = new SerpientesEscalerasFrame();
        frame.setVisible(true);
    }
}
