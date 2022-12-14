CREATE TABLE options (
    id VARCHAR(36),
    text VARCHAR(100),
    question_id BIGINT,
    answer BIT,
    CONSTRAINT fk_options_question FOREIGN KEY(question_id) REFERENCES question(id),
    CONSTRAINT pk_options PRIMARY KEY(id)
);

--INSERT INTO question (id, exam_id, text) VALUES(1, '7b7fd257-2541-433a-bbd7-92a2ab64921c', '1 + 1 =');
INSERT INTO options(id, question_id, text, answer) VALUES('5f15031f-d3ed-44c0-beac-96475f55d96d', 1, '1', 0);
INSERT INTO options(id, question_id, text, answer) VALUES('41d9eaed-727d-4b79-854b-df5381fa9358', 1, '2', 1);
INSERT INTO options(id, question_id, text, answer) VALUES('c4a7c7ec-5941-47cc-9e9f-990aa6c418bc', 1, '3', 0);
INSERT INTO options(id, question_id, text, answer) VALUES('6583f360-16a7-469f-8314-0b4155d30785', 1, '4', 0);
