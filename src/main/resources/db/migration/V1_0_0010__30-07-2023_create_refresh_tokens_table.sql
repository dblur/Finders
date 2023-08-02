create sequence refresh_tokens_id_seq;
alter sequence refresh_tokens_id_seq owner to postgres;

create table public.refresh_tokens (
    id bigint primary key not null default nextval('refresh_tokens_id_seq'::regclass),
    subject character varying(255) not null,
    token character varying(255) not null
);

create unique index uk_refresh_tokens_subject on refresh_tokens using btree (subject);
alter sequence refresh_tokens_id_seq owned by refresh_tokens.id;

