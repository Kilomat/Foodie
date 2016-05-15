<?php
/**
 * Created by PhpStorm.
 * User: aurelienschiltz
 * Date: 15/05/2016
 * Time: 07:01
 */
session_start();
$apiUrl = "http://54.87.186.67:9000/";

/* Pour utiliser les functions, le dernier paramètre sera toujours $data qui est un tableau comprenant les valeurs
à envoyer, les variables passées avant $data si elles existent seront les paramètres à mettre directement dans l'url
dans l'ordre affiché sur la documentation de l'API */

function auth($data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."users/auth");
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function createUser($data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."users");
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);
    return json_decode($result);
}

function getUser($userId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."users/".$userId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function updateUser($userId, $data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."users/".$userId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function createRestaurant($data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."restaurants/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function getRestaurant($restaurantId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."restaurants/".$restaurantId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function getRestaurantsByUser($userId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."restaurants/user/".$userId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function getRestaurants()
{
    global $apiUrl;
    $ch = curl_init($apiUrl."restaurants/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function updateRestaurant($restaurantId, $data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."restaurants/".$restaurantId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function createMoment($data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."moments/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function getMomentsByUser($userId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."moments/".$userId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function deleteMoment($momentId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."restaurants/".$momentId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function createMeal($data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."meals/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function getMeal($mealId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."meals/".$mealId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function getMealsByUser($userId)
{
    global $apiUrl;
    $ch = curl_init($apiUrl."meals/".$userId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_HTTPGET, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function updateMeal($mealId, $data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."meals/".$mealId."/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}

function mealParticipation($data)
{
    global $apiUrl;
    $post = json_encode($data);
    $ch = curl_init($apiUrl."meals/participation/".$_SESSION['token']);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
    curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    $result = curl_exec($ch);
    curl_close($ch);

    return json_decode($result);
}
?>
