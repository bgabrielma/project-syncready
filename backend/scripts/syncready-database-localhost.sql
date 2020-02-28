-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 27-Fev-2020 às 18:57
-- Versão do servidor: 10.4.6-MariaDB
-- versão do PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `syncready_database`
--
CREATE DATABASE IF NOT EXISTS `syncready_database` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `syncready_database`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `alerts`
--

DROP TABLE IF EXISTS `alerts`;
CREATE TABLE `alerts` (
  `pk_alerts` int(11) NOT NULL,
  `uuid_alerts` varchar(255) NOT NULL DEFAULT uuid(),
  `name_alerts` varchar(255) NOT NULL,
  `alert_text` text DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `until_at` datetime NOT NULL,
  `Type_Of_Alert_uuid_type_of_alert` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `alerts`
--

INSERT INTO `alerts` (`pk_alerts`, `uuid_alerts`, `name_alerts`, `alert_text`, `created_at`, `until_at`, `Type_Of_Alert_uuid_type_of_alert`) VALUES
(1, '891225a8-5989-11ea-bd76-30d16bf72dc4', 'Eu sou um alerta, clique-me!', '1234567891011121314151617181920', '2020-02-27 17:49:43', '2020-02-27 17:49:43', '1e3348de-1f8a-11ea-a8e5-0242ac1d0002');

-- --------------------------------------------------------

--
-- Estrutura da tabela `companies`
--

DROP TABLE IF EXISTS `companies`;
CREATE TABLE `companies` (
  `pk_companies` int(11) NOT NULL,
  `uuid_company` varchar(255) NOT NULL DEFAULT uuid(),
  `name` varchar(100) NOT NULL,
  `company_address` varchar(100) NOT NULL,
  `company_telephone` varchar(100) NOT NULL,
  `company_email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `companies`
--

INSERT INTO `companies` (`pk_companies`, `uuid_company`, `name`, `company_address`, `company_telephone`, `company_email`) VALUES
(1, '1e39911a-1f8a-11ea-a8e5-0242ac1d0002', 'SyncReady', 'SyncReady - Portugal', '999999999', 'entity@syncready.pt'),
(3, 'ba588643-5988-11ea-bd76-30d16bf72dc4', 'Worten', 'Rua Fernando Lopes Graça nº17 1ºA, 2725-540 MEM MARTINS (Portugal)', '967451777', 'wortenempresas@worten.pt');

-- --------------------------------------------------------

--
-- Estrutura da tabela `datasheet`
--

DROP TABLE IF EXISTS `datasheet`;
CREATE TABLE `datasheet` (
  `pk_datasheet` int(11) NOT NULL,
  `uuid_datasheet` varchar(255) NOT NULL DEFAULT uuid(),
  `description` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `designation` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `evaluations`
--

DROP TABLE IF EXISTS `evaluations`;
CREATE TABLE `evaluations` (
  `pk_evaluation` int(11) NOT NULL,
  `uuid_evaluation` varchar(255) NOT NULL,
  `evaluation_num_stars` int(5) NOT NULL,
  `evaluation_text` text NOT NULL,
  `user_giver` varchar(255) NOT NULL,
  `Rooms_uuid_room` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `pk_message` int(11) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL,
  `update_at` datetime NOT NULL,
  `uuid_message` varchar(255) NOT NULL DEFAULT uuid(),
  `Status_Message_uuid_status_message` varchar(100) NOT NULL,
  `isImage` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `messages_has_users`
--

DROP TABLE IF EXISTS `messages_has_users`;
CREATE TABLE `messages_has_users` (
  `Messages_uuid_message` varchar(255) NOT NULL DEFAULT uuid(),
  `Users_pk_uuid` varchar(255) NOT NULL,
  `Rooms_uuid_room` varchar(255) NOT NULL,
  `sent_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `pk_permission` int(11) NOT NULL,
  `uuid_permission` varchar(100) NOT NULL,
  `permission_designation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `rooms`
--

DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `pk_rooms` int(11) NOT NULL,
  `uuid_room` varchar(255) NOT NULL DEFAULT uuid(),
  `room_code` varchar(255) NOT NULL,
  `name_room` varchar(100) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `until_at` datetime NOT NULL,
  `Datasheet_uuid_datasheet` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT 'default_group_image.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `status_message`
--

DROP TABLE IF EXISTS `status_message`;
CREATE TABLE `status_message` (
  `pk_status_message` int(11) NOT NULL,
  `uuid_status_message` varchar(100) NOT NULL DEFAULT uuid(),
  `designation` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `status_message`
--

INSERT INTO `status_message` (`pk_status_message`, `uuid_status_message`, `designation`) VALUES
(1, '6bf609b3-3d12-11ea-993c-0242ac140002', 'Enviada'),
(2, '6bf60c15-3d12-11ea-993c-0242ac140002', 'Não enviada'),
(3, '6bf613d4-3d12-11ea-993c-0242ac140002', 'Lida'),
(4, '6bf6143c-3d12-11ea-993c-0242ac140002', 'Não Lida');

-- --------------------------------------------------------

--
-- Estrutura da tabela `status_room`
--

DROP TABLE IF EXISTS `status_room`;
CREATE TABLE `status_room` (
  `pk_status_room` int(11) NOT NULL,
  `uuid_status_room` varchar(45) NOT NULL,
  `status_room_designation` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tickets`
--

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `pk_ticket` int(11) NOT NULL,
  `uuid_ticket` varchar(100) NOT NULL DEFAULT uuid(),
  `Users_pk_uuid` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `Ticket_Options_uuid_ticket_options` varchar(100) NOT NULL,
  `Rooms_uuid_room` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `ticket_options`
--

DROP TABLE IF EXISTS `ticket_options`;
CREATE TABLE `ticket_options` (
  `id_ticket_options` int(11) NOT NULL,
  `uuid_ticket_options` varchar(100) NOT NULL,
  `ticket_option_designation` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `ticket_options`
--

INSERT INTO `ticket_options` (`id_ticket_options`, `uuid_ticket_options`, `ticket_option_designation`) VALUES
(1, '1e3816e3-1f8a-11ea-a8e5-0242ac1d0002', 'Técnico pendente'),
(2, '1e381895-1f8a-11ea-a8e5-0242ac1d0002', 'Em funcionamento'),
(3, '1e381f6d-1f8a-11ea-a8e5-0242ac1d0002', 'Fechada'),
(4, '1e381fae-1f8a-11ea-a8e5-0242ac1d0002', 'Avaliada'),
(5, '1e381fcc-1f8a-11ea-a8e5-0242ac1d0002', 'Suspensa'),
(6, '1e381fe9-1f8a-11ea-a8e5-0242ac1d0002', 'Cancelada');

-- --------------------------------------------------------

--
-- Estrutura da tabela `type_of_alert`
--

DROP TABLE IF EXISTS `type_of_alert`;
CREATE TABLE `type_of_alert` (
  `pk_type_of_alert` int(11) NOT NULL,
  `uuid_type_of_alert` varchar(255) NOT NULL,
  `type_of_alert_designation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `type_of_alert`
--

INSERT INTO `type_of_alert` (`pk_type_of_alert`, `uuid_type_of_alert`, `type_of_alert_designation`) VALUES
(1, '1e3348de-1f8a-11ea-a8e5-0242ac1d0002', 'Informação'),
(2, '1e334b94-1f8a-11ea-a8e5-0242ac1d0002', 'Importante'),
(3, '1e3353bc-1f8a-11ea-a8e5-0242ac1d0002', 'Urgente');

-- --------------------------------------------------------

--
-- Estrutura da tabela `type_of_user`
--

DROP TABLE IF EXISTS `type_of_user`;
CREATE TABLE `type_of_user` (
  `pk_type_of_user` int(11) NOT NULL,
  `uuid_type_of_users` varchar(255) NOT NULL,
  `type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `type_of_user`
--

INSERT INTO `type_of_user` (`pk_type_of_user`, `uuid_type_of_users`, `type`) VALUES
(1, '1e35c91a-1f8a-11ea-a8e5-0242ac1d0002', 'Cliente'),
(2, '1e35cb49-1f8a-11ea-a8e5-0242ac1d0002', 'Técnico'),
(3, '1e35d267-1f8a-11ea-a8e5-0242ac1d0002', 'Entidade'),
(4, '1e35d2ad-1f8a-11ea-a8e5-0242ac1d0002', 'Administrador');

-- --------------------------------------------------------

--
-- Estrutura da tabela `type_of_user_has_alerts`
--

DROP TABLE IF EXISTS `type_of_user_has_alerts`;
CREATE TABLE `type_of_user_has_alerts` (
  `Type_Of_User_uuid_type_of_users` varchar(255) NOT NULL,
  `Alerts_uuid_alerts` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `type_of_user_has_alerts`
--

INSERT INTO `type_of_user_has_alerts` (`Type_Of_User_uuid_type_of_users`, `Alerts_uuid_alerts`) VALUES
('1e35c91a-1f8a-11ea-a8e5-0242ac1d0002', '891225a8-5989-11ea-bd76-30d16bf72dc4'),
('1e35cb49-1f8a-11ea-a8e5-0242ac1d0002', '891225a8-5989-11ea-bd76-30d16bf72dc4'),
('1e35d2ad-1f8a-11ea-a8e5-0242ac1d0002', '891225a8-5989-11ea-bd76-30d16bf72dc4');

-- --------------------------------------------------------

--
-- Estrutura da tabela `type_of_user_has_permissions`
--

DROP TABLE IF EXISTS `type_of_user_has_permissions`;
CREATE TABLE `type_of_user_has_permissions` (
  `Type_Of_User_uuid_type_of_users` varchar(255) NOT NULL,
  `Permissions_uuid_permission` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `pk_user` int(11) NOT NULL,
  `pk_uuid` varchar(255) NOT NULL DEFAULT uuid(),
  `nickname` varchar(100) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telephone` varchar(100) NOT NULL,
  `citizencard` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `update_at` datetime NOT NULL DEFAULT current_timestamp(),
  `Type_Of_User_uuid_type_of_users` varchar(255) DEFAULT '1',
  `image` varchar(255) DEFAULT 'default_user_image.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `users`
--

INSERT INTO `users` (`pk_user`, `pk_uuid`, `nickname`, `fullname`, `address`, `email`, `telephone`, `citizencard`, `password`, `created_at`, `update_at`, `Type_Of_User_uuid_type_of_users`, `image`) VALUES
(1, '944e4117-19f2-11ea-b615-30d16bf72dc4', 'syncready', 'SyncReady Company', 'SyncReady - Portugal', 'admin@syncready.pt', '999999999', '191994073ZV4', '63a9f0ea7bb98050796b649e85481845', '2019-12-15 22:28:03', '2019-12-15 22:28:03', '1e35d267-1f8a-11ea-a8e5-0242ac1d0002', 'default_user_image.png'),
(6, 'ba5a0c2d-5988-11ea-bd76-30d16bf72dc4', 'Worten Inc', 'Worten', 'Rua Fernando Lopes Graça nº17 1ºA, 2725-540 MEM MARTINS (Portugal)', 'wortenempresas@worten.pt', '967451777', '17069643 0 ZX7', '81a3e7f377d5346064f377c6b3e7de3e', '2020-02-27 17:43:56', '2020-02-27 17:43:56', '1e35d267-1f8a-11ea-a8e5-0242ac1d0002', 'default_user_image.png'),
(7, 'ec008be7-5988-11ea-bd76-30d16bf72dc4', 'joaquimteixeira', 'Joaquim Manuel da Silva Teixeira', 'Rua Fernando Lopes Graça nº17 1ºA, 2725-540 MEM MARTINS (Portugal)', 'joaquimteixeira@syncready.pt', '967451777', '19619360 5 ZX7', 'ef19699faddb1935b26580174e8e06fc', '2020-02-27 17:45:19', '2020-02-27 17:47:17', '1e35cb49-1f8a-11ea-a8e5-0242ac1d0002', 'default_user_image.png');

-- --------------------------------------------------------

--
-- Estrutura da tabela `users_has_companies`
--

DROP TABLE IF EXISTS `users_has_companies`;
CREATE TABLE `users_has_companies` (
  `Users_pk_uuid` varchar(255) NOT NULL,
  `Companies_uuid_company` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `users_has_companies`
--

INSERT INTO `users_has_companies` (`Users_pk_uuid`, `Companies_uuid_company`) VALUES
('944e4117-19f2-11ea-b615-30d16bf72dc4', '1e39911a-1f8a-11ea-a8e5-0242ac1d0002'),
('ba5a0c2d-5988-11ea-bd76-30d16bf72dc4', 'ba588643-5988-11ea-bd76-30d16bf72dc4'),
('ec008be7-5988-11ea-bd76-30d16bf72dc4', '1e39911a-1f8a-11ea-a8e5-0242ac1d0002');

-- --------------------------------------------------------

--
-- Estrutura da tabela `users_has_rooms`
--

DROP TABLE IF EXISTS `users_has_rooms`;
CREATE TABLE `users_has_rooms` (
  `Users_pk_uuid` varchar(255) NOT NULL,
  `Rooms_uuid_room` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `users_has_rooms`
--

INSERT INTO `users_has_rooms` (`Users_pk_uuid`, `Rooms_uuid_room`) VALUES
('944e4117-19f2-11ea-b615-30d16bf72dc4', '61ea5625-5989-11ea-bd76-30d16bf72dc4');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `alerts`
--
ALTER TABLE `alerts`
  ADD PRIMARY KEY (`pk_alerts`,`uuid_alerts`);

--
-- Índices para tabela `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`pk_companies`,`uuid_company`);

--
-- Índices para tabela `datasheet`
--
ALTER TABLE `datasheet`
  ADD PRIMARY KEY (`pk_datasheet`,`uuid_datasheet`),
  ADD KEY `uuid_datasheet_idx1` (`uuid_datasheet`);

--
-- Índices para tabela `evaluations`
--
ALTER TABLE `evaluations`
  ADD PRIMARY KEY (`pk_evaluation`,`uuid_evaluation`);

--
-- Índices para tabela `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`pk_message`,`uuid_message`,`Status_Message_uuid_status_message`);

--
-- Índices para tabela `messages_has_users`
--
ALTER TABLE `messages_has_users`
  ADD PRIMARY KEY (`Messages_uuid_message`,`Users_pk_uuid`);

--
-- Índices para tabela `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`pk_permission`,`uuid_permission`);

--
-- Índices para tabela `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`pk_rooms`,`uuid_room`,`room_code`),
  ADD KEY `name_room_UNIQUE` (`name_room`);

--
-- Índices para tabela `status_message`
--
ALTER TABLE `status_message`
  ADD PRIMARY KEY (`pk_status_message`,`uuid_status_message`),
  ADD KEY `uuid_status_message_idx` (`uuid_status_message`);

--
-- Índices para tabela `status_room`
--
ALTER TABLE `status_room`
  ADD PRIMARY KEY (`pk_status_room`,`uuid_status_room`),
  ADD KEY `status_room_idx` (`status_room_designation`);

--
-- Índices para tabela `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`pk_ticket`,`uuid_ticket`);

--
-- Índices para tabela `ticket_options`
--
ALTER TABLE `ticket_options`
  ADD PRIMARY KEY (`id_ticket_options`,`uuid_ticket_options`);

--
-- Índices para tabela `type_of_alert`
--
ALTER TABLE `type_of_alert`
  ADD PRIMARY KEY (`pk_type_of_alert`,`uuid_type_of_alert`);

--
-- Índices para tabela `type_of_user`
--
ALTER TABLE `type_of_user`
  ADD PRIMARY KEY (`pk_type_of_user`,`uuid_type_of_users`);

--
-- Índices para tabela `type_of_user_has_alerts`
--
ALTER TABLE `type_of_user_has_alerts`
  ADD PRIMARY KEY (`Type_Of_User_uuid_type_of_users`,`Alerts_uuid_alerts`);

--
-- Índices para tabela `type_of_user_has_permissions`
--
ALTER TABLE `type_of_user_has_permissions`
  ADD PRIMARY KEY (`Type_Of_User_uuid_type_of_users`,`Permissions_uuid_permission`);

--
-- Índices para tabela `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`pk_user`,`pk_uuid`),
  ADD UNIQUE KEY `pk_uuid_UNIQUE` (`pk_uuid`);

--
-- Índices para tabela `users_has_companies`
--
ALTER TABLE `users_has_companies`
  ADD PRIMARY KEY (`Users_pk_uuid`,`Companies_uuid_company`);

--
-- Índices para tabela `users_has_rooms`
--
ALTER TABLE `users_has_rooms`
  ADD PRIMARY KEY (`Users_pk_uuid`,`Rooms_uuid_room`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `alerts`
--
ALTER TABLE `alerts`
  MODIFY `pk_alerts` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `companies`
--
ALTER TABLE `companies`
  MODIFY `pk_companies` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `datasheet`
--
ALTER TABLE `datasheet`
  MODIFY `pk_datasheet` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `evaluations`
--
ALTER TABLE `evaluations`
  MODIFY `pk_evaluation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `messages`
--
ALTER TABLE `messages`
  MODIFY `pk_message` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `rooms`
--
ALTER TABLE `rooms`
  MODIFY `pk_rooms` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `status_message`
--
ALTER TABLE `status_message`
  MODIFY `pk_status_message` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `status_room`
--
ALTER TABLE `status_room`
  MODIFY `pk_status_room` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tickets`
--
ALTER TABLE `tickets`
  MODIFY `pk_ticket` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `ticket_options`
--
ALTER TABLE `ticket_options`
  MODIFY `id_ticket_options` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `type_of_alert`
--
ALTER TABLE `type_of_alert`
  MODIFY `pk_type_of_alert` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `type_of_user`
--
ALTER TABLE `type_of_user`
  MODIFY `pk_type_of_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT de tabela `users`
--
ALTER TABLE `users`
  MODIFY `pk_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;