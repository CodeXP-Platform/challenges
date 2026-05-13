package com.codexp.challenges.challenges.domain.model;

import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseExpectedOutput;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseHidden;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseInput;
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
@Table(name = "test_cases")
@Getter
@NoArgsConstructor
public class TestCase extends AbstractEntity {

	@EmbeddedId
	@AttributeOverride(
		name = "value",
		column = @Column(name = "test_case_id", nullable = false, updatable = false)
	)
	private TestCaseId id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "challenge_id", nullable = false))
	private CodeTemplateId codeTemplateId;

	@Embedded
	@AttributeOverride(
		name = "value",
		column = @Column(name = "input_data", nullable = false, columnDefinition = "TEXT")
	)
	private TestCaseInput input;

	@Embedded
	@AttributeOverride(
		name = "value",
		column = @Column(name = "expected_output", nullable = false, columnDefinition = "TEXT")
	)
	private TestCaseExpectedOutput expectedOutput;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "is_hidden", nullable = false))
	private TestCaseHidden isHidden;

	public static TestCase create(
		TestCaseId id,
		CodeTemplateId codeTemplateId,
		TestCaseInput input,
		TestCaseExpectedOutput expectedOutput,
		TestCaseHidden isHidden
	) {
		TestCase testCase = new TestCase();

		testCase.id = id;
		testCase.codeTemplateId = codeTemplateId;
		testCase.input = input;
		testCase.expectedOutput = expectedOutput;
		testCase.isHidden = isHidden;

		return testCase;
	}

	public void updatePartially(
		Optional<TestCaseInput> input,
		Optional<TestCaseExpectedOutput> expectedOutput,
		Optional<TestCaseHidden> isHidden
	) {
		input.ifPresent(value -> this.input = value);
		expectedOutput.ifPresent(value -> this.expectedOutput = value);
		isHidden.ifPresent(value -> this.isHidden = value);
	}

	public TestCaseId getId() {
		return id;
	}

	public CodeTemplateId getCodeTemplateId() {
		return codeTemplateId;
	}

	public TestCaseInput getInput() {
		return input;
	}

	public TestCaseExpectedOutput getExpectedOutput() {
		return expectedOutput;
	}

	public TestCaseHidden getIsHidden() {
		return isHidden;
	}
}
