#!/usr/bin/python
import getpass
import hashlib

#our test system only has one user and one pin for it
s_user = "alex"
s_salt = "You thought it was your pizza delivery, but it was me, Dio!"
s_pin  = "c8a909908632d17c1098d9f3f315b5a35e4a757171f65a86b54a7403850c55ff" # 1234

def hash_unicode(a_string, a_salt):
    return hashlib.sha256((a_string + a_salt).encode('utf-8')).hexdigest()

#check if user and password match
def login( user, pin ):
        if user == s_user and hash_unicode(pin, s_salt) == s_pin :
                print("user/PIN : " + user + "/" + pin)
                return True
        return False

#ask for the username and password
user = input("user: ")
pin  = getpass.getpass("PIN :")

hashed = hash_unicode(pin, s_salt)
print("hashed:", hashed)

#check if we can log in
if login(user, pin):
        print("access granted")
else :
	print("access denied")
