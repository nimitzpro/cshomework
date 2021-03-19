import random

def generate_pid(N=1000000):
    pid = random.randint(0, N)
    for process in requests:
        if process.pid == pid:
            return generate_pid(N)
    return pid

class Process:
    pid = 0
    pages = 0

    def __init__(self, pages, pid=generate_pid()):
        self.pages = pages
        self.pid = pid
        
class Block:
	pages = 0
	prev = None
	next = 0
	process = None
	accessed = 0

    def __init__(self, pages, prev=None, next=None):
        self.pages = pages
        self.prev = prev
        self.next = next

class Space:
	exec_list = []
	first = None
    current = None

    def __init__(self):
        self.gen_blocks(32, 16, 16, 16, 16)

    def gen_blocks(self, num2, num4, num8, num16, num32):
        self.first = Block(2)
        current = self.first
        self.gen_group(num2-1, 2)
        self.gen_group(num4, 4)
        self.gen_group(num8, 8)
        self.gen_group(num16, 16)
        self.gen_group(num32, 32)

    def gen_group(self, num, size):
        for i in range(num):
            block = Block(size, self.current)
            self.current.next = block
            self.current = block
        
	def add(self, process):
		block = self.first
		while True:
			if not block.process and process.pages <= block.pages:
				block.process = process
				self.exec_list.append(block)
				break
			if not block.next:
				self.page_replace(process)
				
	def page_replace(self, process):
		for block in self.exec_list:
			if block.pages >= process and block.accessed == 0:
				block.process = process
				self.exec_list.append(self.exec_list.pop(block))
				return True
		return False
		

function process_requests():
	while True:
		process = requests[0]
		requests = requests[1::]
		success = space.page_replace(process)
		if not success:
			requests.append(process)

def gen_requests(n=20):
    for i in range(n):

requests = []