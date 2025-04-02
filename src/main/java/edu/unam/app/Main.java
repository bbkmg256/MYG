/**
 *
 *	@Nombre_del_proyecto: MYG
 *	@Versión: 0.1
 *	@Autor: BBKMG
 *
 */

package edu.unam;

import edu.unam.servicio.*;
// import edu.unam.repositorio.*;
import java.time.LocalDate;

import edu.unam.modelo.*;

//Es un lio administrar el EMF y el EM de los JPAController, DIOOOS!!!

/*
	NOTA GENERAL:
	
	Por el momento no se testearon todas las clases de la capa de servicios,
	por ende no está todo al 100% funcional xd, lo haré en otro momento,
	ya me quiero ir a dormir xd.
*/


public class Main {
	public static void main(String [] args) {
		Cliente c1 = new Cliente(1234, "Jorge", "Bazz", LocalDate.of(2001, 05, 12),
								'M', "Apostoles", "Misiones", 126543, LocalDate.now());
		
		Cliente c2 = new Cliente(12345, "Gimena", "Bxz", LocalDate.of(2002, 10, 12),
								'F', "Apostoles", "Misiones", 236642, LocalDate.now());
		
		Tutor t1 = new Tutor(4321, "LOL", "MAN", LocalDate.of(1998, 01, 10), 'M',
							"Apostoles", "Misiones", 1643123, LocalDate.now());
		
		ClienteServicio servCliente = new ClienteServicio();
		
		servCliente.registrarCliente(c2);
		
		/*
		ClienteJPAController cjpac = new ClienteJPAController();
		cjpac.crearCliente(c1);
		cjpac.cerrarEMF();
		
		TutorJPAController tjpac = new TutorJPAController();
		// tjpac.crearTutor(t1);
		Tutor t2 = tjpac.obtenerTutor(2233);
		System.out.println(t2);
		tjpac.cerrarEMF();
		 */
	}
}
