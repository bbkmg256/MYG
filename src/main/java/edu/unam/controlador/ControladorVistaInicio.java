/*
 * VISTA INICIAL DEL SISTEMA
 */

package edu.unam.controlador;

// LIBRERIAS
//import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import utilidades.RutasVistas;
import utilidades.NavegadorDeVistas;
import utilidades.NumeroDeVersion;
import java.util.Optional;
import edu.unam.servicio.GMServicio;
import edu.unam.servicio.RutinaEjercicioServicio;
import edu.unam.servicio.RutinaServicio;
import edu.unam.servicio.TutorServicio;
import edu.unam.servicio.ClienteServicio;
//import edu.unam.controlador.ejercicio.ControladorVistaCargaEjercicio;
import edu.unam.servicio.EjercicioServicio;
import edu.unam.servicio.EntrenamientoServicio;
//import javafx.scene.layout.Region;


public class ControladorVistaInicio {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTCliente;

    @FXML
    private Button BTEjercicio;

    @FXML
    private Button BTEntrenamiento;

    @FXML
    private Button BTGM;

    @FXML
    private Button BTRutina;

    @FXML
    private Button BTSeguimiento;

    @FXML
    private Button BTTutor;
    
    @FXML
    private Button BTLOL;
    
    @FXML
    private Label LBEGG;
    
    @FXML
    private Label LBBuildVer;
    
    private GMServicio sgm = new GMServicio();
    
    private EjercicioServicio sejer = new EjercicioServicio();
    
    private RutinaServicio srut = new RutinaServicio();
    
    private EntrenamientoServicio sentre = new EntrenamientoServicio();
    
    private ClienteServicio scli = new ClienteServicio();
    
    private TutorServicio stutor = new TutorServicio();
    
    private RutinaEjercicioServicio sre = new RutinaEjercicioServicio();
    
    
    // METODOS Y EVENTOS //
    private void modificarEtiquetaVersionBuild() {
    	this.LBBuildVer.setText(NumeroDeVersion.version);
    }

    private Optional<ButtonType> lanzarMensaje(
    		AlertType tipoDeAlerta, String titulo,
    		String cabecera, String contenido
    ) {
    	Alert alerta = new Alert(tipoDeAlerta);
    	alerta.setTitle(titulo);
    	alerta.setHeaderText(cabecera);
    	alerta.setContentText(contenido);
    	return alerta.showAndWait();
    }
    
    @FXML
    private void eventoBTCliente(ActionEvent event) {
    	NavegadorDeVistas
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_ABM_CLIENTE
    		);
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				LBBuildVer,
    				"Cliente"
    		);
    }

    @FXML
    private void eventoBTEjercicio(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_EJERCICIO
			);
//    	NavegadorDeVistas
//			.getInstancia()
//			.cargarNuevaVista(
//					this.getClass(),
//					RutasVistas.VISTA_CARGA_MODIF_EJER,
//					new ControladorVistaCargaEjercicio()
//			);
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					BTEjercicio,
					"Ejercicio"
			);
    }

    @FXML
    private void eventoBTEntrenamiento(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_ENT
			);
		NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					LBBuildVer,
					"Entrenamiento"
			);
    }

    @FXML
    private void eventoBTGM(ActionEvent event) {
    	NavegadorDeVistas
    		.getInstancia()
    		.cargarNuevaVista(
    				this.getClass(),
    				RutasVistas.VISTA_ABM_GM
    		);
    	NavegadorDeVistas
    		.getInstancia()
    		.cambiarVista(
    				LBBuildVer,
    				"Grupo Muscular"
    		);
    }

    @FXML
    private void eventoBTRutina(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_RUTINA
			);
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					LBBuildVer,
					"Rutina"
			);
    }

    @FXML
    private void eventoBTSeguimiento(ActionEvent event) {

    }

    @FXML
    private void eventoBTTutor(ActionEvent event) {
    	NavegadorDeVistas
			.getInstancia()
			.cargarNuevaVista(
					this.getClass(),
					RutasVistas.VISTA_ABM_TUTOR
			);
    	NavegadorDeVistas
			.getInstancia()
			.cambiarVista(
					LBBuildVer,
					"Tutor"
			);
    }
    
    @FXML
    void BTLOL(ActionEvent event) {
    	this.LBEGG.setVisible(true);
    }

    @FXML
    private void initialize() {
    	modificarEtiquetaVersionBuild();
    	
    	// NO FUNCIONA, VER MAS TARDE (OBTENCION DE DIMENSIONES DEL CONTENEDOR)
//    	Region contenedor = (Region) this.BTLOL.getParent();
//    	System.out.print(contenedor);
//    	System.out.printf("Alto > %f | Ancho > %f%n",
//    			contenedor.getHeight(),
//    			contenedor.getWidth()
//    	);

    	// ESTAS CONDICIONALES DESHABILITAN CIERTOS BOTONES SEGUN QUE //
    	// DATOS FALTEN CARGAR PARA CADA APARTADO...				  //
    	
    	// SEGUIMIENTO
    	if (this.sentre.obtenerTodosLosEntrenamientos().isEmpty()) {
    		this.BTSeguimiento.setDisable(true);
    	}
    	
    	// RUTINA
    	if (this.sejer.obtenerTodosLosEjercicios().isEmpty()) {
    		this.BTRutina.setDisable(true);
    	}
    	
    	// ENTRENAMIENTO
    	if (
    			this.scli.obtenerTodosLosClientes().isEmpty() ||
    			this.stutor.obtenerTodosLosTutores().isEmpty() ||
    			this.srut.obtenerTodasLasRutinas().isEmpty() ||
    			this.sre.obtenerTodasLasRutinaEjercicio().isEmpty()
    		) {
    		this.BTEntrenamiento.setDisable(true);
    	}
    	
    	// EJERCICIO
    	if (this.sgm.obtenerTodosLosGM().isEmpty()) {
    		this.BTEjercicio.setDisable(true);
    	}
    }
}



