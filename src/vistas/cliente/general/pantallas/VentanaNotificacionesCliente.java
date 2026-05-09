package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelSelectorCajas;
import vistas.common.displays.PanelNotificacion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Tipo: Class VentanaNotificacionesCliente.
 */
public class VentanaNotificacionesCliente extends JPanel implements VentanaConDisplay<PanelNotificacion> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante BTN_WIDTH. */
	private static final double BTN_WIDTH = 0.1;
	
	/** Constante BTN_HEIGHT. */
	private static final double BTN_HEIGHT = 0.07;
	
	/** Constante BTN_V_WRAP. */
	private static final double BTN_V_WRAP = 0.05;
	
	/** Constante BTN_H_WRAP. */
	private static final double BTN_H_WRAP = 0.02;
	
	/** Constante EAST_PANEL_WIDTH. */
	private static final double EAST_PANEL_WIDTH = 0.25;
	
	/** Campo notificaciones. */
	private JPanel notificaciones = new JPanel();
	
	/** Constante APPLY_BTN. */
	public static final String APPLY_BTN = "Aplicar cambios";
	
	/** Campo confirmar. */
	JButton confirmar;
	
	/** Campo panelAjustes. */
	PanelSelectorCajas panelAjustes;
	
	/**
	 * Instancia un nuevo Objeto VentanaNotificacionesCliente.
	 *
	 * @param ajustes parámetro ajustes
	 * @param selected parámetro selected
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
	 */
	public void refreshList() {
		notificaciones.revalidate();
		notificaciones.repaint();
	}
	
	/**
	 * Obtiene SelectedOptions.
	 *
	 * @return valor de SelectedOptions
	 */
	public String[] getSelectedOptions() {
		return panelAjustes.getCategoriasSeleccionadas();
	}

	/**
	 * anadirDisplay.
	 *
	 * @param <K> clave genérica
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelNotificacion
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
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		confirmar.addActionListener(c);
	}

}
