package vistas.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.PanelPedido;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelPedidoGestionarPedido extends PanelPedido{
	private static final long serialVersionUID = 1L;
	JButton avanzarEstadoButton;
	
	public PanelPedidoGestionarPedido(String nombreCliente, String imageName, String actionName, String...productos) {
		super(nombreCliente, imageName, actionName, productos);
		
		TiendaFrame t = TiendaFrame.getInstance();
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H)));
		
		ButtonFactory f = new ButtonFactory();
		
		avanzarEstadoButton = f.newRoundedButton("Avanzar estado del pedido", (int)(maxCompHeight), maxCompHeight, 0.5f);
				//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(avanzarEstadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(avanzarEstadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(avanzarEstadoButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		this.add(eastPanel, BorderLayout.EAST);
	}
}
