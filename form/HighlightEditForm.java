package hksarg.sgil.form;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class HighlightEditForm implements Serializable {

	private Integer id;
	
	private Integer thumbnailId;
	
	private Integer tcThumbnailId;
	
	private Integer scThumbnailId;

	@NotBlank(message = "{highlight.titleEn.required}")
	@Size(max = 255, message = "{highlight.titleEn.length}")
	private String titleEn;
	
	@NotBlank(message = "{highlight.titleTc.required}")
	@Size(max = 255, message = "{highlight.titleTc.length}")
	private String titleTc;
	
	@NotBlank(message = "{highlight.titleSc.required}")
	@Size(max = 255, message = "{highlight.titleSc.length}")
	private String titleSc;
	
	@NotBlank(message = "{highlight.contentEn.required}")
	private String contentEn;
	
	@NotBlank(message = "{highlight.contentTc.required}")
	private String contentTc;
	
	@NotBlank(message = "{highlight.contentSc.required}")
	private String contentSc;
	private boolean posted;
	
	private Date newsDate;
	
//	private String dateEn;
//	
//	private String dateTc;
//
//	private String dateSc;
}
