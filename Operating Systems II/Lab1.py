from time import time
import math
import random

class System:
    self.running = True
    self.queues = []

    def __init__(self):
        for i in range(8):
            self.queues.append([])


class Thread:
    self.exec_time = 0
    self.io_processes = {}
    self.status = "Normal"
    self.priority = 0

    def __init__(self, exec_time, io_processes, priority):
        self.exec_time = exec_time
        self.io_processes = io_processes
        self.priority = priority

        # io_process := {Time in code at which to fetch io : Cycles to fetch io}


def execute(thread, duration):


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
            del thread.io_processes[io]
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
    return thread.status

def idle_process():
    time.sleep(3000)
    return "Idling..."


system = System()
    for queue in system.queues:
        for i in range(math.floor(random.randint())):
            
# for queue in system.queues:
# generate processes


while system.running:
    queuesEmpty = True
    for queue in system.queues:
        if len(queue) != 0:
            queuesEmpty = False
            break

    while queuesEmpty and len(system.blocked_queue) == 0:
        idle_process() # if the queues are empty, run the idle process. it has the lowest priority of any process

    for index, queue in enumerate(system.queues):
        for p_index, process in enumerate(queue):
            status = execute(process, 2^process.priority)
            if status == "Blocked":
                system.blocked_queue.append(queue.pop(p_index))
            # elif process.state == "Ready":
            #     execute(process, time_slice)
            if status == "Ready":
                if index != len(system.queues)-1:
                    process.priority += 1
                    system.queues[index+1].append(queue.pop(p_index))

    for index, process in enumerate(system.blocked_queue):
        execute(process, 2^process.priority^2)
        if process.status != "Blocked":
            system.queues[process.priority].append(system.blocked_queue.pop(p_index))