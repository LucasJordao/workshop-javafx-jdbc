package model.services;

import java.util.List;

import model.dao.DepartmentDao;
import model.dao.FactoryDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = FactoryDao.createDepartmentDao();
	
	public List<Department> findAll(){
		List<Department> list = dao.findAll();
		return list;
	}
	
	public void saveOrUpdate(Department obj) {
		if(obj.getId() == null) {
			dao.create(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Department obj) {
		dao.delete(obj.getId());
	}
}
