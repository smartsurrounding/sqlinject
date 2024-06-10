package hksarg.sgil.dao.system;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.system.LoginLogEntity;
import hksarg.sgil.form.LoginLogSearchForm;

public interface LoginLogQueryLocal extends IGenericDao<LoginLogEntity> {

	Long count(LoginLogSearchForm form);

	List<LoginLogEntity> search(LoginLogSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

}
