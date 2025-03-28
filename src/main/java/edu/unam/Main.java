package edu.unam;

import java.time.LocalDate;

import edu.unam.modelo.*;
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
			
		Tutor t1 = new Tutor(
				2233, "Jorge", "nando",
				LocalDate.of(1998, 12, 12),
				'H', "Apóstoles", "Misiones",
				23653, LocalDate.now()
				);
		
		// Instancia del JPAController de cliente
		// ClienteJPAController cjpac = new ClienteJPAController();
		// cjpac.crearCliente(c1);
		// cjpac.crearCliente(c2);
		// cjpac.crearCliente(c1);
		// cjpac.eliminarCliente(1234);
		// cjpac.eliminarCliente(2222);
		// cjpac.actualizarCliente(0, null, null, null, 0, null, null, 0, null);
		// cjpac.cerrarEMF();
		// Realiza una pequeña devolucion del numero de registros de la tabla correspondiente
		
		TutorJPAController tjpac = new TutorJPAController();
		// tjpac.crearTutor(t1);
		tjpac.eliminarTutor(2233);
		tjpac.cerrarEMF();
	}
}
