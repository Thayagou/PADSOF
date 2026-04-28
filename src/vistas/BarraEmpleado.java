package vistas;

import java.awt.*;
import javax.swing.*;

import controladores.ControlEmpleado;

public class BarraEmpleado extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JButton gestionarProductos;
    private JButton gestionarPedidos;
    private JButton valorarObjetos;
    private JButton gestionarIntercambios;

    public BarraEmpleado() {
        TiendaFrame frame = TiendaFrame.getInstance();
        int distFromLeft = frame.optionBarDistFromLeft();
        int btnHeigth = frame.btnHeight();

        setBackground(ColorPalette.BLUE.getColor());
        setPreferredSize(new Dimension(distFromLeft, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        /* Imagen del boton de notificaciones */
		ButtonFactory factory = new ButtonFactory();
		
        gestionarProductos = factory.newButton("Gestionar productos y categorías", btnHeigth, distFromLeft);
        gestionarPedidos = factory.newButton("Gestionar pedidos", btnHeigth, distFromLeft);
        valorarObjetos = factory.newButton("Valorar objetos de segunda mano", btnHeigth, distFromLeft);
        gestionarIntercambios = factory.newButton("Gestionar inntercambios", btnHeigth, distFromLeft);
        add(gestionarProductos);
        add(gestionarPedidos);
        add(valorarObjetos);
        add(gestionarIntercambios);
    }
    
    public void setControlador(ControlEmpleado c) {
		gestionarProductos.addActionListener(c);
		gestionarPedidos.addActionListener(c);
		valorarObjetos.addActionListener(c);
		gestionarIntercambios.addActionListener(c);
	}
}