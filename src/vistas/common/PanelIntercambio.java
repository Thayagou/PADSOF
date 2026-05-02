package vistas.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

public class PanelIntercambio extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final double GAP_PERC = 0.1;
	private static final double PERC_W = (1 - 1.1*BOTON_PERC_W) / 2;
	
	public PanelIntercambio (String nombreEmisor, String imagenEmisor, String[] articulosOfrecidos, String actionName, String nombreReceptor, String imagenReceptor, String[] articulosSolicitados) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, actionName);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int gap = (int)(maxCompHeight *GAP_PERC);
		int fotoSize = maxCompHeight - 2*gap;
		Dimension max = new Dimension(t.getPixelsWidth(PERC_W), Integer.MAX_VALUE);
		
		JPanel info = new JPanel(new GridLayout(1,2));
		info.setOpaque(false);
		
		JPanel emisor = new JPanel();
		emisor.setOpaque(false);
		emisor.setLayout(new BoxLayout(emisor, BoxLayout.X_AXIS));
		emisor.setMaximumSize(max);
		
		ButtonFactory f = new ButtonFactory();
		PanelFotoPerfil perfilEmisor = new PanelFotoPerfil(imagenEmisor, fotoSize);
		JLabel labelEmisor = f.newLabel(nombreEmisor, Fonts.BOLD);
		
		JPanel articulosEmisor = new JPanel();
		articulosEmisor.setOpaque(false);
		articulosEmisor.setLayout(new BoxLayout( articulosEmisor, BoxLayout.Y_AXIS));
		
		articulosEmisor.add(Box.createVerticalStrut(gap));
		for (int i = 0; i < articulosOfrecidos.length && i < 4; i++) {
			articulosEmisor.add(f.newLeftAlignedLabel("- " + articulosOfrecidos[i], Fonts.TEXT));
		}
		if (articulosOfrecidos.length >= 5) articulosEmisor.add(f.newLeftAlignedLabel("- ...", Fonts.TEXT));
		articulosEmisor.add(Box.createVerticalStrut(gap));
		
		emisor.add(perfilEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		emisor.add(labelEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		emisor.add(articulosEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		
		info.add(emisor);
		
		JPanel receptor = new JPanel();
		receptor.setOpaque(false);
		receptor.setLayout(new BoxLayout(receptor, BoxLayout.X_AXIS));
		receptor.setMaximumSize(max);
		
		PanelFotoPerfil perfilreceptor = new PanelFotoPerfil(imagenReceptor, fotoSize);
		JLabel labelReceptor = f.newLabel(nombreReceptor, Fonts.BOLD);
		
		receptor.add(perfilreceptor);
		receptor.add(Box.createHorizontalStrut(gap));
		receptor.add(labelReceptor);
		
		JPanel articulosReceptor = new JPanel();
		articulosReceptor.setOpaque(false);
		articulosReceptor.setLayout(new BoxLayout( articulosReceptor, BoxLayout.Y_AXIS));
		
		articulosReceptor.add(Box.createVerticalStrut(gap));
		for (int i = 0; i < articulosSolicitados.length && i < 4; i++) {
			articulosReceptor.add(f.newLeftAlignedLabel("- " + articulosSolicitados[i], Fonts.TEXT));
		}
		if (articulosSolicitados.length >= 5) articulosReceptor.add(f.newLeftAlignedLabel("- ...", Fonts.TEXT));
		articulosReceptor.add(Box.createVerticalStrut(gap));
		
		receptor.add(perfilreceptor);
		receptor.add(Box.createHorizontalStrut(gap));
		receptor.add(labelReceptor);
		receptor.add(Box.createHorizontalStrut(gap));
		receptor.add(articulosReceptor);
		receptor.add(Box.createHorizontalStrut(gap));
		
		info.add(receptor);
		
		add(info, BorderLayout.CENTER);
	}
	
}
