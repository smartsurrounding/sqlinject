package hksarg.sgil.dao.section;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.section.SectionEntity;
import hksarg.sgil.form.SectionSearchForm;

public interface SectionQueryLocal extends IGenericDao<SectionEntity> {

	
	Long count(SectionSearchForm form);

	List<SectionEntity> search(SectionSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	List<SectionEntity> getSectionsByParentId(Integer parentId);
	
	SectionEntity getSectionByFileName(String fileName);

}
