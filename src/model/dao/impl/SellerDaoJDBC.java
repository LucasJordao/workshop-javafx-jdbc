package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			
			if(rowsAffected > 0) {
				if(rs.next()) {
					seller.setId(rs.getInt(1));
				}
			}
			else {
				throw new DbException("unexpected error! No rows Affect");
			}
			DB.closeResultSet(rs);
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE "
					+ "Id = ?");
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void delete(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = con.prepareStatement(
					"DELETE FROM seller "
					+ "WHERE "
					+ "Id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = con.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"
					);
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep = createDepartment(rs);
				Seller seller = createSeller(rs, dep);
				return seller;
			}
			
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = con.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = createDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = createSeller(rs, dep);
			
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
