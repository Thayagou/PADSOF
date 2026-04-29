package vistas.empleado;

import java.awt.*;
import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class BarraEmpleado extends BarraLateral {
    private static final long serialVersionUID = 1L;
    
    private JButton gestionarProductos;
    private JButton gestionarPedidos;
    private JButton valorarObjetos;
    private JButton gestionarIntercambios;

    public BarraEmpleado() {
        TiendaFrame frame = TiendaFrame.getInstance();
        int distFromLeft = frame.optionBarDistFromLeft();
        int btnHeigth = frame.btnHeight();

        setBackground(ColorPalette.CARD_LIGHT.getColor());
        setPreferredSize(new Dimension(distFromLeft, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));;

        /* Imagen del boton de notificaciones */
		ButtonFactory f = new ButtonFactory();
		
        gestionarProductos = addBtn(f, "Gestionar productos y categorías", btnHeigth, distFromLeft);
        gestionarPedidos = addBtn(f, "Gestionar pedidos", btnHeigth, distFromLeft);
        valorarObjetos = addBtn(f, "Valorar objetos de segunda mano", btnHeigth, distFromLeft);
        gestionarIntercambios = addBtn(f, "Gestionar intercambios", btnHeigth, distFromLeft);
        
        add(gestionarProductos);
        add(gestionarPedidos);
        add(valorarObjetos);
        add(gestionarIntercambios);
    }
    
    private JButton addBtn(ButtonFactory f, String text, int heigth, int width) {
    	JButton btn = f.newButton(text, heigth, width);
    	btn.setBackground(ColorPalette.CARD_LIGHT.getColor());
    	f.addMouseMecanics(btn, ColorPalette.CARD_LIGHT, ColorPalette.CARD_DARK);
    	btn.setBorderPainted(false);
    	
    	return btn;
    }

	@Override
	public void setControlador(ControlBarraLateral c) {
		gestionarProductos.addActionListener(c);
		gestionarPedidos.addActionListener(c);
		valorarObjetos.addActionListener(c);
		gestionarIntercambios.addActionListener(c);
	}
}