# clustering
This project contains java implementations of several clustering algorithms.

## algorithms:  
fft - Farthest First Traversal  
km - k-means (Lloyd) (implemented + works)  
[skm](http://puu.sh/nw5ny.png) - Soft k-means (accuracy issues)  
h - Hierarchical (not yet implemented)  

## compile
```
ant compile  
ant jar  
```

## clean
```
ant clean  
```

## run
```
ant -Darg0=<inputfile> -Darg1=<algorithm> [-Darg2=<SKM iterations>] run  
```

## example
```
ant -Darg0=data/skm/input -Darg1=skm -Darg2=100 run  
```
