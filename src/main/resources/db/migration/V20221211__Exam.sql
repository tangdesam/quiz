CREATE TABLE exam (
    id VARCHAR(36),
    subject VARCHAR(50),
    CONSTRAINT pk_exam PRIMARY KEY(id)
);

INSERT INTO exam(id, subject) VALUES('7b7fd257-2541-433a-bbd7-92a2ab64921c', 'Mathematic Semester 1 First Exam');
INSERT INTO exam(id, subject) VALUES('84e40aab-8ec3-4276-bb10-7fe47ff2de73', 'Science Semester 1 First Exam');