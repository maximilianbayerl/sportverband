package de.bayerl.sportverband.converter;

import de.bayerl.sportverband.entity.Tabelle;
import de.bayerl.sportverband.service.TabellenService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@ApplicationScoped
public class TabellenConverter implements Converter {

    @Inject
    TabellenService tabServ;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null){
            return "";
        }
        Tabelle tabelle = tabServ.findTabelleByName(value);
        if(tabelle == null){
            return "";
        }
        return tabelle;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return null;
        }
        if(!value.getClass().equals(Tabelle.class)){
            return null;
        }
        return String.valueOf(((Tabelle)value).getLigaName());
    }
}
