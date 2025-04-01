/*
	Servicio de cliente (Esto valida la movida de las
	peticiones a BD desde la vista).
*/

package edu.unam.servicio;

// JPA Controller
import edu.unam.repositorio.ClienteJPAController;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Cliente;

/**
*
* @author bbkmg
*/
public class ClienteServicio {
	// JPAController
	private ClienteJPAController cjpac;
	
	// Constructor
	public ClienteServicio() {
		cjpac = new ClienteJPAController();
	}
	
	// Registra un cliente en el sistema
	public void registrarCliente(Cliente cliente) {
		// Si el metodo es diferente a null, es por que se encontró un cliente con el mismo DNI
		if (cjpac.obtenerEntidad(cliente.getDni(), Cliente.class) != null) {
			System.out.printf("[ ERROR ] > El cliente %d ya se encuentra en el sistema!%n", cliente.getDni());
		} else {
			cjpac.crearEntidad(cliente);			
		}

		cjpac.cerrarEMF(); // Finaliza el EMF
	}
	
	// Busca un cliente en el sistema
	public Cliente obtenerCliente(int dni) {
		Cliente cli = cjpac.obtenerEntidad(dni, Cliente.class);
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
		}
		
		cjpac.cerrarEMF();
		return cli;
	}
	
	// Actualiza la información de un cliente
	public void actualizarInfCliente(int dni, String nombre, String apellido,
									LocalDate fechaNac, char sexo, String ciudad,
									String provincia, int codPost, LocalDate fechaIng) {
		
		Cliente cli = cjpac.obtenerEntidad(dni, Cliente.class);
		
		if (cli != null) {
			cjpac.actualizarEntidad(cli, nombre, apellido, fechaNac, sexo, ciudad, provincia, codPost, fechaIng);			
		} else {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
		}
		
		cjpac.cerrarEMF();
	}
	
	// Da de baja a un cliente del sistema
	public void eliminarCliente(int dni) {
		Cliente cli = cjpac.obtenerEntidad(dni, Cliente.class);
		
		// En caso de que no se encuentre cliente en el sistema,
		// simplemente no hace nada y finaliza el metodo destruyendo el EMF
		if (cli != null) {
			cjpac.eliminarEntidad(cli);			
		} else {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
		}
		
		cjpac.cerrarEMF();
	}
}
