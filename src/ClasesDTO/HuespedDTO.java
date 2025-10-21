package ClasesDTO;

import ClasesDeDominio.Direccion;

public class HuespedDTO {
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private String email;
    private String nacionalidad;
    private String direccionCompleta;


    public HuespedDTO(String nombreCompleto, String tipoDocumento, String numeroDocumento,
                      String telefono, String email, String nacionalidad, Direccion direccion) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    public String getNombreCompleto() {
        return nombreCompleto;
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
}
