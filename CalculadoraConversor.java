import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Aplicacion grafica que realiza operaciones matematicas basicas,
 * conversion de temperatura y conversion de moneda (USD <-> COP).
 */

// Jose 
public class CalculadoraConversor extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    // Tasa fija definida por el enunciado: 1 USD = 3200 COP
    private static final double Tasa_dolar = 3200.0;

    // Solo numeros: signo negativo opcional y parte decimal opcional (ej: 12, -4.5, 0.75)
    private static final String Patron_numero = "-?\\d+(\\.\\d+)?";

    // Campos de texto de cada seccion
    private JTextField txtNumero1;
    private JTextField txtNumero2;
    private JTextField txtTemperatura;
    private JTextField txtMoneda;

    // Botones obligatorios
    private JButton btnSumar;
    private JButton btnRestar;
    private JButton btnMultiplicar;
    private JButton btnDividir;
    private JButton btnCelsiusAFahrenheit;
    private JButton btnFahrenheitACelsius;
    private JButton btnUsdACop;
    private JButton btnCopAUsd;

    // Etiqueta donde se muestra el resultado
    private JLabel lblResultado;

    public CalculadoraConversor() {
        setTitle("Calculadora y Conversor");
        setSize(520, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCentral = new JPanel(new GridLayout(3, 1, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelCentral.add(crearPanelOperaciones());
        panelCentral.add(crearPanelTemperatura());
        panelCentral.add(crearPanelMoneda());

        lblResultado = new JLabel("Resultado: ", JLabel.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        lblResultado.setForeground(new Color(0, 90, 160));
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        add(panelCentral, BorderLayout.CENTER);
        add(lblResultado, BorderLayout.SOUTH);
    }
}
