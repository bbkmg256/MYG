/*
	CLASE PRINCIPAL
*/

/**
 *
 *	@Nombre_del_proyecto: MYG
 *	@Versión: -
 *	@Autor: BBKMG
 *
 */

package edu.unam.app;

// LIBRERIAS
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utilidades.NumeroDeVersion;
import utilidades.RutasVistas;
import utilidades.bd.EMFSingleton;

/*
 * 
 * NOTAS GENERALES:
 * 
 * PROBABLEMENTE SEA MEJOR IDEA CAMBIAR LOS BOTONES "CANCELAR" POR ALGO COMO "VOLVER", YA QUE REALMENTE
 * NO CANCELA UNA OPERACION EN SI, SINO QUE RETORNAN A LA VISTA ANTERIOR.
 * 
 * (LITERALMENTE ESTOY JUGANDO CON FUEGO, LA MAQUINA EN LA QUE ESTOY DESARROLLANDO ESTO SE TILDA CADA 2 POR 3 SIN MOTIVO
 * APARENTE, NO SE SI ES PROBLEMA DE HARDWARE O QUE CARAJO, NO LA EXIJO PARA NADA Y AUN ASI SE TILDA DE FORMA EXTRAÑA)
 * 
 * EN CADA CONTROLADOR, EN LAS OPERACIONES QUE SE REALIZAN DE CARGA Y ELIMINACION DE ENTIDADES, SE DEBE VERIFICAR (CON UNA CONSULTA)
 * QUE LOS MISMO SE ENCUENTREN EN LA BD O QUE YA NO SE ENCUENTREN, DESPUES DE LA OPERACION (DEPENDIENDO DE CADA UNA, CARGA O ELIMINACION),
 * ADEMAS DE QUE NO SE DEBE OLVIDAR DE COMPROBAR SI HAY CONEXION CON LA BD ANTES DE REALIZAR ALGUNA DE ESTAS OPERACIONES, PARA EVITAR ERRORES
 * O INCONSISTENCIAS DE LA BD RESPECTO AL CODIGO DE LA APLICACION.
 * 
 * LAS VISTAS DE CLIENTE Y SUS CONTROLADORES LITERALMENTE HAY QUE COPIARLOS Y PEGARLOS PARA LA ENTIDAD TUTOR.
 * 
 * TENGO QUE DEJARDE PONER TANTOS LOS PARA OPERACIONES DEL BACKEND RESPECTO A LO QUE SE VE Y SE LLAMA EN EL FRONT, POR QUE YA HAY
 * LOGS QUE AVISAN DE PROBLEMAS O DE OPERACIONES EXITOSAS Y LAS ESTOY HACIENDO REDUNDANTES EN EL FRONT (EN LOS CONTROLADORES), SI EL BACK
 * YA TIENE LOGS, NO LOS PONGAS EN EL FRONT PELOTUDO!
 * (EL BACK ME VA A DECIR SI HAY UN PROBLEMA EN EL BACK, Y EL FRONT, SI HAY UN PROBLEMA EN EL FRONT, PUNTO)
 * 
 * EN EL BACKEND HAY QUE CREAR ALGUNOS LOGS EN ALGUNOS METODOS QUE DEVUELVEN COSAS PERO NO AVISAN NADA.
 * 
 * RECORDATORIO: LA APP ESTA ROTA HASTA QUE SE TERMINE DE MODIFICAR LOS CONTROLADORES PARA LAS VISTAS DE TUTOR. [SOLUCIONADO]
 * 
 * EL BOTON DE GRUPO MUSCULAR, DE LA VENTANA DE INICIO, POR EL MOMENTO ESTÁ ROTA, XD.
 * 
 * VOY A CAMBIAR LOS MENSAJES DE ERROR POR NO SELECCIONAR UN REGISTRO PARA ELIMINA, POR UN MENSAJE QUE SEA MAS INFORMATIVO QUE DE ERROR.
 * 
 * SI HAY TIEMPO, AGREGAR EL CODIGO KONAMI COMO EASTER EGG, SI BIEN ES
 * "Arriba, arriba, abajo, abajo, izquierda, derecha, izquierda, derecha, B, A", LO VOY A REEMPLAZAR POR "11000101BA".
 * 
 * CREO QUE PROBABLEMENTE REUTILICE VISTAS PARA CLIENTE Y TUTOR, ASÍ ME AHORRO VISTAS IGUALES ALPEDO.
 * 
 * FALTA COMPLETAR LA VISTA DE CARGA/MODIFICACION DE EJERCICIO, Y HACER SU VISTA ABM.
 *  
 *  
 * IMPORTANTE: MOFIDICAR LAS DIMENSIONES DE TODAS LAS VISTAS POST-DESARROLLO, POR QUE SON MUY CHICAS (COMO LA MIA).
 * 
 * RECOMENDADO: MODIFICAR TODOS LOS METODOS DE ACTUALIZACION DE ENTIDADES DE LA CAPA DE SERVICIO, PARA QUE RETORNEN
 * BOOLEANOS (VERDADERO SI TOD0 FUE CORRECTO, FALSO SI ALGO FALLO).
 * 
 * QUITAR EL ID DE LA VISTA ABM DE RutinaEjercicio, CONFUNDE AL ABRIR UNA RUTINA PARA VER SU CONTENIDO.
 * OSEA, COMO SON ENTIDADES DISTINTAS Y Rutina ESTÁ DENTRO DE RutinaEjercicio, Y EL CAMPO ID CORRESPONDE A LOS
 * REGISTROS DE RutinaEjercicio, PUEDE LLEGAR A CONFUNDIR AL USUARIO, COMO NO ES ALGO REALMENTE RELEVANTE PARA EL
 * MISMO, HAY QUE ELIMINAR ESA COLUMNA.
 * 
 */

