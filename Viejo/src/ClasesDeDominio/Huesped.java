package ClasesDeDominio;

public class Huesped {

    private String apellido;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String cuit;
    private String posicionIVA;
    private Direccion direccion;  // Composición
    private String telefono;
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private String direccionCompleta;
    public Huesped() {
    }

    public Huesped(String apellido, String nombre, String tipoDocumento, String numeroDocumento,
                   String cuit, String posicionIVA, Direccion direccion,
                   String telefono, String email, String ocupacion, String nacionalidad) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.cuit = cuit;
        this.posicionIVA = posicionIVA;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }

    // ======== Getters y Setters ========

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getPosicionIVA() {
        return posicionIVA;
    }

    public void setPosicionIVA(String posicionIVA) {
        this.posicionIVA = posicionIVA;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public String getDireccionCompleta() {
        return direccionCompleta;
    }
    public void setDireccionCompleta(Direccion direccion) {
        this.direccionCompleta = direccion != null
                ? direccion.getCalle() + " " + direccion.getNumero() + ", " +
                  direccion.getLocalidad() + ", " + direccion.getProvincia()
                : "";
    }


    // Validadores para cada campo (sin expresiones regulares)
    public boolean validarApellido() {
        if (apellido == null) return false;
        return todosLetrasOEspacios(apellido);
    }

    public boolean validarNombre() {
        if (nombre == null) return false;
        return todosLetrasOEspacios(nombre);
    }

    public boolean validarTipoDocumento() {
        if (tipoDocumento == null) return false;
        String t = tipoDocumento.trim().toUpperCase();
        java.util.Set<String> aceptados = new java.util.HashSet<>(java.util.Arrays.asList(
            "DNI", "PASAPORTE", "LC", "LE", "CI"
        ));
        return aceptados.contains(t);
    }

    public boolean validarNumeroDocumento() {
        if (numeroDocumento == null) return false;
        return todosNumeros(numeroDocumento);
    }

    public boolean validarCuit() {
        if (cuit == null) return false;
        for (int i = 0; i < cuit.length(); i++) {
            char ch = cuit.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    public boolean validarPosicionIVA() {
        if (posicionIVA == null) return false;
        String p = posicionIVA.trim().toUpperCase();
        java.util.Set<String> aceptadas = new java.util.HashSet<>(java.util.Arrays.asList(
            "RESPONSABLE INSCRIPTO", "MONOTRIBUTISTA", "CONSUMIDOR FINAL", "EXENTO", "NO RESPONSABLE"
        ));
        return aceptadas.contains(p);
    }

    public boolean validarDireccion() {
        return todosLetrasOEspacios(direccion.getCalle()) &&
               todosNumeros(String.valueOf(direccion.getNumero())) &&
               todosLetrasOEspacios(direccion.getLocalidad()) &&
               todosLetrasOEspacios(direccion.getProvincia());
    }

    public boolean validarTelefono() {
        if (telefono == null) return false;
        return todosNumeros(String.valueOf(telefono));
    }

    public boolean validarEmail() {
        if (email == null) return false;
        boolean tieneArroba = false;
        boolean tienePunto = false;
        for(int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            if (c == ' ' || c == ',') {
                return false;
            }
            if(c == '@') {
                tieneArroba = true;
            }
            if(c == '.') {
                tienePunto = true;
            }
        }

        return (tieneArroba && tienePunto);
    }

    public boolean validarOcupacion(String ocupacion) {
        if (ocupacion == null) return false;
        return todosLetrasOEspacios(ocupacion);
    }

    public boolean validarNacionalidad() {
        if (nacionalidad == null) return false;
        return todosLetrasOEspacios(nacionalidad);
    }
        
    private boolean todosNumeros(String s) {
        if (s == null || s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    private boolean todosLetrasOEspacios(String s) {
        if (s == null) return false;
        String t = s.trim();
        if (t.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetter(c) || c == ' ')) return false;
        }
        return true;
    }
    public boolean validar() {
        if (!validarApellido()) return false;
        if (!validarNombre()) return false;
        if (!validarTipoDocumento()) return false;
        if (!validarNumeroDocumento()) return false;
        if (!validarCuit()) return false;
        if (!validarPosicionIVA()) return false;
        if (direccion == null) return false;
        if (!validarDireccion()) return false;
        if (!validarTelefono()) return false;
        if (!validarEmail()) return false;
        if (!validarOcupacion(this.ocupacion)) return false;
        if (!validarNacionalidad()) return false;
        return true;
    }
}
