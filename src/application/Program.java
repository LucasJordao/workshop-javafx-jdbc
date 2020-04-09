package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.dao.FactoryDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args){
		Department dep = new Department(1, null);
		
		SellerDao  selDao = FactoryDao.createSellerDao();
		
//		FindByDepartment
		
		System.out.println("FindByDepartment: ");
		
		List<Seller> list = selDao.findByDepartment(dep);
	
		for(Seller sel: list) {
			System.out.println(sel); 
		}
		
		
//		FindAll
		
		System.out.println("\n\nFindAll: ");
		
		List<Seller> lista = selDao.findAll();
		
		for(Seller sel: lista) {
			System.out.println(sel);
		}
		
//		FindById
		
		System.out.println("\n\nFindById: ");
		
		Seller seller = selDao.findById(1);
		System.out.println(seller);
		
//		Delete
		
		System.out.println("\n\nDelete: ");
//		selDao.delete(4);
		System.out.println("Okay");
		
//		Update
		
		System.out.println("\n\nUpdate: ");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Seller sell = null;
		try {
			sell = new Seller(1, "Lucas", "Lucas@hotmail.com", sdf.parse("12/11/2001"), 2999.0, new Department(2, null));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		selDao.update(sell);
	}
}
