create sequence users_id_seq;
alter sequence users_id_seq owner to postgres;

create table public.users (
    id bigint primary key not null default nextval('users_id_seq'::regclass),
    email character varying(255) not null,
    password character varying(255) not null,
    profile_id bigint,
    foreign key (profile_id) references public.profiles (id)
        match simple on update no action on delete no action
);

create unique index uk_users_email on users using btree (email);
create unique index uk_users_profile_id on users using btree (profile_id);
alter sequence users_id_seq owned by users.id;

