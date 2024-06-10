package hksarg.sgil.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CentralSearchForm implements Serializable {


	private List<Integer> ids;
	private String refcode;

}
