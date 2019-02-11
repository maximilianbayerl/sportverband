package de.bayerl.sportverband.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@MappedSuperclass
public class BasisEntity implements Serializable{

    private static final long serialVersionUID = 0L;

    @Id
    @Column( name = "id" )
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public BasisEntity(){

    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasisEntity other = (BasisEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (id=" + id + ')';
    }


}
