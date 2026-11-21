package polizas.DetalleCobertura;

public class DetalleCobertura {
    protected int idPoliza;
    protected int idCobertura;
    protected double sumaAsegurada;
    protected double deducible;

    public DetalleCobertura(int idPoliza, int idCobertura, double sumaAsegurada, double deducible) {
        this.idPoliza = idPoliza;
        this.idCobertura = idCobertura;
        this.sumaAsegurada = sumaAsegurada;
        this.deducible = deducible;
    }

    public int getIdPoliza() { return idPoliza; }
    public void setIdPoliza(int idPoliza) { this.idPoliza = idPoliza; }
    public int getIdCobertura() { return idCobertura; }
    public void setIdCobertura(int idCobertura) { this.idCobertura = idCobertura; }
    public double getSumaAsegurada() { return sumaAsegurada; }
    public void setSumaAsegurada(double sumaAsegurada) { this.sumaAsegurada = sumaAsegurada; }
    public double getDeducible() { return deducible; }
    public void setDeducible(double deducible) { this.deducible = deducible; }

    @Override
    public String toString() { return "Poliza: " + idPoliza + " - Cob: " + idCobertura; }
}