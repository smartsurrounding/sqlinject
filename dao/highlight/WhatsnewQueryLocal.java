package hksarg.sgil.dao.highlight;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.highlight.WhatsnewEntity;
import hksarg.sgil.form.HighlightSearchForm;

public interface WhatsnewQueryLocal extends IGenericDao<WhatsnewEntity> {

	Long count(HighlightSearchForm form);

	List<WhatsnewEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	

}
