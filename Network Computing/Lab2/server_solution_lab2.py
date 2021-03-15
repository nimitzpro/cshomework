from socket import *
from datetime import datetime
import os
import asyncio
import threading
import sys


loop = asyncio.get_event_loop()

async def send_message(client):
    try:
        while True:
            message = input("")
            log = ""
            time = datetime.now()
            try:
                f = open("logs/%s.txt" % datetime.now().date(), "rw")
                log = f.read()
            except:
                f = open("logs/%s.txt" % datetime.now().date(), "a")
            log = log + '[%s]: sent "%s"\n' % (time, message)
            f.write(log)
            f.close()
            client.sendall(message.encode())
    except error:
        print(error)

def handle_connection(client=None):
    try:
        while True:
            time = datetime.now()
            data = client.recv(64).decode()
            if data:
                log = ""
                try:
                    f = open("logs/%s.txt" % datetime.now().date(), "rw")
                    log = f.read()
                except:
                    f = open("logs/%s.txt" % datetime.now().date(), "a")
                log = log + '[%s]: received "%s"\n' % (time, data)
                f.write(log)
                f.close()
                print(">", data)

    except error:
        print("connection error", error)
    finally:
        client.close()

async def main():

    if not os.path.exists("logs"):
        os.mkdir("logs")

    sock = socket(AF_INET, SOCK_STREAM)

    HostName = gethostname()
    IP = gethostbyname(HostName)

    print(HostName, IP)
    server_address = (IP, 10000)
    print('*** Server is starting up on %s port %s ***' % server_address)
    sock.bind(server_address)

    sock.listen(2)
    while True:
        print('*** Waiting for a connection ***')
        client, client_address = await loop.sock_accept(sock)

        print('connection from', client_address)
        
        background_thread = threading.Thread(target=handle_connection, kwargs={"client":client})
        background_thread.daemon = True
        background_thread.start()

        loop.create_task(send_message(client))

    sock.close()

loop.create_task(main())
loop.run_forever()