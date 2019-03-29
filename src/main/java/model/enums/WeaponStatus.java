package model.enums;

public enum WeaponStatus {
    UNLOADED("unloaded", 'u'),
    PARTIALLY_LOADED("partially loaded", 'p'),
    LOADED("fully loaded", 'l');

    private String status;
    private char abbr;

    WeaponStatus(String status, char abbr){
        this.abbr = abbr;
        this.status = status;
    }
}
