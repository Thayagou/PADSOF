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
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final double GAP_PERC = 0.1;
	
	private int gap;
	
	public PanelPedido(String actionName, String...productos) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, actionName);
		gap = (int)(maxCompHeight *GAP_PERC);
		
		ButtonFactory f = new ButtonFactory();
		
		
		JPanel articulosEmisor = new JPanel();
		articulosEmisor.setOpaque(false);
		articulosEmisor.setLayout(new BoxLayout( articulosEmisor, BoxLayout.Y_AXIS));
		
		articulosEmisor.add(Box.createVerticalStrut(gap));
		for (int i = 0; i < productos.length && i < 4; i++) {
			articulosEmisor.add(f.newLeftAlignedLabel("- " + productos[i], Fonts.TEXT));
		}
		if (productos.length >= 5) articulosEmisor.add(f.newLeftAlignedLabel("- ...", Fonts.TEXT));
		articulosEmisor.add(Box.createVerticalStrut(gap));
		
		add(articulosEmisor, BorderLayout.CENTER);
	}
	
	public PanelPedido(String nombreCliente, String imageName, String actionName, String...productos) {
		this(actionName, productos);
		
		int fotoSize = maxCompHeight - 2*gap;
		
		ButtonFactory f = new ButtonFactory();
		PanelFotoPerfil perfilEmisor = new PanelFotoPerfil(imageName, fotoSize);
		JLabel labelEmisor = f.newLabel(nombreCliente, Fonts.BOLD);

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
