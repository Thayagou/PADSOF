package aplicacion;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import sistema.*;
import usuario.*;
import venta.descuentos.*;
import venta.productos.*;
import venta.pedidos.*;
import javax.swing.ImageIcon;


public class PruebaDeUso {

	public static void main(String[] args) throws Exception {
		Tienda tienda = new Tienda();
		
		
		/*Setup de la tienda, sus productos y sus clientes*/
		
		ImageIcon imagen = null;
		
		//Registrarse en la tienda
		tienda.registrarse("Usuario1", "1", "1");
		tienda.registrarse("Usuario2", "2", "2");
		tienda.darDeAltaEmpleado("Empleado1", "123", Permiso.PRODUCTOS);
		tienda.darDeAltaEmpleado("Empleado2", "pass", Permiso.PEDIDOS);
		tienda.darDeAltaEmpleado("Empleado3", "pass", Permiso.INTERCAMBIOS);
		
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
		
		
		
		/*=======================PRUEBA DE USO DE LAS FUNCIONALIDADES DE VENTA =============================*/
		
		System.out.println("Se añade un descuento correctamente: "+tienda.getAlmacen().anadirDescuentoPorcentaje(cAventura, 30, LocalDateTime.MIN, LocalDateTime.MAX, CondicionDescuento.VOLUMEN, 20));
		System.out.println(tienda.getAlmacen().getStock("Hulk Pack").getProducto());
		
		//Buscar productos de la tienda
		Categoria[] categoriasBusqueda = {cAventura};
		System.out.println("\nResultado de búsqueda con filtros 'Aventura', entre 0 y 100 euros, y más de 3 estrellas:\n");
		for(Producto p : tienda.getAlmacen().getProductosPorFiltros(categoriasBusqueda , 0, 100, 3)) {
			System.out.println("\n"+p);
		}
		
		
		//Ver la información detallada de unproducto (el primero de los resultados)
		System.out.println("\nInformacion detallada del primer resultado:\n");
		System.out.println(tienda.getAlmacen().getProductosPorFiltros(categoriasBusqueda , 0, 100, 3)[0]);
		
		//Añadir productos al carrito del Usuario
			tienda.anadirACarritoDe("Usuario1", stComic.getProducto());
			tienda.anadirACarritoDe("Usuario1", stFigura.getProducto());
			tienda.anadirACarritoDe("Usuario1", stPack.getProducto());
			
			System.out.println("\nCarrito de Usuario 1\n");
			System.out.println(tienda.getCliente("Usuario1").getCarrito());
			
			//Cancelamos su compra
			tienda.cancelarCarritoDe("Usuario1");
			
			System.out.println("\nCarrito de Usuario 1 tras cancelar el carrito:\n");
			System.out.println(tienda.getCliente("Usuario1").getCarrito());
		
		//Volvemos a añadir productos al carrito del Usuario
		tienda.anadirACarritoDe("Usuario1", stComic.getProducto());
		tienda.anadirACarritoDe("Usuario1", stFigura.getProducto());
		tienda.anadirACarritoDe("Usuario1", stPack.getProducto());
		
		//Quitamos uno de los productos
		tienda.quitarDeCarritoDe("Usuario1", stComic.getProducto());
		
		//Intentamos comprar un producto agotado
		boolean ret = tienda.anadirACarritoDe("Usuario2", stPack.getProducto());
		System.out.println("Se impide añadir productos agotados al carrito: " + !(ret));

		//Usuario1 paga la compra
		tienda.pagarCarritoDe("Usuario1", "1234123412341234");
		System.out.println(tienda.getCliente("Usuario1"));
		//Comprobamos que se guardó el pedido conrrectamente
		System.out.println("\nPedidos pendientes de la tienda:\n");
		for(Pedido ped : tienda.getHistorial().getPedidosPendientes())
			System.out.println(ped);
		
		//Empleado2 marca el pedido en cada estado hasta Listo
		Pedido pedido = tienda.getHistorial().getPedidosPendientes()[0];
		tienda.getHistorial().avanzarEstadoPedido(tienda.getEmpleado("Empleado2"), pedido); //En preparacion
		tienda.getHistorial().avanzarEstadoPedido(tienda.getEmpleado("Empleado2"), pedido); //Listo
		tienda.getHistorial().avanzarEstadoPedido(tienda.getEmpleado("Empleado2"), pedido); //Recogido
		/*tienda.getHistorial().getPedidosPendientes()[0].nextEstadoPedido(tienda.getEmpleado("Empleado2")); //En preparacion
		tienda.getHistorial().getPedidosPendientes()[0].nextEstadoPedido(tienda.getEmpleado("Empleado2")); //Listo
		tienda.getHistorial().getPedidosPendientes()[0].nextEstadoPedido(tienda.getEmpleado("Empleado2")); //Recogido*/
		
		//System.out.println(tienda.getCliente("Usuario1"));
		//System.out.println(tienda.getCliente("Usuario2"));
		//Vemos que se enviarion las notificaciones correspondientes a cada uno
		System.out.println("\nNotificaciones de Usuario1:\n");
		for (Notificacion n: tienda.getCliente("Usuario1").getNotificaciones()) {
			System.out.println(n);
		}
		
		System.out.println("\nNotificaciones de Empleado2:\n");
		for (Notificacion n: tienda.getEmpleado("Empleado2").getNotificaciones()) {
			System.out.println(n);
		}
		
		System.out.println(tienda.getCliente("Usuario1"));

		try {
	        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tienda.dat"));
	        oos.writeObject(tienda);
	        oos.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

}
