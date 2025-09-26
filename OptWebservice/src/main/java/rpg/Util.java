package rpg;

import iseries.programcall.base.AbstractProgramCallBean;

import java.io.InputStream;

public class Util {

	public static void invokeTrx(AbstractProgramCallBean callBean) throws Exception {
		TareaNpcb tarea = new TareaNpcb(callBean);
		callBean = tarea.invocaTarea();
		tarea = null;
	}// invokeTrx

	public static InputStream obtenerArchivoPorNombreEnPry(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}
}