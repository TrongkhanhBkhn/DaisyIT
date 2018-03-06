package com.daisyit.db.abstraction;
import com.daisyit.entity.CateringId;

import java.util.List;

import com.daisyit.entity.Catering;
import com.daisyit.db.abstraction.DAOException;
public interface CateringDAO {
	
	Catering getCatering(CateringId cateringId) throws DAOException;
	
	List<Catering> getAllCaterings() throws DAOException;

	Catering deleteCatering(Catering catering) throws DAOException;
	
	Catering addCatering(Catering catering) throws DAOException;
	
	List<Catering> addMultiCaterings(List<Catering> caterings)  throws DAOException;
}