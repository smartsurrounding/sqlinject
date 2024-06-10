package hksarg.sgil.dao.pubsubmission;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.pubsubmission.PubSubmissionEntity;
import hksarg.sgil.form.PubSubmissionSearchForm;

public interface PubSubmissionQueryLocal extends IGenericDao<PubSubmissionEntity> {

	List<PubSubmissionEntity> search(PubSubmissionSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList);

	Long count(PubSubmissionSearchForm form);
	
	List<Integer> getIdsByCode(String code);

}
