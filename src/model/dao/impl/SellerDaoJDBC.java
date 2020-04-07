package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	Connection con;
	
	public SellerDaoJDBC(Connection con) {
		this.con = con;
	}

	@Override
	public void create(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = con.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, dep.getId());
			
			rs = st.executeQuery();
			
			Map<Integer, Department> map = new HashMap<>();
			List<Seller> list = new ArrayList<>();
			while(rs.next()) {
				Department depart = map.get(rs.getInt("DepartmentId"));
				
				if(depart == null) {
					depart = createDepartment(rs);
					map.put(rs.getInt("DepartmentId"), depart);
				}
				Seller seller = createSeller(rs, depart);
				list.add(seller);
			}
			return list;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Seller createSeller(ResultSet rs, Department dep) throws SQLException{
			int id = rs.getInt("Id");
			String name = rs.getString("Name");
			String email = rs.getString("Email");
			Date birthDate = rs.getDate("BirthDate");
			Double baseSalary = rs.getDouble("BaseSalary");
			Department department = dep;
			
			Seller seller = new Seller(id, name, email, birthDate, baseSalary, department);
			return seller;
	}
	
	private Department createDepartment(ResultSet rs) throws SQLException{
		int id = rs.getInt("DepartmentId");
		String name = rs.getString("DepName");
		
		Department dep = new Department(id, name);
		
		return dep;
	}

}
