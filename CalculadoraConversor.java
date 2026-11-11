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

// ---------------- Construccion de la interfaz ----------------
    // Daniela
    private JPanel crearPanelOperaciones() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Operaciones matemáticas"));

        JPanel filaCampos = new JPanel(new GridLayout(1, 4, 5, 5));
        filaCampos.add(new JLabel("Número 1:", JLabel.RIGHT));
        txtNumero1 = crearCampoNumerico();
        filaCampos.add(txtNumero1);
        filaCampos.add(new JLabel("Número 2:", JLabel.RIGHT));
        txtNumero2 = crearCampoNumerico();
        filaCampos.add(txtNumero2);

        JPanel filaBotones = new JPanel(new GridLayout(1, 4, 5, 5));
        btnSumar = crearBoton("Sumar");
        btnRestar = crearBoton("Restar");
        btnMultiplicar = crearBoton("Multiplicar");
        btnDividir = crearBoton("Dividir");
        filaBotones.add(btnSumar);
        filaBotones.add(btnRestar);
        filaBotones.add(btnMultiplicar);
        filaBotones.add(btnDividir);

        panel.add(new JLabel("Ingrese los dos números y elija la operación:", JLabel.CENTER));
        panel.add(filaCampos);
        panel.add(filaBotones);
        return panel;
    }

    private JPanel crearPanelTemperatura() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Conversión de temperatura"));

        JPanel filaCampo = new JPanel(new GridLayout(1, 2, 5, 5));
        filaCampo.add(new JLabel("Temperatura (°C o °F):", JLabel.RIGHT));
        txtTemperatura = crearCampoNumerico();
        filaCampo.add(txtTemperatura);

        JPanel filaBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnCelsiusAFahrenheit = crearBoton("°C a °F");
        btnFahrenheitACelsius = crearBoton("°F a °C");
        filaBotones.add(btnCelsiusAFahrenheit);
        filaBotones.add(btnFahrenheitACelsius);

        panel.add(new JLabel("Ingrese la temperatura que desea convertir:", JLabel.CENTER));
        panel.add(filaCampo);
        panel.add(filaBotones);
        return panel;
    }

    private JPanel crearPanelMoneda() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Conversión de moneda (1 USD = $3200 COP)"));

        JPanel filaCampo = new JPanel(new GridLayout(1, 2, 5, 5));
        filaCampo.add(new JLabel("Valor (USD o COP):", JLabel.RIGHT));
        txtMoneda = crearCampoNumerico();
        filaCampo.add(txtMoneda);

        JPanel filaBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnUsdACop = crearBoton("USD a COP");
        btnCopAUsd = crearBoton("COP a USD");
        filaBotones.add(btnUsdACop);
        filaBotones.add(btnCopAUsd);

        panel.add(new JLabel("Ingrese el valor de dinero que desea convertir:", JLabel.CENTER));
        panel.add(filaCampo);
        panel.add(filaBotones);
        return panel;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.addActionListener(this);
        return boton;
    }

    /**
     * Crea un campo de texto que solo deja escribir digitos, un punto decimal
     * y el signo menos. Cualquier letra o caracter especial se ignora.
     */

    //Jose
    private JTextField crearCampoNumerico() { // Crea un campo de texto que solo deja escribir digitos, un punto decimal y el signo menos. Cualquier letra o caracter especial se ignora.
        JTextField campo = new JTextField();
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '-' // Permitir solo dígitos, punto decimal y signo menos
                        && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) { // Permitir borrar con backspace y delete
                    e.consume(); // Ignorar el caracter si no es válido
                }
            }
        });
        return campo; 
    }

    // ---------------- Manejo de eventos ----------------

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        if (origen == btnSumar || origen == btnRestar
                || origen == btnMultiplicar || origen == btnDividir) {
            realizarOperacion(origen);
        } else if (origen == btnCelsiusAFahrenheit || origen == btnFahrenheitACelsius) {
            convertirTemperatura(origen);
        } else if (origen == btnUsdACop || origen == btnCopAUsd) {
            convertirMoneda(origen);
        }
    }

    private void realizarOperacion(Object origen) {
        Double n1 = leerNumero(txtNumero1, "Número 1");
        if (n1 == null) return;
        Double n2 = leerNumero(txtNumero2, "Número 2");
        if (n2 == null) return;

        double resultado;
        String operacion;

        if (origen == btnSumar) {
            resultado = n1 + n2;
            operacion = "+";
        } else if (origen == btnRestar) {
            resultado = n1 - n2;
            operacion = "-";
        } else if (origen == btnMultiplicar) {
            resultado = n1 * n2;
            operacion = "x";
        } else {
            if (n2 == 0) {
                JOptionPane.showMessageDialog(this,
                        "No es posible dividir entre cero.\nIngrese un Número 2 diferente de 0.",
                        "División por cero", JOptionPane.ERROR_MESSAGE);
                txtNumero2.requestFocus();
                return;
            }
            resultado = n1 / n2;
            operacion = "/";
        }

        mostrarResultado(formatear(n1) + " " + operacion + " " + formatear(n2)
                + " = " + formatear(resultado));
    }

    private void convertirTemperatura(Object origen) {
        Double valor = leerNumero(txtTemperatura, "Temperatura");
        if (valor == null) return;

        if (origen == btnCelsiusAFahrenheit) {
            double fahrenheit = (valor * 9 / 5) + 32;
            mostrarResultado(formatear(valor) + " °C = " + formatear(fahrenheit) + " °F");
        } else {
            double celsius = (valor - 32) * 5 / 9;
            mostrarResultado(formatear(valor) + " °F = " + formatear(celsius) + " °C");
        }
    }

    private void convertirMoneda(Object origen) {
        Double valor = leerNumero(txtMoneda, "Valor");
        if (valor == null) return;

        if (valor < 0) {
            JOptionPane.showMessageDialog(this,
                    "El valor de dinero no puede ser negativo.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            txtMoneda.requestFocus();
            return;
        }

        if (origen == btnUsdACop) {
            double pesos = valor * Tasa_dolar;
            mostrarResultado("US$ " + formatear(valor) + " = $ " + formatear(pesos) + " COP");
        } else {
            double dolares = valor / Tasa_dolar;
            mostrarResultado("$ " + formatear(valor) + " COP = US$ " + formatear(dolares));
        }
    }
