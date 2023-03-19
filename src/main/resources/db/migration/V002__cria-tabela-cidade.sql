create table cidade (
    id serial not null,
    nome_cidade character varying(80) not null,
    nome_estado character varying(80) not null,

    CONSTRAINT cidade_pkey PRIMARY KEY (id)
);