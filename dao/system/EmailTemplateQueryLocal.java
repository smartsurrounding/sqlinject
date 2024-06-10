package hksarg.sgil.dao.system;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.system.EmailTemplateEntity;
import hksarg.sgil.form.EmailTemplateSearchForm;

public interface EmailTemplateQueryLocal extends IGenericDao<EmailTemplateEntity> {

	Long count(EmailTemplateSearchForm form);

	List<EmailTemplateEntity> search(EmailTemplateSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList);

	EmailTemplateEntity getByCode(String code);

}
