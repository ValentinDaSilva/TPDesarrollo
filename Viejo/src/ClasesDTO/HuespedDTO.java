package ClasesDTO;

import ClasesDeDominio.Direccion;

public class HuespedDTO {
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private String email;
    private String nacionalidad;
    private String direccionCompleta;
    private Direccion direccion;
    private String posicionIVA;
    private String cuit;
    private String ocupacion;

    public HuespedDTO() {
    }

    public HuespedDTO(String apellido, String nombre, String tipoDocumento, String numeroDocumento,
                   String cuit, String posicionIVA, Direccion direccion,
                   String telefono, String email, String ocupacion, String nacionalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.cuit = cuit;
        this.posicionIVA = posicionIVA;
        this.ocupacion = ocupacion;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }
    public Direccion getDireccion() {
        return direccion;
    }

    public String getPosicionIVA() {
        return posicionIVA;
    }
    public String getCuit() {
        return cuit;
    }
    public String getOcupacion() {
        return ocupacion;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    public void setPosicionIVA(String posicionIVA) {
        this.posicionIVA = posicionIVA;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}
