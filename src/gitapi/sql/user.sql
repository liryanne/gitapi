-- :name insert-user
insert into public.user (id, username)
values (:id, :username)
on conflict (id) do update set username=EXCLUDED.username
returning id;

-- :name select-user-by-username
select * from public.user where username = :username;
