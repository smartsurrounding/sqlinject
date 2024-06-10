package hksarg.sgil.dao.highlight;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.highlight.VideoGalleryEntity;
import hksarg.sgil.form.HighlightSearchForm;

public interface VideoGalleryQueryLocal extends IGenericDao<VideoGalleryEntity> {

	Long count(HighlightSearchForm form);

	List<VideoGalleryEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	

}
