import matplotlib.pyplot as plt

x = [2, 4, 8, 16, 32, 64, 128]

def directMapped():
    plt.plot(x, [50, 60, 65, 68, 48, 5, 0], label="256 bytes")
    plt.plot(x, [66, 73, 80, 81, 71, 49, 5], label="512 bytes")
    plt.plot(x, [71, 82, 88, 91, 86, 75, 52], label="1024 bytes")
    plt.title('Direct Mapped Cache')
    plt.xlabel('Block Size (words)')
    plt.ylabel('Hit Ratio (%)')
    plt.grid()
    plt.xscale("log", basex=2)
    plt.legend()
    plt.show()

def associativeLRU():
    plt.plot(x, [75, 87, 94, 97, 89, 5, None], label="256 bytes")
    plt.plot(x, [75, 87, 94, 97, 98, 89, 5], label="512 bytes")
    plt.plot(x, [75, 87, 94, 97, 98, 99, 94], label="1024 bytes")
    plt.title('Full Associative Cache - LRU Replacement')
    plt.xlabel('Block Size (words)')
    plt.ylabel('Hit Ratio (%)')
    plt.grid()
    plt.xscale("log", basex=2)
    plt.legend()
    plt.show()

def associativeRandom():
    plt.plot(x, [69, 83, 90, 94, 88, 5, None], label="256 bytes")
    plt.plot(x, [73, 86, 93, 95, 94, 88, 5], label="512 bytes")
    plt.plot(x, [75, 87, 93, 97, 98, 97, 90], label="1024 bytes")
    plt.title('Full Associative Cache - Random Replacement')
    plt.xlabel('Block Size (words)')
    plt.ylabel('Hit Ratio (%)')
    plt.grid()
    plt.xscale("log", basex=2)
    plt.legend()
    plt.show()

def nWayAssociative512():
    plt.plot(x, [71, 82, 90, 91, 90, 87, None], label="Set-size 2")
    plt.plot(x, [71, 85, 92, 94, 96, None, None], label="Set-size 4")
    plt.plot(x, [72, 85, 92, 96, None, None, None], label="Set-size 8")
    plt.title('n-way Associative Caching - 512 bytes')
    plt.xlabel('Block Size (words)')
    plt.ylabel('Hit Ratio (%)')
    plt.grid()
    plt.xscale("log", basex=2)
    plt.legend()
    plt.show()

def nWayAssociative1024():
    plt.plot(x, [73, 86, 93, 96, 97, 95, 90], label="Set-size 2")
    plt.plot(x, [73, 87, 93, 95, 97, 98, None], label="Set-size 4")
    plt.plot(x, [74, 87, 93, 96, 97, None, None], label="Set-size 8")
    plt.title('n-way Associative Caching - 1024 bytes')
    plt.xlabel('Block Size (words)')
    plt.ylabel('Hit Ratio (%)')
    plt.grid()
    plt.xscale("log", basex=2)
    plt.legend()
    plt.show()

directMapped()
associativeLRU()
associativeRandom()
nWayAssociative512()
nWayAssociative1024()