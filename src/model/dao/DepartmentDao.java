package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void create(Department department);
	void delete(Integer id);
	void update(Department department);
	Department findById(Integer id);
	List<Department> findAll();
	
}
