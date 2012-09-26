set output "./agents.png"
set term png 

#set xrange [-400:400]
#set yrange [-400:400]
#set ytics 50
#set xtics 50

set xlabel "x"
set ylabel "y"
set title "Mouvement"

plot \
"./agent1.res" title "agent1" with dots ls 3, \
"./agent2.res" title "agent2" with dots ls 2

