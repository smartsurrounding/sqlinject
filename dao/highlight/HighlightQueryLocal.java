package hksarg.sgil.dao.highlight;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.category.CategoryEntity;
import hksarg.sgil.entity.highlight.HighlightEntity;
import hksarg.sgil.form.HighlightSearchForm;

public interface HighlightQueryLocal extends IGenericDao<HighlightEntity> {

	Long count(HighlightSearchForm form);

	List<HighlightEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	

}
