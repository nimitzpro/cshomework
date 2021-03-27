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

page = IP + str(port) + "/HelloWorld.html"

# "HEAD / HTTP/1.1\r\nHost: %s\r\nAccept: text/html" % 
try:
    sock.send(("""GET / HTTP/1.1
Host: %s
Connection: keep-alive
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0
Upgrade-Insecure-Requests: 1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate""" % page).encode())

    amount_received = 0
    while amount_received < 1024:
        data = sock.recv(1024).decode()
        amount_received += len(data)
        print('received "%s"' % data)

finally:
    print('closing socket')
    sock.close()