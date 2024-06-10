package hksarg.sgil.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class HighlightSearchForm implements Serializable {

	private String keyword;
	private String name;
	private Integer excludeId;
	private List<Integer> ids;

}
