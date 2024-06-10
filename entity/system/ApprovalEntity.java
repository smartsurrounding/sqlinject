package hksarg.sgil.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import hksarg.sgil.entity.BaseEntity;
import hksarg.sgil.valuetype.EApprovalStatus;
import hksarg.sgil.valuetype.EApprovalType;
import hksarg.sgil.valuetype.EModule;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "approval")
public class ApprovalEntity extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "module", length = 10)
	@Type(type = "label_enum")
	private EModule module;

	@Column(name = "record_id")
	private String recordId;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 5)
	@Type(type = "label_enum")
	private EApprovalType type;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 5)
	@Type(type = "label_enum")
	private EApprovalStatus status;

	@Column(name = "resaon", length = 5)
	private String reason;

	@Column(name = "description", length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "immediately", nullable = false, length = 1)
	@ColumnDefault("'Y'")
	@Type(type = "yes_no")
	private boolean immediately;

	@Column(name = "effect_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate;

	@Column(name = "posted_user")
	private Integer postedUserId;

	@Column(name = "approved_user")
	private Integer approvedUserId;

	@Column(name = "approved_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

}
