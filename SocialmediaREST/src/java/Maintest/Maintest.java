/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maintest;

import Frontend.Clients.UsersClient;

/**
 *
 * @author Niklas
 */
public class Maintest {
    public static void main(String argv[]) {

    try {
        UsersClient c = new UsersClient();
        Frontend.Viewmodels.TUsers t = new Frontend.Viewmodels.TUsers();
        t.setUsername("t5");
        t.setPass("t5");
        t.setOccupation("viewmodel");
        c.create_XML(t);
        System.out.println("hej");
        Frontend.Viewmodels.TUsers u = c.login_XML(Frontend.Viewmodels.TUsers.class, "u1", "u1");
        System.out.println(u.toString());
        
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
}
