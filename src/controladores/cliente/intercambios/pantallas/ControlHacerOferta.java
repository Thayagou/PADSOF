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
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

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
			if(TiendaFrame.getConfirmacionUsuario("Cuando envíes la oferta tus objetos incluidos en ella quedarán bloqueados y no podrás incluirlos en nuevas ofertas ni podrán hacerte ofertas con ellos. Podrás cancelar la oferta más tarde si aún no ha sido aceptada.")) {
				try {
					tienda.hacerOfertaIntercambio(cliente, getArticulosSeleccionados(cliente), getArticulosSeleccionados(receptor));
					new VentanaMensaje("Su oferta se ha enviado con éxito");
					TiendaFrame.getInstance().volverAtras();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage());
				}
			}
			break;
		case BTN_CANCEL:
			TiendaFrame.getInstance().volverAtras();
			break;
		}
	}
	
	@Override
	public boolean puedeVolver() {
		return false;
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana se seleccionan los artículos para incluir en una oferta. Se muestran a un lado tus objetos y al otro los del otro usuario. Para incluir un artículo en la oferta, haz clic sobre un artículo que no esté seleccionado ya. Para sacar un artículo de la oferta, vuelve a clicar sobre un objeto seleccionado.";
	}

}
