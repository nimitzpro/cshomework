import math

class Vertex:
    def __init__(self, id):
        self.id = id

    def __str__(self):
        return str(self.id)

class APQ():
    def __init__(self, root=None):
        self.items = []

    def add_node(self, node):
        self.items.append(node)
        self.check(len(self.items)-1)
        return node

    def min(self):
        if len(self.items) > 0:
            return self.items[0]
        return "No items"

    def __len__(self):
        return len(self.items)

    def remove_min(self):
        original_smallest = None
        index = 0
        
        if len(self.items) == 0:
            return None
        elif len(self.items) == 1:
            original_smallest = self.items[0]
            self.items = []
            return original_smallest
        else:
            original_smallest = self.items[0]
            self.items[index] = self.items.pop(len(self.items)-1)

        # Swap down
        
        i = 0
        while i < len(self.items):
            smallest = (i+1)*2
            if smallest > len(self.items)-1:
                smallest -= 1
            if smallest > len(self.items)-1:
                break

            if next(iter(self.items[smallest -1])) < next(iter(self.items[smallest])):
                smallest -= 1
            if next(iter(self.items[smallest])) < next(iter(self.items[i])):
               self.swap(smallest, i)
               i = smallest
            else:
                break

        return original_smallest
    
    def check(self, index):
        parent = math.floor(((index + 1) / 2) - 1)
        if parent < 0:
            parent = 0

        if next(iter(self.items[parent])) > next(iter(self.items[index])):
            self.swap(parent, index)
            self.check(parent)

    def swap(self, x1, x2):
        _temp = self.items[x1]
        self.items[x1] = self.items[x2]
        self.items[x2] = _temp

    def __str__(self):
        return str(self.items)
    
    def get_index(self, vertex):
        for i in range(len(self.items)):
            if vertex in self.items[i].values():
                return i


class Graph:
    adj_map = {}
    locs = {}
    closed = {}
    preds = {}
    open = None
    
    def __init__(self):
        self.open = APQ()

    def __str__(self):
        string = "Adjacency map: \n"
        for sv in self.adj_map:
            string += str(sv) + ": [" + "], [".join([str(tv) + ", " + str(self.adj_map[sv][tv]) for tv in self.adj_map[sv]]) + "]\n"
        
        string += "Preds: " + str(self.preds)
        string += "\nLocs: " + str(self.locs)
        string += "\nOpen: " + str(self.open)
        string += "\nClosed: "+ str(self.closed)
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

    def dijkstra(self, s):
        print("Running Dijkstra's Algorithm starting at", s)
        s = self.get_vertex_by_label(s)
        self.locs[s] = self.open.add_node({0:s})
        self.preds[s] = None

        while len(self.open) > 0:
            elem = self.open.remove_min()
            cost = next(iter(elem))
            v = elem[cost]
            self.locs.pop(v)
            pre = self.preds.pop(v)
            self.closed[v] = (cost, pre)
            for w in self.adj_map[v].keys():
                if w not in self.closed.keys():
                    new_cost = cost + self.adj_map[v][w]

                    if w not in self.locs.keys():
                        self.preds[w] = v
                        self.locs[w] = self.open.add_node({new_cost:w})
                    elif new_cost < next(iter(self.open.items[self.open.get_index(w)].keys())):
                        self.preds[w] = v
                        self.open.items.pop(self.open.get_index(w))
                        self.open.add_node({new_cost:w})
                        
        return self.closed
        # return "Closed: "+", ".join([str(self.closed[i][1])+"-->"+str(i) +"("+ str(self.closed[i][0])+")" for i in self.closed])+\
        #     "\nClosed (alt. string): \n" + self.strhelper()
    
    def strhelper(self, a=-1):
        if a == None:
            return "Start"
        if a != -1:
            if self.closed[a][0] > 0:
                return self.strhelper(self.closed[a][1]) +"-->"+str(a) +"("+ str(self.closed[a][0])+")"
            else:
                return str(a)

        string = ""
        for i in self.closed:
            string += self.strhelper(self.closed[i][1]) +"-->"+str(i) +"("+ str(self.closed[i][0])+")"
            string += "\n"

        return string

class VertexR(Vertex):
    def __init__(self, id, lat, lon):
        super().__init__(id)
        self.lat = lat
        self.lon = lon

    # def __str__(self):
    #     return str(self.id)
        # return "{{id:"+str(self.id)+", lat:"+str(self.lat)+", lon:"+str(self.lon)+"}}"

class RouteMap(Graph):
    adj_map = {}
    quick_map = {}
    locs = {}
    closed = {}
    preds = {}
    open = None

    def __init__(self):
        self.open = APQ()

    def __str__(self):

        if len(self.adj_map.keys()) > 100 or len(self.adj_map.values()) > 200:
            return "More than 100 vertices or edges"

        super().__str__()

    def add_vertex(self, id, lat, lon):
        vertex = VertexR(id, lat, lon)
        self.adj_map[vertex] = {}
        self.quick_map[id] = vertex
        return vertex

    def get_vertex_by_label(self, id):
        return self.quick_map[id]
    
    def strhelper(self, a=-1):
        if a == None:
            return "Start"
        if a != -1:
            if self.closed[a][0] > 0:
                return self.strhelper(self.closed[a][1]) +"-->"+str(a) +"("+ str(self.closed[a][0])+")"
            else:
                return str(a)

        string = ""
        for i in self.closed:
            string += self.strhelper(self.closed[i][1]) +"-->"+str(i) +"("+ str(self.closed[i][0])+")"
            string += "\n"

        return string

    def sp(self, v, w):
        w = self.get_vertex_by_label(w)
        d = self.dijkstra(v)
        new = []
        while w != None:
            pair = (w, d[w][0])
            new.append(pair)
            w = d[w][1]
        new = new[::-1]
        return new

    def print_sp(self, sp):
        print("code","latitude","longitude","id","time", sep=",")
        for pair in sp:
            p = pair[0]
            str_pid = str(p.id)
            # if len(str_pid) < 7:
            #     str_pid += "\t"
            print("W", p.lat, p.lon, str_pid, pair[1], sep=",")

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
    return graph

def graphreaderR(filename):
    """ Read and return the route map in filename. """
    graph = RouteMap()
    file = open(filename, 'r')
    entry = file.readline() # either 'Node' or 'Edge'
    num = 0
    while entry == 'Node\n':
        num += 1
        nodeid = int(file.readline().split()[1])
        gps = file.readline().split()
        lat, lon = float(gps[1]), float(gps[2])
        vertex = graph.add_vertex(nodeid, lat, lon)
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
        time = float(file.readline().split()[1]) # Time
        edge = graph.add_edge(sv, tv, time)
        file.readline() # read the one-way data
        entry = file.readline() # either 'Node' or 'Edge'
    print('Read', num, 'edges and added into the graph')
    return graph

def handle(filename, start=1):
    g = graphreader(filename)
    print(g)
    closed = g.dijkstra(start)
    print("Closed: "+", ".join([str(closed[i][1])+"-->"+str(i) +"("+ str(closed[i][0])+")" for i in closed])+\
            "\nClosed (alt. string): \n" + g.strhelper())

def handleR(filename):
    g = graphreaderR(filename)
    sp = g.sp(1669466540, 1147697924)
    g.print_sp(sp)

if __name__ == "__main__":
    # handle("simplegraph1.txt")
    handle("simplegraph2.txt")
    handleR("corkCityData.txt")