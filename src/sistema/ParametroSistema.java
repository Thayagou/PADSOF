package sistema;

import java.io.Serializable;

/**
 * Enum que representa posibles parámetros del sistema
 */
public enum ParametroSistema implements Serializable {
	/** Ponderación que tiene dentro del vector de producto el hecho de pertenecer a una categoría */
	CATEGORIA,

	/** Ponderación que tienen las unidades de productos comprados. Se utiliza a la hora de actualizar el vector de recomendación del cliente tras una compra */
	UDS_COMPRADAS,

	/** Ponderación que tiene el precio pagado por un producto. Se utiliza a la hora de actualizar el vector de recomendación del cliente tras una compra */
	PRECIO_COMPRA,

	/** Ponderación que tiene la media de puntuación de un producto a la hora de calcular su valor de compatibilidad con un determinado usuario */
	VALORACIONES_PRODUCTO,

	/** Ponderación que tiene la compatibilidad entre usuario y producto en el rango [0,1] a la hora de calcular su valor de compatibilidad con un determinado usuario */
	PRODUCTO_RECOMENDADO,

	/** Ponderación que tiene la búsqueda por categorías sobre el vector de intereses del usuaro */
	BUSQUEDA,

	/** Duración del carrito de compra hasta que caduque */
	DURACION_CARRITO,

	/** Duración de una oferta hasta que caduque */
	DURACION_OFERTA, 
	
	/** Precio a pagar por solicitar una valoracion de un artículo */
	PRECIO_VALORACION;
}