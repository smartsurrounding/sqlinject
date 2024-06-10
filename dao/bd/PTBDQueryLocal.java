package hksarg.sgil.dao.bd;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.BD.PTBDEntity;
import hksarg.sgil.form.BDSearchForm;

public interface PTBDQueryLocal extends IGenericDao<PTBDEntity> {


	Long count(BDSearchForm form);

	List<PTBDEntity> search(BDSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	

}
