package vistas.gestor;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import modelo.sistema.Tienda;
import vistas.common.PanelDisplay;
import vistas.common.VentanaConDisplay;

public class VentanaGestionarProductosYCategorias extends JPanel implements VentanaConDisplay<PanelDisplay>{

	private static final String PRODUCTOS = "Productos";
    private static final String CATEGORIAS = "Categorias";

    private CardLayout cardLayout;
    private JPanel contenedor;

    public VentanaGestionarProductosYCategorias() {
        setOpaque(false);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        contenedor.setOpaque(false);

        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setOpaque(false);

        JPanel panelCategorias = new JPanel();
        panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
        panelCategorias.setOpaque(false);

        contenedor.add(panelProductos, PRODUCTOS);
        contenedor.add(panelCategorias, CATEGORIAS);

        add(contenedor, BorderLayout.CENTER);
    }

    public void verGestionarProductos() {
        cardLayout.show(contenedor, PRODUCTOS);
    }

    public void verGestionarCategorias() {
        cardLayout.show(contenedor, CATEGORIAS);
    }

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		
		return panelDisplay;
	}
}
