package hksarg.sgil.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import hksarg.sgil.entity.BaseEntity;
import hksarg.sgil.valuetype.ESession;
import lombok.Data;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "user_privilege")
public class UserPrivilegeEntity extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "session")
	@Type(type = "label_enum")
	private ESession session;
}
