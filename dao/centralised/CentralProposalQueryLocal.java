package hksarg.sgil.dao.centralised;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.centralised.CentralProposalEntity;
import hksarg.sgil.form.CentralSearchForm;
import hksarg.sgil.valuetype.ELang;
import hksarg.sgil.valuetype.EModule;

public interface CentralProposalQueryLocal extends IGenericDao<CentralProposalEntity> {

	Long count(CentralSearchForm form);

	List<CentralProposalEntity> search(CentralSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	
	Integer getMaxSeq();
}
