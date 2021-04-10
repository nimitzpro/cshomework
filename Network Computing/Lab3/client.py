from socket import *
import sys

print(sys.argv)
if len(sys.argv) != 4:
    print("Please re-run with format 'py client.py <server_host> <server_port> <filename>'")
    sys.exit()

server_host = sys.argv[1]
server_port = sys.argv[2]
filename = sys.argv[3]

sock = socket(AF_INET, SOCK_STREAM)

HostName = gethostname()
IP = gethostbyname(HostName)
print("client:", HostName, IP)

server_address = (server_host, int(server_port))
print('connecting to server at %s port %s' % server_address)
sock.connect(server_address)

try:
    sock.send(("Host: \\%s" % filename).encode())

    total = 0
    data = " "
    while data != "":
        data = sock.recv(1024).decode()
        total += len(data)
        print(data)
        # break

finally:
    print('closing socket')
    sock.close()