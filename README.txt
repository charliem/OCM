﻿ OCM  - The Object Cassandra Mapper Read Me
----------------------------------------

Version: 0.6.0

OHM is an interface tool for the Apache Project's
clustered database engine, Cassandra. OCM is licensed under
the Apache License, Version 2.0  a copy of which is
included in the license file.

Interfacing a client application directly with Cassandra can
be difficult since it lacks many features of a
traditional relational database such as the lack of Data
Types.

OCM solves this by providing an ORM like interface, or
at least a weakly relational one, for client
applications to interact with.

This is achieved by the developer creating a OCM spec
file which sets out the structure of the data inside the
database. The developer the feeds these spec files into
the OCM Complier which generates the interface code in a
supported high level language.

This generated code can then be included in the client
application to interact with the database.


Currently OCM provides:

	* Basic CRUD operations on rows.

	* Reading & Updating operations on all Column
	  Family Fields.

	* Type conversion in to the languages native
	  types e.g. float, int, string.

	* Indexing of additional Column Family Fields.


Currently OCM supports code generation in:

	* Java – Interfacing with Cassandra via Hector 



All of the projects source code is kept under version
control using Git. You can access the projects Public
Git repository.

	http://github.com/charliem/OCM.git

To get a copy of the latest version run

git clone http://github.com/charliem/OCM.git

You can find additional information on the projects web
site.

	http://github.com/charliem/OCM


Release Notes:

0.6.0
	* Moved to Hector 0.6 and therefore Cassandra 0.6

	* Moved to using Hector style versioning where the first two version numbers are those of the Cassandra version supported.
