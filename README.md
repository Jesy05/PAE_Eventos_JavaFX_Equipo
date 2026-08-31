# PAE_Eventos_JavaFX_Equipo

Guia practica de **eventos y navegacion en JavaFX** mediante casos empresariales
nicaraguenses. Asignatura: Programacion de Aplicaciones de Escritorio.

## Integrantes

| Rol          | Nombre                        |
|--------------|-------------------------------|
| Integrante 1 | Jose Cristo Carvallo Herrera  |
| Integrante 2 | Jesy Nicole Gonzalez Jarquin  |

Trabajo en pareja (piloto / navegante), intercambiando roles en cada ejercicio.

## Descripcion

Aplicacion JavaFX con un menu principal que navega hacia tres retos:

1. **Inventario de pulperia** — registro de productos con `ActionEvent`, busqueda
   con `KeyEvent` (ENTER) y validacion de campos vacios y numericos.
2. **Recepcion de cafe** — lotes en un `TableView`, detalle con `MouseEvent`,
   `ContextMenu` para editar y eliminar, y confirmacion mediante `Alert`.
3. **Tienda de artesanias** — `MenuBar` (Catalogo, Ventas, Ayuda), `ToolBar`
   (Nuevo, Guardar, Buscar) y catalogo en `TableView` con imagen por producto.

## Requisitos

- JDK 21
- Maven (o el wrapper incluido: `mvnw` / `mvnw.cmd`)

## Ejecucion

Windows:

    mvnw.cmd clean javafx:run

Linux / macOS:

    ./mvnw clean javafx:run

## Estructura

    src/main/java/ni/edu/uam/pae_eventos_javafx_equipo/
      HelloApplication.java   Punto de entrada y navegacion entre vistas
      MainController.java      Menu principal
      Producto.java  / InventarioController.java     (Reto 1)
      LoteCafe.java   / CafeController.java           (Reto 2)
      Artesania.java  / ArtesaniasController.java     (Reto 3)
    src/main/resources/ni/edu/uam/pae_eventos_javafx_equipo/
      main-view.fxml  inventario-view.fxml  cafe-view.fxml  artesanias-view.fxml

## Historial de trabajo

Repositorio compartido, ambos integrantes como colaboradores. Cada integrante
trabajo en ramas propias (`jesy/...`, `jose/...`) y fusiono a `master` por
Pull Request.