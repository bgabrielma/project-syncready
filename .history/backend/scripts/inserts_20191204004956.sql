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

INSERT INTO Ticket_Options (`uuid_ticket_options`, `ticket_option_designation`)
VALUES 
(UUID(), 'Técnico pendente'),
(UUID(), 'Em funcionamento'),
(UUID(), 'Fechada'),
(UUID(), 'Avaliada'),
(UUID(), 'Suspensa'),
(UUID(), 'Cancelada');´

INSERT INTO 'Companies' (`uuid_company`, 'name', 'company_address', 'company_telephone', 'company_email')
VALUES
(UUID, 'Fnac', 'Rua da fnac', '92312412', 'office@fnac.pt')