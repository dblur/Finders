create table public.users_roles (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references public.users (id)
        match simple on update no action on delete no action,
    foreign key (role_id) references public.roles (id)
        match simple on update no action on delete no action
);

