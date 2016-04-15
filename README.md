# API Foodie

Ce document sert de réference pour le fonctionnement de l'API Foodie.

Url de référence pour l'API : ``not_yet_defined``

## Fonctionnement

Les appels sont toujours au format ``JSON``.

Les retours sont toujours au format ``JSON``.

## Requêtes nécessitant une authentification

Toutes les requêtes à l'API nécessitant d'être authentifié devront comporter les paramètres ``uid``, ``nonce`` et ``sign`` dans l'url.

### uid

L'indentifiant unique de l'utilisateur (récupéré après une requête de login).

### nonce

Suite de 6 chiffres généré aléatoirement à chaque requête.
Le même nonce doit être utilisé pour le paramètre ``sign``, il permet d'éviter les attaques par rejeu.

### sign

Signature prouvant l'authenticité d'une requête. Elle est composée du type de requête (GET, POST, PUT, DELETE), de l'url appelée (sans paramètres), du nonce (identique au précèdent) et de la clé secrète de 30 caractères (récupérée après une requête de login).

Cette signature est sous la forme suivante : 

```
HMAC-SHA1( "HTTPVerb:URL:nonce", "API_KEY" )
```


# Sommaire

- [Fonctions utilisateur](#fonctions-utilisateur)
    - [X] [Connexion](#connexion)
    - [x] [Inscription](#inscription)
    - [X] [Informations](#informations)
    - [ ] [Mise à jour des informations](#mise-à-jour-des-informations)


- [Gestion des repas](#gestion-des-repas)
    - [ ] [Ajouter un repas](#ajouter-un-repas)
    - [ ] [Modifier un repas](#modifier-un-repas)
    - [ ] [Recherche de repas](#recherche-de-repas)
    - [ ] [Informations repas](#informations-repas)
    - [ ] [Liste des participants à un repas](#liste-des-participants-à-un-repas)
    - [ ] [Participer à un repas](#participer-à-un-repas)
    - [ ] [Annuler participation à un repas](#annuler-participation-à-un-repas)
    - [ ] [Approuver participation à un repas](#approuver-participation-à-un-repas)
    - [ ] [Révoquer participation à un repas](#révoquer-participation-à-un-repas)
    - [ ] [Annuler un repas](#annuler-un-repas)
    
    
- [Evaluations](#evaluations)
    - [ ] [Poster une évaluation](#poster-une-evaluation)
    - [ ] [Recherche évaluations repas](#recherche-evaluations-repas)
    - [ ] [Recherche évaluations membre](#recherche-evaluations-membre)


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
200     | Ok                        | {"uid":"5", "api_key":"qZmoyusC5eypJECWSrepQw6g"}
400     | Erreur dans les paramètres| {"error":"Bad parameter"}
401     | Echec d'authentification  | {"error":"Bad credentials"}

## Inscription

```
POST users/
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
GET users/__USER_ID__/
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

***Nécessite une authentification***

Tous les paramètres ne sont pas obligatoires, il est possible d'en modifier que quelques un ou même un seul.

```
PUT users/__USER_ID__/
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

