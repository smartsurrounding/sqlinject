package hksarg.sgil.dao.highlight;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.highlight.PTWhatsnewEntity;
import hksarg.sgil.form.HighlightSearchForm;

public interface PTWhatsnewQueryLocal extends IGenericDao<PTWhatsnewEntity> {


	Long count(HighlightSearchForm form);

	List<PTWhatsnewEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	

}
