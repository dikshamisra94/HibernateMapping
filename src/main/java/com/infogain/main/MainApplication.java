package com.infogain.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.infogain.model.Address;
import com.infogain.model.Customer;
import com.infogain.util.HibernateUtil;

public class MainApplication {

	public static void main(String[] args) {

		Customer customer1 = new Customer("Diksha", "Misra");
		Customer customer2 = new Customer("Aakash", "Misra");
		Customer customer3 = new Customer("Reema", "Joshi");

		Address address1 = new Address("Ambala","India");
		List<Customer> customerInfo1 = new ArrayList<Customer>();

		Address address2 = new Address("Chandigarh","India");
		List<Customer> customerInfo2 = new ArrayList<Customer>();

		customer1.setAddress(address1);
		customer2.setAddress(address1);
		customer3.setAddress(address2);

		customerInfo1.add(customer1); // Same Address //
		customerInfo1.add(customer2); // for two customers //

		customerInfo2.add(customer3);

		address1.setCustomers(customerInfo1);  //Customers for address1 //
		address2.setCustomers(customerInfo2); // Customers for address2 //

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(address1);
		session.save(address2);
		
		Criteria cr = session.createCriteria(Customer.class);

		List<Customer> customer = (List<Customer>) cr.list();
		for (Customer s : customer) {
			System.out.println("Customer Details : " + s);
			System.out.println("Customer Address Details: "+ s.getAddress());
		}

		Criteria cr1 = session.createCriteria(Address.class);		
		List<Address> addressList = (List<Address>) cr1.add(Restrictions.eq("city", "Ambala")).list();
		for (Address s : addressList) {
			System.out.println(s.getCustomers());
		}

		session.getTransaction().commit();

		session.close();
	}

}
