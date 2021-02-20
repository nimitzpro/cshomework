import time
import math
import random

class System:
    running = True
    queues = []
    blocked_queue = []

    def __init__(self):
        for i in range(8):
            self.queues.append([])


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
        return "PID: " + str(self.pid) + ", priority: " + str(self.priority) + ", exec_time: " + str(self.exec_time)
        # io_process := {Time in code at which to fetch io : Cycles to fetch io}


def execute(thread):
    duration = 2**thread.priority
    print("attempting to run thread ", str(thread))

    if thread.status == "Blocked":
        if thread.io_processes[max(thread.io_processes.keys())] == 0:
            thread.status = "Ready"
            thread.io_processes.remove(thread.io_processes[max(thread.io_processes.keys())])
        else:
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
    
    time.sleep(thread.exec_time - actual_time)
    thread.exec_time = actual_time
    if thread.exec_time == 0:
        thread.status = "Complete"
    return thread.status

def idle_process():
    time.sleep(3000)
    return "Idling..."


system = System()
for index, queue in enumerate(system.queues):
    for i in range(random.randint(1, 20)):
        queue.append(Thread(random.randint(1, 999999), random.randint(1, 100), {}, index))
            
# for queue in system.queues:
# generate processes


while system.running:
    for queue in system.queues:
        print([str(thread) for thread in queue])

    queuesEmpty = True
    for queue in system.queues:
        if len(queue) != 0:
            queuesEmpty = False
            break

    while queuesEmpty and len(system.blocked_queue) == 0:
        print(idle_process()) # if the queues are empty, run the idle process. it has the lowest priority of any process
    
    a = 0
    while a < len(system.queues):
        b = 0
        while b < len(system.queues[a]):
            status = execute(system.queues[a][b])
            if status == "Blocked":
                system.blocked_queue.append(system.queues[a].pop(b))
            elif status == "Ready":
                if a < len(system.queues)-1:
                    system.queues[a][b].priority += 1
                    system.queues[a+1].append(system.queues[a].pop(b))
            else:
                system.queues[a].pop(b)
            b += 1

        a += 1


    for p_index, process in enumerate(system.blocked_queue):
        execute(process)
        if process.status != "Blocked":
            system.queues[process.priority].append(system.blocked_queue.pop(p_index))