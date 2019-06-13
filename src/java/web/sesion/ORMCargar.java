/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.sesion;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ORMCargar implements ServletContextListener
{
    @Override  
    public void contextInitialized(ServletContextEvent event)
    {
        ORMUtil.getSesionCMS();
    }
    @Override  
    public void contextDestroyed(ServletContextEvent event)
    {
        ORMUtil.cerrarCMS();
    }
}