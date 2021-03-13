class Vertex:
    id = 0

    def __init__(self, id):
        self.id = id

    def __str__(self):
        return str(self.id)

class Graph:
    adj_map = {}

    def __str__(self):
        string = ""
        for sv in self.adj_map:
            string += str(sv) + ": " + ", ".join([str(tv) + "|" + str(self.adj_map[sv][tv]) for tv in self.adj_map[sv]]) + "\n"
        return string

    def add_vertex(self, node):
        self.adj_map[Vertex(node)] = {}

    def get_vertex_by_label(self, id):
        for node in self.adj_map.keys():
            if node.id == id:
                return node

    def add_edge(self, sv, tv, length):
        self.adj_map[sv][tv] = length

        self.adj_map[tv][sv] = length



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