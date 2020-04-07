package application;

import model.dao.DepartmentDao;
import model.dao.FactoryDao;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		DepartmentDao depDao = FactoryDao.createDepartmentDao();
		
//		Create Department
		
		Department dep = new Department(0, "Operation");
		depDao.create(dep);
		
//		Delete Department
		
//		depDao.delete(5);

//	Update Department
		
		dep.setName("Desk");
		depDao.update(dep);
		System.out.println(dep.getId());
		
	}

}
