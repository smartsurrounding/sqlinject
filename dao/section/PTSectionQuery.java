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
import hksarg.sgil.entity.section.PTSectionEntity;
import hksarg.sgil.entity.section.SectionEntity;
import hksarg.sgil.form.SectionSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(PTSectionQueryLocal.class)
public class PTSectionQuery extends GenericDao<PTSectionEntity> implements PTSectionQueryLocal {

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
		String sql = "SELECT COUNT(o) FROM PTSectionEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<PTSectionEntity> search(SectionSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM PTSectionEntity o";
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
	public PTSectionEntity getSectionByFileName(String fileName) {
		
        String sql = "SELECT o FROM PTSectionEntity o where o.deletedBy is null  ";
		
		if(!StringUtil.isNullOrEmpty(fileName))
			sql+=" and o.fileName=:fileName ";

		logger.info("get section query :"+sql);
		
		Query q= em.createQuery(sql);

		if(!StringUtil.isNullOrEmpty(fileName))
			q.setParameter("fileName", fileName);
		
		
		if(q.getResultList()!=null && !q.getResultList().isEmpty())
		   return (PTSectionEntity) q.getResultList().get(0);
		
		return null;
	}

	

}
