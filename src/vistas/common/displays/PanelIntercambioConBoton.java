package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

// TODO: Auto-generated Javadoc
/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelIntercambioConBoton extends PanelIntercambio {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante H_GAP. */
	private static final double H_GAP = 0.1;
	
	/** Botón asociado a la acción de. */
	private JButton boton;

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombreEmisor parámetro nombreEmisor
	 * @param imagenEmisor parámetro imagenEmisor
	 * @param articulosOfrecidos parámetro articulosOfrecidos
	 * @param actionName parámetro actionName
	 * @param buttonName parámetro buttonName
	 * @param nombreReceptor parámetro nombreReceptor
	 * @param imagenReceptor parámetro imagenReceptor
	 * @param articulosSolicitados parámetro articulosSolicitados
	 */
	public PanelIntercambioConBoton (String nombreEmisor, String imagenEmisor, String[] articulosOfrecidos, String actionName, String buttonName, String nombreReceptor, String imagenReceptor, String[] articulosSolicitados) {
		super (nombreEmisor, imagenEmisor, articulosOfrecidos, actionName, nombreReceptor, imagenReceptor, articulosSolicitados);
		
		inicializarBoton(buttonName);
	}
	
	/**
	 * inicializarBoton.
	 *
	 * @param nombre parámetro nombre
	 */
	public void inicializarBoton(String nombre) {
		JPanel wrapperEast = new JPanel();
		wrapperEast.setLayout(new BoxLayout(wrapperEast, BoxLayout.X_AXIS));
		wrapperEast.setOpaque(false);

		int spaceBetween = TiendaFrame.getInstance().getPixelsWidth(H_GAP);
		wrapperEast.add(Box.createHorizontalGlue());
		wrapperEast.add(Box.createHorizontalStrut(spaceBetween));

		TiendaFrame t = TiendaFrame.getInstance();
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		boton = ButtonFactory.newRoundedButton(nombre, (int) (maxCompHeight), maxCompHeight, 0.5f);
		boton.setActionCommand(nombre);
		ButtonFactory.paintButton(boton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(boton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		int gapSize = (int) (maxCompHeight * (1 - BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(boton);
		eastPanel.add(Box.createVerticalStrut(gapSize));

		wrapperEast.add(eastPanel);
		wrapperEast.add(Box.createHorizontalStrut(gapSize));

		add(wrapperEast, BorderLayout.EAST);
	}
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param l Control que es añadido a los componentes
	 */
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		boton.addActionListener(l);
	}
}
