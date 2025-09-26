package service;

import util.MessageService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;

@Stateless
public class ClientServicejt {
    @Inject
    private MessageService messageService;

    public String validaSaldo(BigDecimal saldo)
    {
        if (saldo.doubleValue() >= 10000.0 ) {
            return messageService.obtenerMensaje("14") ; // "Saldo OK
        }
        return messageService.obtenerMensaje("13") ; // "Saldo NO OK < 10000
    
    }

}
