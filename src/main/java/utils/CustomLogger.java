package utils;

import de.bayerl.sportverband.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.qualifiers.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CustomLogger {

    @Produces
    @ApplicationScoped
    @OptionMannschaft
    public Logger mannschaftLogger () {
        return LogManager.getLogger(MannschaftsService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionSpielplan
    public Logger spielplanLogger () {
        return LogManager.getLogger(SpielplanService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionSpielplanAnzeigen
    public Logger spielplanAnzeigenLogger () {
        return LogManager.getLogger(SpielplanAnzeigenService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionStatistik
    public Logger statistikLogger () {
        return LogManager.getLogger(StatistikService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionTabellenposition
    public Logger tabellenPositionLogger () {
        return LogManager.getLogger(TabellenPositionService.class);
    }

    @Produces
    @ApplicationScoped
    @OptionTabelle
    public Logger tabelleLogger () {
        return LogManager.getLogger(TabellenService.class);
    }


}
