package com.hotelPremier.classes.DTO;

public class SuperiorFamilyPlanDTO {

    private boolean camasIndividuales;
    private boolean camaDoble;

    public SuperiorFamilyPlanDTO() {
    }

    public boolean isCamasIndividuales() {
        return camasIndividuales;
    }

    public void setCamasIndividuales(boolean camasIndividuales) {
        this.camasIndividuales = camasIndividuales;
    }

    public boolean isCamaDoble() {
        return camaDoble;
    }

    public void setCamaDoble(boolean camaDoble) {
        this.camaDoble = camaDoble;
    }
}
