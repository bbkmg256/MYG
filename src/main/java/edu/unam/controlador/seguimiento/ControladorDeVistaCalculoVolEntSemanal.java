package edu.unam.controlador.seguimiento;

//import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.GrupoMuscular;
import edu.unam.modelo.Seguimiento;
import edu.unam.servicio.EjercicioServicio;
import edu.unam.servicio.GMServicio;
//import edu.unam.servicio.RutinaEntrenamientoServicio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import utilidades.AlmacenadorDeEntidadesSingleton;
import utilidades.navegacion.NavegadorDeVistasSingleton;
import utilidades.navegacion.RutasVistas;

/*
 * 
 * ESTO HAY QUE VERLO, LOS METODOS ESPECIALES PARA LAS FECHAS NO SON MUY ENTENDIBLES Y CORRECTAS...
 * 
 */

public class ControladorDeVistaCalculoVolEntSemanal {
	// ATRIBUTOS, NODOS Y ELEMENTOS DE ESCENA //
    @FXML
    private Button BTAtras;

    @FXML
    private Button BTCalcular;

    @FXML
    private ChoiceBox<Integer> ChoBoSE;

    @FXML
    private ComboBox<GrupoMuscular> CoBoGM; // ELEMENTOS GM

    @FXML
    private TextField txtResultado;
	
//    private LocalDate[] semanaUno, semanaDos, semanaTres, semanaCuatro;
    private List<LocalDate> semUno, semDos, semTres, semCuatro;
    
//    private LocalDate[] fechasDeEntrenamiento;
    
    private Entrenamiento ent;

    private List<Seguimiento> listaSeg;
    
    private List<Seguimiento> listaSemanaUno, listaSemanaDos, listaSemanaTres, listaSemanaCuatro;
    
    private EjercicioServicio sejer;
    
    private GMServicio sgm;
    
    private HashMap<Integer, List<Seguimiento>> hm;
    

    
    
    // METODOS Y EVENTOS //
	private void inicializarDatos(List<Seguimiento> lista) {
//		this.semanaUno = new LocalDate[6];
//		this.semanaDos = new LocalDate[6];
//		this.semanaTres = new LocalDate[6];
//		this.semanaCuatro = new LocalDate[6];
		
		this.semUno = new ArrayList<>();
		this.semDos = new ArrayList<>();
		this.semTres = new ArrayList<>();
		this.semCuatro = new ArrayList<>();
		
		this.ent =
				AlmacenadorDeEntidadesSingleton
					.getInstancia().getEntrenamiento();
		
		this.listaSeg = new ArrayList<>(); this.listaSeg.addAll(lista);
		
		this.listaSemanaUno = new ArrayList<>();
		this.listaSemanaDos = new ArrayList<>();
		this.listaSemanaTres = new ArrayList<>();
		this.listaSemanaCuatro = new ArrayList<>();
		
//		this.fechasDeEntrenamiento = new LocalDate[6*4];
		
		this.sejer = new EjercicioServicio();
		this.sgm = new GMServicio();
		
    	this.hm = new HashMap<>();
	}
	
