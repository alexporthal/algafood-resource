alter table pedido add codigo varchar(36) not null default uuid_in(md5(random()::text || random()::text)::cstring);
alter table pedido add constraint uk_pedido_codigo unique (codigo);