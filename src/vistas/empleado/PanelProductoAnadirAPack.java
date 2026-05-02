package vistas.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

public class PanelProductoAnadirAPack extends PanelProducto{
	private static final long serialVersionUID = 1L;
	private JSpinner numUds;
	
	public PanelProductoAnadirAPack (String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, "", categorias);

		ButtonFactory f = new ButtonFactory();
		
		TiendaFrame t = TiendaFrame.getInstance();
		int hSpinner = (int)(maxCompHeight * BOTON_PERC_H);
		int wSpinner = t.getPixelsWidth(BOTON_PERC_W);
		Dimension maxSize = new Dimension(wSpinner, hSpinner);
		
		numUds = ButtonFactory.spinnerEntero(Fonts.BOLD, hSpinner, wSpinner);
		numUds.setMaximumSize(maxSize);
		numUds.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel udsLabel = f.newLeftAlignedLabel("Unidades a añadir:", Fonts.BOLD);
		udsLabel.setMaximumSize(maxSize);
		udsLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(udsLabel);
		eastPanel.add(numUds);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		add(eastPanel, BorderLayout.EAST);
		
	}
}
