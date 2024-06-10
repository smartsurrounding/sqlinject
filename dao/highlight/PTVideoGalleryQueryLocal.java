package hksarg.sgil.dao.highlight;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.highlight.PTVideoGalleryEntity;
import hksarg.sgil.form.HighlightSearchForm;

public interface PTVideoGalleryQueryLocal extends IGenericDao<PTVideoGalleryEntity> {


	Long count(HighlightSearchForm form);

	List<PTVideoGalleryEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	

}
