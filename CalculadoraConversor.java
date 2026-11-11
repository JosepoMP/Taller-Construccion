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

    private static final long serialVersionUID = 1L; // Identificador de version para la serializacion de objetos, un número de identificación que Java pide a las clases que heredan de JFrame

    // Tasa fija definida por el enunciado: 1 USD = 3200 COP
    private static final double Tasa_dolar = 3200.0;

    // Solo numeros: signo negativo opcional y parte decimal opcional (ej: 12, -4.5, 0.75)
    private static final String Patron_numero = "-?\\d+(\\.\\d+)?"; // Expresion regular para validar numeros

    // Campos de texto de cada seccion
    private JTextField txtNumero1;
    private JTextField txtNumero2;
    private JTextField txtTemperatura;
    private JTextField txtMoneda;

    // Botones obligatorios
    private JButton btnSumar; // Aquí solo se declaran los componentes: se reserva un “cajón con nombre” para cada uno, pero todavía están vacíos
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicacion al cerrar la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new BorderLayout(10, 10)); // Espacio entre los componentes del BorderLayout

        JPanel panelCentral = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 filas, 1 columna, 10px de espacio entre filas y columnas
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); //margen invisible, para que nada quede pegado al borde
        panelCentral.add(crearPanelOperaciones());
        panelCentral.add(crearPanelTemperatura());
        panelCentral.add(crearPanelMoneda());

        lblResultado = new JLabel("Resultado: ", JLabel.CENTER); // etiqueta: resultado y Centra el texto dentro de la etiqueta
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        lblResultado.setForeground(new Color(0, 90, 160)); // Color azul oscuro para el resultado
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10)); //Margen alrededor del resultado

        add(panelCentral, BorderLayout.CENTER); // Pone el estante con las 3 secciones en el centro de la ventana
        add(lblResultado, BorderLayout.SOUTH); // Pone el resultado abajo 
    }


