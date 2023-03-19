SET session_replication_role = 'replica';

delete
from cidade;
delete
from cozinha;
delete
from estado;
delete
from forma_pagamento;
delete
from grupo;
delete
from grupo_permissao;
delete
from permissao;
delete
from produto;
delete
from restaurante;
delete
from restaurante_forma_pagamento;
delete
from restaurante_usuario_responsavel;
delete
from usuario;
delete
from usuario_grupo;
delete
from pedido;
delete
from item_pedido;
delete
from oauth2_registered_client;


SET session_replication_role = 'origin';

ALTER SEQUENCE cidade_id_seq RESTART WITH 1;
ALTER SEQUENCE cozinha_id_seq RESTART WITH 1;
ALTER SEQUENCE estado_id_seq RESTART WITH 1;
ALTER SEQUENCE forma_pagamento_id_seq RESTART WITH 1;
ALTER SEQUENCE grupo_id_seq RESTART WITH 1;
ALTER SEQUENCE permissao_id_seq RESTART WITH 1;
ALTER SEQUENCE produto_id_seq RESTART WITH 1;
ALTER SEQUENCE restaurante_id_seq RESTART WITH 1;
ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
ALTER SEQUENCE pedido_id_seq RESTART WITH 1;
ALTER SEQUENCE item_pedido_id_seq RESTART WITH 1;

insert into cozinha (nome)
values ('Tailandesa');
insert into cozinha (nome)
values ('Indiana');
insert into cozinha (nome)
values ('Argentina');
insert into cozinha (nome)
values ('Brasileira');

insert into estado (nome)
values ('Minas Gerais');
insert into estado (nome)
values ('São Paulo');
insert into estado (nome)
values ('Ceará');

insert into cidade (nome, estado_id)
values ('Uberlândia', 1);
insert into cidade (nome, estado_id)
values ('Belo Horizonte', 1);
insert into cidade (nome, estado_id)
values ('São Paulo', 2);
insert into cidade (nome, estado_id)
values ('Campinas', 2);
insert into cidade (nome, estado_id)
values ('Fortaleza', 3);

insert into restaurante (nome, ativo, aberto, taxa_frete, cozinha_id, data_cadastro, data_atualizacao,
                         endereco_cidade_id,
                         endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro)
