import random
import time

full = {2:"▪", 4:"■", 8:"▲", 16:"◆", 32:"●"}
empty = {2:"▫", 4:"□", 8:"△", 16:"◇", 32:"○"}

class Process:
    pid = 0
    pages = 0

    def generate_pid(self, space, N=1000000):
        pid = random.randint(0, N-1)
        for process in space.requests:
            if process.pid == pid:
                return self.generate_pid(N)
        return pid

    def __init__(self, space, pages=-1):
        if pages == -1:
            pages = random.randint(0, 5)
            if pages == 0:
                pages = 1
        self.pages = 2**pages
        self.pid = self.generate_pid(space)
    
    def __str__(self):
        string = "PID: " + str(self.pid) + ", pages: " + str(self.pages)
        return string


class Block:
    pages = 0
    prev = None
    next = None
    process = None
    accessed = 0

    def __init__(self, pages, prev=None, next=None):
        self.pages = pages
        self.prev = prev
        self.next = next

    def __str__(self):
        string = "Process: " + (str(self.process) if self.prev != None else "None") + ", prev pages: " + (str(self.prev.pages) if self.prev != None else "None") + ", next pages: " + (str(self.next.pages) if type(self.prev) != None else "None") + ", pages: " + str(self.pages)
        return string

class Space:
    exec_list = [] # a queue maintaining the execution order of processes
    first = None
    current = None
    requests = [] # a queue holding all incoming processes

    def __init__(self):
        self.gen_blocks(32, 16, 16, 16, 16)
        print(str(self.first))
    
    def __str__(self):
        string = "Blocks: \n"
        self.current = self.first
        string += str(self.current.pages) + "p\t"
        count = 0
        while self.current != None:
            count += 1
            if self.current.process:
                string += full[self.current.pages] + " "
            else:
                string += empty[self.current.pages] + " "
            if self.current.next != None:
                if self.current.next.pages != self.current.pages:
                    string += "\n" + str(self.current.next.pages) + "p\t"
                elif count % 16 == 0:
                    string += "\n\t"

            self.current = self.current.next
        
        return string

    def gen_blocks(self, num2, num4, num8, num16, num32):
        self.first = Block(2)
        self.current = self.first
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
            # print(str(block))
            if block.process == None and process.pages <= block.pages:
                block.process = process
                self.exec_list.append(block)
                return True
            elif type(block.next) == None:
                return self.page_replace(process)
            block = block.next
				
    def page_replace(self, process):
        for block in self.exec_list:
        	if block.pages >= process and block.accessed == 0:
        		block.process = process
        		self.exec_list.append(self.exec_list.pop(block))
        		return True
        return False

    def gen_requests(self, n=20):
        print("Generating", n, "processes requesting space...")
        for i in range(n):
            self.requests.append(Process(self))

    def process_requests(self):
        print("Handling requests: ", [str(process) for process in self.requests])
        while True:
            process = self.requests[0]
            print("Handling process", str(process))
            if len(self.requests) > 1:
                self.requests = self.requests[1::]
            else:
                self.requests = []
                break
            success = self.add(process)
            time.sleep(1)
            if not success:
                self.requests.append(process)
            print(str(self))


space = Space()
print(str(space))
space.gen_requests()
space.process_requests()