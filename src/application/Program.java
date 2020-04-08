package application;

import java.util.List;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department dep = new Department(1, null);
		
		SellerDao  selDao = FactoryDao.createSellerDao();
		
//		FindByDepartment
		
		System.out.println("FindByDepartment: ");
		
		List<Seller> list = selDao.findByDepartment(dep);
	
		for(Seller sel: list) {
			System.out.println(sel); 
		}
		
		
//		FindAll
		
		System.out.println("FindAll: ");
		
		List<Seller> lista = selDao.findAll();
		
		for(Seller sel: lista) {
			System.out.println(sel);
		}
	}

}
