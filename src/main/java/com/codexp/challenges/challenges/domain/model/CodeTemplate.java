package com.codexp.challenges.challenges.domain.model;

import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.EntryFunctionName;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateCode;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;
import com.codexp.challenges.shared.domain.model.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_templates")
@Getter
@NoArgsConstructor
public class CodeTemplate extends AbstractEntity {

	@EmbeddedId
	@AttributeOverride(
		name = "value",
		column = @Column(name = "code_template_id", nullable = false, updatable = false)
	)
	private CodeTemplateId id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "challenge_id", nullable = false))
	private ChallengeId challengeId;

	@Embedded
	@AttributeOverride(
		name = "value",
		column = @Column(name = "entry_function_name", nullable = false)
	)
	private EntryFunctionName entryFunctionName;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "language", nullable = false))
	private TemplateLanguage language;

	@Embedded
	@AttributeOverride(
		name = "value",
		column = @Column(name = "template_code", nullable = false, columnDefinition = "TEXT")
	)
	private TemplateCode templateCode;

	public static CodeTemplate create(
		CodeTemplateId id,
		ChallengeId challengeId,
		EntryFunctionName entryFunctionName,
		TemplateLanguage language,
		TemplateCode templateCode
	) {
		CodeTemplate codeTemplate = new CodeTemplate();

		codeTemplate.id = id;
		codeTemplate.challengeId = challengeId;
		codeTemplate.entryFunctionName = entryFunctionName;
		codeTemplate.language = language;
		codeTemplate.templateCode = templateCode;

		return codeTemplate;
	}

	public void updatePartially(
		Optional<EntryFunctionName> entryFunctionName,
		Optional<TemplateLanguage> language,
		Optional<TemplateCode> templateCode
	) {
		entryFunctionName.ifPresent(value -> this.entryFunctionName = value);
		language.ifPresent(value -> this.language = value);
		templateCode.ifPresent(value -> this.templateCode = value);
	}
}
