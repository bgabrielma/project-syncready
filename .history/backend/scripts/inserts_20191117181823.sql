USE syncready_database;

INSERT INTO Type_Of_Alert (`uuid_type_of_alert`, `type_of_alert_designation`)
VALUES 
(UUID(), 'Informação'),
(UUID(), 'Importante'),
(UUID(), 'Urgente');

INSERT INTO Type_Of_User (`uuid_type_of_users`, `type`)
VALUES 
(UUID(), 'Cliente'),
(UUID(), 'Técnico'),
(UUID(), 'Administrador'),
(UUID(), 'Entidade');