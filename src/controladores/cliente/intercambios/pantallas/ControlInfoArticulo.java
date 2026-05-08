package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaInfoArticulo;
import vistas.common.app.TiendaFrame;

public class ControlInfoArticulo implements ControladorPantalla {
	
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private ArticuloSegundaMano articulo;
	private VentanaInfoArticulo vista;
	
	private final String USER_PFP = "pfp.png";
	private static final String actionOffer = "Hacer oferta";
	private static final String actionWallet = "Ver cartera";

	public ControlInfoArticulo(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.articulo = articulo;
		
		ClienteRegistrado dueno = articulo.getPropietario();
		
		String estado;
		double estimacion;
		if(articulo.getValoracion() != null) {
			switch(articulo.getValoracion().getEstadoFisico()) {
			case PERFECTO:
				estado = "Perfecto";
				break;
			case MUY_BUENO:
				estado = "Muy bueno";
				break;
			case USO_LIGERO:
				estado = "Uso ligero";
				break;
			case USO_EVIDENTE:
				estado = "Uso evidente";
				break;
			case MUY_USADO:
				estado = "Muy usado";
				break;
			case DANADO:
				estado = "Dañado";
				break;
			case PENDIENTE:
				estado = "Pendiente de valoración";
				break;
			default:
				estado = "Error";
				break;
			}
			estimacion = articulo.getValoracion().getPrecioEstimado();
		} else {
			estado = "Sin valorar";
			estimacion = -1;
		}
		
		boolean ajeno;
		if(cliente == dueno) ajeno = false;
		else ajeno = true;
		
		ArrayList<String> listCategorias = new ArrayList<>();
		for(Categoria c : articulo.getCategorias()) {
			listCategorias.add(c.getNombre());
		}
		String[] categorias = listCategorias.toArray(new String[0]);
		
		this.vista = new VentanaInfoArticulo(dueno.getNombre(), USER_PFP, articulo.getNombre(), articulo.getImage(), articulo.getDescripcion(), articulo.getInteresadoEn(), estado, estimacion, ajeno, actionOffer, actionWallet, categorias);
		this.vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionWallet:
			SwingUtilities.invokeLater(() -> new ControlManejoCartera(tienda, cliente, articulo.getPropietario()));
			break;
		case actionOffer:
			SwingUtilities.invokeLater(() -> new ControlHacerOferta(tienda, cliente, articulo.getPropietario()));
			break;
		}
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}


	@Override
	public String getExplicacion() {
		return "Aquí se ve la información sobre un artículo de segunda mano.";
	}
	
}
