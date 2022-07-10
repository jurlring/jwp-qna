package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.utils.fixture.QuestionFixture;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        Question expect = QuestionFixture.Q1;
        Question actual = questions.save(expect);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("식별자와 삭제되지 않은 조건으로 조회할시 정삭적으로 조회된다.")
    void findByIdAndDeletedFalse_not_deleted() {
        Question expect1 = QuestionFixture.Q1;
        questions.save(expect1);

        Optional<Question> found = questions.findByIdAndDeletedFalse(expect1.getId());

        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("식별자와 삭제되지 않은 조건으로 데이터가 조회되지 않는다.")
    void findByIdAndDeletedFalse_deleted() {
        Question expect1 = QuestionFixture.Q1;
        questions.save(expect1);

        expect1.setDeleted(true);

        Optional<Question> found = questions.findByIdAndDeletedFalse(expect1.getId());

        assertThat(found).isEmpty();
    }
}