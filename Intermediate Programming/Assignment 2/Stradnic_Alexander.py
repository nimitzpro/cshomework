from tkinter import *
from tkinter import ttk
import random
import time
import math

root = Tk()

screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()

referenceScale = 2073600
scale = math.log((screen_height * screen_width), referenceScale)


scaledSize = int(50 * scale)
halfSize = int(scaledSize / 2)

difficulty = 0


cwidth, cheight = screen_width, screen_height-int(100*scale)
cx, cy = 0, 0
points = 0
oval = None

t = 0

colour = ["black", "green", "red"]

c = Canvas(root, width=cwidth, height=cheight, background="#ffffff")

l = Label(root, text="Points : "+str(points), font=("Arial", 12))


l.pack()

def draw():
    global cx, cy, t, oval, scaledSize, halfSize

    if difficulty == 1:
        scaledSize = int(random.randint(30, 50) * scale)
        halfSize = int(scaledSize / 2)
    elif difficulty == 2:
        scaledSize = int(random.randint(15, 30) * scale)
        halfSize = int(scaledSize / 2)


    cx = random.randint(0, cwidth-scaledSize)
    cy = random.randint(0, cheight-scaledSize)
    oval = c.create_oval(cx, cy, cx+scaledSize, cy+scaledSize, fill=colour[difficulty])
    c.pack()
    t = int(time.time())

def delete():
    global c, oval
    c.delete(oval)
    c.pack()
    draw()

def getorigin(e):
    global points, l, difficulty

    centerX = cx + halfSize
    centerY = cy + halfSize
    if math.sqrt((e.x - centerX)**2 + (e.y - centerY)**2) <= halfSize:
        if time.time() - t <= (2 / (difficulty*0.25 + 1)):
            points += 1
            l.configure(text="Points : " + str(points))
            l.pack()
            if points == 10:
                difficulty = 1
            elif points == 20: 
                difficulty = 2

        delete()

root.bind("<Button 1>",getorigin)

draw()
root.mainloop()