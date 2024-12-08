package com.tercerotest.models.enumerator;

public enum TipoEnergia {
    ELECTRICA("ELECTRICA"), EOLICA("EOLICA"), NUCLEAR("NUCLEAR"), GEOTERMICA("GEOTERMICA"),SOLAR("SOLAR"), HIDROELECTRICA("HIDROELECTRICA");    
    
    private String name;

    TipoEnergia(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
