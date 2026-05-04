package vistas.empleado.gestionarPedidos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

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
	
	public PanelPedidoGestionarPedido(String nombreCliente, String estado, String imageName, String actionName, String...productos) {
		super(nombreCliente, estado, imageName, actionName, productos);
		
		TiendaFrame t = TiendaFrame.getInstance();
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H)));
		
		avanzarEstadoButton = ButtonFactory.newRoundedButton(actionName, (int)(maxCompHeight), maxCompHeight, 0.5f);
		ButtonFactory.paintButton(avanzarEstadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(avanzarEstadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(avanzarEstadoButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		this.add(eastPanel, BorderLayout.EAST);
	}
	
	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		avanzarEstadoButton.addActionListener(c);
	}
}
