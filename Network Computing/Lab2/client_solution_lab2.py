from socket import *
import asyncio
import threading
import sys

async def send_message(sock):
    try:
        while True:
            message = input("")
            sock.sendall(message.encode())
    except error:
        print(error)

def rec_message(sock=None):
    try:
        while True:
            data = sock.recv(64).decode()
            if data:
                print(">", data)
    except error:
        print(error)

async def main():
    sock = socket(AF_INET, SOCK_STREAM)

    HostName = gethostname()
    IP = gethostbyname(HostName)

    print(HostName, IP)
    server_address = (IP, 10000)
    print('connecting to server at %s port %s' % server_address)
    sock.connect(server_address)

    background_thread = threading.Thread(target=rec_message, kwargs={"sock":sock})
    background_thread.daemon = True
    background_thread.start()

    await send_message(sock)

    sock.close()


loop = asyncio.get_event_loop()
loop.create_task(main())
loop.run_forever()
