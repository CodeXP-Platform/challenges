package com.codexp.challenges.challenges.domain.model;

import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
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
	private ChallengeId challengeId;

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
		ChallengeId challengeId,
		TestCaseInput input,
		TestCaseExpectedOutput expectedOutput,
		TestCaseHidden isHidden
	) {
		TestCase testCase = new TestCase();

		testCase.id = id;
		testCase.challengeId = challengeId;
		testCase.input = input;
		testCase.expectedOutput = expectedOutput;
		testCase.isHidden = isHidden;

		return testCase;
	}
}
