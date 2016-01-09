# pogo-tree

An object tree to whatever mapper.

In contrast to Object-Relational-Mapping this thing maps only a tree structure. Is therefor limited but also simpler.
It's nor much different from a json file but supports transactions and scalability from the underlying persistence backend.

There are no additional services like schema or validation.

Uses [hops-db](https://github.com/creichlin/hops-db) as a backend.
