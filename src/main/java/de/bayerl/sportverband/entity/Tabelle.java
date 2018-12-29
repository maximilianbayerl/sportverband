package de.bayerl.sportverband.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

@Entity
public class Tabelle extends BasisEntity implements Serializable {

    @Getter
    @Setter
    private String ligaName;

    @Getter
    @Setter
    private Date saison;

    @Getter
    @Setter
    @OneToMany(mappedBy = "tabelle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Tabellenposition> tabellenPositionen;

    public Tabelle() {
    }

    public Tabelle(String ligaName, Date saison){
        this.ligaName = ligaName;
        this.saison = saison;
    }

    public Tabellenposition addTabellenPosition(Tabellenposition tabPos){
        tabellenPositionen.add(tabPos);
        tabPos.setTabelle(this);
        return tabPos;
    }
}
