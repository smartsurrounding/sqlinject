package hksarg.sgil.dao.system;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.system.ApprovalEntity;
import hksarg.sgil.form.ApprovalSearchForm;

public interface ApprovalQueryLocal extends IGenericDao<ApprovalEntity> {

	Long count(ApprovalSearchForm form);

	List<ApprovalEntity> search(ApprovalSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	List<Integer> getDequeueIds();

}