	private void establecerFechasParaSemanas() {
		LocalDate arregloFechas[] = new LocalDate[6*4];
		LocalDate fechaActual = this.ent.getFechaInicio();
		
		for (int i = 0; i < arregloFechas.length; i++) {
			if (fechaActual.getDayOfWeek() == DayOfWeek.SUNDAY) {
				fechaActual = fechaActual.plusDays(1);
			}
			
			arregloFechas[i] = fechaActual;
			fechaActual = fechaActual.plusDays(1);
		}
		
		// OPTIMIZE: ASIGNA LAS FECHAS (DE LA PEOR MANERA POSIBLE)
		for (int i = 0; i < 6; i++) {			
			this.semUno.add(arregloFechas[i]);
		}
		
		for (int i = 6; i < 6*2; i++) {
			this.semDos.add(arregloFechas[i]);
		}
		
		for (int i = 6*2; i < 6*3; i++) {
			this.semTres.add(arregloFechas[i]);
		}
		
		for (int i = 6*3; i < 6*4; i++) {
			this.semCuatro.add(arregloFechas[i]);
		}
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
	
	// HACK: ESTO ES UN ASCO, PERO ESTOY APURADO
	private List<Seguimiento> ordenarYAlmacenarFechasEnSemanas(List<LocalDate> listaSemFechas) {
		List<Seguimiento> listaSemSeguimientos = new ArrayList<>();
		
		for (Seguimiento seg: this.listaSeg) {
//			for (LocalDate fechaSeg : listaSemFechas) {
//				if (seg.getFechaHoy().isEqual(fechaSeg)) {
//					listaSemSeguimientos.add(seg);
////					break;
//				}
//			}
			
			// SI LA LISTA CONTIENE LA FECHA DEL SEGUIMIENTO QUE SE ESTÁ ITERANDO //
			// ENTONCES PERTENECE A ESA SEMANA DE ENTRENAMIENTO.                  //
			if (listaSemFechas.contains(seg.getFechaHoy())) {
				listaSemSeguimientos.add(seg);
			}
		}
		
		return listaSemSeguimientos;
	}
	
	private void actualizarComboBox() {
		this.CoBoGM.getItems().clear();
		this.CoBoGM.getItems().addAll(this.sgm.obtenerTodosLosGM());
		
		this.CoBoGM.setConverter(new StringConverter<GrupoMuscular>() {
			@Override
			public String toString(GrupoMuscular gm) {
				return (gm == null) ? null : gm.getNombreGrupo();
			}
			
			@Override
			public GrupoMuscular fromString(String str) {
				return null;
			}
		});
	}
	
	// NOTE: METODO PRINCIPAL DESPUES DE "INITIALIZE()"
	public void iniciar(List<Seguimiento> lista) {
		this.inicializarDatos(lista);
		
		this.ChoBoSE.getItems().addAll(1, 2, 3, 4);
		this.ChoBoSE.setValue(1);
		
		this.actualizarComboBox();
		
		this.establecerFechasParaSemanas();
		
		this.listaSemanaUno.addAll(
				this.ordenarYAlmacenarFechasEnSemanas(this.semUno)
		);
		this.listaSemanaDos.addAll(
				this.ordenarYAlmacenarFechasEnSemanas(this.semDos)
		);
		this.listaSemanaTres.addAll(
				this.ordenarYAlmacenarFechasEnSemanas(this.semTres)
		);
		this.listaSemanaCuatro.addAll(
				this.ordenarYAlmacenarFechasEnSemanas(this.semCuatro)
		);
		
    	// HACK: ES UNA M!ERDA, PERO SE PUEDE MEJORAR
    	hm.put(1, this.listaSemanaUno); hm.put(2, this.listaSemanaDos);
    	hm.put(3, this.listaSemanaTres); hm.put(4, this.listaSemanaCuatro);
	}
	
    @FXML
    void eventoBTAtras(ActionEvent event) {
    	NavegadorDeVistasSingleton
    	.getInstancia()
    	.cargarNuevaVista(
    			this.getClass(),
    			RutasVistas.VISTA_ABM_SEG
    	);

	ControladorVistaABMSeg CVABMS =
			NavegadorDeVistasSingleton
				.getInstancia()
				.obtenerControladorDeNuevaVista();
	
	CVABMS.iniciar();
	
	NavegadorDeVistasSingleton
    	.getInstancia()
    	.cambiarVista(
    			this.BTAtras,
    			"Seguimiento"
    	);
    }

    @FXML
    void eventoBTCalcular(ActionEvent event) {
    	int semana = this.ChoBoSE.getSelectionModel().getSelectedItem();
    	GrupoMuscular gm = this.CoBoGM.getSelectionModel().getSelectedItem();
    	
    	if (gm == null) {
    		this.lanzarMensaje(
    				AlertType.ERROR, "Error!",
    				"ERROR DE CAMPO",
    				"Debe elegir un grupo muscular..."
    		);
    		System.err.println(
    				"[ ERROR ] > Debe elegir un grupo muscular para continuar!"
    		);
    		return;
    	}
    	
    	List<Ejercicio> listaEj = this.sgm.obtenerListaDeEjercicios(gm.getIdGM());
    	double volumenEntrenamientoSemanal = 0;
		
    	for (Seguimiento regSeg: hm.get(semana)) {
			if (listaEj.contains(regSeg.getEjercicioRealizado())) {
				volumenEntrenamientoSemanal += regSeg.getPesoTrabajado() * regSeg.getCantRepeticionesRealizado() * regSeg.getCantSerieRealizado();
			}
		} this.txtResultado.setText(String.format("%.2f",volumenEntrenamientoSemanal));
		
		// LOGs
		for (Seguimiento i : this.listaSemanaUno) {
			System.out.println(i.getFechaHoy());
		} System.out.println("-----");
		
		System.out.println(volumenEntrenamientoSemanal);
    }
    
    // NOTE: NO USADO
    @FXML
    private void initialize() {
    	
    }
}




// DEPRECATED: MOMENTANEAMETE QUEDA ACÁ POR LAS DUDAS

// OPTIMIZE: HAY QUE VER UNA FORMA DE OBTENER
//for (Seguimiento regSeg: hm.get(semana)) {
//	if (this.sejer.obtenerEjercicioYSusObjetos(regSeg.getEjercicioRealizado().getIdEjercicio()).getGrupoMuscular().getIdGM() == gm.getIdGM()) {
//		volumenEntrenamientoSemanal += regSeg.getPesoTrabajado() * regSeg.getCantRepeticionesRealizado() * regSeg.getCantSerieRealizado();
//	}
//} this.txtResultado.setText(String.format("%.2f",volumenEntrenamientoSemanal));

/*
switch (semana) {
	case 1: {
		for (Seguimiento regSeg: this.listaSemanaUno) {
			if (this.sejer.obtenerEjercicioYSusObjetos(regSeg.getEjercicioRealizado().getIdEjercicio()).getGrupoMuscular().getIdGM() == gm.getIdGM()) {
				volumenEntrenamientoSemanal += regSeg.getPesoTrabajado() * regSeg.getCantRepeticionesRealizado() * regSeg.getCantSerieRealizado();
			}
		} this.txtResultado.setText(String.format("%.2f",volumenEntrenamientoSemanal));
		
		// LOG
		for (Seguimiento i : this.listaSemanaUno) {
			System.out.println(i.getFechaHoy());
		} System.out.println("-----");
		
		
		System.out.println(volumenEntrenamientoSemanal);
		break;
	}
	
	case 2: {
		for (Seguimiento regSeg: this.listaSemanaDos) {
			if (this.sejer.obtenerEjercicioYSusObjetos(regSeg.getEjercicioRealizado().getIdEjercicio()).getGrupoMuscular().getIdGM() == gm.getIdGM()) {
				volumenEntrenamientoSemanal += regSeg.getPesoTrabajado() * regSeg.getCantRepeticionesRealizado() * regSeg.getCantSerieRealizado();
			}
		} this.txtResultado.setText(String.format("%.2f",volumenEntrenamientoSemanal));
		
		// LOG
		for (Seguimiento i : this.listaSemanaDos) {
			System.out.println(i.getFechaHoy());
		} System.out.println("-----");

		break;
	}
	
	case 3: {
		for (Seguimiento regSeg: this.listaSemanaTres) {
			if (this.sejer.obtenerEjercicioYSusObjetos(regSeg.getEjercicioRealizado().getIdEjercicio()).getGrupoMuscular().getIdGM() == gm.getIdGM()) {
				volumenEntrenamientoSemanal += regSeg.getPesoTrabajado() * regSeg.getCantRepeticionesRealizado() * regSeg.getCantSerieRealizado();
			}
		} this.txtResultado.setText(String.format("%.2f",volumenEntrenamientoSemanal));
		
		// LOG
		for (Seguimiento i : this.listaSemanaTres) {
			System.out.println(i.getFechaHoy());
		} System.out.println("-----");

		break;
	}
	
	case 4: {
		for (Seguimiento regSeg: this.listaSemanaCuatro) {
			if (this.sejer.obtenerEjercicioYSusObjetos(regSeg.getEjercicioRealizado().getIdEjercicio()).getGrupoMuscular().getIdGM() == gm.getIdGM()) {
				volumenEntrenamientoSemanal += regSeg.getPesoTrabajado() * regSeg.getCantRepeticionesRealizado() * regSeg.getCantSerieRealizado();
			}
		} this.txtResultado.setText(String.format("%.2f",volumenEntrenamientoSemanal));
		
		// LOG
		for (Seguimiento i : this.listaSemanaCuatro) {
			System.out.println(i.getFechaHoy());
		} System.out.println("-----");

		// NO HACE FALTA BREAK
	}
	
	default: {}
}

// LOGs
for (LocalDate i : this.semUno) {
	System.out.println(i);
}
System.out.println("------");
for (LocalDate i : this.semDos) {
	System.out.println(i);
}
System.out.println("------");
for (LocalDate i : this.semTres) {
	System.out.println(i);
}
System.out.println("------");
for (LocalDate i : this.semCuatro) {
	System.out.println(i);
}
*/  
