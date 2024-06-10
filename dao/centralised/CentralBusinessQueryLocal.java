package hksarg.sgil.dao.centralised;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.centralised.CentralBusinessEntity;
import hksarg.sgil.form.CentralSearchForm;
import hksarg.sgil.valuetype.ELang;
import hksarg.sgil.valuetype.EModule;

public interface CentralBusinessQueryLocal extends IGenericDao<CentralBusinessEntity> {

	Long count(CentralSearchForm form);

	List<CentralBusinessEntity> search(CentralSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	Integer getMaxSeq();

}
