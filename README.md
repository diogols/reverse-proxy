# reverse-proxy
This is a small project made under Computer Communication course of University of Minho.

The goal of the project is to provide a reverse proxy meaning that a front-end server can choose the best candidate
to attend the request from a client. The back-end server is monitored via UDP while the clients connect to it via TCP.

Known issue: when shutting down a backend server after adding it to the table entry, the system will not
recognize it is offline. To solve this, I should remove back-end servers using a defined criteria when the back-end server stops responding to requests.
