# 1. Description de l'application
Le thème du projet est la simulation de colonies d’insectes sociaux et plus précisément celui de fourmilières, tel qu’illustré en figure 1. La notion d’intelligence collective constitut tout l'intérêt de cette thématique : chaque membre de la colonie n’obéit individuellement qu’à des règles comportementales très simples, mais il émerge de ces comportements simples une auto-organisation complexe et « intelligente » de la colonie, permettant typiquement la survie de la fourmilière dans notre cas.

Le but du projet est donc de créer une application simulant des colonies de fourmis en quête de nourriture. La figure 1 donne un exemple d’état possible dela simulation à produire.
Le modèle que nous proposons est simmplement constitué:
- **d'un environnement**
- **de fourmilières**
- **de fourmis**
- **des termites**

Les fourmis et l'environnemnet intéragissent:
- L'environnement contient les fourmilières auxquelles appartiennent les fourmis;   
	de plus, il génère périodiquement des sources de nourriture aléatoirement dispersés dans lesquelles les fourmis peuvent aller puiser.
- les fourmis collectent la nourriture pour la ramener à leur fourmilière et déposent dans l’environnement des traces de phéromone qui seront utilisées par leur congénères pour se déplacer ; ces traces vont leur permettre notamment de retrouver leur fourmilière plus facilement ou de privilégier les chemins allant vers de la nourriture ; la phéromone déposée s’évapore au cours du temps et peut finir par disparaître.

Deux types de fourmis seront simulées : les **ouvrières** et les **soldates**.  Les premières collectent la nourriture alors que les secondes ont pour rôle de défendre le territoire contre des prédateurs. Les prédateurs seront soit des termites, soit des fourmis de fourmilières concurrentes. Ils ont pour rôle principal de réguler les populations de fourmis dans la simulation.


Deux modes de déplacement sont possibles pour les animaux simulés (fourmis ou termites) :
- **le déplacement inertiel** : les animaux se déplacent au hasard en privilégiant les directions qui leur font face ; c’est ce mode de déplacement qu’utiliserontles termites ainsi que les fourmis en l’absence de phéromone ;
- **le déplacement sensoriel**: utilisé par les fourmis pour suivre les traces dephéromone déposées dans l’environnement.

L’environnement est modélisé comme un terrain rectangulaire en deux dimensions.Il est cependant géré de façon torique: les animaux dépassant les limites del’environnement par un côté réapparaîtront sur le côté opposé.

A titre d’exemple, on peut voir sur la figure 1 un environnement contenant des fourmilières (petits tas de terre), des fourmis ouvrières (en noir), soldates (en  rouge) et des termites (brunes bicolores). Les fourmis laissent sur le sol des traces de phéromone (en vert) qui s’évaporent au cours du temps (elles s’éclaircissent).Les petits tas vert foncé représentent de la nourriture dans laquelle les fourmispeuvent 
aller puiser.

> **Remarque** 
>
> Nous fournissons une interface graphique permettant de modifier les paramètres de la simulation  
> Plus de détails dans la [video de démonstration](https://www.youtube.com/watch?v=UWBoE0b2ERQ).
