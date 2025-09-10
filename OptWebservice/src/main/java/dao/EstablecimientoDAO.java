package dao;

import entidad.Establecimiento;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;

import java.util.List;


@Stateless
public class EstablecimientoDAO extends GenericDAO<Establecimiento> {

    public EstablecimientoDAO() {
        super(Establecimiento.class);
    }

    public Establecimiento obtenerPorCodigo(String key){

        //JPA --> NO SQL --> JPQL
        String sql=" SELECT est FROM Establecimiento est WHERE est.codigo= :parametroEntrada ";

        Query consulta=this.em.createQuery(sql);
        consulta.setParameter("parametroEntrada",key);
        List<Establecimiento> listaEstablecimientos =consulta.getResultList();
        if(listaEstablecimientos.isEmpty())
        {
            return null;
        }else{
            return listaEstablecimientos.get(0);
        }
    }


}
