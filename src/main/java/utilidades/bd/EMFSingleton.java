/*
 * CLASE SINGLETON EMPLEADA PARA CONECTAR A LA BD (UNA SOLA INSTANCIACION DEL ENTITY MANAGER FACTORY)
 */

package utilidades.bd;

import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


/*
 * 
 * NOTA:
 * 
 * SI, YA SE QUE ES RARO Y MEDIO SIN SENTIDO EL METODO INICIAR(), SI YA EL RETORNO DEL ATRIBUTO CREA LA CONEXION DEL
 * EMF, PENSE QUE PARA QUE TENGA MAS CONTEXTO EL CODIGO, SERÍA BUENA IDEA EL METODO INICIAR, SI A FUTURO DA PROBLEMAS,
 * LO PLANTEO DE OTRA MANERA.
 * 
 * NO HAY PROBLEMA CON DEFINIR LA INSTANCIACION DE LA CLASE SI PREVIAMENTE NO HABÍA UNA, POR QUE EN ESTE CASO QUEREMOS
 * QUE LA CLASE SE INSTANCIE APENAS SE INICIE LA APLICACION, NO NECESARIAMENTE EN ALGUN PUNTO ESPECIFICIO.
 * 
 * 
 * "private static final EMFSingleton instancia = new EMFSingleton();"
 * 
 * Privado -> Para que no se acceda desde fuera de la clase.
 * Estatico -> Para que pertenezca a la clase, no a una instanciacion, y podes usarlo sin requerir del constructor.
 * Final -> Para que el atributo sea constante, impideando re instanciarla o cambiar su valor de alguna forma.
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class EMFSingleton {
	// ATRIBUTOS //
	private static final EMFSingleton instancia = new EMFSingleton(); // INSTANCIA DE ESTA MISMA CLASE
//	private static EMFSingleton instancia = null; // NO HACE FALTA, BORRAR DESPUES
	private EntityManagerFactory emf = null;
	private final String UP = "persistencia"; // UNIDAD DE PERSISTENCIA
	
	// CONSTRUCTOR //
	// PRIVADO PARA EVITAR INSTANCIACION DESDE AFUERA
	private EMFSingleton() {}
	
	// SET //
	//...
	
	// GET //
	public static EMFSingleton getInstancia() {
		return instancia;
	}
	
	// NO HACE FALTA DEFINIR ESTO, BORRAR DESPUES
//	public static EMFSingleton getInstancia() {
//		if (instancia == null) {
//			instancia = new EMFSingleton();
//		}
//		
//		return instancia;
//	}
	
	// INSTANCIA EL OBJETO SOLO SI EL ATRIBUTO EMF ES NULO
	public EntityManagerFactory getEMF() {		
		return this.emf;
	}
	
	// METODOS //
	// EXTABLECE LA CONFIGURACION DE CONEXION
	public void iniciarEMF() {
		if (this.emf != null) {
			System.out.println("[ ! ] > El EMF ya fué iniciado!"); // LOG
			return;
		}
		
		try {
			this.emf = Persistence.createEntityManagerFactory(UP);
			System.out.println("[ EXITO ] > EMF iniciado correctamente!"); // LOG	
		} catch (Exception e) {
			System.out.println(e); // LOG
		}
	}
	
	// FINALIZA AL EMF (DIGAMOS QUE FINALIZA EL PROCESO QUE PERMITE LA CONEXION)
	public void cerrarEMF() {
		if (this.emf != null && this.emf.isOpen()) {
			this.emf.close();
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
	
	// VERIFICA SI HAY CONEXION A LA BD
	public boolean hayConexion() {
		EntityManager em = null;
		try {
			em = this.emf.createEntityManager();
			em.createNativeQuery("SELECT 1").getSingleResult();
			System.out.println("[ EXITO ] > Conexión correcta!"); // LOG
			return true; // HAY CONEXIÓN
		} catch (Exception e) {
			System.out.println(e); // LOG
			System.out.println("[ ERROR ] > Fallo de conexión! "); // LOG
			return false; // NO HAY CONEXIÓN
		} finally {
			// ES NECESARIO COMPROBAR QUE ESTÉ ABIERTO O SEA DISTINTO DE NULL, SINO SE ROMPE
			if (em != null && em.isOpen()) {
				em.close();				
			}
		}
	}
}
