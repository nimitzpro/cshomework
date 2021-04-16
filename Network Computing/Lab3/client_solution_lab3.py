# from the socket module import all
from socket import *

sock = socket(AF_INET, SOCK_STREAM)

HostName = gethostname()
IP = gethostbyname(HostName)
port = 6789

print(HostName, IP)

server_address = ("localhost", port)
print('connecting to server at %s port %s' % server_address)
sock.connect(server_address)

page = "/HelloWorld.html"
print("GET", page)

try:
    sock.send(("GET %s" % page).encode())

    data = sock.recv(1024).decode()
    while len(data) > 0:
        print("%s" % data, end="")
        data = sock.recv(1024).decode()

    print()

finally:
    print('closing socket')
    sock.close()