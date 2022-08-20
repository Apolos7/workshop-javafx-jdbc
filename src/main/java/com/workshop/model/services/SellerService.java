package com.workshop.model.services;

import java.util.List;

import com.workshop.model.dao.DaoFactory;
import com.workshop.model.dao.SellerDao;
import com.workshop.model.entities.Seller;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        List<Seller> list = dao.findAll();
        return list;
    }

    public void saveOrUpdate(Seller Seller) {
        if (Seller.getId() != null) {
            dao.update(Seller);
        } else {
            dao.insert(Seller);
        }
    }

    public void remove(Seller Seller) {
        dao.deleteById(Seller.getId());
    }

}