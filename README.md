# Foodie
Why we build foodie :  
Ease foodie’s life Ease the process of discovering the “hot” momentum, restaurants, dishes, the process of discovering the information about coupon, discounts, events, and the process of seat reservation, dishes ordering, … 

Ease people engagement :  
Ease the process of having a dinner party or dine together, share moments among friends and with family, and the process of exploring the people who have the same interest, … 

Ease QoS improvement :  
Ease the process for restaurant mgr to discover business trends in time, and the process to find out potential risk to downgrade the satisfaction of customer, …




# API Foodie

Ce document sert de réference pour le fonctionnement de l'API Foodie.

## Fonctionnement

Les retours sont toujours au format ``JSON``.


# Sommaire

- [Fonctions utilisateur](#fonctions-utilisateur)
    - [x] [Connexion](#connexion)
    - [x] [Inscription](#inscription)
    - [x] [Informations](#informations)
    - [x] [Mise à jour des informations](#mise-à-jour-des-informations)





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
    firstname:          "Prénom",
    lastname:           "Nom",
    bday:               "Date de naissance (YYYY-MM-DD)",
    adress:             "Adresse",
    city:               "Ville",
    zipcode:            "Code postal",
    bio:                "Bio",
    gender:             "Genre",
    phone:              "Numéro de téléphone",
    newsletter:         "Newsletter (0-1)",
    notification:       "Notifications (0-1)"
}
```

## Mise à jour des informations

Tous les paramètres ne sont pas obligatoires, il est possible d'en modifier que quelques un ou même un seul.

```
PUT users/__USER_ID__/
{
    firstname:          "Prénom",
    lastname:           "Nom",
    oldpassword:        "Ancien mot de passe",
    newpassword:        "Nouveau mot de passe",
    bday:               "Date de naissance (YYYY-MM-DD)",
    adress:             "Adresse",
    city:               "Ville",
    zipcode:            "Code postal (00000)",
    bio:                "Bio",
    gender:             "Genre (0 1 ou 2)",
    phone:              "Numéro de téléphone (0000000000)",
    newsletter:         "Newsletter (0 ou 1)",
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

