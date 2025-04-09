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
Se modificó el fichero `devnote.txt` a `devnote.md`, pasando todo el log de desarrollo a Markdown, y además se lo organizó para mayor compención

---