from random import randint
import sys

name, n, m, mids, h = sys.argv
n = int(n)
m = int(m)
hardness = int(h)
mids = int(mids)

start1 = (randint(1,n), randint(1,m))
start2 = (randint(1,n), randint(1,m))
while (start2 == start1):
    start2 = (randint(1, n), randint(1, m))
goal = (randint(1,n), randint(1, m))
midpoints = []
for i in range(0, mids):
    midpoints.append((randint(1, n), randint(1, m)))
print n, m
print start1[0], start1[1]
print start2[0], start2[1]
print goal[0], goal[1]
print mids
for i in range(0, mids):
    print midpoints[i][0], midpoints[i][1]
for i in range(1, n+1):
    for j in range(1, m+1):
        able = True
        for w in range(0, mids):
            if (i,j) == midpoints[w]:
                able = False
        if (able and randint(1,hardness) == 1 and (i, j) != goal and (i, j) != start1 and (i, j) != start2):
            sys.stdout.write("X")
        else:
            sys.stdout.write("O")
    print ""



