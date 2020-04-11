package model.services;

import java.util.List;

import model.dao.SellerDao;
import model.dao.FactoryDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao dao = FactoryDao.createSellerDao();
	
	public List<Seller> findAll(){
		List<Seller> list = dao.findAll();
		return list;
	}
	
	public void saveOrUpdate(Seller obj) {
		if(obj.getId() == null) {
			dao.create(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Seller obj) {
		dao.delete(obj.getId());
	}
}
