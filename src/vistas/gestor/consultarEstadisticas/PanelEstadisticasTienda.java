package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.YearMonth;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelEstadisticasTienda extends PanelDisplay{
	private static final long serialVersionUID = 1L;
	private static double MAX_H = 0.06;
	private static double MAX_H_COMP = 0.9;
	public static final double LABEL_WIDTH = 0.15;
	
	public PanelEstadisticasTienda (YearMonth mes, double recaudacion, int uds, double porcentaje) {
		super(MAX_H, MAX_H * MAX_H_COMP);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int hComps = (int)(maxCompHeight * BOTON_PERC_H);
		int wComps = t.getPixelsWidth(LABEL_WIDTH);
		Dimension maxSize = new Dimension(wComps, hComps);
		
		String mesString = mes.getMonth().toString() + "/ " + mes.getYear();
		
		JLabel nombreLabel = new JLabel(mesString);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);
		
		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);
		
		statsPanel.add(crearColumnaStat(String.format("%.2f €", recaudacion), maxSize, ColorPalette.DARK_GREY));
		statsPanel.add(crearColumnaStat(String.format("%d uds", uds), maxSize, ColorPalette.DARK_GREY));
		statsPanel.add(crearColumnaStat(String.format("%.3f %%", porcentaje), maxSize, ColorPalette.DARK_GREY));
		add(statsPanel, BorderLayout.EAST);
		
	}
	
	public static JPanel crearColumnaStat(String texto, Dimension maxSize, ColorPalette colorTexto) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
	    JLabel label = ButtonFactory.newLabel(texto, Fonts.BOLD);
	    label.setForeground(colorTexto.getColor());
	    label.setMaximumSize(maxSize);
	    label.setPreferredSize(maxSize);
	    //label.setVerticalTextPosition(SwingConstants.CENTER);
	    //label.setAlignmentX(SwingConstants.CENTER);  // Centro horizontal en BoxLayout Y_AXIS
	    
	    panel.add(Box.createVerticalGlue());      // Empuja desde arriba
	    panel.add(label);
	    panel.add(Box.createVerticalGlue());      // Empuja desde abajo
	    
	    return panel;
	}
}
