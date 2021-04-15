from socket import *
from datetime import datetime
import os


ip = "localhost"
port = 6789
sock = socket(AF_INET, SOCK_DGRAM)
sock.bind((ip, port))

if not os.path.exists("logs"):
    os.mkdir("logs")

print("Listening on %s:%s..." % (ip, port))

while True:
    try:
        msg, client = sock.recvfrom(2048)
        msg = msg.decode()

        print("Receiving", msg, "from", client)
        time = datetime.now()
        print(time)
        log = ""
        try:
            f = open("logs/%s.txt" % (time.date()), "rw")
            log = f.read()
        except:
            f = open("logs/%s.txt" % (time.date()), "a")

        log += '[%s]%s: received "%s"\n' % (time, client, msg)
        f.write(log)
        f.close()

        msg = msg.upper()
        msg2 = str(time) + ": " + msg
        
        print(msg2)
        sock.sendto(msg2.encode(), client)

    except:
        sock.close()
        break