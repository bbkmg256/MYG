/*
 * CLASE SINGLETON EMPLEADA PARA CONECTAR A LA BD (UNA SOLA INSTANCIACION DEL ENTITY MANAGER FACTORY)
 */

package utilidades;

import jakarta.persistence.Persistence;
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
 */


/**
*
* @Autor: BBKMG
*/
public class EMFSingleton {
	// ATRIBUTOS
	private static final EMFSingleton instancia = new EMFSingleton(); // INSTANCIA DE ESTA MISMA CLASE
//	private static EMFSingleton instancia = null; // NO HACE FALTA, BORRAR DESPUES
	private EntityManagerFactory emf = null;
	private final String UNIDADPERSISTENCIA = "persistencia";
	
	// CONSTRUCTOR
	// CONSTRUCTOR PRIVADO PARA EVITAR INSTANCIACION DESDE AFUERA
	private EMFSingleton() {}
	
	// SET
	//...
	
	// GET
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
		// BORRAR DESPUES
//		if (this.emf == null) {
//			try {
//				this.emf = Persistence.createEntityManagerFactory("persistencia");				
//				System.out.println("[ EXITO ] > EMF iniciado correctamente!");
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//		}
		
		return this.emf;
	}
	
	// METODOS
	// SOLAMENTE INSTANCIA 1 SOLA VEZ EL EMF (O DIGAMOS QUE ESTABLECE LA CONEXION A BD 1 SOLA VEZ)
	public void iniciarEMF() {
		if (this.emf != null) {
			System.out.println("[ EXITO ] > El EMF ya fué iniciado!");
			return;
		}
		
		try {
			this.emf = Persistence.createEntityManagerFactory(UNIDADPERSISTENCIA);
			System.out.println("[ EXITO ] > EMF iniciado correctamente!");			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// OBVIAMENTE ESTO MATA LA CONEXION A BD
	public void cerrarEMF() {
		if (this.emf != null && this.emf.isOpen()) {
			this.emf.close();
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}
