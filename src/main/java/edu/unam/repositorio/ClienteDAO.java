/*
	DAO - Entidad Cliente
*/

// Se viene el verdadero BACKEND xd

package edu.unam.repositorio;

// Libs.
// Varios
import java.time.LocalDate;
import java.util.List;

// Entidad
import edu.unam.modelo.Cliente;

// JPA
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


/*

NOTA:

- Tener en cuenta que cuando se relacionen las clases, se tendrá que modificar los
JPAController para adaptarlas a las relaciones.

NO ESTA CLASE COMO TODAS LAS DEL LA CAPA DE PERSISTENCIA VAN A MANEJARSE INDIVIDUALMENTE
POR MAS CODIFO REDUNDANTE QUE HAYA, ASÍ SE EVITA QUE MODIFICANDO UNA CLASE GENERAL, SE VAYA
AL CARAJO EL RESTO DE CLASES HIJAS.

ADEMAS ME DA MAS FLEXIBILIDAD YA QUE HAY ENTIDADES QUE SE RELACIONAN CON OTRA MEDIANTE UNA LISTA
Y PUEDO CREAR LA ENTIDAD Y ASOCIARLA EN UN SOLO METODO.

*/


/**
*
* @Autor: BBKMG
*/
public class ClienteDAO {
	// ATRIBUTOS
	private EntityManagerFactory emf;
	private EntityManager manager;

	// Constructor
	public ClienteDAO() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// CREAR ENTIDAD
	public void crearEntidadCliente(Cliente entidadCliente) {
		manager = emf.createEntityManager(); // Administrador de entidades
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadCliente);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			System.out.println(e);
			manager.getTransaction().rollback();			
		} finally {
			manager.close();
		}
	}
	
	// LEER ENTIDAD
	public Cliente obtenerEntidadCliente(int dni) {
		Cliente regEntidad = null;
		manager = emf.createEntityManager();
			
		try {
			regEntidad = manager.find(Cliente.class, dni);
			// System.out.println(regCli);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}			
		return regEntidad;
	}
	
	// LEER ENTIDADES
	public List<Cliente> obtenerEntidadesCliente() {
		manager = emf.createEntityManager();
		String consulta = String.format("SELECT %c FROM %s %c", 'c', "Cliente", 'c'); // Consulta JPQL
		System.out.println(consulta);
		TypedQuery<Cliente> consultaPreparada; // Variable de consulta (Castea automaticamente el dato obtenido)
		List<Cliente> regEntidades = null;
			
		try {
			consultaPreparada = manager.createQuery(consulta, Cliente.class);
			regEntidades = consultaPreparada.getResultList();
		} catch (Exception e) {
			System.out.println(e);
		}
		return regEntidades;
	}
	
	// ACTUALIZAR ENTIDAD
	public void actualizarEntidadCliente(Cliente entidadCliente, String nombre, String apellido,
										LocalDate fechaNac, char sexo, String ciudad,
										String provincia, int codPost, LocalDate fechaIng) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadCliente = manager.merge(entidadCliente);
			
			// ACTUALIZACION DE ATRIBUTOS
			entidadCliente.setNombre(nombre);
			entidadCliente.setApellido(apellido);
			entidadCliente.setFechaNacimiento(fechaNac);
			entidadCliente.setSexo(sexo);
			entidadCliente.setCiudad(ciudad);
			entidadCliente.setProvicia(provincia);
			entidadCliente.setCodigoPostal(codPost);
			entidadCliente.setFechaIngreso(fechaIng);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}

	// ELIMINAR ENTIDAD
	public void eliminarEntidadCliente(Cliente entidadCliente) {
		manager = emf.createEntityManager();

		try {
			manager.getTransaction().begin();
			entidadCliente = manager.merge(entidadCliente); // Reconecta una entidad al gestor de entidades (E.M) que está por fuera del contexto de persistencia
			manager.remove(entidadCliente);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();		
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	public boolean hayConexion() {
		return emf.isOpen();
	}
	
	public void cerrarEMF() {
		emf.close();
		System.out.println("[ EXITO ] > EMF finalizado correctamente!");
	}
}