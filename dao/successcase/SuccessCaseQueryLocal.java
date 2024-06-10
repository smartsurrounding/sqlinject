package hksarg.sgil.dao.successcase;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.successcase.SuccessCaseEntity;
import hksarg.sgil.form.SuccessCaseSearchForm;

public interface SuccessCaseQueryLocal extends IGenericDao<SuccessCaseEntity> {

	List<SuccessCaseEntity> search(SuccessCaseSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList);

	Long count(SuccessCaseSearchForm form);

}
