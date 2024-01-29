create table solar_system
(
    symbol varchar(16) primary key,
    x      int not null,
    y      int not null

);

create table waypoint
(
    symbol        varchar(16) primary key,
    system_symbol varchar(16) not null,
    type          varchar(16) not null,
    x             int not null,
    y             int not null,

    constraint fk_system
        foreign key (system_symbol)
            references solar_system (symbol)
);

create table waypoint_jump_gate
(
    symbol varchar(16),
    system_symbol   varchar(16),

    PRIMARY KEY (symbol, system_symbol),

    constraint fk_symbol
        foreign key (symbol)
            references waypoint (symbol),

    constraint fk_system_symbol
        foreign key (system_symbol)
            references solar_system (symbol)
);