// ---------------- Construccion de la interfaz ----------------
    // Daniela
    private JPanel crearPanelOperaciones() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5)); // 3 filas, 1 columna, 5px de espacio entre filas y columnas
        panel.setBorder(BorderFactory.createTitledBorder("Operaciones matemáticas")); // Titulo del panel - sirve para dibujar una línea de borde alrededor de un componente que incluye un título de texto en su parte superior.

        JPanel filaCampos = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 fila, 4 columnas, 5px de espacio entre filas y columnas
        filaCampos.add(new JLabel("Número 1:", JLabel.RIGHT)); // Crea una etiqueta con el texto alineado a la derecha desde el inicio
        txtNumero1 = crearCampoNumerico(); // Crea un campo de texto que solo deja escribir digitos, un punto decimal y el signo menos. Cualquier letra o caracter especial se ignora.
        filaCampos.add(txtNumero1); // meter el cuadro de texto dentro de la ventana para que el usuario lo pueda ver y usar.
        filaCampos.add(new JLabel("Número 2:", JLabel.RIGHT)); // Crea una etiqueta con el texto alineado a la derecha desde el inicio
        txtNumero2 = crearCampoNumerico(); // Crea un campo de texto que solo deja escribir digitos, un punto decimal y el signo menos. Cualquier letra o caracter especial se ignora.
        filaCampos.add(txtNumero2); // metee el cuadro de texto dentro de la ventana para que el usuario lo pueda ver y usar.

        JPanel filaBotones = new JPanel(new GridLayout(1, 4, 5, 5)); // 1 fila, 4 columnas, 5px de espacio entre filas y columnas
        btnSumar = crearBoton("Sumar"); // Crea un boton con el texto "Sumar" y agrega un ActionListener para manejar el evento de clic
        btnRestar = crearBoton("Restar");
        btnMultiplicar = crearBoton("Multiplicar");
        btnDividir = crearBoton("Dividir");
        filaBotones.add(btnSumar); // Agrega el botón de sumar a la fila de botones
        filaBotones.add(btnRestar);
        filaBotones.add(btnMultiplicar);
        filaBotones.add(btnDividir);

        panel.add(new JLabel("Ingrese los dos números y elija la operación:", JLabel.CENTER));
        panel.add(filaCampos);
        panel.add(filaBotones);
        return panel; // Devuelve el panel completo con los campos y botones de operaciones matemáticas
    }

    private JPanel crearPanelTemperatura() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5)); // 3 filas, 1 columna, 5px de espacio entre filas y columnas
        panel.setBorder(BorderFactory.createTitledBorder("Conversión de temperatura"));

        JPanel filaCampo = new JPanel(new GridLayout(1, 2, 5, 5)); // 1 fila, 2 columnas, 5px de espacio entre filas y columnas
        filaCampo.add(new JLabel("Temperatura (°C o °F):", JLabel.RIGHT)); // Crea una etiqueta con el texto alineado a la derecha desde el inicio
        txtTemperatura = crearCampoNumerico(); // Crea un campo de texto que solo deja escribir digitos, un punto decimal y el signo menos. Cualquier letra o caracter especial se ignora.
        filaCampo.add(txtTemperatura); // mete el cuadro de texto dentro de la ventana para que el usuario lo pueda ver y usar.

        JPanel filaBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        btnCelsiusAFahrenheit = crearBoton("°C a °F"); // Crea un boton con el texto "°C a °F" y agrega un ActionListener para manejar el evento de clic
        btnFahrenheitACelsius = crearBoton("°F a °C");
        filaBotones.add(btnCelsiusAFahrenheit); // Agrega el botón de conversión de Celsius a Fahrenheit a la fila de botones
        filaBotones.add(btnFahrenheitACelsius);

        panel.add(new JLabel("Ingrese la temperatura que desea convertir:", JLabel.CENTER));
        panel.add(filaCampo); // Agrega la fila de campo de texto al panel
        panel.add(filaBotones); // Agrega la fila de botones al panel
        return panel;
    }

    private JPanel crearPanelMoneda() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5)); // 3 filas, 1 columna, 5px de espacio entre filas y columnas
        panel.setBorder(BorderFactory.createTitledBorder("Conversión de moneda (1 USD = $3200 COP)"));

        JPanel filaCampo = new JPanel(new GridLayout(1, 2, 5, 5)); // 1 fila, 2 columnas, 5px de espacio entre filas y columnas
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

    // sirve para detectar y responder a los clics del usuario en botones en un cuadro de texto y en vez de repetir lo mismo 8 veces, se escribe una vez
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.addActionListener(this); // Agrega un ActionListener al botón para manejar el evento de clic
        return boton; // devueulve el botón creado con el texto y el ActionListener agregado
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
            public void keyTyped(KeyEvent e) { // indica que estamos reescribiendo un método que ya existía
                char c = e.getKeyChar(); // toma el carácter que se escribió y lo guarda en la variable c
                if (!Character.isDigit(c) && c != '.' && c != '-' // ! significa “no” y && significa “y” 
                        && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) { // Permitir borrar con backspace y delete
                    e.consume(); 
                } // basicamente si el usuario presionó una tecla que NO es un número, NO es un punto, NO es un signo menos, y tampoco está intentando borrar lo tira a la basura y no llega al campo de texto
            }
        });
        return campo; 
    }

    // ---------------- Manejo de eventos ----------------

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource(); // Obtiene el botón que se presionó

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
        if (n1 == null) return; // Si el primer número es inválido, se cancela la operación, lo mismo con el n2 
        Double n2 = leerNumero(txtNumero2, "Número 2");
        if (n2 == null) return;

        double resultado; // Almacena el resultado de la operación
        String operacion; // Almacena el símbolo de la operación realizada

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
                JOptionPane.showMessageDialog(this, // Muestra un cuadro de diálogo de error si el usuario intenta dividir entre cero
                        "No es posible dividir entre cero.\nIngrese un Número 2 diferente de 0.",
                        "División por cero", JOptionPane.ERROR_MESSAGE);
                txtNumero2.requestFocus(); // Pone el foco en el campo de texto del segundo número para que el usuario pueda corregirlo fácilmente
                return;
            }
            resultado = n1 / n2;
            operacion = "/";
        }

        mostrarResultado(formatear(n1) + " " + operacion + " " + formatear(n2)
                + " = " + formatear(resultado)); // Java guarda los numeros con .0, "formatear" quita el .0 si es un numero entero
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
            JOptionPane.showMessageDialog(this, // JOptionPane sirve para mostrar ventanas emergentes (cuadros de diálogo) de forma rápida y sencilla
                    "El valor de dinero no puede ser negativo.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            txtMoneda.requestFocus(); // Pone el foco en el campo de texto del valor de dinero para que el usuario pueda corregirlo fácilmente
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

    // ---------------- Validaciones y utilidades ----------------

    /**
     * Lee y valida el contenido de un campo. Muestra un JOptionPane si el campo
     * esta vacio o si no contiene un numero valido. Retorna null en caso de error.
     */
    
    // Daniela 
    private Double leerNumero(JTextField campo, String nombreCampo) {
        String texto = campo.getText().trim(); // getText() lee lo que escribió el usuario y trim() le quita los espacios al inicio y al final. Así " 5 " queda "5" y un campo con solo espacios cuenta como vacío

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El campo \"" + nombreCampo + "\" no puede estar vacío.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            campo.requestFocus();
            return null;
        }

        if (!texto.matches(Patron_numero)) {
            JOptionPane.showMessageDialog(this,
                    "El campo \"" + nombreCampo + "\" solo admite números.\n"
                            + "No se permiten letras ni caracteres especiales.\n"
                            + "Ejemplos válidos: 25, -8, 3.75",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
            campo.requestFocus();
            campo.selectAll();
            return null;
        }

        return Double.parseDouble(texto); // si todo bien, covierte el texto "25" a un numero 25 y lo devuelve
    }

    private void mostrarResultado(String texto) {
        lblResultado.setText("Resultado: " + texto);
    }

    /** Muestra el numero sin ".0" cuando es entero y con maximo 4 decimales si no. */
    private String formatear(double numero) {
        if (numero == Math.rint(numero)) {
            return String.format("%,.0f", numero); // Si el número es entero, lo formatea sin decimales
        }
        return String.format("%,.4f", numero).replaceAll("0+$", "").replaceAll("[.,]$", ""); // Si el número tiene decimales, lo formatea con hasta 4 decimales y elimina ceros innecesarios al final
    }

    public static void main(String[] args) {
        new CalculadoraConversor().setVisible(true); // Crea una instancia de la clase CalculadoraConversor y la hace visible en pantalla
    }
}
