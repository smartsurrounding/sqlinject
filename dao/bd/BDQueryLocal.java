package hksarg.sgil.dao.bd;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.BD.BDEntity;
import hksarg.sgil.entity.category.CategoryEntity;
import hksarg.sgil.form.BDSearchForm;

public interface BDQueryLocal extends IGenericDao<BDEntity> {

	Long count(BDSearchForm form);

	List<BDEntity> search(BDSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	BDEntity getByName(String name);
	
}