public class App extends Application {
	@Override
	public void start(Stage ventana) throws Exception {
		boolean verificacionActivada = true; // PARA ACTIVAR O DESACTIVAR LA VERIFICACION CONEXION INICIAL
		
		// A ESTE CONTEXTO ME REFIERO (VER CLASE EMFSINGLETON EN PAQUETE "UTILIDADES" PARA MAS CONTEXTO)
		EMFSingleton.getInstancia().iniciarEMF(); // ESTABLECE LA CONFIGURACION PARA LA CONEXION A BD
		
		// COMPRUEBA SI EXISTE O ES POSIBLE CONEXION ALGUNA CON UNA BD
		if (!EMFSingleton.getInstancia().hayConexion() && verificacionActivada) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error!");
			alerta.setHeaderText("ERROR DE CONEXION A BASE DE DATOS!");
			alerta.setContentText("Problemas al intentar conectar con la BD!");
			alerta.showAndWait();
//			System.out.println("[ ERROR ] > No hay conexión a una BD o la misma está caida!"); // LOG
			
			// FINALIZA EL PROCESO DEL EMF (EN ESTE CASO, CUANDO HAY PROBLEMAS DE CONEXION)
			EMFSingleton.getInstancia().cerrarEMF();
			return;
		}
		
		// XD
		if(this.getClass().getResource("/lulz/4xdwzu.jpg") == null) {
			System.out.println("[ ! ] > No toques a la papa!");
//			System.out.println("[ ! ] > Funciona!");
			return;
		}
		
		// MODIFICAR TODO ESTO PARA QUE UTILICE EL SINGLETON DE NAVEGACIÓN
		AnchorPane raizVistaInicio = FXMLLoader.load(this.getClass().getResource(RutasVistas.VISTA_INICIO)); // LEE EL FICHERO FXML DE LA VISTA
		Scene escenaInicio = new Scene(raizVistaInicio); // CREA UNA NUEVA ESCENA EN BASE AL CONTENEDOR RAIZ
		ventana.setScene(escenaInicio); // ESTABLECE LA ESCENA EN LA VENTANA
		ventana.setTitle("Inicio"); // ESTABLECE TITUTO A LA VENTANA
		ventana.setResizable(false);
		ventana.show(); // MUESTRA LA VENTANA
		
		// RETONAR EL ANCHO Y ALTO DEL CONTENEDOR DE LA VISTA
//		System.out.printf(
//				"Ancho -> %f | Alto -> %f%n",
//				raizVistaInicio.getWidth(),
//				raizVistaInicio.getHeight()
//		);
	}
	
	// MAIN
	public static void main(String [] args) {
		// MENSAJE DE INICIO (LOGs)
		System.out.printf("[ MYG - %s ]%n", NumeroDeVersion.version);
		System.out.println("by BBKMG");
		
		// LANZA LA INTERFAZ INICIAL
		App.launch(args);
		
		// FINALIZA EL PROCESO DEL EMF (EN ESTE CASO, AL FINALIZAR LA EJECUCION DE LA APP)
		EMFSingleton.getInstancia().cerrarEMF();
		
		// <3
		System.out.println("L: 2013 - 2025");
	}
}

// CODIGO SIN USAR //

//FXMLLoader cargaVistaInicio = FXMLLoader.load(this.getClass().getResource(RutasVistas.VISTA_INICIO)); // LEE EL FICHERO FXML DE LA VISTA
//AnchorPane raizVistaInicio = cargaVistaInicio.load(); // CARGA EL FICHERO A UN CONTENEDOR RAIZ

// PASO UNICAMENTE EL EMF PARA QUE FALLE DENTRO DEL METODO AL CREAR EL EM, DANDO SEÑAL DE SI HAY O NO CONEXION
//if (!ComprobarConexionBD.hayConexion(EMFSingleton.getInstancia().getEMF()) && verificacionActivada) {
//	// MOMENTANEAMENTE DEJO ESTO ASI, DESPUES LO CAMBIO
//	Alert alerta = new Alert(AlertType.ERROR);
//	alerta.setTitle("Error!");
//	alerta.setHeaderText("ERROR DE CONEXION A BASE DE DATOS!");
//	alerta.setContentText("No se encuentra una base de datos activa!");
//	alerta.showAndWait();
//	System.out.println("[ ERROR ] > No hay conexión a una BD o la misma está caida!"); // LOG
//	
//	// FINALIZA EL PROCESO DEL EMF (EN ESTE CASO, CUANDO HAY PROBLEMAS DE CONEXION)
//	EMFSingleton.getInstancia().cerrarEMF();
//	return;
//}



                                                 
//                   ███████████                   
//                 ████████████████                
//                ██████████████████               
//               █████████▒▒▒▒▒▒▓▒▓██              
//               █████▒▒▒▒▓▒▒▒▒▒▒▒▒▒█  ▓           
//               ████▒▒▒▒▒███▒▒▒▓▓█▓▓ ▓            
//                ██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  ▓     / |-------------------------------------|
//                 ▓▒▒█▒█▒▒▒█▒▒▒▒▒▒▓█▓▓    <   | Harry, este código es una mi*rda :) |
//                  ▒▒▒▓▒▒▒▒▒▒▒▒▒█▓  ▓       \ |-------------------------------------|
//              ████▒▒▒▒▒▒▓▒▓▒▒▒▒▒▒ ▓              
//           ██████▒▒█▒▒▓▒▒▒▒▓▓████▓               
//         ▓███████████▒▒▒▒▒▒▒██████████           
//     ▓████████████████████████████████████▓      
//     ██████████████████████████████████████▓     
//    ████████████████████████████████████████     
