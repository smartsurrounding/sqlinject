package hksarg.sgil.dao.highlight;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.highlight.PTVideoGalleryEntity;
import hksarg.sgil.form.HighlightSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(PTVideoGalleryQueryLocal.class)
public class PTVideoGalleryQuery extends GenericDao<PTVideoGalleryEntity> implements PTVideoGalleryQueryLocal {

	private ConditionResult getCondition(HighlightSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			if (!StringUtil.isNullOrEmpty(form.getKeyword())) {
				conditions.add("(o.titleEn LIKE :keyword OR o.titleTc LIKE :keyword)", "keyword", "%" + form.getKeyword() + "%");
			}
			
			

		}

		return conditions;
	}

	@Override
	public Long count(HighlightSearchForm form) {
		String sql = "SELECT COUNT(o) FROM PTVideoGalleryEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<PTVideoGalleryEntity> search(HighlightSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM PTVideoGalleryEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "titleEn":
						orderBy = "o.titleEn";
						break;
					case "titleTc":
						orderBy = "convertTC(o.titleTc)";
						break;
					case "titleSc":
						orderBy = "convertSC(o.titleSc)";
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
			sql += " ORDER BY o.newsDate DESC, o.lastUpdate DESC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	

}
