package vistas.common;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

public class PanelPedido extends PanelDisplay{

	private static final long serialVersionUID = 1L;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final double GAP_PERC = 0.1;
	
	private int gap;
	
	public PanelPedido(String actionName, String estado, String...productos) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, actionName);
		gap = (int)(maxCompHeight *GAP_PERC);
		
		
		JPanel articulosEmisor = new JPanel();
		articulosEmisor.setOpaque(false);
		articulosEmisor.setLayout(new BoxLayout(articulosEmisor, BoxLayout.Y_AXIS));
		
		articulosEmisor.add(Box.createVerticalStrut(gap));
		articulosEmisor.add(Box.createVerticalStrut(gap));
		articulosEmisor.add(ButtonFactory.newLeftAlignedLabel(productos[0], Fonts.TEXT));
		for (int i = 1; i < productos.length && i < 4; i++) {
			articulosEmisor.add(ButtonFactory.newLeftAlignedLabel(", " + productos[i], Fonts.TEXT));
		}
		if (productos.length >= 5) articulosEmisor.add(ButtonFactory.newLeftAlignedLabel(", ...", Fonts.TEXT));
		articulosEmisor.add(Box.createVerticalStrut(gap));
		
		articulosEmisor.add(Box.createVerticalStrut(gap));
		articulosEmisor.add(ButtonFactory.newLabel(estado, Fonts.SMALL));
		
		add(articulosEmisor, BorderLayout.CENTER);
	}
	
	public PanelPedido(String nombreCliente, String estado, String imageName, String actionName, String...productos) {
		this(actionName, estado, productos);
		
		int fotoSize = maxCompHeight - 2*gap;
		
		PanelFotoPerfil perfilEmisor = new PanelFotoPerfil(imageName, fotoSize);
		JLabel labelEmisor = ButtonFactory.newLabel(nombreCliente, Fonts.BOLD);

		JPanel emisor = new JPanel();
		emisor.setOpaque(false);
		emisor.setLayout(new BoxLayout(emisor, BoxLayout.X_AXIS));
		
		emisor.add(perfilEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		emisor.add(labelEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		
		add(emisor, BorderLayout.WEST);
	}
	
	
}
