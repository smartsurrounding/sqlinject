package hksarg.sgil.dao.system;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.system.EmailQueueEntity;
import hksarg.sgil.form.EmailQueueSearchForm;

public interface EmailQueueQueryLocal extends IGenericDao<EmailQueueEntity> {

	List<Integer> getDequeueEmailIds(Integer failCount);
	Long count(EmailQueueSearchForm form);
	List<EmailQueueEntity> search(EmailQueueSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList);

}
