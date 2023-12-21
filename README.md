# Projet ACL
Bonjour et bienvenue sur la page du projet de 2A ISN en Algorithme et Conception de Logiciel. 
Groupe composé de : 
* Fouques Apolline, Atchekou Gbenongime Stanislas, Edy Donatien, Abdessadek Mostafa, Idrissa Conde
* Nom d'équipe : *Team Random*

## But du jeu
Le héros se déplace dans un labyrinthe et doit trouver une clef pour accéder à une porte qui mène au prochain niveau. Le héro répète ceci jusqu'à trouver la clef du coffre-trésor, sur son chemin, il doit éviter les monstres qui lui feront perdre de la vie ainsi que les flammes sur son chemin. Pour régénérer, le héro peut prendre des potions de vie qui augmenteront sa barre de vie.

## Organisation du dépôt
### Dossiers et fichiers disponibles
- le code source du jeu dans le dossier _labgame_
- les dossiers associés à chaque sprint nommés _sprint1_, _sprint2_ et _sprint3_
- un fichier _Conception.pdf_ avec les fonctionnalités finales du jeu, les diagrammes et l'organisation de chaque sprint
- un fichier _backlog_generel.txt_ où est présenté le backlog général du projet
- un diaporama de soutenance

### Versions du jeu disponibles
- un __tag V1__, __tag V2__ et __tag V3__ sont disponibles
- la version finale est celle du tag V3

## Suite d'instruction pour lancer le Jeu :
1. l'utilisateur doit avoir à la fois ***Git Bash*** et ***Maven*** installés sur leurs machines pour cloner le dépôt
2. aller dans le dossier projet-acl/labgame
3. entrer la ligne de commande suivante : mvn package
4. si le message affiché comporte *"BUILD SUCCESS"*, alors on peut poursuivre en entrant la ligne de commande suivante : mvn javafx:run
