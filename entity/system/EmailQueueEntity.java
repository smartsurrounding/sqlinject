package hksarg.sgil.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import hksarg.sgil.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "email_queue")
public class EmailQueueEntity extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "subject")
	private String subject;

	@Lob
	@Column(name = "content", nullable = false, columnDefinition = "TEXT", length = 65535)
	private String content;

	@Column(name = "from_email")
	private String from;

	@Column(name = "from_name")
	private String fromName;

	@Column(name = "to_mail", length = 1000)
	private String to;

	@Column(name = "cc", length = 1000)
	private String cc;

	@Column(name = "bcc", length = 1000)
	private String bcc;

	@Column(name = "reply_to", length = 1000)
	private String replyTo;

	@Column(name = "sent_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentDate;
	
	@Column(name = "fail_count")
	private Integer failCount;

}
