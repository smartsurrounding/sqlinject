package hksarg.sgil.dao.file;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.file.TempFileEntity;
import hksarg.sgil.util.StringUtil;
import hksarg.sgil.valuetype.EModule;

@Stateless
@Local(TempFileQueryLocal.class)
public class TempFileQuery extends GenericDao<TempFileEntity> implements TempFileQueryLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<TempFileEntity> search(Integer formId,EModule module,String key) { 
		String sql = "SELECT o FROM TempFileEntity o Where o.module=:module ";  
		if(formId !=null) {
			sql+=" And  o.formId =:formId ";
		}
		if(!StringUtil.isNullOrEmpty(key)) {
			sql+=" And  o.formKey =:key ";
		}
		Query q = em.createQuery(sql);
		q.setParameter("module", module);
		if(formId !=null) {
			q.setParameter("formId", formId);
		}
		if(!StringUtil.isNullOrEmpty(key)) {
			q.setParameter("key", key);
		}
        List results = q.getResultList();
		return results;
	}

	@Override
	public void delete(Integer formId,EModule module,String key, List<Integer> ids) {
		String sql="DELETE FROM TempFileEntity o Where o.module=:module  ";
		if(formId !=null) {
			sql+=" And  o.formId =:formId ";
		}
		if(!StringUtil.isNullOrEmpty(key)) {
			sql+=" And  o.formKey =:key ";
		}
		if(ids !=null && !ids.isEmpty()) {
			sql+=" And o.id NOT IN (:ids) ";
		}
		Query q = em.createQuery(sql);
		
		q.setParameter("module", module);
		if(ids !=null && !ids.isEmpty())
		   q.setParameter("ids", ids); 
		if(formId !=null) {
			q.setParameter("formId", formId);
		}
		if(!StringUtil.isNullOrEmpty(key)) {
			q.setParameter("key", key);
		}
		q.executeUpdate();
	}

	@Override
	public TempFileEntity getByKeyName(Integer formId,EModule module,String key, String fileName) {
		String sql = "SELECT o FROM TempFileEntity o Where o.module=:module  and o.fileName =:fileName ";  
		if(formId !=null) {
			sql+=" And  o.formId =:formId ";
		}
		if(!StringUtil.isNullOrEmpty(key)) {
			sql+=" And  o.formKey =:key ";
		}
		Query q = em.createQuery(sql); 
		q.setParameter("module", module);
		q.setParameter("fileName", fileName); 
		if(formId !=null) {
			q.setParameter("formId", formId);
		}
		if(!StringUtil.isNullOrEmpty(key)) {
			q.setParameter("key", key);
		}
        List results = q.getResultList();
        if(results!=null && !results.isEmpty())
        	return (TempFileEntity) results.get(0);
		return null;
	} 
	
	
	@Override
	public void update(Integer formId,EModule module,String key) {
		
		String sql="update TempFileEntity  o set o.formId =:formId  Where o.module=:module  ";
		 
		if(!StringUtil.isNullOrEmpty(key)) {
			sql+=" And  o.formKey =:key ";
		} 
		Query q = em.createQuery(sql); 
		q.setParameter("module", module); 
		if(!StringUtil.isNullOrEmpty(key)) {
			q.setParameter("key", key);
		}
		q.setParameter("formId", formId);
		q.executeUpdate();
	}
}
