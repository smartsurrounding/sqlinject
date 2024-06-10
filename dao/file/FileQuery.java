package hksarg.sgil.dao.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.file.FileEntity;
import hksarg.sgil.form.FileSearchForm;
import hksarg.sgil.valuetype.EModule;
import hksarg.sgil.vo.FileVo;

@Stateless
@Local(FileQueryLocal.class)
public class FileQuery extends GenericDao<FileEntity> implements FileQueryLocal {

	private ConditionResult getCondition(FileSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			if (form.getModule() != null) {
				conditions.add("o.module = :module", "module", form.getModule());
			}
			if (form.getRecordId() != null) {
				conditions.add("o.recordId = :recordId", "recordId", form.getRecordId());
			}
			if (form.getLastUpdateUser() != null) {
				conditions.add("o.lastUpdateUser = :lastUpdateUser", "lastUpdateUser", form.getLastUpdateUser());
			}
			if (form.getViewId() != null) {
				conditions.add("o.viewId = :viewId", "viewId", form.getViewId());
			}else {
				conditions.add("o.viewId IS NULL");
			}
			if (form.getIsImg() != null) {
				conditions.add("o.isImg = :isImg", "isImg", form.getIsImg());
			}
			if (form.getRemark() != null) {
				conditions.add("o.remark LIKE :remark", "remark", "%" + form.getRemark() + "%");
			}
			if (form.getFileName() != null) {
				conditions.add("o.fileName LIKE :fileName", "fileName", "%" + form.getFileName() + "%");
			}
			if (form.getMatchedFileName() != null) {
				conditions.add("o.fileName = :matchedFileName", "matchedFileName", form.getMatchedFileName() );
			}
			if(form.getCateId()!=null) {
				conditions.add("c = :cateId", "cateId", form.getCateId());
			}
			if(form.getTechId()!=null) {
				conditions.add("t = :techId", "techId", form.getTechId());
			}
			
			if(form.getCateIds()!=null && !form.getCateIds().isEmpty()) {
				conditions.add("(c IS NULL OR c IN :cateIds)", "cateIds", form.getCateIds());
			}
			if(form.getTechIds()!=null && !form.getTechIds().isEmpty()) {
				conditions.add("(t IS NULL OR t IN :techIds)", "techIds", form.getTechIds());
			}
			
			if (form.getHideImg() != null) {
				conditions.add("o.hideImg = :hideImg", "hideImg", form.getHideImg());
			}
		}

		return conditions;
	}

	@Override
	public Long count(FileSearchForm form) {

		String sql = "SELECT COUNT(distinct o.id) FROM FileEntity o ";
		if(form!=null && (form.getCateId()!=null || (form.getCateIds()!=null && !form.getCateIds().isEmpty())) ) {
			sql+=" LEFT JOIN o.categories as c ";
		}
		if(form!=null   && (form.getTechId()!=null || (form.getTechIds()!=null && !form.getTechIds().isEmpty())) ) {
			sql+=" LEFT JOIN o.technologies as t ";
		}
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

//	@Override
//	public List<FileEntity> getDeleted(EModule module, Integer recordId) {
//
//		String sql = "SELECT o FROM FileEntity o WHERE o.module = :module AND o.recordId = :recordId AND o.deletedBy IS NOT NULL";
//		Query q = this.em.createQuery(sql);
//		q.setParameter("module", module);
//		q.setParameter("recordId", recordId);
//		return q.getResultList();
//	}

	@Override
	public List<FileEntity> search(FileSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {

		String sql = "SELECT o FROM FileEntity o ";
		if(form!=null && (form.getCateId()!=null || (form.getCateIds()!=null && !form.getCateIds().isEmpty())) ) {
			sql+=" LEFT JOIN o.categories as c ";
		}
		if(form!=null &&   (form.getTechId()!=null || (form.getTechIds()!=null && !form.getTechIds().isEmpty())) ) {
			sql+=" LEFT JOIN o.technologies as t ";
		}
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "createDate":
						orderBy = "o.createDate";
						break;
					case "recordId":
						orderBy = "o.recordId";
						break;
					case "fileName":
						orderBy = "o.fileName";
						break;
					case "remarks":
						orderBy = "o.remarks";
						break;
					case "lastUpdate":
						orderBy = "o.lastUpdate";
						break;
					case "displayOrder":
						orderBy = "o.displayOrder";
						break;
					case "hideImg":
						orderBy = "o.hideImg";
						break;
					default:
						continue;
				}
				if (orderBy != null) {
					orderBys.add(orderBy + " " + (sort.getSortOrder().equals(SortOrder.DESCENDING) ? "DESC" : "ASC"));
				}
			}
		}

		sql+=" GROUP BY o.id ";
		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.displayOrder ASC";
		}
 //       logger.info("search file query :"+sql);
		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}
	
	

	@Override
	public List<FileVo> searchVo(FileSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {

		String sql = "SELECT new hksarg.sgil.vo.FileVo(o.id,o.thumbnailId,o.fileName,o.mimeType,o.remark,o.isImg) FROM FileEntity o ";
		if(form!=null && (form.getCateId()!=null || (form.getCateIds()!=null && !form.getCateIds().isEmpty())) ) {
			sql+=" LEFT JOIN o.categories as c ";
		}
		if(form!=null &&   (form.getTechId()!=null || (form.getTechIds()!=null && !form.getTechIds().isEmpty())) ) {
			sql+=" LEFT JOIN o.technologies as t ";
		}
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "createDate":
						orderBy = "o.createDate";
						break;
					case "recordId":
						orderBy = "o.recordId";
						break;
					case "fileName":
						orderBy = "o.fileName";
						break;
					case "remarks":
						orderBy = "o.remarks";
						break;
					case "lastUpdate":
						orderBy = "o.lastUpdate";
						break;
					case "displayOrder":
						orderBy = "o.displayOrder";
						break;
					case "hideImg":
						orderBy = "o.hideImg";
						break;
					default:
						continue;
				}
				if (orderBy != null) {
					orderBys.add(orderBy + " " + (sort.getSortOrder().equals(SortOrder.DESCENDING) ? "DESC" : "ASC"));
				}
			}
		}

		sql+=" GROUP BY o.id ";
		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.displayOrder ASC";
		}
 //       logger.info("search file query :"+sql);
