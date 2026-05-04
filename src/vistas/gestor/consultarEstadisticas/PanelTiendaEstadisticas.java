package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

public class PanelTiendaEstadisticas extends PanelDisplay{
	private static final long serialVersionUID = 1L;
	private static double MAX_HEIGHT = 0.08;
	private static double MAX_COMP_HEIGHT = 0.9;
	private static double TEXT_FIELD_H = 0.5;
	
	public PanelTiendaEstadisticas(String nombreParametro, String valorString) {
		super(MAX_HEIGHT, MAX_COMP_HEIGHT * MAX_HEIGHT, "");
		
		int gap = (int)((maxHeight * TEXT_FIELD_H)/2);
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W);
		Dimension size = new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H));
		
		JLabel paramLabel = ButtonFactory.newLeftAlignedLabel(nombreParametro, Fonts.BOLD);
		paramLabel.setPreferredSize(size);
		add(paramLabel, BorderLayout.WEST);
		
		JPanel valorPanel = new JPanel();
		valorPanel.setPreferredSize(size);
		
		JLabel valorLabel = ButtonFactory.newLeftAlignedLabel(valorString, Fonts.BOLD);
		paramLabel.setPreferredSize(size);
		add(paramLabel, BorderLayout.WEST);
		
		valorPanel.setOpaque(false);
		valorPanel.setLayout(new BoxLayout(valorPanel, BoxLayout.Y_AXIS));
		valorPanel.add(Box.createHorizontalStrut(gap));
		valorPanel.add(valorLabel);
		valorPanel.add(Box.createHorizontalStrut(gap));
		
		add(valorPanel, BorderLayout.CENTER);
	}
}
