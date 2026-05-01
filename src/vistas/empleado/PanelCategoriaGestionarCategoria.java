package vistas.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.PanelCategoria;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelCategoriaGestionarCategoria extends PanelCategoria{
	
	private static final long serialVersionUID = 1L;

	public PanelCategoriaGestionarCategoria(String nombreCategoria) {
		super(nombreCategoria, "");
		ButtonFactory f = new ButtonFactory();
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H)));
		
		JButton modButton = f.newRoundedButton("Modificar", (int)(maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
				//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		JButton borrarButton = f.newRoundedButton("Borrar", (int)(maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(borrarButton, ColorPalette.RED, ColorPalette.WHITE);
		f.addMouseMecanics(borrarButton, ColorPalette.RED, ColorPalette.LIGHT_RED);
		//modButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 3);
		eastPanel.add(Box.createHorizontalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createHorizontalStrut(gapSize));
		eastPanel.add(borrarButton);
		eastPanel.add(Box.createHorizontalStrut(gapSize));
		
		add(eastPanel, BorderLayout.EAST);
	}
	
}
