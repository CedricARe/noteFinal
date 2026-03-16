# Explication de `NoteService.java`

Le fichier `NoteService.java` contient un service Spring (`@Service`) responsable du calcul de la note finale d'un candidat pour différentes matières en fonction de plusieurs règles et paramètres configurables en base de données.

## 1. Structure générale et Injections de dépendances
Le service utilise l'annotation `@Autowired` pour injecter deux repositories :
- `NoteRepository` : Pour récupérer les notes d'un candidat depuis la base de données.
- `ParametresRepository` : Pour récupérer les règles (paramètres) de calcul spécifiques à chaque matière.

## 2. Classe interne `FinalNoteResult`
Il s'agit d'une classe "Data Transfer Object" (DTO) utilisée pour stocker et renvoyer le résultat final du calcul pour une matière donnée.
Elle contient deux attributs :
- `matiere` : La matière concernée.
- `finalNote` : La note finale calculée, de type `Double`.

## 3. La méthode principale `calculateFinalNotes(Long candidatId)`

Cette méthode contient toute la logique métier pour traiter les notes d'un candidat. Voici son fonctionnement étape par étape :

### a. Récupération et regroupement des notes
```java
List<Note> notes = noteRepository.findByCandidatId(candidatId);
Map<Matiere, List<Note>> notesByMatiere = notes.stream()
        .collect(Collectors.groupingBy(Note::getMatiere));
```
Le service récupère d'abord toutes les notes du candidat, puis utilise l'API Stream de Java pour les regrouper par `Matiere` dans une `Map`. Ainsi, pour chaque matière, on a une liste des notes correspondantes.

### b. Traitement par matière
Ensuite, une boucle itère sur chaque entrée de la `Map` (donc sur chaque matière). Si la liste des notes d'une matière est vide, on passe à la suivante avec `continue`.

### c. Extraction de métriques métier de base
```java
double max = matiereNotes.stream().mapToDouble(Note::getNote).max().orElse(0.0);
double min = matiereNotes.stream().mapToDouble(Note::getNote).min().orElse(0.0);
double moyenne = matiereNotes.stream().mapToDouble(Note::getNote).average().orElse(0.0);
```
Le service calcule la note la plus élevée (`max`), la plus basse (`min`), et la moyenne classique (`moyenne`) des notes pour cette matière. Ces valeurs pourront être utilisées à la fin en tant que note finale si les conditions sont remplies.

### d. L'algorithme des sommes de différences
```java
double sommeDifferences = 0.0;
for (int i = 0; i < matiereNotes.size(); i++) {
    for (int j = i + 1; j < matiereNotes.size(); j++) {
        sommeDifferences += Math.abs(matiereNotes.get(i).getNote() - matiereNotes.get(j).getNote());
    }
}
```
C'est le cœur de l'analyse comportementale de ce service. Il calcule l'écart absolu entre **toutes les paires uniques de notes possibles** dans la matière. Si un étudiant a les notes [10, 12, 15], il calcule: `|10-12| + |10-15| + |12-15| = 2 + 5 + 3 = 10`.
Cette valeur de `sommeDifferences` sert d'indicateur de dispersion ou d'irrégularité dans les notes de l'étudiant pour cette matière.

### e. Recherche de la condition (Paramètre) la plus optimisée
Ensuite, le service récupère les paramètres ("règles") de la matière `List<Parametres> params = parametresRepository.findByMatiere(matiere);`.
Le code évalue chaque paramètre face à la `sommeDifferences` calculée plus haut:

```java
String op = p.getOperateur().getNom();
double seuil = p.getSeuil();
```
Il vérifie si l'opérateur (qui peut être `<`, `>`, `<=`, `>=`) fonctionne entre la `sommeDifferences` et le `seuil` défini dans le paramètre.

**Logique de sélection en cas de multiples correspondances :**
S'il y a plusieurs paramètres dont la condition est validée, le service garde **le meilleur paramètre** en cherchant celui dont le seuil est le plus proche de la `sommeDifferences` (`distance = Math.abs(sommeDifferences - seuil)`).
En cas d'égalité sur la distance, il privilégie le paramètre avec le seuil le plus bas.

### f. Établissement de la note finale
```java
if (bestParam != null) {
    String res = bestParam.getResolution().getNom();

    if (res.equalsIgnoreCase("plus petit"))
        finalValue = min;
    else if (res.equalsIgnoreCase("plus grand"))
        finalValue = max;
    else if (res.equalsIgnoreCase("moyenne"))
        finalValue = moyenne;
}
results.add(new FinalNoteResult(matiere, finalValue));
```
Une fois le *meilleur paramètre* défini pour cette matière, le système regarde le mode de `resolution` voulu. En fonction du libellé :
- `"plus petit"` : la note finale retenue sera la valeur `min`.
- `"plus grand"` : la note finale retenue sera la valeur `max`.
- `"moyenne"` : la note finale retenue sera la valeur `moyenne`.

Un objet `FinalNoteResult` est alors créé et ajouté à la liste des résultats renvoyée par la méthode.

## En résumé
Ce service n'effectue pas qu'un simple calcul de moyenne. Il analyse l'écart entre les différentes notes (la volatilité / dispersion des notes) pour déterminer quelles règles mathématiques appliquer (garder la meilleure note, la pire, ou faire la moyenne) afin de statuer sur le résultat final.
