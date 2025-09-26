package dao;
import entidad.Clilui;
import javax.ejb.Stateless;
import javax.persistence.Query;

import java.util.List;


@Stateless

public class CliluiDAO extends GenericDAO<Clilui>{

    public CliluiDAO() {
        super(Clilui.class);
    }

    public List<Clilui> obtenerTodos() {
        return em.createQuery("SELECT c FROM Clilui c", Clilui.class)
                .getResultList();
    }


    public Clilui obtenerClilui(Integer key){

        String sql=" SELECT a FROM Clilui a WHERE a.codigo=:parametroEntrada ";
        Query consulta=this.em.createQuery(sql);
        consulta.setParameter("parametroEntrada",key);
        List<Clilui> listaClilui =consulta.getResultList();
        if(listaClilui.isEmpty())
        {
            return null;
        }else{
            return listaClilui.get(0);
        }
    }


    public Integer borraClilui(Integer Clave){
        String sql = "delete  FROM Clilui  WHERE codigo=:parametroEntrada";
        Query consulta=this.em.createQuery(sql);
        consulta.setParameter("parametroEntrada",Clave);

        Integer res = consulta.executeUpdate();
        return res;
    }
    public void insertClilui(Clilui registro  ) {

        insert(registro);

    }
    public void updateClilui(Clilui registro  ) {

        update(registro);

    }


}
