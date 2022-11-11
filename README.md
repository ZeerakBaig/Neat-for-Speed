# NEAT for Speed
![alt text](https://github.com/ZeerakBaig/Neat-for-Speed/blob/master/Logo6.png)

NEAT for Speed, a simulation system modelling autonomous vehicle navigation control by neuroevolution of augmenting topologies inspired algorithm. The project demonstrates an exciting optimization technique by a method of evolving neural networks with a genetic algorithm. NEAT puts into practice the theory that it is best to begin evolution with small, simple networks and let them grow in complexity over generations. In this way, neural networks in NEAT progress in complexity exactly as organisms in nature have since the first cell. Finding extremely intricate and complicated neural networks is made possible by this process of continuous elaboration. The project simulates an environment where each vehicle is represented by a neural network. The topology of such vehicles becomes more and more complicated as the evolutionary process progresses through discrete steps. The key techniques applied for developing this algorithm include: 
- Tracking genes through historical markers to allow crossover among topologies
- Using speciation to preserve the innovations, and developing structures from simple initial structures
- Elitism to carry the best performing genomes into the next generation 

The evolutionary process begins with a simple perceptron-like feed-forward neural network representing a vehicle in the environment. As the process proceeds, the complexity of the network's structure may grow either by inserting a new neuron into a connection path or creating a new connection between 2 existing neurons. In every generation, the system simulates a vehicle that can achieve the highest fitness. From a user's point of view, the system allows configurable parameters for the NEAT algorithm, a customizable simulation environment using a mini drawing application built into the system itself, and an interactive dashboard to view the overall performance of the evolutionary process


## Tech Stack


[![AGPL License](https://img.shields.io/badge/Java-16.0-brightgreen)](http://www.gnu.org/licenses/agpl-3.0/)
![GraphStream](https://img.shields.io/badge/GraphStream-1.3-red)
