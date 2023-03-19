create table forma_pagamento
(
    id        serial                not null,
    descricao character varying(60) not null,

    CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id)
);

create table grupo
(
    id   serial                not null,
    nome character varying(60) not null,

    CONSTRAINT grupo_pkey PRIMARY KEY (id)
);

create table grupo_permissao
(
    grupo_id     bigint not null,
    permissao_id bigint not null
);

create table permissao
(
    id        serial                 not null,
    descricao character varying(60)  not null,
    nome      character varying(100) not null,

    CONSTRAINT permissao_pkey PRIMARY KEY (id)
);

create table produto
(
    id             serial                not null,
    restaurante_id bigint                not null,
    nome           character varying(80) not null,
    descricao      text                  not null,
    preco          decimal(10, 2)        not null,
    ativo          boolean               not null,

    CONSTRAINT produto_pkey PRIMARY KEY (id)
);

create table restaurante
(
    id                   serial                      not null,
    cozinha_id           bigint                      not null,
    nome                 character varying(80)       not null,
    taxa_frete           decimal(10, 2)              not null,
    data_atualizacao     timestamp without time zone not null,
    data_cadastro        timestamp without time zone not null,

    endereco_cidade_id   bigint,
    endereco_cep         character varying(9),
    endereco_logradouro  character varying(100),
    endereco_numero      character varying(20),
    endereco_complemento character varying(60),
    endereco_bairro      character varying(60),

    CONSTRAINT restaurante_pkey PRIMARY KEY (id)
);

create table restaurante_forma_pagamento
(
    restaurante_id     bigint not null,
    forma_pagamento_id bigint not null
);

create table usuario
(
    id            serial                      not null,
    nome          character varying(80)       not null,
    email         character varying(255)      not null,
    senha         character varying(255)      not null,
    data_cadastro timestamp without time zone not null,

    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

create table usuario_grupo
(
    usuario_id bigint not null,
    grupo_id   bigint not null
);



alter table grupo_permissao
    add constraint fk_grupo_permissao_permissao
        foreign key (permissao_id) references permissao (id);

alter table grupo_permissao
    add constraint fk_grupo_permissao_grupo
        foreign key (grupo_id) references grupo (id);

alter table produto
    add constraint fk_produto_restaurante
        foreign key (restaurante_id) references restaurante (id);

alter table restaurante
    add constraint fk_restaurante_cozinha
        foreign key (cozinha_id) references cozinha (id);

alter table restaurante
    add constraint fk_restaurante_cidade
        foreign key (endereco_cidade_id) references cidade (id);

alter table restaurante_forma_pagamento
    add constraint fk_rest_forma_pagto_forma_pagto
        foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table restaurante_forma_pagamento
    add constraint fk_rest_forma_pagto_restaurante
        foreign key (restaurante_id) references restaurante (id);

alter table usuario_grupo
    add constraint fk_usuario_grupo_grupo
        foreign key (grupo_id) references grupo (id);

alter table usuario_grupo
    add constraint fk_usuario_grupo_usuario
        foreign key (usuario_id) references usuario (id);