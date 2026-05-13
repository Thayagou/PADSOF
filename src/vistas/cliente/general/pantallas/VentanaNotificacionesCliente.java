package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import controladores.TiendaFrame;
import vistas.common.assets.PanelSelectorCajas;
import vistas.common.displays.PanelNotificacion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Pantalla que muestra las notificaciones del cliente y permite configurar los ajustes de las mismas.
 */
public class VentanaNotificacionesCliente extends JPanel implements VentanaConDisplay<PanelNotificacion> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante BTN_WIDTH. Anchura del botón de confirmación como porcentaje de la pantalla. */
	private static final double BTN_WIDTH = 0.1;
	
	/** Constante BTN_HEIGHT. Altura del botón de confirmación como porcentaje de la pantalla. */
	private static final double BTN_HEIGHT = 0.07;
	
	/** Constante BTN_V_WRAP. Margen vertical alrededor del botón como porcentaje de la altura de la pantalla. */
	private static final double BTN_V_WRAP = 0.05;
	
	/** Constante BTN_H_WRAP. Margen horizontal alrededor del botón como porcentaje de la anchura de la pantalla. */
	private static final double BTN_H_WRAP = 0.02;
	
	/** Constante EAST_PANEL_WIDTH. Anchura del panel lateral de ajustes como porcentaje de la pantalla. */
	private static final double EAST_PANEL_WIDTH = 0.25;
	
	/** Campo notificaciones. Panel que contiene la lista de notificaciones del usuario. */
	private JPanel notificaciones = new JPanel();
	
	/** Constante APPLY_BTN. Comando de acción para el botón de aplicar cambios en los ajustes. */
	public static final String APPLY_BTN = "Aplicar cambios";
	
	/** Campo confirmar. Botón que aplica los cambios seleccionados en los ajustes. */
	JButton confirmar;
	
	/** Campo panelAjustes. Panel con casillas de verificación para configurar las opciones de notificaciones. */
	PanelSelectorCajas panelAjustes;
	
	/**
	 * Construye la interfaz con la lista de notificaciones a la izquierda y el panel de ajustes a la derecha.
	 *
	 * @param ajustes Array con los nombres de las opciones de ajuste disponibles.
	 * @param selected Array con los índices de las opciones que aparecen seleccionadas por defecto.
	 */
	public VentanaNotificacionesCliente(String[] ajustes, int[] selected) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		int btnHeight = TiendaFrame.getInstance().getPixelsHeight(BTN_HEIGHT);
		int btnWidth = TiendaFrame.getInstance().getPixelsWidth(BTN_WIDTH);
		int btnVWrap = TiendaFrame.getInstance().getPixelsHeight(BTN_V_WRAP);
		int btnHWrap = TiendaFrame.getInstance().getPixelsWidth(BTN_H_WRAP);
		int eastPanelW = TiendaFrame.getInstance().getPixelsWidth(EAST_PANEL_WIDTH);

		notificaciones.setLayout(new BoxLayout(notificaciones, BoxLayout.Y_AXIS));
		notificaciones.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(notificaciones);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);
		
		panelAjustes = new PanelSelectorCajas(ajustes, selected);
		
		confirmar = ButtonFactory.newRoundedButton(APPLY_BTN, btnHeight, btnWidth, 1);
		JPanel btnPanel = new JPanel(new BorderLayout());
		btnPanel.add(confirmar);
		btnPanel.setMaximumSize(new Dimension(btnWidth, btnHeight));
		JPanel wrappped = PanelFactory.wrapVertical(PanelFactory.wrapHorizontal(btnPanel, btnHWrap), btnVWrap);
		
		JPanel eastPanel = new JPanel(new BorderLayout());
		eastPanel.add(panelAjustes, BorderLayout.CENTER);
		eastPanel.add(wrappped, BorderLayout.SOUTH);
		eastPanel.setPreferredSize(new Dimension(eastPanelW, 0));

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Notificaciones", contenido));
		this.add(BorderLayout.EAST, PanelFactory.getVentanaConCabecera("Ajustes", eastPanel));
		
		refreshList();
	}
	
	/**
	 * refreshList.
	 * Refresca la interfaz para mostrar los cambios en el panel de notificaciones.
	 */
	public void refreshList() {
		notificaciones.revalidate();
		notificaciones.repaint();
	}
	
	/**
	 * Obtiene SelectedOptions.
	 *
	 * @return valor de SelectedOptions, un array con los nombres de las opciones de ajuste seleccionadas.
	 */
	public String[] getSelectedOptions() {
		return panelAjustes.getCategoriasSeleccionadas();
	}

	/**
	 * anadirDisplay.
	 * Añade una notificación al panel de la lista y refresca la vista.
	 *
	 * @param <K> subtipo de PanelNotificacion del panel a añadir.
	 * @param panelDisplay Panel de la notificación a añadir.
	 * @return valor de tipo PanelNotificacion, el mismo panel que se añadió.
	 */
	@Override
	public <K extends PanelNotificacion> PanelNotificacion anadirDisplay(K panelDisplay) {
		notificaciones.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
	
	/**
	 * Establece Controlador.
	 *
	 * @param c controlador que manejará los eventos del botón de confirmación.
	 */
	public void setControlador(ActionListener c) {
		confirmar.addActionListener(c);
	}

}