import math

class APQ():
    def __init__(self, root=None):
        self.items = []

    def add_node(self, node):
        self.items.append(node)
        self.check(len(self.items)-1)

    def min(self):
        return self.items[0]

    def remove_min(self):
        original_smallest = self.items[0]
        
        index = 0
        self.items[index] = self.items.pop(len(self.items)-1)

        # Swap down

        i = 0
        while i < len(self.items):
            smallest = (i+1)*2
            if smallest > len(self.items)-1:
                smallest -= 1
            if smallest > len(self.items)-1:
                break

            if self.items[smallest -1] < self.items[smallest]:
                smallest -= 1
            if self.items[smallest] < self.items[i]:
               self.swap(smallest, i)
               i = smallest
            else:
                break

        return original_smallest
    
    def check(self, index):
        parent = math.floor(((index + 1) / 2) - 1)
        if parent < 0:
            parent = 0

        if self.items[parent] > self.items[index]:
            self.swap(parent, index)
            self.check(parent)

    def swap(self, x1, x2):
        _temp = self.items[x1]
        self.items[x1] = self.items[x2]
        self.items[x2] = _temp


    def __str__(self):
        return str(self.items)

    # def __str__(self):
    #     string = "(" + str(self.left) + str(self.element) + str(self.right) + ")"
    #     return string


queue = APQ()

queue.add_node(1)
queue.add_node(5)
queue.add_node(6)
queue.add_node(4)
queue.add_node(3)
queue.add_node(2)
queue.add_node(9)
queue.add_node(10)
queue.add_node(11)
queue.remove_min()
queue.remove_min()

print(queue)