# reverse_proxy
This is a project made under Computer Comunication course.
The goal of the project is to provide a reverse proxy meaning that a front-end server can choose the best candidate
to attend the request from a client. The back-end server it's monitored via UDP while the clients connect to it via TCP.

Bug: when shutting down some back-end server after adding it to the table entry it won't
recognize it's shut. To resolve i should remove back-end servers after some criteria when not responding to requests.
