package ni.edu.uam.pae_eventos_javafx_equipo;

public class Artesania {

    private final String codigo;
    private final String nombre;
    private final String categoria;
    private final double precio;

    public Artesania(String codigo, String nombre, String categoria, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    public String getCodigo()    { return codigo; }
    public String getNombre()    { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio()    { return precio; }
}