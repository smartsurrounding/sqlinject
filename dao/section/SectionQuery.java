package hksarg.sgil.dao.section;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.section.SectionEntity;
import hksarg.sgil.form.SectionSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(SectionQueryLocal.class)
public class SectionQuery extends GenericDao<SectionEntity> implements SectionQueryLocal {

	private ConditionResult getCondition(SectionSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			if (!StringUtil.isNullOrEmpty(form.getTitle())) {
				conditions.add("o.title LIKE :title ", "title", "%" + form.getTitle() + "%");
			}

			if (form.getMaxLevel()!= null) {
				conditions.add("o.sectionLevel<=:maxLevel", "maxLevel", form.getMaxLevel());
			}
			if (form.getParentId()!= null) {
				conditions.add("o.parentId=:parentId", "parentId", form.getParentId());
			}
		}

		return conditions;
	}

	@Override
	public Long count(SectionSearchForm form) {
		String sql = "SELECT COUNT(o) FROM SectionEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<SectionEntity> search(SectionSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM SectionEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "title":
						orderBy = "o.title";
						break;										
					case "lastUpdate":
						orderBy = "o.lastUpdate";
						break;
					default:
						continue;
				}
				if (orderBy != null) {
					orderBys.add(orderBy + " " + (sort.getSortOrder().equals(SortOrder.DESCENDING) ? "DESC" : "ASC"));
				}
			}
		}

		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.parentId ASC,o.displayOrder ASC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	@Override
	public List<SectionEntity> getSectionsByParentId(Integer parentId) {
		
		String sql = "SELECT o FROM SectionEntity o where o.deletedBy is null ";
		if(parentId!=null)
			sql+=" and o.parentId=:parentId ";
		else
			sql+=" and o.parentId is null ";
		
		sql+=" order by o.displayOrder asc ";
		
		Query q= em.createQuery(sql);
		if(parentId!=null)
			q.setParameter("parentId", parentId);
		
		return q.getResultList();
	}
	

	@Override
	public SectionEntity getSectionByFileName(String fileName) {
		
        String sql = "SELECT o FROM SectionEntity o where o.deletedBy is null  ";
		
		if(!StringUtil.isNullOrEmpty(fileName))
			sql+=" and o.fileName=:fileName ";

		logger.info("get section query :"+sql);
		
		Query q= em.createQuery(sql);

		if(!StringUtil.isNullOrEmpty(fileName))
			q.setParameter("fileName", fileName);
		
		
		if(q.getResultList()!=null && !q.getResultList().isEmpty())
		   return (SectionEntity) q.getResultList().get(0);
		
		return null;
	}

	

}
