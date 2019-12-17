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
(UUID(), 'Entidade'),
(UUID(), 'Administrador');

INSERT INTO Status_Message (`uuid_status_message`, `designation`)
VALUES 
(UUID(), 'Enviada'),
(UUID(), 'Não enviada'),
(UUID(), 'Lida'),
(UUID(), 'Não Lida');

INSERT INTO Status_Room (`uuid_status_room`, `status_room_designation`)
VALUES 
(UUID(), 'Técnico pendente'),
(UUID(), 'Em funcionamento'),
(UUID(), 'Fechada'),
(UUID(), 'Avaliada'),
(UUID(), 'Suspensa'),
(UUID(), 'Cancelada');