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

/**
 * Subclase de PanelIntercambio que usamos para mostrar los intercambios que requieren algún botón dentro de un scroll.
 */
public class PanelIntercambioConBoton extends PanelIntercambio {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de espacio que se deja horizontalmente entre componentes */
	private static final double H_GAP = 0.1;
	
	/** Botón asociado a la acción del panel, depende del uso */
	private JButton boton;

	
	/**
	 * Instancia un nuevo panel de intercambio con botón que se añadirá a una ventana 
	 *
	 * @param nombreEmisor Nombre del emisor del intercambio
	 * @param imagenEmisor Nombre del receptor del intercambio
	 * @param articulosOfrecidos Artículos ofrecidos al intercambiar
	 * @param actionName Nombre de acción asociada con pulsar sobre el panel
	 * @param buttonName Nombre del botón añadido a la derecha
	 * @param nombreReceptor parámetro nombreReceptor
	 * @param imagenReceptor parámetro imagenReceptor
	 * @param articulosSolicitados parámetro articulosSolicitados
	 */
	public PanelIntercambioConBoton (String nombreEmisor, String imagenEmisor, String[] articulosOfrecidos, String actionName, String buttonName, String nombreReceptor, String imagenReceptor, String[] articulosSolicitados) {
		super (nombreEmisor, imagenEmisor, articulosOfrecidos, actionName, nombreReceptor, imagenReceptor, articulosSolicitados);
		
		inicializarBoton(buttonName);
	}
	
	/**
	 * Inicializa el botón con el nombre asignado y lo añade a la derecha del panel
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
