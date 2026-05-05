package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;

public class ControlInfoArticulo implements ActionListener {
	
	private Tienda tienda;
	private ClienteRegistrado cliente;
	private ArticuloSegundaMano articulo;

	public ControlInfoArticulo(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.articulo = articulo;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
