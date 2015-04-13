package com.project.rest;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.BaseData;
import com.project.entities.FileInfo;
import com.project.entities.UE;

@RunWith(Arquillian.class)
public class UeRestTest {
	
	private static final Logger log = LoggerFactory.getLogger(UeRestTest.class);
	FileInfo fileInfo = new FileInfo("filename.xml", "filepath");
	
	/*@ArquillianResource
	private URL deploymentURL;*/

	@Deployment
	public static WebArchive createDeployment() {
		
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		
		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class,"test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
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
		em.createQuery("delete from UE").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		UE ue = new UE(4231, "uemarketingName", "uemanufacturer", "ueaccessCapability");
		BaseData b = new BaseData();
		b.setUeFK(ue);
		b.setDate(new Date());
		tx.begin();
		em.joinTransaction();
		em.persist(ue);
		em.persist(b);
		tx.commit();
		em.clear();
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
	public void countCallFailuresDateRange(@ArquillianResteasyResource UeRest ueRest) throws ParseException{
		final String code = "c00";
		final String dateS = "2015-04-01T09:00:00";
		final String dateE = "3015-01-01T09:00:00";
		final String tac = "4231";
		final String data = code + dateS + dateE + tac;
		
		final List<String[]> info = ueRest.countCallFailuresDateRange(data);

		assertEquals(1, info.size());
		assertEquals(info.get(0)[15], "1");
		assertEquals(info.get(0)[11], "4231");
		assertEquals(info.get(0)[18], "uemarketingName");
		assertEquals(info.get(0)[19], "uemanufacturer");
		assertEquals(info.get(0)[20], "ueaccessCapability");
		
	}
}
