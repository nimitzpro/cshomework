import time
import math
import random

# System Class definition
class System:
    running = True
    queues = []
    blocked_queue = []

    def __init__(self):
        for i in range(8):
            self.queues.append([])

# Definition of the Thread Class
class Thread:
    exec_time = 0
    pid = 0
    io_processes = {}
    status = "Ready"
    priority = 0

    def __init__(self, pid, exec_time, io_processes, priority):
        self.pid = pid
        self.exec_time = exec_time
        self.io_processes = io_processes
        self.priority = priority

    def __str__(self):
        return "PID: " + str(self.pid) + ", priority: " + str(self.priority) + ", exec_time: " + str(self.exec_time) + ", io: " + str(self.io_processes)
        # io_process := {Time in code at which to fetch io : Cycles to fetch io}

# Function to execute threads
def execute(thread):
    duration = 2**thread.priority
    print("Attempting to run thread ", str(thread))

    if thread.status == "Blocked": # If the thread is blocked, check if it is done fetching the data. If it is done, change its status to Ready
        if thread.io_processes[max(thread.io_processes.keys())] == 0:
            thread.status = "Ready"
            thread.io_processes.pop(max(thread.io_processes.keys()))
        else: # If the data hasn't been fetched yet, reduce the cycles to fetch by 1
            thread.io_processes[max(thread.io_processes.keys())] -= 1
            return thread.status
    

    final_time = 0
    if thread.exec_time > duration:
        final_time = thread.exec_time - duration
    
    actual_time = final_time
    for io in thread.io_processes.keys():
        if thread.exec_time == io:
            thread.status = "Blocked"
            return thread.status

        if final_time == io:
            if io > actual_time:
                actual_time = io
        elif final_time < io:
            if io > actual_time:
                actual_time = io
                thread.status = "Blocked"
    
    time.sleep(0.1*(thread.exec_time - actual_time)) # Sleep for time * 0.1 to speed up
    thread.exec_time = actual_time
    if thread.exec_time == 0:
        thread.status = "Complete"
    return thread.status

def idle_process():
    time.sleep(5)
    return "Idling..."


system = System()
for index, queue in enumerate(system.queues):
    for i in range(random.randint(1, 3)): # Generate between 1 and N threads per queue
        thread = Thread(random.randint(1, 999999), random.randint(1, 100), {}, index)

        if random.randint(0,2) != 0: # Generate io requests for threads
            thread.io_processes[random.randint(0, thread.exec_time)] = random.randint(1, 5)
            
        queue.append(thread)

while system.running:
    for index, queue in enumerate(system.queues): # print all queues at the start of every cycle
        print("Queue", index, ":", [str(thread) for thread in queue])

    queuesEmpty = True
    for queue in system.queues: # check if the queues are empty
        if len(queue) != 0:
            queuesEmpty = False
            break

    while queuesEmpty and len(system.blocked_queue) == 0:
        print(idle_process()) # if the queues are empty, run the idle process. It has the lowest priority of any process
    
    a = 0
    while a < len(system.queues): # loop through queues
        b = 0
        while b < len(system.queues[a]): # loop and execute threads in a queue
            status = execute(system.queues[a][b])
            if status == "Blocked":
                
                if system.queues[a][b].priority != 0: # NEW : WAS ADDED AFTER SCREENSHOTS WERE TAKEN. This boosts the thread's priority instead of just remaining at the same level
                    system.queues[a][b].priority -= 1

                system.blocked_queue.append(system.queues[a].pop(b))

            elif status == "Ready":
                if a < len(system.queues)-1: # move an unfinished thread down to the front of the next highest priority list
                    system.queues[a][b].priority += 1
                    _temp = [system.queues[a].pop(b)]
                    for i in system.queues[a+1]:
                        _temp.append(i)
                    system.queues[a+1] = _temp
            else:
                system.queues[a].pop(b)

        a += 1

    # Go through the blocked queue and reduce each fetch time by 1
    print("Blocked queue: ", [str(thread) for thread in system.blocked_queue])
    for p_index, process in enumerate(system.blocked_queue):
        execute(process)
        if process.status != "Blocked": # If the thread is not blocked anymore, then move it back to the priority queue it belongs to
            system.queues[process.priority].append(system.blocked_queue.pop(p_index))