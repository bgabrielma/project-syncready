USE syncready_database;

INSERT INTO Type_Of_Alert (`uuid_type_of_alert`, `type_of_alert_designation`)
VALUES 
(UUID(), 'INFO'),
(UUID(), 'IMPORTANT'),
(UUID(), 'DANGER');