/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.sesion;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.Work;

public class ORMUtil {

    private static final SessionFactory sesionCMS;
    private static final StandardServiceRegistry registroCMS;
    private static Connection c;

    static {
        try {
            
            registroCMS = new StandardServiceRegistryBuilder().configure("/hibernate-mdk.xml").build();
            sesionCMS = (new MetadataSources(registroCMS).getMetadataBuilder().build()).getSessionFactoryBuilder().build();

        } catch (HibernateException x) {
            throw new ExceptionInInitializerError(x);
        }
    }

    public static SessionFactory getSesionCMS() {
        return sesionCMS;
    }

    public static void cerrarCMS() {
        sesionCMS.close();
    }


    public static Connection getConnection(Session s) {
        s.doWork(new Work() {
            @Override
            public void execute(Connection conexion) throws SQLException {
                c = conexion;
            }
        });
        return c;
    }

}
