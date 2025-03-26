package edu.unam;

import java.time.LocalDate;
import java.util.List;

import edu.unam.modelo.Cliente;
import edu.unam.persistencia.*;

/*
	NOTA:

	Es un lio administrar el EMF y el EM de los JPAController, DIOOOS!!!
*/


public class Main {
	public static void main(String [] args) {
		// Instancia de la entidad Cliente
		
		Cliente c1 = new Cliente(
				1234, "LAL", "LOLMAN",
				LocalDate.of(2001, 05, 12), 'M', "LANDIA",
				"India", 12345, LocalDate.now()
				);
				
		Cliente c2 = new Cliente(
				2222, "fuck", "MAN",
				LocalDate.of(2002, 06, 14), 'M', "DIA",
				"Indo", 123, LocalDate.now()
				);
			
		/*
		Cliente c3 = new Cliente(
				2221, "fack", "MeN",
				LocalDate.of(2002, 06, 14), 'M', "DIA",
				"Indo", 123, LocalDate.now()
				);
		 */
		
		// Instancia del JPAController de cliente
		ClienteJPAController cjpac = new ClienteJPAController();
		cjpac.crearCliente(c1);
		cjpac.crearCliente(c2);
		// cjpac.crearCliente(c1);
		// cjpac.eliminarCliente(1234);
		// cjpac.eliminarCliente(2222);
		cjpac.cerrarEMF();
		// Realiza una pequeña devolucion del numero de registros de la tabla correspondiente
		// cjpac.test();
	}
}
