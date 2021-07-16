# Basics

Genetische Algorithmus für das Training.
1. Beweisen, dass dies funktioniert.

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
     AND: „*“
     OR: „+“
     NOT: „/“ or „~“
## Linear Neurons with input-limits and without reinforcement.
### IS (A)
           N0 
    -10  => -10  
    +10  => +10  

### NOT /(A)
           N0 
    -10  => +10  
    +10  => -10  

### AND (A * B)
             N0 
    -10 -10 => -10  
    +10 -10 =>   0  should better -10 ?
    -10 +10 =>   0  should better -10 ?
    +10 +10 => +10 

### NAND ~(A * B)
             N0 
    -10 -10 => +10  
    +10 -10 => +10   fail
    -10 +10 => +10   fail
    +10 +10 => -10 

### OR (A + B)
             N0 
    -10 -10 =>   0  
    +10 -10 =>   5  
    -10 +10 =>   5 
    +10 +10 => +10 

### NOR ~(A + B)
             N0 
    -10 -10 => +10 
    +10 -10 =>   5  
    -10 +10 =>   5 
    +10 +10 =>   0 

### XOR XOR(A, B)
    XOR(A, B) = (A + B) * /(A * B)

             N0 
    -10 -10 => -10
    +10 -10 => +10
    -10 +10 => +10
    +10 +10 => -10

### XNOR / XAND
             N0 
    -10 -10 => +10  
    +10 -10 => -10  
    -10 +10 => -10 
    +10 +10 => +10 

### Count
             N0 N1
    -10 -10 => -10 -10
    +10 -10 => +10 -10
    -10 +10 => -10 +10
    +10 +10 => +10 +10

## Func-Neuron.
### IS (A)
           N0 
    -10  => -10  
    +10  => +10  

### NOT /(A)
           N0 
    -10  => +10  
    +10  => -10  

### AND / MIN (A * B)
             N0 
    -10 -10 => -10 
    +10 -10 => -10 
    -10 +10 => -10
    +10 +10 => +10 

### NAND ~(A * B)
             N0 
    -10 -10 => +10  
    +10 -10 => +10   fail
    -10 +10 => +10   fail
    +10 +10 => -10 

### OR / MAX (A + B)
             N0 
    -10 -10 =>   0  
    +10 -10 => +10  
    -10 +10 => +10 
    +10 +10 => +10 

### NOR ~(A + B)
             N0 
    -10 -10 => +10 
    +10 -10 =>   5  
    -10 +10 =>   5 
    +10 +10 =>   0 

### XOR XOR(A, B)
    XOR(A, B) = (A + B) * /(A * B)

             N0 
    -10 -10 => -10
    +10 -10 => +10
    -10 +10 => +10
    +10 +10 => -10

### XNOR / XAND
             N0 
    -10 -10 => +10  
    +10 -10 => -10  
    -10 +10 => -10 
    +10 +10 => +10 

### Count
             N0 N1
    -10 -10 => -10 -10
    +10 -10 => +10 -10
    -10 +10 => -10 +10
    +10 +10 => +10 +10

# Links
* https://mixed.de/das-exklusiv-oder-warum-neuron-nicht-gleich-neuron-ist/
* https://medium.com/@lucaspereira0612/solving-xor-with-a-single-perceptron-34539f395182
* https://jaxenter.de/maschinelles-lernen-beispiel-neuroph-43725
* https://deeplearning4j.konduit.ai/getting-started/quickstart

