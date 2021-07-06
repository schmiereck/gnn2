# Basics

Genetische Algorithmus für das Training.
1. Beweisen das das funktioniert.

# Erweiterungen

1. Netz bekommt eine Sequenz als Eingabe.
Das netz soll jeweils die nächste Eingabe erraten.
Beim Trainingspeichert das Netzt jeweils den vorangegangenen Input (oder mehrere)
und traniert sich selber darauf, aus dem vorangegangenen Inut den aktuellen Input korrekt 
vorherzusagen.
2. Die Parameter den GA werden durch ein NN gesteuert, welches sich verbessert.

# Ziel

Ein sich selbst verbesserndes Netz das Vorhersagen zu Inputs liefert und
sich 
1. gut fühlt, wenn es diese richtig vorhersagt.
2. überrascht ist, wenn unerwartete neue Eingaben kommen.

----
# Aufgaben
## IS
           N0 
    -10  => -10  
    +10  => +10  

## NOT
           N0 
    -10  => +10  
    +10  => -10  

## AND
             N0 
    -10 -10 => -10  
    +10 -10 =>   0  
    -10 +10 =>   0 
    +10 +10 => +10 

## OR
             N0 
    -10 -10 =>   0  
    +10 -10 =>   5  
    -10 +10 =>   5 
    +10 +10 => +10 

## XOR
             N0 
    -10 -10 => -10  
    +10 -10 => +10  
    -10 +10 => +10 
    +10 +10 => -10 

## XAND
             N0 
    -10 -10 => +10  
    +10 -10 => -10  
    -10 +10 => -10 
    +10 +10 => +10 

## Count
             N0 N1
    -10 -10 => -10 -10
    +10 -10 => +10 -10
    -10 +10 => -10 +10
    +10 +10 => +10 +10


