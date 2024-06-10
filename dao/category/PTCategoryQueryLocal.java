package hksarg.sgil.dao.category;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.category.PTCategoryEntity;
import hksarg.sgil.form.CategorySearchForm;

public interface PTCategoryQueryLocal extends IGenericDao<PTCategoryEntity> {


	Long count(CategorySearchForm form);

	List<PTCategoryEntity> search(CategorySearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	

}