values ('Thai Gourmet', true, true, 10, 1, (now() at time zone 'utc'), (now() at time zone 'utc'), 1, '38400-999',
        'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (nome, ativo, aberto, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values ('Thai Delivery', true, true, 9.50, 1, (now() at time zone 'utc'), (now() at time zone 'utc'));
insert into restaurante (nome, ativo, aberto, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values ('Tuk Tuk Comida Indiana', true, true, 15, 2, (now() at time zone 'utc'), (now() at time zone 'utc'));
insert into restaurante (nome, ativo, aberto, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values ('Java Steakhouse', true, true, 12, 3, (now() at time zone 'utc'), (now() at time zone 'utc'));
insert into restaurante (nome, ativo, aberto, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values ('Lanchonete do Tio Sam', true, true, 11, 4, (now() at time zone 'utc'), (now() at time zone 'utc'));
insert into restaurante (nome, ativo, aberto, taxa_frete, cozinha_id, data_cadastro, data_atualizacao)
values ('Bar da Maria', true, true, 6, 4, (now() at time zone 'utc'), (now() at time zone 'utc'));

insert into forma_pagamento (descricao)
values ('Cartão de crédito');
insert into forma_pagamento (descricao)
values ('Cartão de débito');
insert into forma_pagamento (descricao)
values ('Dinheiro');


insert into permissao (nome, descricao)
values ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (nome, descricao)
values ('EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permissao (nome, descricao)
values ('CONSULTAR_FORMAS_PAGAMENTO', 'Permite consultar formas de pagamento');
insert into permissao (nome, descricao)
values ('EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (nome, descricao)
values ('CONSULTAR_CIDADES', 'Permite consultar cidades');
insert into permissao (nome, descricao)
values ('EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (nome, descricao)
values ('CONSULTAR_ESTADOS', 'Permite consultar estados');
insert into permissao (nome, descricao)
values ('EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (nome, descricao)
values ('CONSULTAR_USUARIOS', 'Permite consultar usuários');
insert into permissao (nome, descricao)
values ('EDITAR_USUARIOS', 'Permite criar ou editar usuários');
insert into permissao (nome, descricao)
values ('CONSULTAR_RESTAURANTES', 'Permite consultar restaurantes');
insert into permissao (nome, descricao)
values ('EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (nome, descricao)
values ('CONSULTAR_PRODUTOS', 'Permite consultar produtos');
insert into permissao (nome, descricao)
values ('EDITAR_PRODUTOS', 'Permite criar ou editar produtos');
insert into permissao (nome, descricao)
values ('CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (nome, descricao)
values ('GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (nome, descricao)
values ('GERAR_RELATORIOS', 'Permite gerar relatórios');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, true, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, true, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Salada picante com carne grelhada',
        'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20,
        true, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, true, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, true, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé',
        79, true, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('T-Bone',
        'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89,
        true, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, true, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, true, 6);


insert into grupo (nome)
values ('Gerente'),
       ('Vendedor'),
       ('Secretária'),
       ('Cadastrador');

-- Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissao (grupo_id, permissao_id)
select 1, id
from permissao;

-- Adiciona permissoes no grupo do vendedor
insert into grupo_permissao (grupo_id, permissao_id)
select 2, id
from permissao
where nome like 'CONSULTAR_%';

insert into grupo_permissao (grupo_id, permissao_id)
values (2, 14);

-- Adiciona permissoes no grupo do auxiliar
insert into grupo_permissao (grupo_id, permissao_id)
select 3, id
from permissao
where nome like 'CONSULTAR_%';

-- Adiciona permissoes no grupo cadastrador
insert into grupo_permissao (grupo_id, permissao_id)
select 4, id
from permissao
where nome like '%_RESTAURANTES'
   or nome like '%_PRODUTOS';

insert into usuario (nome, email, senha, data_cadastro)
values ('João da Silva', 'joao.ger@algafood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W',
        (now() at time zone 'utc')),
       ('Maria Joaquina', 'maria.vnd@algafood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W',
        (now() at time zone 'utc')),
       ('José Souza', 'jose.aux@algafood.com.br', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W',
        (now() at time zone 'utc')),
       ('Sebastião Martins', 'sebastiao.cad@algafood.com.br',
        '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', (now() at time zone 'utc')),
       ('Manoel Lima', 'manoel.loja@gmail.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W',
        (now() at time zone 'utc')),
       ('Débora Mendonça', 'email.teste.aw+debora@gmail.com',
        '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', (now() at time zone 'utc')),
       ('Carlos Lima', 'email.teste.aw+carlos@gmail.com',
        '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', (now() at time zone 'utc'));

insert into usuario_grupo (usuario_id, grupo_id)
values (1, 1),
       (1, 2),
       (2, 2),
       (3, 3),
       (4, 4);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id)
values (1, 5),
       (3, 5);


insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, data_criacao, subtotal, taxa_frete, valor_total)
values ('f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'CRIADO', (now() at time zone 'utc'), 298.90, 10, 308.90);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status, data_criacao, subtotal, taxa_frete, valor_total)
values ('d178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', (now() at time zone 'utc'), 79, 0, 79);

insert into item_pedido (pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 6, 1, 79, 79, 'Ao ponto');


-- insert into oauth_client_details (
--     client_id, resource_ids, client_secret,
--     scope, authorized_grant_types, web_server_redirect_uri, authorities,
--     access_token_validity, refresh_token_validity, autoapprove
-- )
-- values (
--            'app-web', null, '$2y$12$w3igMjsfS5XoAYuowoH3C.54vRFWlcXSHLjX7MwF990Kc2KKKh72e',
--            'READ,WRITE', 'password', null, null,
--            60 * 60 * 6, 60 * 24 * 60 * 60, null
--        );
--
-- insert into oauth_client_details (
--     client_id, resource_ids, client_secret,
--     scope, authorized_grant_types, web_server_redirect_uri, authorities,
--     access_token_validity, refresh_token_validity, autoapprove
-- )
-- values (
--            'analytics', null, '$2y$12$fahbH37S2pyk1RPuIHKP.earzFmgAJJGo26rE.59vf4wwiiTKHnzO',
--            'READ,WRITE', 'authorization_code', 'http://localhost:8082', null,
--            null, null, true
--        );
--
-- insert into oauth_client_details (
--     client_id, resource_ids, client_secret,
--     scope, authorized_grant_types, web_server_redirect_uri, authorities,
--     access_token_validity, refresh_token_validity, autoapprove
-- )
-- values (
--            'faturamento', null, '$2y$12$fHixriC7yXX/i1/CmpnGH.RFyK/l5YapLCFOEbIktONjE8ZDykSnu',
--            'READ,WRITE', 'client_credentials', null, 'CONSULTAR_PEDIDOS,GERAR_RELATORIOS',
--            null, null, null
--        );


INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('1', 'app-backend', '2022-08-16 19:04:12', '$2a$10$97f9cT/X/htp85ELK8.IhOBpCRHAmn0Z0cYOJVscCj6esvTIFYOrS', NULL, '1', 'client_secret_basic', 'client_credentials', '', 'READ', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('2', 'app-web', '2022-08-16 19:04:12', '$2a$10$ku07Df8C0xrgJ.lId5.Cie..VZH4AReQ0wNIKaqvcMlC3MrjT6IF2', NULL, '2', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html,http://127.0.0.1:8080/authorized', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",900.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",86400.000000000]}');

INSERT INTO oauth2_registered_client
(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
VALUES('3', 'analytics', '2022-08-16 19:04:12', '$2a$10$E5f93hZ5kq97tcZVVUEtru08Eg9KBkziAdyZegNT/cfgJItimzPwW', NULL, '3', 'client_secret_basic', 'authorization_code', 'http://www.foodanalytics.local:8082', 'READ,WRITE', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",1800.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

