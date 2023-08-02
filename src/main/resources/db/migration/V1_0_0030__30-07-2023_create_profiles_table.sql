create sequence profiles_id_seq;
alter sequence profiles_id_seq owner to postgres;

create table public.profiles (
    id bigint primary key not null default nextval('profiles_id_seq'::regclass),
    age integer not null,
    city character varying(255) not null,
    country character varying(255) not null,
    experience character varying(255),
    first_name character varying(255) not null,
    phone character varying(255) not null,
    second_name character varying(255) not null,
    sex character varying(255) not null,
    company_id bigint,
    foreign key (company_id) references public.companies (id)
        match simple on update no action on delete no action
);

create unique index uk_profiles_company_id on profiles using btree (company_id);
alter sequence profiles_id_seq owned by profiles.id;
