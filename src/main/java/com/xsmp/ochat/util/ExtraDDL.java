package com.xsmp.ochat.util;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraDDL implements SessionCustomizer {
	
	private static Logger logger = LoggerFactory.getLogger(ExtraDDL.class);

	@Override
	public void customize(Session session) throws Exception {
		
		logger.info("---> CUSTOMIZE <---");
		
		/*
		session.getEventManager().addListener(new SessionEventAdapter() {
            @Override
            public void postLogin(SessionEvent event) {
                String fileName = ""; //(String) event.getSession().getProperty("import.sql.file");
                UnitOfWork unitOfWork = event.getSession().acquireUnitOfWork();
                importSql(unitOfWork, fileName);
                unitOfWork.commit();
                
                logger.info("---> POST LOGIN <---");
            }    
        });
        */

	}

	protected void importSql(UnitOfWork unitOfWork, String fileName) {
		//try {
		//	unitOfWork.executeNonSelectingSQL("CREATE TRIGGER PRODUCT_PRICE_UPDATE AFTER UPDATE ON ESPM_PRODUCT CALL \"com.xsmp.espm.util.ProductPushNotificationTrigger\"");
		//}
		//catch (Exception s) {
		//}
	}

}
