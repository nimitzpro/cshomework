class Vertex:
    def __init__(self, id):
        self.id = id

    def __str__(self):
        return str(self.id)

class APQ():
    def __init__(self, root=None):
        self.root = root
        self.current = root

    def add_node(self, node):
        if self.root != None:
            self.root.add_node(node)
        else:
            self.root = node
    
    def __str__(self):
        return str(self.root)

    def remove_min(self):
        rt = self.root.element



        return rt

class Node():
    def __init__(self, element):
        self.element = element
        self.right = None
        self.left = None
        self.parent = None

    def add_node(self, node):
        if self.left == None:
            self.left = node
            self.left.parent = self
            node.verify()
            return True
        elif self.right == None:
            self.right = node
            self.right.parent = self
            node.verify()
        else:
            if not self.left.add_node(node):
                self.right.add_node(node)

    def verify(self):
        if self.parent != None:
            if self.element < self.parent.element:
                _temp = self.element
                self.element = self.parent.element
                self.parent.element = _temp
                self.parent.verify()

    def __str__(self):
        string = "(" + str(self.left) + str(self.element) + str(self.right) + ")"
        return string


class Graph:
    adj_map = {}
    locs = {}
    closed = {}
    preds = {}

    open = APQ()

    def __str__(self):
        string = ""
        for sv in self.adj_map:
            string += str(sv) + ": [" + "], [".join([str(tv) + ", " + str(self.adj_map[sv][tv]) for tv in self.adj_map[sv]]) + "]\n"
        
        string += str(self.locs)
        string += "\n"+ str(self.closed)
        string += "\n" + str(self.preds)
        string += "\n" + str(self.open)
        return string

    def add_vertex(self, node):
        vertex = Vertex(node)
        self.adj_map[vertex] = {}
        return vertex

    def get_vertex_by_label(self, id):
        for node in self.adj_map.keys():
            if node.id == id:
                return node

    def add_edge(self, sv, tv, length):
        self.adj_map[sv][tv] = length

        self.adj_map[tv][sv] = length

        return str(self.adj_map[sv])

    def dijkstra(self):
        for point in self.adj_map.keys():
            self.open.add_node(Node(point))

        print(self.open)


def graphreader(filename):
    """ Read and return the route map in filename. """
    graph = Graph()
    file = open(filename, 'r')
    entry = file.readline() # either 'Node' or 'Edge'
    num = 0
    while entry == 'Node\n':
        num += 1
        nodeid = int(file.readline().split()[1])
        vertex = graph.add_vertex(nodeid)
        entry = file.readline() # either 'Node' or 'Edge'
        print('Read', num, 'vertices and added into the graph')
        num = 0
    while entry == 'Edge\n':
        num += 1
        source = int(file.readline().split()[1])
        sv = graph.get_vertex_by_label(source)
        target = int(file.readline().split()[1])
        tv = graph.get_vertex_by_label(target)
        length = float(file.readline().split()[1])
        edge = graph.add_edge(sv, tv, length)
        file.readline() # read the one-way data
        entry = file.readline() # either 'Node' or 'Edge'
    print('Read', num, 'edges and added into the graph')
    print(graph)
    return graph

graphreader("simplegraph1.txt")