package ni.edu.uam.pae_eventos_javafx_equipo;

public class Producto {

    private final String codigo;
    private final String nombre;
    private final double precio;
    private final int cantidad;

    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getCodigo()  { return codigo; }
    public String getNombre()  { return nombre; }
    public double getPrecio()  { return precio; }
    public int getCantidad()   { return cantidad; }
}