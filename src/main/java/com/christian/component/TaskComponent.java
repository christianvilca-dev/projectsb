package com.christian.component;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Ejecuta procesos repititivos 
 * 
 * Ejemplos: 
 * 		Enviar correos electronicos
 * 		automaticos Eliminar datos en las tablas en las base de datos
 * 
 * @author Christian
 *
 */
@Component("taskComponent")
public class TaskComponent {

	private static final Log LOG = LogFactory.getLog(TaskComponent.class);

	@Scheduled(fixedDelay = 5000)
	public void doTask() {
		LOG.info("TIME IS: " + new Date());
	}
}
