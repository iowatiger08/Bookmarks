package liquibase.ext;

import liquibase.logging.LogLevel;
import liquibase.logging.core.AbstractLogger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LiquibaseCommonsLoggingBridge extends AbstractLogger{
	private Log logger;
	
	public int getPriority() {
		return 10;
	}
	public void setName(String name) {
		logger = LogFactory.getLog(name);
	}
//	@Override
	public void setLogLevel(LogLevel level) {
		
	}
	public void setLogLevel(String logLevel, String logFile) {
		setLogLevel(logLevel);
	}
	
	public void debug(String message) {
		logger.debug(message);
	}

	public void debug(String message, Throwable arg1) {
		logger.debug(message, arg1);
	}

	public void info(String message) {
		logger.info(message);
	}

	public void info(String message, Throwable arg1) {
		logger.info(message, arg1);
	}

	public void severe(String message) {
		logger.error(message);
	}

	public void severe(String message, Throwable arg1) {
		logger.error(message, arg1);
	}

	public void warning(String message) {
		logger.warn(message);
	}

	public void warning(String message, Throwable arg1) {
		logger.warn(message, arg1);
	}


}
