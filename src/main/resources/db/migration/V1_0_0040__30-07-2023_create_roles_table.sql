create sequence roles_id_seq;
alter sequence roles_id_seq owner to postgres;

create table public.roles (
    id bigint primary key not null default nextval('roles_id_seq'::regclass),
    name character varying(255) not null
);

create unique index uk_roles_name on roles using btree (name);
alter sequence roles_id_seq owned by roles.id;

