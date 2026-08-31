package ni.edu.uam.pae_eventos_javafx_equipo;

public class LoteCafe {

    private String codigo;
    private String productor;
    private double quintales;
    private double humedad;

    public LoteCafe(String codigo, String productor, double quintales, double humedad) {
        this.codigo = codigo;
        this.productor = productor;
        this.quintales = quintales;
        this.humedad = humedad;
    }

    public String getCodigo()     { return codigo; }
    public String getProductor()  { return productor; }
    public double getQuintales()  { return quintales; }
    public double getHumedad()    { return humedad; }

    public void setProductor(String productor) { this.productor = productor; }
    public void setQuintales(double quintales) { this.quintales = quintales; }
    public void setHumedad(double humedad)     { this.humedad = humedad; }
}