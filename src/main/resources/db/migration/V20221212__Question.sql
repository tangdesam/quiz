CREATE SEQUENCE "QUESTION_SEQ"
INCREMENT BY 1
START WITH 101;

CREATE TABLE question (
    id BIGINT,
    exam_id VARCHAR(36),
    text VARCHAR(200),
    CONSTRAINT fk_question_exam FOREIGN KEY(exam_id) REFERENCES exam(id),
    CONSTRAINT pk_question PRIMARY KEY(id)
);

INSERT INTO question (id, exam_id, text) VALUES(1, '7b7fd257-2541-433a-bbd7-92a2ab64921c', '1 + 1 =');
INSERT INTO question (id, exam_id, text) VALUES(2, '7b7fd257-2541-433a-bbd7-92a2ab64921c', '1 x 1 =');