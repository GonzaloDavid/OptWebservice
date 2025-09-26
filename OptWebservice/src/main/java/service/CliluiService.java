package service;

import javax.ejb.Stateless;
import java.math.BigDecimal;
@Stateless
public class CliluiService {

    public String validaSaldo(BigDecimal saldo)
    {
        if (saldo != null && saldo.compareTo(new BigDecimal("1000")) > 0) {
            return "Cliente puede acceder a un crédito";
        } else {
            return "Saldo de Cliente igual o menor a 1000";
        }
    }
}
