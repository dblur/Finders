create sequence companies_id_seq;
alter sequence companies_id_seq owner to postgres;

create table public.companies (
    id bigint primary key not null default nextval('companies_id_seq'::regclass),
    city character varying(255) not null,
    country character varying(255) not null,
    email character varying(255) not null,
    name character varying(255) not null,
    phone character varying(255) not null,
    scope character varying(255) not null
);

alter sequence companies_id_seq owned by companies.id;

