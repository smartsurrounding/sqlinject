package hksarg.sgil.dao.highlight;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.highlight.PTHighlightEntity;
import hksarg.sgil.form.HighlightSearchForm;

public interface PTHighlightQueryLocal extends IGenericDao<PTHighlightEntity> {


	Long count(HighlightSearchForm form);

	List<PTHighlightEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	

}
