package ci.nkagou.parcauto.enums;

public enum Genre {
    MASCULIN("Masculin"),
    FEMININ("Féminin");

//    MASCULIN, FEMININ

    private final String libelle;

    Genre(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
