from socket import *
import sys

arg_list = list(sys.argv)
try:
    domain_name = arg_list[1]
    port = int(arg_list[2])
except:
    print("Please re-run this file using the format 'py UDPClient.py <domain_name> <port>")
    sys.exit()

sock = socket(AF_INET, SOCK_DGRAM)
msg = input("Send Message: ").encode()
sock.sendto(msg, (domain_name, port))

rec_msg, address = sock.recvfrom(2048)
print(rec_msg.decode())
sock.close()