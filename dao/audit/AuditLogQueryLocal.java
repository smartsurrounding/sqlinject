package hksarg.sgil.dao.audit;

import java.util.List;
import java.util.Map;

import hksarg.sgil.form.AuditSearchForm;

public interface AuditLogQueryLocal {

	<T> T get(Class<T> clazz, int revision);

	<T> String getAuditEntityName(Class<T> clazz);

	String getTableName(String className);

	String[] getColumnNames(String className);

	<T> Map getWithMap(Class<T> clazz, int id, int revision,List<String> fieldNames);

	<T> List<Object> get(Class<T> clazz, Integer id, Integer revision);

	<T> Map getPreviousWithMap(Class<T> clazz, int id, int revision,List<String> fieldNames);

	Long count(AuditSearchForm form);

	List<Object[]> search(AuditSearchForm form, Integer firstResult, Integer maxResults);

}