// CODIGO SIN USAR //

//private void cambiarVista(String rutaVista, String tituloDeVentana) {
//// SI YA HAY UNA VENTANA DE ESTE TIPO, SOLO LA TRAE AL FRENTE
//if (ventana != null && ventana.isShowing()) {
//	ventana.toFront();
//	return;
//}

//try {
//	// SE CARGA LA VISTA A UN TIPO PARENT POR QUE NO SE SABE EL TIPO DE CONTENEDOR DE LA VISTA PADRE/PRINCIPAL
//	Parent nuevaVista = FXMLLoader.load(this.getClass().getResource(rutaVista));
//	Scene escena = new Scene(nuevaVista);
//	
//	// OBTIENE EL STAGE O VENTANA ACTUAL DESDE ALGUNO DE LOS NODOS QUE CONTIENE
//	Stage ventana = (Stage) LBBuildVer.getScene().getWindow();
//	ventana.setScene(escena);
//	ventana.setTitle(tituloDeVentana);
//	ventana.setResizable(false);
//	ventana.show();
	
//	ventana = new Stage();
//	ventana.setTitle(tituloDeVentana);
//	ventana.setResizable(false);
//	ventana.setScene(escena);
//	ventana.setOnCloseRequest(e -> ventana = null); // AL CERRAR LA VENTANA, EL ESTATICO "VENTANA" SE VUELVE NULO
//	// ventana.setAlwaysOnTop(true);
//	ventana.show();
	
//	// CIERRA LA VENTANA ACTUAL
//	ventanaActual = (Stage) this.LBBuildVer.getScene().getWindow();
//	this.ventanaActual.close();
//} catch (IOException e) {
//	System.out.println(e);
//}  
//}

//this.cambiarVista(RutasVistas.VISTA_ABM_GM, "Grupo Muscular");

//this.cambiarVista(RutasVistas.VISTA_ABM_CLIENTE, "Cliente");

//@FXML
//private Stage ventanaActual;

//private static Stage ventana;


//if (this.sentre.obtenerTodosLosEntrenamientos().isEmpty()) {
//this.lanzarMensaje(
//		AlertType.ERROR, "Error!", "DATOS INSUFICIENTES",
//		"No puede crear seguimientos sin entrenamientos, " +
//		"agregue almenos uno antes de crear seguimientos..."
//);
//System.out.println(
//		"[ ERROR ] > Imposible crear seguimientos " +
//		"sin rutinas!"
//); // LOG
//return;
//}



//if (this.sejer.obtenerTodosLosEjercicios().isEmpty()) {
//this.lanzarMensaje(
//		AlertType.ERROR, "Error!", "DATOS INSUFICIENTES",
//		"No puede crear rutinas sin ejercicios, " +
//		"agregue almenos uno antes de crear rutinas..."
//);
//System.out.println(
//		"[ ERROR ] > Imposible crear rutinas " +
//		"sin ejercicios!"
//); // LOG
//return;
//}


//// ARREGLAR ESTA COSAS DESPUES
//boolean noHayDatosEnCTR = 
//		this.scli.obtenerTodosLosClientes().isEmpty() ||
//		this.stutor.obtenerTodosLosTutores().isEmpty() ||
//		this.srut.obtenerTodasLasRutinas().isEmpty();
////System.out.println(noHayDatosEnCTR);
//// CTR -> CLIENTE - TUTOR - RUTINA
//if (noHayDatosEnCTR) {
//	this.lanzarMensaje(
//			AlertType.ERROR, "Error!", "DATOS INSUFICIENTES",
//			"No puede crear entrenamientos sin rutinas, clientes y tutores, " +
//			"agregue almenos uno de cada uno antes de crear entrenamientos..."
//	);
//	System.out.println(
//			"[ ERROR ] > Imposible crear entrenamientos " +
//			"sin rutinas, clientes y tutores!"
//	); // LOG
//	return;
//}


////System.out.println(this.sgm.obtenerTodosLosGM());
//// DESPUES METER ESTO EN UN METODO APARTE!!
//if (this.sgm.obtenerTodosLosGM().isEmpty()) {
//this.lanzarMensaje(
//		AlertType.ERROR, "Error!", "DATOS INSUFICIENTES",
//		"No puede crear ejercicios sin grupos musculares, " +
//		"agregue almenos uno antes de crear ejercicios..."
//);
////this.lanzarMensaje(
////		AlertType.ERROR, "Error!", "OPERACION FALLIDA",
////		"Seleccione un cliente..."
////);
//System.out.println(
//		"[ ERROR ] > Imposible crear ejercicios " +
//		"sin grupos musculares!"
//); // LOG
//return;
//}
//

