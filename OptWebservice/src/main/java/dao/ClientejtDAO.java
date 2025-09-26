package dao;



import dto.Cliente;
import entidad.Clientejt;
import entidad.Establecimiento;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ClientejtDAO extends GenericDAO<ClientejtDAO> {

    public ClientejtDAO() {
        super(ClientejtDAO.class);
    }

    public List<Clientejt>   obtenerClientes(){

        String sql=" SELECT a FROM Clientejt a";

        Query consulta=this.em.createQuery(sql);
        //consulta.setParameter("parametroEntrada",key);
        List<Clientejt> listaClientes =consulta.getResultList();
        if(listaClientes.isEmpty())
        {
            return null;
        }else{
            return listaClientes;
        }
    }


}
