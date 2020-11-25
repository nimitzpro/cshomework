class Track:
    def __init__(self, name, artiste):
        self.__name = name
        self.__artiste = artiste
        self.__timesplayed = 0
    

    def __str__(self):
        s = self.__name + "; " + self.__artiste + " (" + str(self.__timesplayed) + ")"
        return s

    def get_name(self):
        return self.__name
    def get_artiste(self):
        return self.__artiste

    def play(self):
        self.__timesplayed += 1
        return "\nPlaying song " + self.__name

class PyToonzNode:
    def __init__(self, track, prevnode, nextnode):
        self.track = track
        self.prevnode = prevnode
        self.nextnode = nextnode

    def __str__(self):
        return str(self.track)
    
    def play(self):
        return self.track.play()

class PyToonz:
    def __init__(self):
        self.size = 0
        self.first = None
        self.current = None

    def __str__(self):
        x = self.first
        s = "\nPlaylist:\n"
        while type(x) == PyToonzNode:
            if self.current == x:
                s += "--> "
            s = s + str(x) + "\n"
            if type(x.nextnode) == PyToonzNode:
                x = x.nextnode
            else:
                break
        
        return s

    def length(self):
        return self.size
        
    def add_track(self, track):
        if self.size == 0:
            return self.add_first(track)

        x = self.get_last()

        node = PyToonzNode(track, x, None)
        x.nextnode = node

    def add_first(self, track):
        if self.size == 0:
            node = PyToonzNode(track, None, None)
            self.first = node
            self.current = node
        else:
            node = PyToonzNode(track, None, self.first)
            self.first.prevnode = node
            self.first = node
        
        self.size += 1

    def add_after(self, track):
        node = PyToonzNode(track, self.current, self.current.nextnode)
        if type(self.current.nextnode) == PyToonzNode:
            self.current.nextnode.prevnode = node
            
        self.current.nextnode = node

        self.size += 1

    def get_first(self):
        return self.first

    def get_current(self):
        return "Current Track: " + str(self.current)

    def get_last(self):
        x = self.first
        while type(x.nextnode) == PyToonzNode:
            x = x.nextnode
        return x
        
    def remove_node(self, node):
        x = self.first
        while x != node:
            x = x.nextnode
        
        p = x.prevnode
        n = x.nextnode

        if type(p) != None:
            p.nextnode = n
        if type(n) != None:
            n.prevnode = p
    
        self.size -= 1

    def remove_first(self):
        if type(self.first.nextnode) == PyToonzNode:
            x = self.first.nextnode
            x.prevnode = None
            self.first = x
            self.current = self.first
        else:
            self.first = None
            self.current = None
        self.size -= 1

    def remove_last(self):
        x = self.get_last()
        
        x.prevnode.nextnode = None
        self.size -= 1

    def remove_current(self):
        if self.first == self.current:
            self.remove_first()
        if type(self.current.nextnode) == PyToonzNode:
            if type(self.current.prevnode) == PyToonzNode:
                self.current.nextnode.prevnode = self.current.prevnode
                self.current.prevnode = self.current.nextnode
                self.size -= 1
                self.current = self.current.nextnode
        else:
            self.remove_last()

    def reset(self):
        self.current = self.first

    def next_track(self):
        if type(self.current.nextnode) == PyToonzNode:
            self.current = self.current.nextnode
        else:
            self.current = self.first
    
    def prev_track(self):
        if type(self.current.prevnode) == PyToonzNode:
            self.current = self.current.prevnode
        else:
            self.current = self.get_last()

    def play(self):
        if type(self.current) == None:
            print("No track is selected.")
        else:
            print(self.current.play())

if __name__ == "__main__":
    t1 = Track("Darude - Sandstorm", "V. Virtanen")
    t2 = Track("TT", "Twice")
    t3 = Track("Signal", "Twice")
    t4 = Track("Breakthrough", "Twice")

    l = PyToonz()
    l.add_track(t1)
    l.add_track(t2)
    l.add_track(t3)
    l.add_track(t4)

    print(l)

    l.play()
    l.next_track()
    l.play()
    l.next_track()
    print(l.get_current())
    l.play()
    l.reset()
    l.play()
    l.remove_current()
    print(l)
    l.prev_track()
    l.play()
    
    print(l)
    l.prev_track()