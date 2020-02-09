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

INSERT INTO Ticket_Options (`uuid_ticket_options`, `ticket_option_designation`)
VALUES 
(UUID(), 'Técnico pendente'),
(UUID(), 'Em funcionamento'),
(UUID(), 'Fechada'),
(UUID(), 'Avaliada'),
(UUID(), 'Suspensa'),
(UUID(), 'Cancelada');

/* INSTRUÇÕES PARA PRIMEIRA ENTRADA DO SISTEMA*/

/* CRIAR EMPRESA */

INSERT INTO Companies (`uuid_company`, `name`, `company_address`, `company_telephone`, `company_email`)
VALUES
(UUID(), 'SyncReady', 'SyncReady - Portugal', '999999999', 'entity@syncready.pt');

/* IR AO PHPMYADMIN */

/* CRIAR UM UTILIZADOR DO TIPO ENTIDADE CHAMADO SYNCREADY */
INSERT INTO `Users` (`pk_user`, `pk_uuid`, `nickname`, `fullname`, `address`, `email`, `telephone`, `citizencard`, `password`, `created_at`, `update_at`, `Type_Of_User_uuid_type_of_users`) 
VALUES (NULL, '944e4117-19f2-11ea-b615-30d16bf72dc4', 'syncready', 'SyncReady Company', 'SyncReady - Portugal', 'entity@syncready.pt', 
'999999999', '191994073ZV4', 'syncready', current_timestamp(), current_timestamp(), ATENÇÃO - SUBSTITUIR COM O UUID DO TYPE_OF_USER QUE SEJA ENTIDADE);

/* CRIAR UM REGISTO NA TABELA Users_has_Companies colocando o UUID da syncready com o UUID da companie syncready*/