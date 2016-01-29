import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentsTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		//�������ö���
		Configuration config = new Configuration().configure();
		//��������ע�����
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();
		//�����Ự��������
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//�Ự����
		session = sessionFactory.openSession();
		//��������
		transaction = session.beginTransaction();
	}

	@Test
	public void testSaveStudents() {
//		Students s = new Students(1, "John", "��", new Date(), "China");
		Students s = new Students();
		s.setSid(102);
		s.setGender("��");
		s.setSname("����");
		s.setBirthday(new Date());
		Address address = new Address("334400", "13812345678", "China");
		s.setAddress(address);
		
		Address as = new Address();
		Students s2 = new Students(101, "С��", "Ů", new Date(), as);
		
		session.save(s);
		session.save(s2);
	}
	
	@Test
	public void testWriteBlob() throws Exception{
		Address address = new Address();
		Students s = new Students(1, "John", "��", new Date(),address);
		File f = new File("E:" + File.separator + "hib.jpg");
		InputStream input = new FileInputStream(f);
		Blob image = Hibernate.getLobCreator(session).createBlob(input, input.available());
		s.setPicture(image);
		session.save(s);
	}
	
	@Test
	public void testReadBlob() throws Exception{
		Students s = (Students) session.get(Students.class, 1);
		Blob image = s.getPicture();
		InputStream input = image.getBinaryStream();
		File f = new File("E:" + File.separator + "hib_2.jpg");
		OutputStream output = new FileOutputStream(f);
		byte[] buff = new byte[input.available()];
		input.read(buff);
		output.write(buff);
		input.close();
		output.close();
	}
	
	@Test
	public void testGetStudents(){
		Students s = (Students) session.get(Students.class, 100);
		System.out.println(s);
	}
	
	@Test
	public void testLoadStudents(){
		Students s = (Students) session.load(Students.class, 100);
		System.out.println(s);
	}
	
	@Test
	public void testUpdateStudents(){
		Students s = (Students) session.get(Students.class, 100);
		s.setGender("Ů");
		session.update(s);
	}
	
	@Test
	public void testDeleteStudents(){
		Students s = (Students) session.get(Students.class, 102);
		session.delete(s);
	}

	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
}
