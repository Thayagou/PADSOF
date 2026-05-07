package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelPedidoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import vistas.cliente.venta.pantallas.VentanaCompras;
import vistas.common.app.TiendaFrame;

public class ControlVerCompras implements ActionListener, ControladorPantalla { 
    private Tienda tienda;
    private ClienteRegistrado cliente;
 
    // [NUEVO] La vista se crea una sola vez en el constructor y se reutiliza
    private final VentanaCompras vista;
 
    public ControlVerCompras(Tienda tienda, ClienteRegistrado cliente) {
        this.tienda  = tienda;
        this.cliente = cliente;
 
        // Construir la vista UNA SOLA VEZ
        this.vista = new VentanaCompras();
        cargarPedidos();
        vista.setControlador(this);
 
        // [NUEVO] Navegar usando la pila en lugar de setVistaActual()
        TiendaFrame.getInstance().navegarA(this);
    }
 
    // ── Carga / refresco de datos ─────────────────────────────────────────
 
    private void cargarPedidos() {
        vista.limpiarPedidos(); // limpia antes de rellenar (ver VentanaCompras)
        for (Pedido pedido : cliente.getPedidos()) {
            new ControlPanelPedidoCliente(tienda, cliente, pedido, vista);
        }
    }
 
    // ── ControladorPantalla ───────────────────────────────────────────────
 
    @Override
    public JPanel getVista() {
        return vista;
    }
 
    /**
     * Al volver a esta pantalla refrescamos los pedidos por si han cambiado.
     */
    @Override
    public void mostrar() {
        cargarPedidos();
    }
 
    @Override
    public void ocultar() {
        // Nada que pausar en esta pantalla
    }
 
    @Override
    public void destruir() {
        // Nada que liberar
    }
 
    // ── ActionListener ────────────────────────────────────────────────────
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // Acciones globales futuras (ej. filtros, ordenación)
    }
}