
sources : https://github.com/ridane/project_training_api
<br>ce qu'il reste à faire est dans le fichier "conf/routes"

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
    - [X] [Gestion des amis](#gestion-des-amis)

- [Fonctions restaurant](#fonctions-restaurant)
    - [X] [Créer restaurant](#créer-restaurant)
    - [x] [Information restaurant](#information-restaurant)
    - [X] [Récupérer les restaurants par utilisateur](#récupérer-les-restaurants-par-utilisateur)
    - [X] [Récupérer tous les restaurants](#récupérer-tous-les-restaurants)
    - [x] [Modifier restaurant](#modifier-restaurant)

- [Moment](#moment)
    - [X] [Poster un moment](#poster-un-moment)
    - [X] [Effacer un moment](#effacer-un-moment)
    - [X] [Récupérer les moments d'un utilisateur](#récupérer-moment-par-utilisateur) 

- [Gestion des repas](#gestion-des-repas)
    - [X] [Ajouter un repas](#ajouter-un-repas)
    - [X] [Informations repas](#informations-repas)
    - [X] [Récupérer les repas par restaurant](#récupérer-les-repas-par-restaurant)
    - [X] [Modifier repas](#modifier-repas)
    - [X] [Participation à un repas](#participation-à-un-repas)




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
200     | Ok                        | "User added to friends" - "User removed from friends"
401     | Echec d'authentification  | {"error":"Bad credentials"}
403     | Droits insuffisants       | {"error":"Forbidden"}
406     | Verification formulaire   | {"champ ayant provoqué l'erreur": "erreur"}



## Gestion des amis

***Nécessite une authentification***

```
PUT users/__JWT__
{
    userId:          "userId à ajouter à la liste d'amis",
    friend:          (true - false)
}
```























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

## Récupérer tous les restaurants

***Nécessite une authentification***

```
GET /restaurants/__JWT__
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

## Modification restaurant


***Nécessite une authentification***

```
PUT /restaurants/__RESTAURANTUD__/__JWT__
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
  "ok": "Restaurant updated"
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


## Effacer un moment

***Nécessite une authentification***



```
GET /moments/__MOMENTID__/__JWT__
```

- Retour en cas de succès

```
{
  "ok": "Moment deleted"
}
```
















## Ajouter un repas

***Nécessite une authentification***

```
POST /meals/__JWT__
{
    "restaurantId": "5729e38bdf20371c262cb7e1",
    "title": "meal1",
    "description": "description1",
    "price": 20,      
    "city": "city1"
}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "Meal Created"
401     | Echec d'authentification  | {"error":"Bad credentials"}
406     | Vérification formulaire   | {"champ ayant provoqué l'erreur": "erreur"}


## Informations repas

***Nécessite une authentification***

```
GET /meals/__MEALID__/__JWT__
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
  "Meal": {
    "id": "5729ed63df20371c262cb7e4",
    "restaurantId": "5729e38bdf20371c262cb7e1",
    "title": "meal1",
    "description": "description1",
    "price": 20,
    "city": "city1",
    "active": true,
    "postedAt": "2016-05-04T20:38:59.733+08:00"
  }
}
```


## Récupérer les repas par restaurant

***Nécessite une authentification***

```
GET /meals/restaurant/:restaurantId/:jwt
```

- Status code

Valeur  | Description              | Retour Json
------- | -----------              | -----------
200     | Ok                       | voir ci-dessous
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
406     | Vérification formulaire  | {"champ ayant provoqué l'erreur": "erreur"}


- Retour en cas de succès

```
"Meals": [
    {
      "id": "573747bf6d4bb81e5a98dfba",
      "restaurantId": "5737477c6d4bb81e5a98dfb9",
      "title": "titleMeal2",
      "description": "description2",
      "price": 20,
      "city": "Lyon",
      "participants": [],
      "active": true,
      "postedAt": "2016-05-14T23:43:59.472+08:00"
    },
    {
      "id": "573747ca6d4bb81e5a98dfbb",
      "restaurantId": "5737477c6d4bb81e5a98dfb9",
      "title": "titleMeal1",
      "description": "description1",
      "price": 10,
      "city": "Lyon",
      "participants": [],
      "active": true,
      "postedAt": "2016-05-14T23:44:10.026+08:00"
    },
    {
      "id": "573747d46d4bb81e5a98dfbc",
      "restaurantId": "5737477c6d4bb81e5a98dfb9",
      "title": "titleMeal3",
      "description": "description3",
      "price": 30,
      "city": "Lyon",
      "participants": [],
      "active": true,
      "postedAt": "2016-05-14T23:44:20.266+08:00"
    }
  ]
}
```


## Modifier repas

***Nécessite une authentification***


```
PUT /meals/__MEALID__/__JWT__
{
    "title": "nouveau title",
    "description": "nouvelle description",
    "price": "nouveau prix (10)
    "active": (true - false)
}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "Meal updated"
401     | Echec d'authentification  | {"error":"Bad credentials"}
403     | Droits insuffisants       | {"error":"Forbidden"}
406     | Verification formulaire   | {"champ ayant provoqué l'erreur": "erreur"}



## Participation à un repas

***Nécessite une authentification***


```
PUT  /meals/participation/:jwt
{
    "mealId": "5731e7f087d464cdd0e9f8da",
    "participation": (true-false)
}
```

- Status code

Valeur  | Description               | Retour Json
------- | -----------               | -----------
200     | Ok                        | "User added to meal"  "User removed from meal"
401     | Echec d'authentification  | {"error":"Bad credentials"}
403     | Droits insuffisants       | {"error":"Forbidden"}
406     | Verification formulaire   | {"champ ayant provoqué l'erreur": "erreur"}
