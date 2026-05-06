package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticuloSeleccionable;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlHacerOferta implements ControladorPantalla {
	
	Tienda tienda;
	ClienteRegistrado cliente;
	ClienteRegistrado receptor;
	VentanaOfertaIntercambio vista;
	Map<ClienteRegistrado, List<ArticuloSegundaMano>> articulos = new HashMap<>();
	Map<ArticuloSegundaMano, ControlPanelArticuloSeleccionable> controladores = new HashMap<>();
	
	private static final String BTN_CONFIRM = "Confirmar";
	private static final String BTN_CANCEL = "Cancelar";
	
	public ControlHacerOferta(Tienda tienda, ClienteRegistrado cliente, ClienteRegistrado receptor) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.receptor = receptor;
		
		this.vista = new VentanaOfertaIntercambio(BTN_CANCEL, BTN_CONFIRM);
		vista.setControlador(this);
		
		articulos.put(cliente, new ArrayList<>());
		articulos.put(receptor, new ArrayList<>());
		
		anadirArticulos(cliente);
		anadirArticulos(receptor);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	private void anadirArticulos(ClienteRegistrado cliente) {
		for(ArticuloSegundaMano a : cliente.getCartera().getArticulosDisponibles()) {
			ControlPanelArticuloSeleccionable c = new ControlPanelArticuloSeleccionable(tienda, this.cliente, a, vista);
			articulos.get(cliente).add(a);
			controladores.put(a, c);
		}
	}
	
	private ArticuloSegundaMano[] getArticulosSeleccionados(ClienteRegistrado cliente) {
		ArrayList<ArticuloSegundaMano> array = new ArrayList<>();
		
		for(ArticuloSegundaMano a : articulos.get(cliente)) {
			if(controladores.get(a).isSelected()) array.add(a);
		}
		return array.toArray(new ArticuloSegundaMano[0]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case BTN_CONFIRM:
			try {
				tienda.hacerOfertaIntercambio(cliente, getArticulosSeleccionados(cliente), getArticulosSeleccionados(receptor));
				new VentanaMensaje("Su oferta se ha enviado con éxito");
				TiendaFrame.getInstance().volverAtras();
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
			break;
		case BTN_CANCEL:
			TiendaFrame.getInstance().volverAtras();
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
