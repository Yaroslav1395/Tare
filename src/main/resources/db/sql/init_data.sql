INSERT INTO Zavod.roles(role)
VALUES
('DEVELOPER'),
('ADMIN');

INSERT INTO Zavod.users(username, name, surname, patronymic, password, is_deleted)
VALUES
('isakhno', 'Ярослав', 'Сахно', 'Андреевич', '$2a$10$DimBen9baU6AzZi.O2q/WusIS6OflyWa092jAIaqdfQ8GevBrta8S', 0);

INSERT INTO Zavod.user_role(user_id, role_id)
VALUES
(1,1);