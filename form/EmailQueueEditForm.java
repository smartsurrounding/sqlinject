package hksarg.sgil.form;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class EmailQueueEditForm implements Serializable {
	
	private Integer id;

	private String subject;

	private String content;

	private String from;

	private String fromName;

	private String to;

	private String cc;

	private String bcc;

	private String replyTo;

	private Date sentDate;
	
	private Integer failCount;
}
