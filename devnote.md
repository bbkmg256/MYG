# Notas de Desarrollo – Proyecto MYG

**Autor:** BBKMG

**Proyecto:** MYG – Sistema de gestión de clientes para un gimnasio

---

## Índice
- [16/12/24](#161224)
- [18/12/24](#181224)
- [02/02/25](#020225)
- [26/02/25](#260225)
- [13/03/25](#130325)
- [14/03/25](#140325)
- [15/03/25](#150325)
- [26/03/25](#260325)
- [27/03/25](#270325)
- [29/03/25](#290325)
- [30/03/25](#300325)
- [31/03/25](#310325)
- [02/04/25](#020425)
- [03/04/25](#030425)
- [05/04/25](#050425)
- [09/04/25](#090425)
- [10/04/25](#100425)
- [11/04/25](#110425)
- [12/04/25](#120425)
- [13/04/25](#130425)
- [15/04/25](#150425)
- [18/04/25](#180425)
- [28/04/25](#280425)
- [03/05/25](#030525)
- [11/05/25](#110525)
- [12/05/25](#120525)
- [17/05/25](#170525)

---

### 16/12/24
Inicio (por séptima vez) del proyecto integrador de POO.
Se planteó crear las clases básicas y realizar la primera conexión con la base de datos. 
Lo que más se me complica por ahora es estructurar el diagrama de clases y registrar el entrenamiento de usuarios. Aunque tengo una base, sigue costando. Seguiré trabajando en eso.

---

### 18/12/24
Se definieron las clases principales y se resolvieron problemas con dependencias de base de datos que no funcionaban.
Falta realizar el mapeo con JPA y definir bien las relaciones entre clases.

---

### 02/02/25
Creación del archivo `persistence.xml`.

---

### 26/02/25
Retomé el proyecto.
Relacioné las entidades entre sí, aunque por ahora están comentadas para evitar errores.
Falta aplicar correctamente las anotaciones `@OneToOne`, `@OneToMany`, etc.

---

### 13/03/25
Por problemas con NetBeans, se cambió el IDE y se rehizo el proyecto desde cero.
Todo vuelve a funcionar, pero faltan muchos módulos, la interfaz gráfica y completar los `JPAController`.

---

### 14/03/25
Se trabajó en el `JPAController` de la entidad Cliente.
Se logró la carga de datos, aunque se debe validar que no se repitan entidades existentes.

---

### 15/03/25
Desarrollo del CRUD para Clientes:
- Implementada carga y baja.
- Falta búsqueda y actualización.
- Problemas con Hibernate: necesita la BD creada previamente. No es lo ideal, pero aceptable por ahora.

Centralicé pruebas en el `main` para simplificar.

---

### 26/03/25
CRUD de Cliente casi funcional.
No probé la actualización aún. Estuve todo el día debuggeando mis propios errores.
Curiosamente, Hibernate con `update` en el `persistence.xml` sí crea tablas nuevas, aunque había leído que no lo haría. Mejor así.

---

### 27/03/25
Avances en el `JPAController` de la entidad Tutor.
Aún sin establecer relaciones, se deberá ajustar más adelante.

---

### 29/03/25
Creada la capa de repositorio.
Clases de `JPAController` listas, pero hay mucho código repetido.
Probablemente use genéricos para unificar lógica.

También creé la capa de servicios, que se conectará al repositorio para interactuar con el modelo.

---

### 30/03/25
La capa de servicios ya está funcionando como intermediaria.
El código todavía está desprolijo y repetido.
Pienso usar clases genéricas base y herencia para simplificar.

---

### 31/03/25
Modificada la capa de persistencia:
Todas las clases heredan de `JPAController`.
Cada clase hija implementa su propio `actualizarEntidad()`.
La capa de servicio también está casi completa, aunque aún falta lógica de negocio.

---

### 02/04/25
Se integró JavaFX al proyecto:
- Inicialmente de forma manual.
- Luego, correctamente mediante Maven.

Tuve problemas con `module-info.java` por cambiar el nombre del proyecto (`GYM` a `MYG`), así que decidí eliminar esa clase.

---

### 03/04/25
[Nota personal: ¡Vi un leak de la peli de Minecraft y me la creí!]

Ya se pueden usar archivos `.fxml` para las vistas.
Desde ahora trabajaré con ellos para construir la interfaz.

---

### 05/04/25
Trabajos en el backend:
- Se creó el método `obtenerEntidades()` en `JPAController`, para traer listas completas desde la BD.
- Útil para vistas que muestran todos los registros.

Además, se creó un tablero Trello con las siguientes columnas:
- **Cosas pendientes**
- **Cosas en proceso**
- **Cosas realizadas**
- **Cosas pospuestas**

Por último, eliminé `module-info.java` por los problemas de refactor y dependencias tras el cambio de nombre del proyecto.

---

### 09/04/25
Se modificó el fichero `devnote.txt` a `devnote.md`, pasando todo el log de desarrollo a Markdown, y además se lo organizó para mayor comprensión.

Se **modificaron** las clases de la capa de **persistencia**, diferenciando cuales son los **DAO's (Data Access Object)** del **JPAController** heredado.

Se **recomentó** las **clases** de la **capa del modelo** de **minuscula a mayuscula** para mejor comprensión, por cantidad no se recomentará el resto, pero **desde ahora se utilizará la mayuscula**.

---

### 10/04/25
Se trabajo en las relaciones de las entidades, por el momento se logró relacionar de forma funcional las clases `Ejercicio` con `GrupoMuscular`.

---

### 11/04/25
Se trabajo en los mapeados de las relaciones de las entidades `GrupoMucular` y `Ejercicio`, y `Rutina`, la relacion entre las 2 ultimas por el momento
no funciona, y se plantea en tomarlo como una relacion de M a M. Por otro lado, la relacion entre `GrupoMusuclar` y `Ejercicio` si funciona y es posible
cargar entidades relacionadas, ahora bien, respecto a esto, se modificaron ciertas cosas en la capa `repositorio` y `servicio`, para el primero, se agrego
un metodo en la clase `GMDAO` llamado `actualizarRelacion()` que recibe 2 parametros (una entidad de tipo GM y otra entidad de tipo ejercicio), este metodo
permite cargar una entidad ejercicio que se persistio anteriormente, para poder mantener los enlaces de la relacion (añade la entidad a la lista `ejercicios`).

Tambien se modificó la clase `GMServicio` de la capa de servicio, se agregaron 2 metodos mas, `actualizarRelacionGMEjercicio` y `finalizarConexion`, el primero para
actualizar la lista "ejercicios" de la entida GM al que se halla relacionado alguna entidad ejercicio, y la segunda, para finalizar la conexion con la BD.

Mencionar tambien que se agrego 1 metodo y se modifico otro de la clase jpacontroller del que herendan las clases de la capa persistencia, se agrego el metodo
`hayConexion` que permite verificar si el EMF (Entity Manager Factory) está activo (o si hay conexion abierta con la BD), y se modifico `cerrarEMF`, quitando la
logica interna que tenia, para derivarserla a la capa de servicio.

---

### 12/04/25
Se trabajó en la relación entre `Entrenamiento` y las entidades `Cliente` y `Tutor`, por el momento las relaciones no se pueden probar hasta tener solucionado
los problemas con las relacion de `Ejercicio` y `Rutina` (aun que en realidad puedo hacer pruebas eliminando momentaneamente el atributo rutina de entrenamiento, bueno
aveces soy medio boludo xd).

También se agregaron nombre especificos para los atributos (de alguna entidades) que están escritos con "camelCase", ya que en la BD las columnas aparecen en minusculas todas juntas
y queda medio raro para leer.

---

### 13/04/25
Se refactorizaron algunos metodos de todas las clases de la capa de servicio (los de creación entidades, actualizacion de informacion de entidades, actualizacion de relaciones entre entidades
y eliminación de entidad), especificamente las sentencias "if", no es un cambio significativo, pero para evitar redundancia mas que nada.

---

### 15/04/25
Se refactorizó toda la capa de persistencia, haciendo independiente a cada DAO, es decir, ya no heredan de la clase `JPAController`, sino que cada DAO se maneja con sus metodos sin
afectar a otros DAO's, **por ende la clase `JPAController` queda totalmente obsoleta**, pero por el momento la voy a dejar donde está, además se realizó otro cambio bastante importante deacuerdo a
la forma de asociación respecto a clases hijas y padres, cuando se instancias una clase hija, dentro de su mismo metodo de carga hacia la BD, esta realiza la operacion pertinente para que la clase
padre almacene a esta instanciacion de la entidad hija dentro de su lista de clases hijas (esto deacuerdo como se asocian las relaciones 1 a muchos).

Además, y por consecuencia, al haber modificado metodos de los DAO's de la capa de persistencia, se tuvo que modificar los metodos de los servicios de la capa de servicios, donde se cambiaron algunos
nombres de metodos y se adaptarón estos mismo a las modificacions de parametros realizadas en los metodos de los DAO's.

NOTA IMPORTANTE: NO SE TESTEARON LOS CAMBIOS, Y SE LO DEJO COMO PENDIENTE!!

---

### 18/04/25
Se creó la rama `inestable` para tabajar mas comodamente con cambios sin afectar a la rama `main` del proyecto (y basicamente no cagarla xd).

---

### 27/04/25
Se modificaron las operaciones que agregaban a objetos de entidades hijas en las lista relacional de las entidades padres de estas,
ahora estas operaciones forman parte de cada servicio que lo requiera, cumpliendo la buna practica de dejar a los DAO's solamente hacer
sus operaciones crud y nada mas.

Se modificaron las operaciones de actualizacion de los atributos de las entidades en sus respectivos DAO's, ahora son metodos separados,
que modifican 1 solo atributo nada mas, permitiendo al usuario modificar solo lo que necesita.
NOTA IMPORTANTE: Algunas entidades todavía no pueden modificar sus atributos por que no se probaron sus relaciones ni cargarlas a la BD,
despues de probar que funcionan correctamente, se terminaran sus metodos actualizar.

Se modificaron los metodos que eliminan entidades de la BD en la capa de repositiorio. Cuando se elimina una entidad hija de la BD, se supone
que se debe desenlazar este objeto de la lista relacional que tiene su entidad padre, esto no se hacía, ahora si.

---

### 28/04/25
Se arreglo un fallo de diseño que había con las operaciones de actualizacion de datos de las entidades. Estas hacian cambios por dato en la
BD, rompiendo la atomicidad de operaciones, ahora el objeto se trae de la BD y se modifica en memoria, antes de mergearlo (actualizarlo) a la BD.

---

### 03/05/25
Se está refactorizado los servicios y los DAOs por problemas de diseño, la idea es modificar todo para tener a las clases y capas funcionando como deben,
el DAO solo preocupandose por las operaciones a BD y los servicios generando las transacciones. Hasta el momento se modificaron los servicios y DAOs
correspondientes a las entidades de `Cliente`, `Tutor`, `GM`, `Ejercicio` y `Rutina`.

Además, cosas que nunca se menciona, la creacion del EMF (EntityManagerFactory) se redefinió de forma correcta en un Singleton, para instanciarlo y
hacer la conexion a BD solo 1 vez durante toda la ejecucion del codigo, evitando "sobrecarga" innecesaria o mal uso del mismo.

Y por último, se limpiaron un poco los comentarios o codigos comentados que ya no se usan xd.

---

### 11/05/25
Se modificó la entidad seguimiento, ahora el atributo "ejercicioRealizado" no es un simple String, sino que pasa a ser una relacion de Ejercicio, para poder
confirmar al GM que pertenece cuando se esté calculando "volumen de entenamiento semanal, por cliente y grupo muscular".
Tambíen se modifico este aspecto en su servicio (el de seguimiento).

Se quitaron las relaciones bidireccionales entre entidades que no lo necestiaban:
- Cliente
- Tutor
- GM
- Ejercicio (Hacia RutinaEjercicio)
- Rutina (Hacia Entrenamiento)

y se dejaron los de Rutina (Hacia RutinaEjercicio) y Entrenamiento (Hacia Seguimiento), en los servicios este aspecto también fue modificado.

En todos los servicios (a excepcion de Tutor y Cliente) se quitaron la comprobacion de ID's repetidos, como esto lo administra automaticamente
hibernate, no es necesario comprobar si el id de un registro ya se encuentra en la BD, por que lo normal sería que no pase.

Se modificó la entidad Entrenamiento, se elimino el atributo "volumenEntrenamiento", por que esto será calculado para el usuario y no almacenado
como tal, no tiene sentido.

---

### 12/05/25
Se creó una clase test (TestFuncionamiento), donde se está desarrollando una pequeña aplicacion de prueba para testear las funcionalidades de la aplicación
con la BD, para posteriormente desarrollar la UI, tadavía no está terminado, por ende es algo pendiente a finalizar y probar.

---

### 17/05/25
Se modificaron los servicios de las entidades, ahora validan solamente aquellos parametros o atributos que puedan ser o no, null (osea, todos aquellos que
sean objetos).

No se llegó a terminar la clase de prueba (TestFuncionamiento), pero se realizaron pruebas con esta y la mayoría de funcionalidades están correctar,
aún así esta clase probablemente se vaya cosntruyendo a medida que se va terminando el proyecto.

Hoy comienza el frontend, dando por termindado (aun que sujeto a cambios), el backend.