//		return this.search(sql, condition.getValueMap(), offset, maxResult);
		
		Query q = this.em.createQuery(sql);
		for(Entry<String,Object> entry:condition.getValueMap().entrySet()) {
			q.setParameter(entry.getKey(), entry.getValue());
		}
		
		if (offset != null)
			q.setFirstResult(offset);

		if (maxResult != null)
			q.setMaxResults(maxResult);
		List resultList = q.getResultList();
		return resultList;
	}


	@Override
	public int updateRecordId(String findViewId, Integer updateRecordId) {
		String sql = "UPDATE FileEntity o SET o.viewId=NULL, o.recordId=:updateRecordId WHERE o.viewId=:findViewId";
		Query q = em.createQuery(sql);
		q.setParameter("findViewId", findViewId);
		q.setParameter("updateRecordId", updateRecordId);

		return q.executeUpdate();

	}

	@Override
	public List<Object[]> getUsedPhotoById(Integer id) {
		String sql="SELECT o.record_type, o.id from ("
				+ "(select 'IT_FORM' as record_type,id from temp_proposal where thumbnail_id =:id and deleted_by IS NULL) "
				+ "union all "
				+ "(select 'B_FORM' as record_type,id from temp_business where thumbnail_id =:id and deleted_by IS NULL)"

				+ "union all "
				+ "(select 'CIT_FORM' as record_type,id from cent_proposal where thumbnail_id =:id and deleted_by IS NULL) "
				+ "union all "
				+ "(select 'CB_FORM' as record_type,id from cent_business where thumbnail_id =:id and deleted_by IS NULL)"
				
				+ "union all "
				+ "(select 'WIT_FORM' as record_type,id from web where thumbnail_id =:id and deleted_by  IS NULL and module =:module_1) "
				+ "union all "
				+ "(select 'WB_FORM' as record_type,id from web where thumbnail_id =:id and deleted_by  IS NULL and module =:module_2)"
				+ "union all "
				+ "(select 'HIGHLIGHT' as record_type,id from high_light where thumbnail_id =:id and deleted_by IS NULL ) "
				+ "union all "
				+ "(select 'PT_HIGHLIGHT' as record_type,id from pt_high_light where thumbnail_id =:id and deleted_by IS NULL )"
				+ "union all "
				+ "(select 'WHATS_NEW' as record_type,id from whats_new where thumbnail_id =:id and deleted_by IS NULL ) "
				+ "union all "
				+ "(select 'PT_WHATS_NEW' as record_type,id from pt_whats_new where thumbnail_id =:id and deleted_by IS NULL )"
				+ ") as o";
		
//		logger.info("id :"+id);
//		logger.info("sql :"+sql);
		Query q = em.createNativeQuery(sql);
		q.setParameter("id", id);
//		q.setParameter("deletedBy", null);
		q.setParameter("module_1", EModule.WIT_FORM.getLabel());
		q.setParameter("module_2", EModule.WB_FORM.getLabel());
		return q.getResultList();
	}

}
