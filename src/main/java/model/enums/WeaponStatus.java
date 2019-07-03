package model.enums;

public enum WeaponStatus {
    UNLOADED("unloaded", 'u'),
    PARTIALLY_LOADED("partially loaded", 'p'),
    LOADED("fully loaded", 'l');

    private String status;

    WeaponStatus(String status, char abbr){
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
