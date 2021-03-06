package com.project.rest;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.FileInfo;
import com.project.entities.UE;

@RunWith(Arquillian.class)
public class EventCauseRestTest {
	
	FileInfo fileInfo = new FileInfo("filename.xml", "filepath");
		
		@PersistenceContext
		EntityManager em;

		@Inject
		UserTransaction tx;

		@Before
		public void setUpPersistenceModuleForTest() throws Exception {
			clearDataFromPersistenceModule();
			insertTestData();
			beginTransaction();
		}

		private void clearDataFromPersistenceModule() throws Exception {
			tx.begin();
			em.joinTransaction();
			em.createQuery("delete from BaseData").executeUpdate();
			tx.commit();
			tx.begin();
			em.joinTransaction();
			em.createQuery("delete from EventCause").executeUpdate();
			tx.commit();
		}

		private void insertTestData() throws Exception, ParseException {
			BaseData b = new BaseData();
			EventCause eventCause = new EventCause(55, 5555, "eventCauseDescription");
			UE ue = new UE(5500, "uemarketingName", "uemanufacturer", "ueaccessCapability");
			FailureClass failureClass = new FailureClass(6, "test");
			b.setEventCauseFK(eventCause);
			b.setImsi(191911000023112L);
			b.setUeFK(ue);
			b.setFaliureClassFK(failureClass);
			b.setDate(new Date());
			tx.begin();
			em.joinTransaction();
			em.persist(eventCause);
			em.persist(ue);
			em.persist(failureClass);
			em.persist(b);
			tx.commit();
			em.clear();
			
			b= null;
			eventCause = null;
			ue = null;
			failureClass = null;
		}

		private void beginTransaction() throws Exception {
			tx.begin();
			em.joinTransaction();
		}

		@After
		public void endTransaction() throws Exception {
			tx.commit();
		}
		
		@Test
		public void getEventCauseCombi(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			String code = "c00";
			String imsi = "191911000023112";
			String data = code + imsi;
			
			List<String[]> info = evCauseRest.getEventCauseCombi(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[1], "55");
			assertEquals(info.get(0)[5], "5555");
			assertEquals(info.get(0)[21], "eventCauseDescription");
			
			info = null;
		}
		@Test
		public void countUniqueEventCauseByModel(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			String code = "c00";
			String model = "uemarketingName";
			String data = code + model;
			
			List<String[]> info = evCauseRest.countUniqueEventCauseByModel(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[15], "1");
			assertEquals(info.get(0)[1], "55");
			assertEquals(info.get(0)[5], "5555");
			assertEquals(info.get(0)[21], "eventCauseDescription");
			
			info = null;
		}
		
		@Test
		public void getImsiByCauseClass(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			String code = "c00";
			String causeClass = "6";
			String data = code + causeClass;
			
			List<String[]> info = evCauseRest.getImsiByCauseClass(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[7], "191911000023112");
			assertEquals(info.get(0)[15], "1");
			
			info = null;
		}
		@Test
		public void countUniqueEventCauseByImsiDate(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			String code = "c00";
			String imsi = "191911000023112";
			String dateS = "2015-04-01T09:00:00";
			String dateE = "3015-01-01T09:00:00";
			String data = imsi + "::"+ code + dateS + dateE;
						
			List<String[]> info = evCauseRest.countUniqueEventCauseByImsiDate(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[1], "1");
			assertEquals(info.get(0)[2], "eventCauseDescription");
			
			info = null;
		}
		@Test
		public void getUniqueEventCauseByImsiByCauseCode(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			String code = "c00";
			String imsi = "191911000023112";
			String causeCode = "55";
			String data = causeCode + "::" + code + imsi;
						
			List<String[]> info = evCauseRest.getUniqueEventCauseByImsiByCauseCode(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[1], "1");
			assertEquals(info.get(0)[2], "eventCauseDescription");
			
			info = null;
		}
}
