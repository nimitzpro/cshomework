import sys

text = sys.argv[1]
language = 'en'

alphabet = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z']

if len(sys.argv) == 3:
    language = sys.argv[2]

chars = {}

text = text.lower()

for char in text:
    if char in alphabet:
#        print("got to here")
        if char not in chars.keys():
            chars[char] = 1
        else:
            chars[char] += 1

chars[' '] = 0 # Remove spaces from frequency analysis

en_probabilities = ['e','t','a','o','i','n','s','h','r','d','l','c','u','m','w','f','g','y','p','b','v','k','j','x','q','z']

max_char = "a"
maxN = 0
for char in chars.keys():
    if chars[char] > maxN:
        maxN = chars[char]
        max_char = char

# print(maxN, max_char)

shift = alphabet.index('e') - alphabet.index(max_char)


decrypted_text = ""
for char in text:
    if char in alphabet:
        decrypted_text += alphabet[alphabet.index(char) + shift]
    else:
        decrypted_text += char

print("Caesar Shift: " + str(shift) + "\n")
print(decrypted_text)
