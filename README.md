# pogo-tree

A db to tree structure mapper.

In contrast to Object-Relational-Mapping this thing maps only a tree structure. It is therefore limited but also simpler.
It's not much different from a json file but supports transactions and scalability from the underlying persistence backend.

There are no additional services like schema or validation.

Uses [hops-db](https://github.com/creichlin/hops-db) and a sql datasource as a backend.
