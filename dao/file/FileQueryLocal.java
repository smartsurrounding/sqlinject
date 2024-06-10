package hksarg.sgil.dao.file;

import java.util.List;

import org.primefaces.model.SortMeta;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.file.FileEntity;
import hksarg.sgil.form.FileSearchForm;
import hksarg.sgil.vo.FileVo;

public interface FileQueryLocal extends IGenericDao<FileEntity> {

	Long count(FileSearchForm form);

	List<FileVo> searchVo(FileSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);
	List<FileEntity> search(FileSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList);

	int updateRecordId(String findViewId, Integer updateRecordId);

//	List<FileEntity> getDeleted(EModule module, Integer integer);
	
	List<Object[]> getUsedPhotoById(Integer id);

}
