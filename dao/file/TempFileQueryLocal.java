package hksarg.sgil.dao.file;

import java.util.List;

import hksarg.sgil.dao.IGenericDao;
import hksarg.sgil.entity.file.TempFileEntity;
import hksarg.sgil.valuetype.EModule;

public interface TempFileQueryLocal extends IGenericDao<TempFileEntity> {
 

	List<TempFileEntity> search(Integer id,EModule module,String key); 
	void delete(Integer id,EModule module,String key, List<Integer> ids);
	TempFileEntity getByKeyName(Integer id,EModule module,String key,String fileName);
	void update(Integer formId,EModule module,String key);
}
