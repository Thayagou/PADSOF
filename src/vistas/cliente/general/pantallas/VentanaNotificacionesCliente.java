package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.*;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelSelectorCajas;
import vistas.common.displays.PanelNotificacion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

public class VentanaNotificacionesCliente extends JPanel implements VentanaConDisplay<PanelNotificacion> {

	private static final long serialVersionUID = 1L;
	
	private static final double BTN_WIDTH = 0.1;
	private static final double BTN_HEIGHT = 0.07;
	
	private static final double BTN_V_WRAP = 0.05;
	private static final double BTN_H_WRAP = 0.02;
	
	private static final double EAST_PANEL_WIDTH = 0.25;
	
	private JPanel notificaciones = new JPanel();
	
	JButton confirmar;
	PanelSelectorCajas panelAjustes;
	
	public VentanaNotificacionesCliente(String[] ajustes, int[] selected, String actionName) {
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
		
		confirmar = ButtonFactory.newRoundedButton("Aplicar cambios", btnHeight, btnWidth, 1);
		confirmar.setActionCommand(actionName);
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
	
	public void refreshList() {
		notificaciones.revalidate();
		notificaciones.repaint();
	}
	
	public String[] getSelectedOptions() {
		return panelAjustes.getCategoriasSeleccionadas();
	}

	@Override
	public <K extends PanelNotificacion> PanelNotificacion anadirDisplay(K panelDisplay) {
		notificaciones.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
	
	public void setControlador(ActionListener c) {
		confirmar.addActionListener(c);
	}

}
