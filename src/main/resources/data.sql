INSERT INTO task (name, begin_date, end_date) VALUES
  ('Построить дом', PARSEDATETIME('01.07.2018', 'dd.MM.yyyy'), PARSEDATETIME('10.07.2018', 'dd.MM.yyyy')),
  ('Вскопать землю', PARSEDATETIME('03.07.2018', 'dd.MM.yyyy'), PARSEDATETIME('21.07.2018', 'dd.MM.yyyy'));
INSERT INTO participant (id, name) VALUES
  (1, 'Светлана'),
  (2, 'Серей'),
  (3, 'Андрей'),
  (4, 'Роман'),
  (5, 'Владимир');

INSERT INTO tasks_participants (task_id, participant_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 4),
  (2, 5);