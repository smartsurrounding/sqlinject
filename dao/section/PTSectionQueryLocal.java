package hksarg.sgil.dao.section;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.section.PTSectionEntity;
import hksarg.sgil.entity.section.SectionEntity;
import hksarg.sgil.form.SectionSearchForm;

public interface PTSectionQueryLocal extends IGenericDao<PTSectionEntity> {

	Long count(SectionSearchForm form);

	List<PTSectionEntity> search(SectionSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	
	PTSectionEntity getSectionByFileName(String fileName);

}
