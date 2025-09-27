package rpg.conexion;

import iseries.programcall.base.AbstractProgramCallBean;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Clase que retorna el callbean despues de la ejecucion del thread
 * correspondiente a un pool
 * 
 * @author bincuasj
 * @version 1.0.0 25/7/2018
 */
public class TareaNpcb implements Callable<AbstractProgramCallBean> {

	private AbstractProgramCallBean apCallBean;

	public TareaNpcb(AbstractProgramCallBean apCallBean) {
		super();
		this.apCallBean = apCallBean;
	}

	public AbstractProgramCallBean call() {
		Conexion conexion = new Conexion();
		AbstractProgramCallBean aprCallBean;
		aprCallBean = conexion.conectaAS400(apCallBean);
		conexion = null;
		return aprCallBean;
	}

	/**
	 * Ejecuta el thread en un pool, espera el resutado y lo retorna.
	 * 
	 * @return AbstractProgramCallBean aCallBean
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public AbstractProgramCallBean invocaTarea() throws InterruptedException, ExecutionException, Exception {
		AbstractProgramCallBean aCallBean = null;
		Future<AbstractProgramCallBean> future = Conexion.someterThreadNpcb(this);
		aCallBean = (AbstractProgramCallBean) future.get();
		future = null;
		return aCallBean;
	}

}
