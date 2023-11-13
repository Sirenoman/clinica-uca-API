create table pacientes(
	id bigint not null auto_increment,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    documento_id varchar(100) not null unique,
    telefono varchar(20) not null,
    activo tinyint not null,
    
    primary key(id)
);