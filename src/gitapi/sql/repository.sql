-- :name insert-repository
insert into public.repository (id, name, user_id, url, size, stargazers, watchers,
                               type_access, updated_at, created_at)
values (:id, :name, :user-id, :url, :size, :stargazers, :watchers, 'public', :updated-at, :created-at)
on conflict (id) do update set
                        stargazers=EXCLUDED.stargazers,
                        watchers=EXCLUDED.watchers,
                        size=EXCLUDED.size,
                        updated_at=EXCLUDED.updated_at
returning id;

-- :name select-repositories-by-user
select
       r.watchers, r.name, r.size, r.updated_at, r.created_at,
       r.id, r.url, r.stargazers, r.user_id
from public.repository r
join public.user u on u.id = r.user_id
where u.username = :username;

-- :name select-repository-by-id
select id
from public.repository
where id = :id;
