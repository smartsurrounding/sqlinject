package hksarg.sgil.dao.category;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.category.CategoryEntity;
import hksarg.sgil.form.CategorySearchForm;

public interface CategoryQueryLocal extends IGenericDao<CategoryEntity> {

	public String ConcatCategoryName(List<Integer> bdId);

	Long count(CategorySearchForm form);

	List<CategoryEntity> search(CategorySearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	CategoryEntity getByName(String name);
	

	List<Integer> getIdsByCode(String code);

}
