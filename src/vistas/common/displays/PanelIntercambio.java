package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFotoPerfil;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar los intercambios dentro de un scroll.
 */
public class PanelIntercambio extends PanelDisplay {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de pantalla utilizado para. */
	private static final double FOTO_H_PERC = 0.99;
	
	/** Porcentaje de altura de pantalla que ocupa. */
	private static final double MAX_HEIGHT = 0.16;
	
	/** Porcentaje de pantalla utilizado para. */
	private static final double GAP_PERC = 0.1;
	
	/** Porcentaje de pantalla utilizado para el panel de los participantes del intercambio. */
	private static final double PERC_W = (1 - 1.1*BOTON_PERC_W) / 2;
	
	/**
	 * Instancia un nuevo panel de intercambio que se añadirá a una ventana 
	 *
	 * @param nombreEmisor Nombre del emisor del intercambio
	 * @param imagenEmisor Nombre del receptor del intercambio
	 * @param articulosOfrecidos Artículos ofrecidos al intercambiar
	 * @param actionName Nombre de acción asociada con pulsar sobre el panel
	 * @param nombreReceptor parámetro nombreReceptor
	 * @param imagenReceptor parámetro imagenReceptor
	 * @param articulosSolicitados parámetro articulosSolicitados
	 */
	public PanelIntercambio (String nombreEmisor, String imagenEmisor, String[] articulosOfrecidos, String actionName, String nombreReceptor, String imagenReceptor, String[] articulosSolicitados) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, actionName);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int gap = (int)(maxCompHeight *GAP_PERC);
		int fotoSize = (maxCompHeight - 2*gap)/2;
		int widthParticipantes = t.getPixelsWidth(PERC_W);
		int widthInfo = widthParticipantes/2 - fotoSize;
		Dimension max = new Dimension(widthParticipantes, Integer.MAX_VALUE);
		
		JPanel info = new JPanel(new GridLayout(1,2));
		info.setOpaque(false);
		info.setMaximumSize(new Dimension(widthParticipantes, maxCompHeight));
		info.setPreferredSize(new Dimension(widthParticipantes, maxCompHeight));
		
		JPanel emisor = new JPanel();
		emisor.setOpaque(false);
		emisor.setLayout(new BoxLayout(emisor, BoxLayout.X_AXIS));
		emisor.setMaximumSize(max);
		
		PanelFotoPerfil perfilEmisor = new PanelFotoPerfil(imagenEmisor, fotoSize);
		JLabel labelEmisor = ButtonFactory.newLabel(Fonts.truncar(nombreEmisor, widthInfo/2, Fonts.BOLD.getFont(), info), Fonts.BOLD);
		
		JPanel articulosEmisor = new JPanel();
		articulosEmisor.setOpaque(false);
		articulosEmisor.setLayout(new BoxLayout( articulosEmisor, BoxLayout.Y_AXIS));
		
		articulosEmisor.add(Box.createVerticalStrut(gap));
		articulosEmisor.add(ButtonFactory.newLeftAlignedLabel(Fonts.truncar(articulosOfrecidos[0], widthInfo/2, Fonts.TEXT.getFont(), articulosEmisor), Fonts.TEXT));
		for (int i = 1; i < articulosOfrecidos.length && i < 4; i++) {
			articulosEmisor.add(ButtonFactory.newLeftAlignedLabel(Fonts.truncar(articulosOfrecidos[i], widthInfo/2, Fonts.TEXT.getFont(), articulosEmisor), Fonts.TEXT));
		}
		if (articulosOfrecidos.length >= 5) articulosEmisor.add(ButtonFactory.newLeftAlignedLabel("...", Fonts.TEXT));
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

		PanelFotoPerfil perfilReceptor = new PanelFotoPerfil(imagenReceptor, fotoSize);
		JLabel labelReceptor = ButtonFactory.newLabel(Fonts.truncar(nombreReceptor, widthInfo/2, Fonts.BOLD.getFont(), info), Fonts.BOLD);

		JPanel articulosReceptor = new JPanel();
		articulosReceptor.setOpaque(false);
		articulosReceptor.setLayout(new BoxLayout(articulosReceptor, BoxLayout.Y_AXIS));

		articulosReceptor.add(Box.createVerticalStrut(gap));
		articulosReceptor.add(ButtonFactory.newLeftAlignedLabel(Fonts.truncar(articulosSolicitados[0], widthInfo/2, Fonts.TEXT.getFont(), articulosEmisor), Fonts.TEXT));
		for (int i = 1; i < articulosSolicitados.length && i < 4; i++) {
			
		    articulosReceptor.add(ButtonFactory.newLeftAlignedLabel(Fonts.truncar(articulosOfrecidos[i], widthInfo/2, Fonts.TEXT.getFont(), articulosEmisor), Fonts.TEXT));
		}
		if (articulosSolicitados.length >= 5) 
		    articulosReceptor.add(ButtonFactory.newLeftAlignedLabel("...", Fonts.TEXT));
		articulosReceptor.add(Box.createVerticalStrut(gap));

		receptor.add(perfilReceptor);
		receptor.add(Box.createHorizontalStrut(gap));
		receptor.add(labelReceptor);
		receptor.add(Box.createHorizontalStrut(gap));
		receptor.add(articulosReceptor);
		receptor.add(Box.createHorizontalStrut(gap));

		info.add(receptor);
		
		add(info, BorderLayout.CENTER);
	}
	
}
