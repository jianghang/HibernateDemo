import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

public class SessionTest {
	@Test
	public void testOpenSession(){
		Configuration config = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		//1.在事务提交或回滚之后需要手动关闭
		//2.每次都创建新的session对象
		Session session = sessionFactory.openSession();
		
		if(session != null){
			System.out.println("session创建成功！");
		}else{
			System.out.println("session创建失败！");
		}
	}
	
	@Test
	public void testGetCurrentSession(){
		Configuration config = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		
		//1.事物提交或者回滚之后会自动关闭
		//2.使用现有的session对象
		Session session = sessionFactory.getCurrentSession();
		if(session != null){
			System.out.println("session创建成功！");
		}else{
			System.out.println("session创建失败！");
		}
	}
}
