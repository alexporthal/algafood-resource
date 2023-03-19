create table estado (
    id serial not null,
    nome character varying(80) not null,

    CONSTRAINT estado_pkey PRIMARY KEY (id)
);

insert into estado (nome) select distinct nome_estado from cidade;

alter table cidade add column estado_id bigint;

update cidade set estado_id = queryestado.id
    from (select id, nome from estado) as queryestado
where queryestado.nome = cidade.nome_estado;

alter table cidade add constraint fk_cidade_estado
    foreign key (estado_id) references estado (id);

alter table cidade drop column nome_estado;

ALTER TABLE cidade ALTER COLUMN estado_id SET NOT NULL;

alter table cidade rename column nome_cidade to nome;