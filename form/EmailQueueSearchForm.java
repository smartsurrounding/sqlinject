package hksarg.sgil.form;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class EmailQueueSearchForm implements Serializable {
	private String keyword;
}
