# from the socket module import all
from socket import *
from datetime import datetime
import os

# Check if logs directory exists; if not then create logs directory. A logs directory reduces clutter

if not os.path.exists("logs"):
    os.mkdir("logs")


# Create a TCP server socket
#(AF_INET is used for IPv4 protocols)
#(SOCK_STREAM is used for TCP)
sock = socket(AF_INET, SOCK_STREAM)
# if we did not import everything from socket, then we would have to write the previous line as:
# sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

HostName = gethostname()
IP = gethostbyname(HostName)

print(HostName, IP)
# set values for host 'localhost' - meaning this machine and port number 10000
server_address = (IP, 10000)
# output to terminal some info on the address details
print('*** Server is starting up on %s port %s ***' % server_address)
# Bind the socket to the host and port
sock.bind(server_address)

# Listen for one incoming connections to the server
sock.listen(1)

# we want the server to run all the time, so set up a forever true while loop
while True:

    # Now the server waits for a connection
    print('*** Waiting for a connection ***')
    # accept() returns an open connection between the server and client, along with the address of the client
    connection, client_address = sock.accept()
    
    try:
        print('connection from', client_address)

        # Receive the data in small chunks and retransmit it
        while True:
            time = datetime.now()
            # decode() function returns string object
            data = connection.recv(16).decode()
            if data:
                log = ""
                try:
                    f = open("logs/%s.txt" % datetime.now().date(), "rw")
                    log = f.read()
                except:
                    f = open("logs/%s.txt" % datetime.now().date(), "a")
                log = log + '[%s]: received "%s"\n' % (time, data)
                print('[%s]: received "%s"' % (time, data))
                f.write(log)
                f.close()
                print('sending data back to the client')
                # encode() function returns bytes object
                connection.sendall(data.encode())
            else:
                print('no more data from', client_address)
                break
            
    finally:
        # Clean up the connection
        connection.close()

# now close the socket
sock.close();