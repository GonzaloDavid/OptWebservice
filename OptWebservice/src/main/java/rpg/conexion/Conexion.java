package rpg.conexion;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;
import iseries.programcall.base.AS400Connection;
import iseries.programcall.base.AbstractProgramCallBean;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Clase que permite el manejo del pool de conexiones
 * 
 * @author
 * @version 1.0.0
 */
public class Conexion {


	private static final String USUARIO="USUARIO";
	private static final String CLAVE="CLAVE";
	private static String servidor = null;
	private static int maxConnections = 0;
	private static int maxLifeTime = 0;
	private static int maxInactivity = 0;
	private static int cleanupInterval = 0;
	private static int connPreconnected = 0;
	private static int maxNumThreads = 0;
	private static AS400ConnectionPool as400ConnectionPool = null;
	private static ExecutorService executorService = null;

	static {


		servidor = "GIBS2.baninter.int";
		maxConnections = 10;
		maxLifeTime =10;
		maxInactivity = 10;
		cleanupInterval = 10;
		connPreconnected = 10;

		as400ConnectionPool = new AS400ConnectionPool();
		as400ConnectionPool.setMaxConnections(maxConnections);
		as400ConnectionPool.setMaxLifetime(maxLifeTime);
		as400ConnectionPool.setMaxInactivity(maxInactivity);
		as400ConnectionPool.setCleanupInterval(cleanupInterval);

		try {
			as400ConnectionPool.fill(servidor, USUARIO, CLAVE, AS400.COMMAND, connPreconnected);
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}

		maxNumThreads = 8;
		executorService = Executors.newFixedThreadPool(maxNumThreads);
	}

	/**
	 * Metodo que realiza el manejo de conexiones con el pool y desconexion del
	 * comando
	 * 
	 * @param apCallBean
	 * @return
	 */
	public AbstractProgramCallBean conectaAS400(AbstractProgramCallBean apCallBean) {
		AS400 as400;
		try {
			as400 = as400ConnectionPool.getConnection(servidor, USUARIO, CLAVE, AS400.COMMAND);
			AS400Connection as400Connection = new AS400Connection(as400);
			apCallBean.initConnection(as400Connection);
			apCallBean.invoke();
			as400ConnectionPool.returnConnectionToPool(as400);
		} catch (ConnectionPoolException e) {
			System.out.println("Error al ejecutar el programa. " + e.getMessage());
		} finally {
			as400 = null;
		}
		return apCallBean;
	}

	/**
	 * Somete un thread en un pool y devuelve un objeto para acceder al
	 * resultado.
	 * 
	 * @param callable
	 *            tipo de thread que permite retornar un resultado
	 * @return Future permite acceder al resultado de la ejecución del thread
	 */
	public static Future<AbstractProgramCallBean> someterThreadNpcb(Callable<AbstractProgramCallBean> callable) {
		return executorService.submit(callable);
	}

	/**
	 * Finaliza los pools de threads y conexiones para liberar recursos.
	 */
	public static void finaliza() {

		if (executorService != null) {

			executorService.shutdown();
			executorService = null;
		}
		if (as400ConnectionPool != null) {

			as400ConnectionPool.close();
			as400ConnectionPool = null;
		}
	}
}
