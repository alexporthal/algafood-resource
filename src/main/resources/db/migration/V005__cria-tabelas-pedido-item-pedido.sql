create table pedido
(
    id                   serial                      not null,
    subtotal             numeric(10, 2)              not null,
    taxa_frete           numeric(10, 2)              not null,
    valor_total          numeric(10, 2)              not null,

    restaurante_id       integer                     not null,
    usuario_cliente_id   integer                     not null,
    forma_pagamento_id   integer                     not null,

    endereco_cidade_id   integer                     not null,
    endereco_cep         varchar(9)                  not null,
    endereco_logradouro  varchar(100)                not null,
    endereco_numero      varchar(20)                 not null,
    endereco_complemento varchar(60)                 null,
    endereco_bairro      varchar(60)                 not null,

    status               varchar(10)                 not null,
    data_criacao         timestamp without time zone not null,
    data_confirmacao     timestamp without time zone null,
    data_cancelamento    timestamp without time zone null,
    data_entrega         timestamp without time zone null,

    primary key (id),

    constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
    constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id)
);

create table item_pedido
(
    id             serial         not null,
    quantidade     integer        not null,
    preco_unitario numeric(10, 2) not null,
    preco_total    numeric(10, 2) not null,
    observacao     varchar(255)   null,
    pedido_id      integer        not null,
    produto_id     integer        not null,

    primary key (id),
    constraint uk_item_pedido_produto unique (pedido_id, produto_id),

    constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id),
    constraint fk_item_pedido_produto foreign key (produto_id) references produto (id)
);