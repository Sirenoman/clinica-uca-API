create table medicos(
	id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null,
    telefono varchar(20) not null,
    documento varchar(100) not null,
    activo tinyint,
    especialidad varchar(100) not null,
    
    primary key(id)
);