package hksarg.sgil.dao.successcase;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.successcase.PTSuccessCaseEntity;
import hksarg.sgil.form.SuccessCaseSearchForm;

public interface PTSuccessCaseQueryLocal extends IGenericDao<PTSuccessCaseEntity> {


	Long count(SuccessCaseSearchForm form);

	List<PTSuccessCaseEntity> search(SuccessCaseSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	

}
