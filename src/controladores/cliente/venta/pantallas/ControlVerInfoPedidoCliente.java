package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelItemPedido;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.pantallas.VentanaInfoPedidoCliente;
import vistas.common.app.TiendaFrame;

public class ControlVerInfoPedidoCliente implements ControladorPantalla {

	private VentanaInfoPedidoCliente vista;

	public ControlVerInfoPedidoCliente(Tienda tienda, ClienteRegistrado cliente, Pedido pedido) {
		this.vista = new VentanaInfoPedidoCliente();

		for (StockExterno st : pedido.getItemsPedido()) {
			new ControlPanelItemPedido(tienda, cliente, st, vista);
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			/* Sin acciones para esta ventana */
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Aquí se muestra la información de un pedido que realizó usted. Puede valorar los productos que compró pinchando en \"Valorar\"";
	}
}
