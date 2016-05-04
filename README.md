# API Foodie

Ce document sert de réference pour le fonctionnement de l'API Foodie.

Url de référence pour l'API : `http://54.87.186.67:9000/`

## Fonctionnement

Les appels sont toujours au format ``JSON``.

Les retours sont toujours au format ``JSON``.

## Requêtes nécessitant une authentification

Penser à bien renvoyer le JWT (Json Web Token) fourni à la connection.


# Sommaire

- [Fonctions utilisateur](#fonctions-utilisateur)
    - [X] [Connexion](#connexion)
    - [x] [Inscription](#inscription)
    - [X] [Informations](#informations)
    - [X] [Mise à jour des informations](#mise-à-jour-des-informations)

- [Fonctions restaurant](#fonctions-restaurant)
    - [X] [Créer](#créer-restaurant)
    - [x] [Information restaurant](#information-restaurant)
    - [X] [Récupérer les restaurants par utilisateur](#récupérer-les-restaurants-par-utilisateur)

- [Moment](#moment)
    - [X] [Poster un moment](#poster-un-moment)
    - [X] [Récupérer les moments d'un utilisateur](#récupérer-moment-par-utilisateur) 

- [Gestion des repas](#gestion-des-repas)
    - [ ] [Ajouter un repas](#ajouter-un-repas)
    - [ ] [Modifier un repas](#modifier-un-repas)
    - [ ] [Recherche de repas](#recherche-de-repas)
    - [ ] [Informations repas](#informations-repas)
    - [ ] [Liste des participants à un repas](#liste-des-participants-à-un-repas)
    - [ ] [Participer à un repas](#participer-à-un-repas)
    - [ ] [Annuler participtions repas](#annuler-un-repas)




# Fonctions utilisateur

## Connexion

```
POST users/auth
{
    email:      "Adresse email",
    password:   "Mot de passe"
}
```
- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | {"uid":"5"}
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
401     | Echec d'authentification  | {"error":"Bad credentials"}

## Inscription

```
POST users
{
    email:      "Adresse email",
    password:   "Mot de passe"
}
```

- Status code

Valeur  | Description              | Retour Json
------- | -----------              | -----------
200     | Ok                       | "OK"
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
406     | Vérification formulaire  | {"champ ayant provoqué l'erreur": "erreur"}

## Informations

***Nécessite une authentification***

```
GET users/__USER_ID__/__JWT__
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | Voir ci-dessous
401     | Echec d'authentification  | {"error":"Bad credentials"}
404     | ID inconnue               | {"error":"Unknown ID"}

- Retour en cas de succès

```
data {
    firstName:          "Prénom",
    lastName:           "Nom",
    birthday:           "Date de naissance (YYYY-MM-DD)",
    adress:             "Adresse",
    city:               "Ville",
    zipcode:            "Code postal",
    bio:                "Bio",
    gender:             "Genre",
    phone:              "Numéro de téléphone",
    notification:       "Notifications (0-1)"
}
```

## Mise à jour des informations



***Nécessite une authentification***<br>
***Le JWT renvoyé remplace l'ancien***


```
PUT users/__USER_ID__/__JWT__
{
    firstName:          "Prénom",
    lastName:           "Nom",
    oldPassword:        "Ancien mot de passe",
    newPassword:        "Nouveau mot de passe",
    birthday:           "Date de naissance (YYYY-MM-DD)",
    adress:             "Adresse",
    city:               "Ville",
    zipcode:            "Code postal (00000)",
    bio:                "Bio",
    gender:             "Genre (1 ou 2)",
    phone:              "Numéro de téléphone (0000000000)",
    notification:       "Notifications (0 ou 1)"
}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "OK"
401     | Echec d'authentification  | {"error":"Bad credentials"}
403     | Droits insuffisants       | {"error":"Forbidden"}
406     | Verification formulaire   | {"champ ayant provoqué l'erreur": "erreur"}











# Fonctions restaurant

## Créer restaurant

***Nécessite une authentification***

```
POST restaurants/__JWT__
{
    "name": "nom restaurant",
    "adress": "adresse restaurant",
    "city": "ville restaurant",
    "description": "description restaurant",
    "places": "nombre de place (4)"   
}
```

- Status code

Valeur  | Description              | Retour Json
------- | -----------              | -----------
200     | Ok                       | "Rstaurant created"
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
406     | Vérification formulaire  | {"champ ayant provoqué l'erreur": "erreur"}


## Information restaurant


***Nécessite une authentification***

```
GET /restaurants/__RESTAURANTID__/__JWT__
```

- Status code

Valeur  | Description              | Retour Json
------- | -----------              | -----------
200     | Ok                       | voir ci-dessous
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
406     | Vérification formulaire  | {"champ ayant provoqué l'erreur": "erreur"}


- Retour en cas de succès

```
{
  "Restaurant": {
    "id": "5729e38bdf20371c262cb7e1",
    "userId": "570fd7cb3e9c3ab6b23f48f6",
    "name": "restaurant1",
    "adress": "adress1",
    "city": "city1",
    "description": "description1",
    "places": 2
  }
}
```


## Récupérer les restaurants par utilisateur

***Nécessite une authentification***

```
GET /restaurants/user/__USERID/__JWT__
```

- Status code

Valeur  | Description              | Retour Json
------- | -----------              | -----------
200     | Ok                       | voir ci-dessous
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
406     | Vérification formulaire  | {"champ ayant provoqué l'erreur": "erreur"}


- Retour en cas de succès

```
{
  "Restaurants": [
    {
      "id": "5729e38bdf20371c262cb7e1",
      "userId": "570fd7cb3e9c3ab6b23f48f6",
      "name": "restaurant1",
      "adress": "adress1",
      "city": "city1",
      "description": "description1",
      "places": 2
    },
    {
      "id": "5729e3c0df20371c262cb7e2",
      "userId": "570fd7cb3e9c3ab6b23f48f6",
      "name": "restaurant2",
      "adress": "adress1",
      "city": "city1",
      "description": "description1",
      "places": 4
    }
  ]
}
```















# Moment

## Poster un moment

***Nécessite une authentification***

```
POST moments/__JWT__
{
    content:     "contenu",
    location:    "lieu"
}
```
- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "Moment created"
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
401     | Echec d'authentification  | {"error":"Bad credentials"}

## Récupérer moment par utilisateur

***Nécessite une authentification***


```
GET moments/__USER_ID__/__JWT__
```

- Retour en cas de succès

```
{
  "Moments": [
    {
      "id": "57274c05212129f448c65d12",
      "userId": "572097a68f8d6997feab8c02",
      "content": "momentContent",
      "location": "momentLocation",
      "postedAt": "momentPostedAt"
    },
    {
      "id": "57274c26212129f448c65d15",
      "userId": "572097a68f8d6997feab8c02",
      "content": "2",
      "location": "2",
      "postedAt": "2"
    },
    {
      "id": "57274c2f212129f448c65d16",
      "userId": "572097a68f8d6997feab8c02",
      "content": "3",
      "location": "3",
      "postedAt": "3"
    }
    ...
  ]
}
```
















## Ajouter un repas

***Nécessite une authentification***

```
POST meal/
{
    title:          "Nom du repas",
    description:    "Description du repas",
    places:         "Nombre de places",
    price:          "Prix",
    start_date:     "Jour et heure du repas (YYYY-MM-DD HH:MM:SS)",
    duration:       "Durée du repas (en minutes)",
    city:           "City",
}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "OK"
401     | Echec d'authentification  | {"error":"Bad credentials"}
406     | Vérification formulaire   | {"champ ayant provoqué l'erreur": "erreur"}

## Modifier un repas

## Recherche de repas

Un appel GET à meal/ sans parmètres renverra tous les repas avec une limite de 20.

Il n'est pas indispensable de spécifier tout les paramètres pour la recherche.

```
GET meal/?w=__WORD__&c=__CITY__&pm=__PRICE_MAX__=&l=__LIMIT__
{
    __WORD__:           "Mot clé (cherche dans le titre du repas et dans la description)",
    __PRICE_MAX__:      "Prix maximum"
    __CITY__:           "Latitude spécifiée par l'utilisateur",
    __LIMIT__:          "Limite de résultat (défault 20)"
}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | Voir ci dessous
404     | No result                 | {"error":"No results found matching your criteria"}

- Retour en cas de succès

```
data [
    {
        restaurant_id   "Id du restaurant",
        title:          "Nom du repas",
        description:    "Description du repas",
        places:         "Nombre de places",
        places_free:    "Nombre de places restantes"
        price:          "Prix",
        start:          "Jour et heure du repas (YYYY-MM-DD HH:MM:SS)",
        duration:       "Durée du repas (en minutes)",
        status:         "Status du repas"
    },
    {
        restaurant_id   "Id du restaurant",
        title:          "Nom du repas",
        description:    "Description du repas",
        places:         "Nombre de places",
        places_free:    "Nombre de places restantes"
        price:          "Prix",
        start:          "Jour et heure du repas (YYYY-MM-DD HH:MM:SS)",
        duration:       "Durée du repas (en minutes)",
        status:         "Status du repas"
    }
    ...
]
```

## Informations repas

```
GET meal/__MEAL_ID__/
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | Voir ci-dessous
404     | ID inconnue               | {"error":"Unknown ID"}

- Retour en cas de succès

```
data {
    restaurant_id   "Id du restaurant",
    title:          "Nom du repas",
    description:    "Description du repas",
    places:         "Nombre de places",
    places_free:    "Nombre de places restantes"
    price:          "Prix",
    start:          "Jour et heure du repas (YYYY-MM-DD HH:MM:SS)",
    duration:       "Durée du repas (en minutes)",
    badge_smoke:    "Fumeur (0-1)",
    badge_animal:   "Animaux sur place (0-1)",
    badge_musique:  "Musique  (0-1)",
    badge_halal:    "Nourriture Halal (0-1)",
    badge_casher:   "Nourriture Casher (0-1)",
    status:         "Status du repas"
}
```

## Liste des participants à un repas

```
GET meal/participate/__MEAL_ID__
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | Voir ci-dessous
404     | ID repas inconnue         | {"error":"Unknown ID"}

- Retour en cas de succès

```
data {
    "USER_ID1": "Status participation",
    "USER_ID2": "Status participation",
    "USER_ID3": "Status participation",
    ...
}
```

## Participer à un repas

***Nécessite une authentification***

TODO

```
POST meal/participate/__MEAL_ID__
{

}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "OK"
401     | Echec d'authentification  | {"error": "Bad credentials"}
403     | Plus de places disponible | {"error": "Full"}
404     | ID repas inconnue         | {"error": "Unknown ID"}
404     | Date du repas dépassée    | {"error": "End"}
406     | Déjà inscrit au repas     | {"error": "Already registered"}


## Annuler un repas

***Nécessite une authentification***

```
DELETE meal/__MEAL_ID__
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "OK"
401     | Echec d'authentification  | {"error":"Bad credentials"}
403     | Droits insuffisants       | {"error":"Forbidden"}
404     | ID repas inconnue         | {"error":"Unknown ID"}
