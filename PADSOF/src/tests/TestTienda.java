package tests;
import java.time.LocalDate;
import java.time.LocalDateTime;

import sistema.*;
import usuario.*;
import venta.descuentos.CondicionDescuento;
import venta.productos.*;
import venta.pedidos.*;
import javax.swing.ImageIcon;

public class TestTienda {

	public static void main(String[] args) throws Exception {
		Tienda tienda = new Tienda();
		ImageIcon imagen = null;
		tienda.registrarse("Usuario1", "1", "1");
		tienda.registrarse("Usuario2", "2", "2");
		tienda.darDeAltaEmpleado("Empleado1", "123", Permiso.PRODUCTOS);
		
		tienda.getAlmacen().anadirCategoria("Infantil");
		Categoria cInfantil = tienda.getAlmacen().getCategoria("Infantil");
		tienda.getAlmacen().anadirCategoria("Aventuras");
		Categoria cAventura = tienda.getAlmacen().getCategoria("Aventuras");
		
		tienda.getAlmacen().anadirComic(5, "El increíble Hulk", "Comic 199 de hulk", 17.50, imagen, LocalDate.now(), "Stan Lee", 50, "MarvelComics", cAventura);
		Stock stComic = tienda.getAlmacen().getStock("El increíble Hulk");
		tienda.getAlmacen().anadirFigura(3, "Hulk Action Figure", "Figura de accion de Hulk", 29.50, imagen, "19x20 cm", "Lego", "Hierro", cInfantil);
		Stock stFigura = tienda.getAlmacen().getStock("Hulk Action Figure");
		Stock[] stocksDelPack = {stComic, stFigura};
		tienda.getAlmacen().anadirPack(1, "Hulk Pack", "Comic y figura de Hulk", 38.75, imagen, stocksDelPack, cAventura, cInfantil);
		Stock stPack = tienda.getAlmacen().getStock("Hulk Pack");
		
		System.out.println("Se añade un descuento correctamente: "+tienda.getAlmacen().anadirDescuentoPorcentaje(cAventura, 30, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.VOLUMEN, 20));
		System.out.println(tienda.getAlmacen().getStock("Hulk Pack").getProducto());
		
		tienda.anadirACarritoDe("Usuario1", stComic.getProducto());
		tienda.anadirACarritoDe("Usuario1", stFigura.getProducto());
		tienda.anadirACarritoDe("Usuario1", stPack.getProducto());
		
		boolean ret = tienda.anadirACarritoDe("Usuario2", stPack.getProducto());
		System.out.println("Se impide añadir productos agotados al carrito: " + !(ret));
		
		Pedido pedido = tienda.getCliente("Usuario1").carritoAPedido();
		System.out.println(pedido);
		
		tienda.pagarCarritoDe("Usuario1");		
	}

}
