# Basics

Genetischer Algorithmus für das Training.
1. Beweisen, dass dies funktioniert.

Die Neuronen können verschiedene Typen von Verknüpfungsfunktionen haben (AND; OR; NAND; XOR, ...).
Da für diese beim lernen keine "einfachen" Anpassungen über Backpropagation implementierbar 
sind, wird das lernen über einen genetischen Algorithmus mit einfachen Zufallsänderungen
in den Verknüpfungen realisiert.

Es werden jeweils die besten Individuen mutiert und weiter verwendet.

Auch dieser Algorithmus kann in ein lokales Optimum laufen, aus dem er nicht
heraus findet.
Diese Situation soll erkannt werden und dann ein spezieller Modus verwendet werden,
um aus dem lokalen Optimum herauszufinden.
* Lösungsmöglichkeiten:
  * Zeitweises umdrehen der Bewertungsfunktion und die schlechtesten Individuen verwenden.

# ToDo

Wenn Anzahl Individuen mit gleicher Fitness ansteigt (Ausbrechen aus lokalem Optimum)
 - den Bereich in dem die Fittnes verglichen wird reduzieren (Berge reduzieren)
- sortieren mit Zufallszahl verknüpfen um sortierung durcheinander zu bringen.

Threshold-Types

Könnte durch einen Low-Level und einen High-Level-Threshold in einem abgebildet werden(?).
1. wenn Input höher als Threshold dann HIGH, sonst NULL
2. wenn Input höher als Threshold dann HIGH, sonst Input
3. wenn Input kleiner als Threshold dann NULL, sonst Input

   
# Definitionen

Ein **Individuum** existiert in einer **Population**.

In einer **Population** können **Individuen** verschiedener **Generationen** existieren.

Eine **Generation** entsteht durch Mutation aus **Individuen** einer Vorgängergeneration.

Ein **Individuum** reagiert auf **Reize** (**Input**) durch eine **Reaktion** (**Output**).
Seine **Eignung** (**Fitness**) wird dadurch gemessen, inwieweit seine Reaktion mit der erwarteten Reaktion auf einen bestimmten Reiz übereinstimmt.

Die Reaktionen eines Individuums auf Reize entsprechen/ bestimmen seinem Verhalten in einer **Umgebung** (**Environment**) und legen fest, wie gut er an diese Umgebung **angepasst** (**fit**) ist.

# Erweiterungen

1. Netz bekommt eine Sequenz als Eingabe.
Das Netz soll jeweils die nächste Eingabe erraten.
Beim Training speichert das Netz jeweils den vorangegangenen Input (oder mehrere)
und trainiert sich selber darauf, aus dem vorangegangenen Input den aktuellen Input korrekt 
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

